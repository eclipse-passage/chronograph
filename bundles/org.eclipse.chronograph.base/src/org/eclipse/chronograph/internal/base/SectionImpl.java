/*******************************************************************************
 *	Copyright (c) 2020 ArSysOp
 *
 *	This program and the accompanying materials are made available under the
 *	terms of the Eclipse Public License 2.0 which is available at
 *	http://www.eclipse.org/legal/epl-2.0.
 *
 *	SPDX-License-Identifier: EPL-2.0
 *
 *	Contributors:
 *	Sergei Kovalchuk <sergei.kovalchuk@arsysop.ru> - 
 *												initial API and implementation
 *******************************************************************************/
package org.eclipse.chronograph.internal.base;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chronograph.internal.api.Brick;
import org.eclipse.chronograph.internal.api.Group;
import org.eclipse.chronograph.internal.api.Section;

/**
 * 
 * Implementation of {@link Section}
 *
 */
public class SectionImpl implements Section {

	private String id;
	private List<Group> groups = new ArrayList<>();

	public SectionImpl(String id) {
		this.id = id;
	}

	public SectionImpl(String id, List<Group> groups) {
		this.id = id;
		this.groups = groups;
	}

	@Override
	public String id() {
		return id;
	}

	public List<Group> groups() {
		return groups;
	}

	@Override
	public List<Brick> bricks() {
		List<Brick> bricks = new ArrayList<>();
		for (Group gr : groups) {
			bricks.addAll(gr.bricks());
		}
		return bricks;
	}
}
