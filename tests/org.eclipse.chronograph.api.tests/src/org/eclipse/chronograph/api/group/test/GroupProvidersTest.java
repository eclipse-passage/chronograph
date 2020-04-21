package org.eclipse.chronograph.api.group.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chronograph.api.test.TestInputObject;
import org.eclipse.chronograph.api.test.TestInputObjects;
import org.eclipse.chronograph.internal.api.Group;
import org.eclipse.chronograph.internal.api.Section;
import org.eclipse.chronograph.internal.api.adapters.GroupAdapter;
import org.eclipse.chronograph.internal.api.builders.GroupBuilder;
import org.eclipse.chronograph.internal.api.providers.GroupContentProvider;
import org.eclipse.chronograph.internal.base.SectionImpl;
import org.eclipse.chronograph.internal.providers.GroupContentProviderImpl;
import org.junit.Before;
import org.junit.Test;

public class GroupProvidersTest {
	private List<TestInputObject> input = new ArrayList<>();
	GroupBuilder<List<TestInputObject>, TestInputObject> groupBuilder;
	GroupAdapter<TestInputObject, Group> groupAdapter;
	GroupContentProvider<TestInputObject> groupProvider;

	@Before
	public void prepareModel() {
		input = TestInputObjects.getInput();
		groupBuilder = new TestGroupBuilder();
		groupAdapter = new TestGroupAdapter();
		groupProvider = new GroupContentProviderImpl<TestInputObject, TestInputObject>(groupBuilder, groupAdapter);
	}

	@Test
	public void groupProviderTest() {
		Section section = new SectionImpl("test.parent.id.1");
		List<Group> groupsBySection = groupProvider.getGroupsBySection(input, section);
		assertNotNull(groupsBySection);
		assertFalse(groupsBySection.isEmpty());
	}
}
