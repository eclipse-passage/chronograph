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

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.chronograph.api.test.TestInputObject;
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