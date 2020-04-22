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
