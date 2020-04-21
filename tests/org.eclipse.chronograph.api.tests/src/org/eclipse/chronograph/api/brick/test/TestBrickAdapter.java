/*******************************************************************************
 * Copyright (c) 2020 ArSysOp and Others
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 *
 * Contributors:
 *     Sergei Kovalchuk<sergei.kovalchuk@arsysop.ru> - initial API and implementation
 *******************************************************************************/
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
