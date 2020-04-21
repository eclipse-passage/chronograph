package org.eclipse.chronograph.api.brick.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.chronograph.api.test.TestInputObject;
import org.eclipse.chronograph.internal.api.Brick;
import org.eclipse.chronograph.internal.api.Group;
import org.eclipse.chronograph.internal.api.adapters.BrickAdapter;
import org.eclipse.chronograph.internal.base.BrickImpl;

public class TestBrickAdapter implements BrickAdapter<TestInputObject, Brick> {

	@Override
	public List<Brick> adapt(List<TestInputObject> inputObjects, Group container) {
		Map<TestInputObject, Brick> objectsToBricks = new HashMap<>();

		List<Brick> bricks = inputObjects.stream()
				.map(p -> objectsToBricks.computeIfAbsent(p, new Function<TestInputObject, Brick>() {
					@Override
					public Brick apply(TestInputObject t) {
						Brick brick = new BrickImpl(container, t.id, t.start, t.end);
						return brick;
					}
				})).collect(Collectors.toList());
		return bricks;
	}

}
