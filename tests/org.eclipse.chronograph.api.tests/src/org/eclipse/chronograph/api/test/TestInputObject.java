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

/**
 * 
 * Test input object implementation
 *
 */
public class TestInputObject {

	public String id;
	public String name;
	public String description;
	public String subContainerId;
	public String containerId;
	public String rootContainerId;
	public int start;
	public int end;

	public TestInputObject(String id, String containerId, String subContainerId, String rootContainerId, String name,
			String description, int start, int end) {
		super();
		this.id = id;
		this.containerId = containerId;
		this.subContainerId = subContainerId;
		this.rootContainerId = rootContainerId;
		this.name = name;
		this.description = description;
		this.start = start;
		this.end = end;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
