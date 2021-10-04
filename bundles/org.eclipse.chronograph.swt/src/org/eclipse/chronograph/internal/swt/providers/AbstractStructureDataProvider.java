package org.eclipse.chronograph.internal.swt.providers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.chronograph.internal.api.data.StructureDataProvider;
import org.eclipse.chronograph.internal.api.graphics.Brick;
import org.eclipse.chronograph.internal.api.graphics.Group;
import org.eclipse.chronograph.internal.api.graphics.Position;
import org.eclipse.chronograph.internal.base.BrickImpl;
import org.eclipse.chronograph.internal.base.GroupImpl;

public abstract class AbstractStructureDataProvider implements StructureDataProvider {
	private List<Group> rootGroups = new ArrayList<>();
	private Map<Group, List<Group>> group2groups = new HashMap<>();
	private Map<Group, List<Brick>> group2elemnt = new HashMap<>();

	@Override
	public void restructure(List<Object> input) {
		rootGroups = getRoots(input).stream() //
				.map(getGroupByObject()) //
				.collect(Collectors.toList());

		// created groups by sections
		structureGroupByGroup(rootGroups);

		// if section as root does not have groups let's find elements
		for (Entry<Group, List<Group>> entry : group2groups.entrySet()) {
			if (entry.getValue() == null || entry.getValue().isEmpty()) {
				// groups does not defined for root let's find elements for it
				Group key = entry.getKey();
				group2elemnt.put(key, structureElementsByGroup(key));
			} else {
				entry.getValue().stream().forEach(c -> {
					group2elemnt.put(c, structureElementsByGroup(c));
				});
			}
		}
	}

	private void structureGroupByGroup(List<Group> groupElements) {
		groupElements.stream().forEach(p -> {
			List<Object> childs = getGroups(p.data());
			if (!childs.isEmpty()) {
				List<Group> groups = childs.stream() //
						.map(getGroupByObject()) //
						.collect(Collectors.toList());
				group2groups.put(p, groups);
				structureGroupByGroup(groups);
			}
		});
	}

	public List<Brick> structureElementsByGroup(Group key) {
		return getElements(key.data()).stream() //
				.map(getBrickByObject())//
				.collect(Collectors.toList());
	}

	@Override
	public List<Brick> getElementsByGroup(Group key) {
		return group2elemnt.computeIfAbsent(key, new Function<Group, List<Brick>>() {
			@Override
			public List<Brick> apply(Group t) {
				return Collections.emptyList();
			}
		});
//		return getElements(key.data()).stream() //
//				.map(getBrickByObject())//
//				.collect(Collectors.toList());
	}

	@Override
	public List<Group> getChildGroups(Group obj) {
		return group2groups.computeIfAbsent(obj, new Function<Group, List<Group>>() {
			@Override
			public List<Group> apply(Group t) {
				return Collections.emptyList();
			};
		});
	}

	@Override
	public Optional<Position> getMaxBrickPosition() {
		return group2elemnt.values().stream() //
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

	@Override
	public List<Group> groups() {
		return rootGroups;
	}

	protected Function<Object, Group> getGroupByObject() {
		return new Function<Object, Group>() {
			@Override
			public Group apply(Object t) {
				return new GroupImpl(t);
			}
		};
	}

	private Function<Object, Brick> getBrickByObject() {
		return new Function<Object, Brick>() {
			@Override
			public Brick apply(Object t) {
				return new BrickImpl(t);
			}
		};
	}
}
