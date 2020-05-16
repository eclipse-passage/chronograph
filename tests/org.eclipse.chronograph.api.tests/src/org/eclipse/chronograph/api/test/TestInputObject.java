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
