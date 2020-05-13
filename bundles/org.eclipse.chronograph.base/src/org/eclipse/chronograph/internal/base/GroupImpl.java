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
import org.eclipse.chronograph.internal.api.GroupContainer;

/**
 * 
 * Implementation of {@link Group} interface
 *
 */
public class GroupImpl implements Group {

	private final String id;
	private final GroupContainer parent;
	private final List<Brick> bricks;

	public GroupImpl(String id, GroupContainer parent) {
		this.id = id;
		this.parent = parent;
		this.bricks = new ArrayList<Brick>();
	}

	public GroupImpl(String id, List<Brick> bricks) {
		this.id = id;
		this.parent = null;
		this.bricks = bricks;
	}

	public GroupImpl(String id, GroupContainer parent, List<Brick> bricks) {
		this.id = id;
		this.parent = parent;
		this.bricks = bricks;
	}

	@Override
	public String id() {
		return id;
	}

	@Override
	public List<? extends Brick> bricks() {
		return bricks;
	}

	@Override
	public GroupContainer container() {
		return parent;
	}

	@Override
	public List<? extends Group> groups() {
		return parent.groups();
	}

}
