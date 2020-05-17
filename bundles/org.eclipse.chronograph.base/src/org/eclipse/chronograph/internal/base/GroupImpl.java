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

import org.eclipse.chronograph.internal.api.graphics.Brick;
import org.eclipse.chronograph.internal.api.graphics.Group;
import org.eclipse.chronograph.internal.api.graphics.GroupContainer;

/**
 * 
 * Implementation of {@link Group} interface
 *
 */
public class GroupImpl<D> implements Group<D> {

	private final String id;
	private final GroupContainer<D> parent;
	private final List<Brick<D>> bricks;

	public GroupImpl(String id, GroupContainer<D> parent) {
		this.id = id;
		this.parent = parent;
		this.bricks = new ArrayList<>();
	}

	public GroupImpl(String id, List<Brick<D>> bricks) {
		this.id = id;
		this.parent = null;
		this.bricks = bricks;
	}

	public GroupImpl(String id, GroupContainer<D> parent, List<Brick<D>> bricks) {
		this.id = id;
		this.parent = parent;
		this.bricks = bricks;
	}

	@Override
	public String id() {
		return id;
	}

	@Override
	public List<? extends Brick<D>> bricks() {
		return bricks;
	}

	@Override
	public GroupContainer<D> container() {
		return parent;
	}

	@Override
	public List<Group<D>> groups() {
		return parent.groups();
	}

}
