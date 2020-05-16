/*******************************************************************************
 * Copyright (c) 2020 ArSysOp
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0/.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Sergei Kovalchuk <sergei.kovalchuk@arsysop.ru> - initial API and implementation
 *******************************************************************************/
package org.eclipse.chronograph.api.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.chronograph.internal.api.Brick;
import org.eclipse.chronograph.internal.base.BrickImpl;

public class TestBrickAdapter implements Function<List<TestInputObject>, List<Brick>> {

	@Override
	public List<Brick> apply(List<TestInputObject> inputObjects) {
		Map<TestInputObject, Brick> objectsToBricks = new HashMap<>();

		List<Brick> bricks = inputObjects.stream()
				.map(p -> objectsToBricks.computeIfAbsent(p, new Function<TestInputObject, Brick>() {
					@Override
					public Brick apply(TestInputObject t) {
						Brick brick = new BrickImpl(t.id, t.start, t.end);
						return brick;
					}
				})).collect(Collectors.toList());
		return bricks;
	}

}
