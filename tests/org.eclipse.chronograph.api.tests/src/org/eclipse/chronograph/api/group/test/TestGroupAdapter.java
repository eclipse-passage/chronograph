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
