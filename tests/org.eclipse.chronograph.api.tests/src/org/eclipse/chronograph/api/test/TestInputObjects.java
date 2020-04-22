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
