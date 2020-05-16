/*******************************************************************************
 *	Copyright (c) 2020 ArSysOp
 *
 *	This program and the accompanying materials are made available under the
 *	terms of the Eclipse Public License 2.0 which is available at
 *	http://www.eclipse.org/legal/epl-2.0.
 *
 *	SPDX-License-Identifier: EPL-2.0
 *
 *	Contributors:
 *	Sergei Kovalchuk <sergei.kovalchuk@arsysop.ru> - 
 *												initial API and implementation
 *******************************************************************************/

package org.eclipse.chronograph.internal.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.chronograph.internal.api.Brick;
import org.eclipse.chronograph.internal.api.Group;
import org.eclipse.chronograph.internal.api.Section;
import org.eclipse.chronograph.internal.api.Storage;
import org.eclipse.chronograph.internal.api.data.Access;
import org.eclipse.chronograph.internal.api.providers.BrickContentProvider;
import org.eclipse.chronograph.internal.api.providers.ContainerProvider;
import org.eclipse.chronograph.internal.api.providers.GroupContentProvider;
import org.eclipse.chronograph.internal.api.providers.SectionContentProvider;
import org.eclipse.chronograph.internal.api.providers.SubGroupContentProvider;

/**
 * Class intended to aggregate data
 *
 */
public class PlainData<I> implements Storage {

	private final Access<I> access;
	private final List<Class<?>> structure;
	private final ContainerProvider<I> provider;
	private final Map<String, Section> sectionsById = new HashMap<>();
	private final Map<String, List<Group>> groupsBySection = new HashMap<>();
	private final Map<Group, List<Group>> subGroupsBygroup = new HashMap<>();
	private final Map<Group, List<Brick>> bricksBySubgroup = new HashMap<>();

	public PlainData(Access<I> access, ContainerProvider<I> provider) {
		this.access = access;
		this.structure = new ArrayList<>();
		this.provider = provider;
		// FIXME: wrong place, data is not yet needed
		createRegistry();
	}

	private void createRegistry() {
		SectionContentProvider<I> sectionProvider = provider.getSectionContentProvider();
		GroupContentProvider<I> groupProvider = provider.getGroupContentProvider();
		SubGroupContentProvider<I> subgroupProvider = provider.getSubGroupContentProvider();
		BrickContentProvider<I> brickProvider = provider.getBrickContentProvider();
		List<I> input = provider.getInput();
		List<Section> sections = sectionProvider.getSections(input);
		for (Section section : sections) {
			sectionsById.put(section.id(), section);
			List<Group> groups = groupProvider.getGroupsBySection(input, section);
			groupsBySection.put(section.id(), groups);
			for (Group group : groups) {
				List<Group> subGroups = subgroupProvider.getGroupsBySectionGroup(input, section, group);
				subGroupsBygroup.put(group, subGroups);
				for (Group subGroup : subGroups) {
					List<Brick> bricks = brickProvider.getBricksBySubGroup(input, group.id(), subGroup);
					bricksBySubgroup.put(subGroup, bricks);
				}
			}
		}
	}

	public List<Section> getSections() {
		return new ArrayList<Section>(sectionsById.values());
	}

	public List<Group> getGroupBySection(Section section) {
		return groupsBySection.getOrDefault(section.id(), Collections.emptyList());
	}

	public List<Group> getSubGroupByGroupSection(Group group) {
		return subGroupsBygroup.getOrDefault(group, Collections.emptyList());
	}

	public List<Brick> getBrickBySubgroup(String subgroupId, String groupId, String sectionId) {
		if (!sectionId.isEmpty()) {
			Section section = sectionsById.get(sectionId);
			List<Group> groups = groupsBySection.get(section.id());
			for (Group group : groups) {
				if (group.id().equals(groupId)) {
					List<Group> subGroups = subGroupsBygroup.get(group);
					for (Group subGroup : subGroups) {
						return bricksBySubgroup.get(subGroup);
					}
				}
			}
		}
		return new ArrayList<>();
	}

	@Override
	public List<Brick> query(Predicate<Brick> predicate) {
		return bricksBySubgroup.values().stream() //
				.flatMap(List::stream) //
				.filter(predicate) //
				.collect(Collectors.toList());
	}

	public void restructure(List<Class<?>> types) {
		clear();
		if (types.size() < 3) {
			// FIXME: we should be more flexible and the code below can really be
			// generalized
			return;
		}
		structure.addAll(types);
		Predicate<I> filter = (Predicate<I>) t -> true; // FIXME: support filters
		List<I> input = access.input().apply(filter);
		@SuppressWarnings("unchecked")
		Class<Object> type0 = (Class<Object>) types.get(0);
		@SuppressWarnings("unchecked")
		Class<Object> type1 = (Class<Object>) types.get(1);
		@SuppressWarnings("unchecked")
		Class<Object> type2 = (Class<Object>) types.get(2);
		Map<String, List<I>> grouping0 = input.stream().collect(Collectors.groupingBy(access.grouping(type0)));
		Map<String, List<I>> grouping1 = input.stream().collect(Collectors.groupingBy(access.grouping(type1)));
		Map<String, List<I>> grouping2 = input.stream().collect(Collectors.groupingBy(access.grouping(type2)));
		List<Section> sections = input.stream().map(access.map(type0))//
				.filter(Optional::isPresent)//
				.map(Optional::get)//
				.distinct()//
				.map(access.identification(type0))//
				.map(id -> new SectionImpl(id))//
				.collect(Collectors.toList());
		for (Section section : sections) {
			List<I> g0 = grouping0.getOrDefault(section.id(), Collections.emptyList());
			sectionsById.put(section.id(), section);
			List<Group> groups = input.stream().map(access.map(type1))//
					.filter(Optional::isPresent)//
					.map(Optional::get)//
					.distinct()//
					.map(access.identification(type1))//
					.map(id -> new GroupImpl(id, section))//
					.collect(Collectors.toList());
			groupsBySection.put(section.id(), groups);
			for (Group group : groups) {
				String id1 = group.id();
				List<I> g1 = grouping1.getOrDefault(id1, Collections.emptyList());
				List<Group> subGroups = input.stream().map(access.map(type2))//
						.filter(Optional::isPresent)//
						.map(Optional::get)//
						.distinct()//
						.map(access.identification(type2))//
						.map(id -> new GroupImpl(id, group))//
						.collect(Collectors.toList());
				subGroupsBygroup.put(group, subGroups);
				for (Group subGroup : subGroups) {
					List<Brick> bricks = grouping2.getOrDefault(subGroup.id(), Collections.emptyList()).stream()//
							.filter(g0::contains)//
							.filter(g1::contains)//
							.map(i -> new BrickImpl(access.identification(access.type()).apply(i),
									access.start().apply(i), access.end().apply(i)))//
							.collect(Collectors.toList());
					bricksBySubgroup.put(subGroup, bricks);
				}
			}
		}
	}

	public void clear() {
		structure.clear();
		bricksBySubgroup.clear();
		groupsBySection.clear();
		sectionsById.clear();
		subGroupsBygroup.clear();
	}

	public List<Class<?>> structure() {
		return new ArrayList<Class<?>>(structure);
	}
}
