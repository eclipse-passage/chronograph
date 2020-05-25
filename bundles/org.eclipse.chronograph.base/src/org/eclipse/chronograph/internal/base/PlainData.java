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
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.chronograph.internal.api.data.Resolution;
import org.eclipse.chronograph.internal.api.graphics.Brick;
import org.eclipse.chronograph.internal.api.graphics.Group;
import org.eclipse.chronograph.internal.api.graphics.Position;

/**
 * Class intended to aggregate data
 *
 */
public class PlainData<D> {

	private final Resolution<D> access;
	private final List<Class<?>> structure;
	private final Map<String, Group> sectionsById = new HashMap<>();
	private final Map<String, List<Group>> groupsBySection = new HashMap<>();
	private final Map<Group, List<Group>> subGroupsBygroup = new HashMap<>();
	private final Map<Group, List<Brick<D>>> bricksBySubgroup = new HashMap<>();

	public PlainData(Resolution<D> access) {
		this.access = access;
		this.structure = new ArrayList<>();
	}

	public List<Group> groups() {
		return new ArrayList<>(sectionsById.values());
	}

	public List<Group> subGroups(Group section) {
		return groupsBySection.getOrDefault(section.id(), Collections.emptyList());
	}

	public List<Group> getSubGroupByGroupSection(Group group) {
		return subGroupsBygroup.getOrDefault(group, Collections.emptyList());
	}

	// FIXME: most probably does not work as expected
	public List<Brick<D>> getBrickBySubgroup(String subgroupId, String groupId, String sectionId) {
		if (!sectionId.isEmpty()) {
			Group section = sectionsById.get(sectionId);
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

	public List<Brick<D>> query(Predicate<Brick<D>> predicate) {
		return bricksBySubgroup.values().stream() //
				.flatMap(List::stream) //
				.filter(predicate) //
				.collect(Collectors.toList());
	}

	public Optional<Position> getMaxBrickPosition() {
		return bricksBySubgroup.values().stream() //
				.flatMap(List::stream) //
				.map(Brick::position) //
				.max(new Comparator<Position>() {
					@Override
					public int compare(Position o1, Position o2) {
						if (o1.end() > o2.end()) {
							return 1;
						}
						return 0;
					}
				});
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
		String id00 = ""; //$NON-NLS-1$
		List<Group> sections = input.stream().map(access.adapt(type0))//
				.filter(Optional::isPresent)//
				.map(Optional::get)//
				.distinct()//
				.map(d -> new GroupImpl(access.identification(type0).apply(d), id00, 0, d))//
				.collect(Collectors.toList());
		for (Group group0 : sections) {
			String id0 = group0.id();
			List<D> g0 = grouping0.getOrDefault(group0.id(), Collections.emptyList());
			sectionsById.put(id0, group0);
			List<Group> groups1 = input.stream().map(access.adapt(type1))//
					.filter(Optional::isPresent)//
					.map(Optional::get)//
					.distinct()//
					.map(d -> new GroupImpl(access.identification(type1).apply(d), id0, 1, d))//
					.collect(Collectors.toList());
			groupsBySection.put(group0.id(), groups1);
			for (Group group1 : groups1) {
				String id1 = group1.id();
				List<D> g1 = grouping1.getOrDefault(id1, Collections.emptyList());
				List<Group> groups2 = input.stream().map(access.adapt(type2))//
						.filter(Optional::isPresent)//
						.map(Optional::get)//
						.distinct()//
						.map(d -> new GroupImpl(access.identification(type2).apply(d), id1, 2, d))//
						.collect(Collectors.toList());
				subGroupsBygroup.put(group1, groups2);
				for (Group group2 : groups2) {
					List<Brick<D>> bricks = grouping2.getOrDefault(group2.id(), Collections.emptyList()).stream()//
							.filter(g0::contains)//
							.filter(g1::contains)//
							.map(i -> new BrickImpl<>(access.identification(access.type()).apply(i),
									access.start().apply(i), access.end().apply(i), i))//
							.collect(Collectors.toList());
					bricksBySubgroup.put(group2, bricks);
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
