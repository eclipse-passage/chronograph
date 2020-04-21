package org.eclipse.chronograph.api.test;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Test input faced
 *
 */
public class TestInputObjects {
	private static List<TestInputObject> input = new ArrayList<>();
	static {
		input.add(new TestInputObject("test.id.1", "test.container.id.1", "test.subcontainer.id.1", "test.parent.id.1",
				"test.name.1", "test.description.1", 1, 2));
		input.add(new TestInputObject("test.id.2", "test.container.id.2", "test.subcontainer.id.2", "test.parent.id.2",
				"test.name.2", "test.description.2", 2, 3));
		input.add(new TestInputObject("test.id.3", "test.container.id.3", "test.subcontainer.id.3", "test.parent.id.3",
				"test.name.3", "test.description.3", 3, 4));
	}

	public static List<TestInputObject> getInput() {
		return input;
	}

}
