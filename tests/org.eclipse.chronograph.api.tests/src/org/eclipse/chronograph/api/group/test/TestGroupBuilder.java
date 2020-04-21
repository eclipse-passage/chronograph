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
