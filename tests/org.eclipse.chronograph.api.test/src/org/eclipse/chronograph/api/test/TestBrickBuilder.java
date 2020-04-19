package org.eclipse.chronograph.api.test;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.chronograph.internal.api.builders.BrickBuilder;

public class TestBrickBuilder implements BrickBuilder<List<TestInputObject>, TestInputObject> {

	@Override
	public List<TestInputObject> apply(List<TestInputObject> t) {
		return t;
	}

	@Override
	public List<TestInputObject> applyGroup(List<TestInputObject> input, String groupId) {
		return input.stream().filter(p -> p.containerId.equals(groupId)).collect(Collectors.toList());

	}

	@Override
	public List<TestInputObject> applySubGroup(List<TestInputObject> input, String groupId, String subGroupId) {
		return input.stream().filter(p -> p.containerId.equals(groupId)) //
				.filter(p -> p.rootContainerId.equals(subGroupId)) //
				.collect(Collectors.toList());
	}

}