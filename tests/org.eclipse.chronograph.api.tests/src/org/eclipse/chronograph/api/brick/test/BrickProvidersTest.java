package org.eclipse.chronograph.api.brick.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chronograph.api.test.TestInputObject;
import org.eclipse.chronograph.api.test.TestInputObjects;
import org.eclipse.chronograph.internal.api.Brick;
import org.eclipse.chronograph.internal.api.Group;
import org.eclipse.chronograph.internal.api.Section;
import org.eclipse.chronograph.internal.api.adapters.BrickAdapter;
import org.eclipse.chronograph.internal.api.builders.BrickBuilder;
import org.eclipse.chronograph.internal.base.GroupImpl;
import org.eclipse.chronograph.internal.base.SectionImpl;
import org.eclipse.chronograph.internal.providers.BrickContentProviderImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * Tests for {@ BrickContentProvider} used test implementation of
 * {@link BrickAdapter} {@link BrickBuilder}
 *
 */
public class BrickProvidersTest {
	private List<TestInputObject> input = new ArrayList<>();
	private BrickBuilder<List<TestInputObject>, TestInputObject> testBrickBuilder;
	private BrickAdapter<TestInputObject, Brick> testBrickAdapter;
	private BrickContentProviderImpl<TestInputObject, TestInputObject> testBrickProvider;

	@Before
	public void prepareModel() {
		input = TestInputObjects.getInput();
		testBrickBuilder = new TestBrickBuilder();
		testBrickAdapter = new TestBrickAdapter();
		testBrickProvider = new BrickContentProviderImpl<TestInputObject, TestInputObject>(testBrickBuilder,
				testBrickAdapter);
	}

	@Test
	public void brickProvidersNotNullTest() {
		assertNotNull(testBrickBuilder);
		assertNotNull(testBrickAdapter);
		assertNotNull(testBrickProvider);
	}

	@Test
	public void getBricksForInputTest() {
		List<Brick> bricks = testBrickProvider.getBricks(input);
		assertNotNull(bricks);
	}

	@Test
	public void getBricksForParametrizedInputPositiveTest() {
		Section section = new SectionImpl("test.parent.id.1");
		Group group = new GroupImpl("test.container.id.1", section);
		Group subGroup = new GroupImpl("test.subcontainer.id.1", group);

		List<Brick> bricks = testBrickProvider.getBricksByGroup(input, subGroup);
		assertNotNull(bricks);

		bricks = testBrickProvider.getBricksBySubGroup(input, group.id(), subGroup);
		assertNotNull(bricks);
	}

	@Test
	public void getBricksForParametrizedInputNegativeTest() {
		Section section = new SectionImpl("test.parent.id.non");
		Group group = new GroupImpl("test.container.id.non", section);
		Group subGroup = new GroupImpl("test.subcontainer.id.non", group);

		List<Brick> bricks = testBrickProvider.getBricksByGroup(input, subGroup);
		assertTrue(bricks.isEmpty());

		bricks = testBrickProvider.getBricksBySubGroup(input, group.id(), subGroup);
		assertTrue(bricks.isEmpty());
	}

}
