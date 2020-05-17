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

import org.eclipse.chronograph.internal.api.data.Access;
import org.eclipse.chronograph.internal.api.graphics.Brick;
import org.eclipse.chronograph.internal.api.graphics.Group;
import org.eclipse.chronograph.internal.api.graphics.Section;
import org.eclipse.chronograph.internal.api.graphics.Storage;

/**
 * Class intended to aggregate data
 *
 */
public class PlainData<D> implements Storage<D> {

	private final Access<D> access;
	private final List<Class<?>> structure;
	private final Map<String, Section> sectionsById = new HashMap<>();
	private final Map<String, List<Group>> groupsBySection = new HashMap<>();
	private final Map<Group, List<Group>> subGroupsBygroup = new HashMap<>();
	private final Map<Group, List<Brick<D>>> bricksBySubgroup = new HashMap<>();

	public PlainData(Access<D> access) {
		this.access = access;
		this.structure = new ArrayList<>();
	}

	public List<Section> getSections() {
		return new ArrayList<>(sectionsById.values());
	}

	public List<Group> getGroupBySection(Section section) {
		return groupsBySection.getOrDefault(section.id(), Collections.emptyList());
	}

	public List<Group> getSubGroupByGroupSection(Group group) {
		return subGroupsBygroup.getOrDefault(group, Collections.emptyList());
	}

	public List<Brick<D>> getBrickBySubgroup(String subgroupId, String groupId, String sectionId) {
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
	public List<Brick<D>> query(Predicate<Brick<D>> predicate) {
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
		Predicate<D> filter = (Predicate<D>) t -> true; // FIXME: support filters
		List<D> input = access.input().apply(filter);
		@SuppressWarnings("unchecked")
		Class<Object> type0 = (Class<Object>) types.get(0);
		@SuppressWarnings("unchecked")
		Class<Object> type1 = (Class<Object>) types.get(1);
		@SuppressWarnings("unchecked")
		Class<Object> type2 = (Class<Object>) types.get(2);
		Map<String, List<D>> grouping0 = input.stream().collect(Collectors.groupingBy(access.grouping(type0)));
		Map<String, List<D>> grouping1 = input.stream().collect(Collectors.groupingBy(access.grouping(type1)));
		Map<String, List<D>> grouping2 = input.stream().collect(Collectors.groupingBy(access.grouping(type2)));
		List<Section> sections = input.stream().map(access.adapt(type0))//
				.filter(Optional::isPresent)//
				.map(Optional::get)//
				.distinct()//
				.map(access.identification(type0))//
				.map(id -> new SectionImpl(id))//
				.collect(Collectors.toList());
		for (Section section : sections) {
			String id0 = section.id();
			List<D> g0 = grouping0.getOrDefault(section.id(), Collections.emptyList());
			sectionsById.put(id0, section);
			List<Group> groups = input.stream().map(access.adapt(type1))//
					.filter(Optional::isPresent)//
					.map(Optional::get)//
					.distinct()//
					.map(access.identification(type1))//
					.map(id -> new GroupImpl(id, id0))//
					.collect(Collectors.toList());
			groupsBySection.put(section.id(), groups);
			for (Group group : groups) {
				String id1 = group.id();
				List<D> g1 = grouping1.getOrDefault(id1, Collections.emptyList());
				List<Group> subGroups = input.stream().map(access.adapt(type2))//
						.filter(Optional::isPresent)//
						.map(Optional::get)//
						.distinct()//
						.map(access.identification(type2))//
						.map(id -> new GroupImpl(id, id1))//
						.collect(Collectors.toList());
				subGroupsBygroup.put(group, subGroups);
				for (Group subGroup : subGroups) {
					List<Brick<D>> bricks = grouping2.getOrDefault(subGroup.id(), Collections.emptyList()).stream()//
							.filter(g0::contains)//
							.filter(g1::contains)//
							.map(i -> new BrickImpl<>(access.identification(access.type()).apply(i),
									access.start().apply(i), access.end().apply(i), i))//
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
