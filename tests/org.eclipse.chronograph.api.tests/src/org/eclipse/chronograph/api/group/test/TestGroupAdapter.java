package org.eclipse.chronograph.api.group.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.chronograph.api.test.TestInputObject;
import org.eclipse.chronograph.internal.api.Group;
import org.eclipse.chronograph.internal.api.Section;
import org.eclipse.chronograph.internal.api.adapters.GroupAdapter;
import org.eclipse.chronograph.internal.base.GroupImpl;

public class TestGroupAdapter implements GroupAdapter<TestInputObject, Group> {

	@Override
	public List<Group> adapt(List<TestInputObject> inputObjects, Section container) {
		Map<TestInputObject, Group> objectToGroup = new HashMap<>();

		return inputObjects.stream().map(p -> objectToGroup.computeIfAbsent(p, new Function<TestInputObject, Group>() {
			public Group apply(TestInputObject t) {
				return new GroupImpl(t.containerId, container);
			}

		})).collect(Collectors.toList());

	}

}
