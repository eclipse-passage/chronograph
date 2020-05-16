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

import org.eclipse.chronograph.internal.api.Brick;
import org.eclipse.chronograph.internal.api.Position;

public class BrickImpl implements Brick {

	private final String id;
	private final Position position;

	public BrickImpl(String id, int start, int end) {
		this.id = id;
		this.position = new PositionImpl(start, end);
	}

	@Override
	public String id() {
		return this.id;
	}

	@Override
	public Position position() {
		return position;
	}

}
