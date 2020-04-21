package org.eclipse.chronograph.api.group.test;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.chronograph.api.test.TestInputObject;
import org.eclipse.chronograph.internal.api.builders.GroupBuilder;

public class TestGroupBuilder implements GroupBuilder<List<TestInputObject>, TestInputObject> {

	@Override
	public List<TestInputObject> apply(List<TestInputObject> t) {
		return t;
	}

	@Override
	public List<TestInputObject> applySection(List<TestInputObject> input, String sectionId) {
		return input.stream().filter(p -> p.rootContainerId.equals(sectionId)).collect(Collectors.toList());
	}

}
