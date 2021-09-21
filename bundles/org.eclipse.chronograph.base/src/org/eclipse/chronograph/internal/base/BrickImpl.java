/*******************************************************************************
 *	Copyright (c) 2020, 2021 ArSysOp
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

import org.eclipse.chronograph.internal.api.graphics.Brick;
import org.eclipse.chronograph.internal.api.graphics.Position;

public class BrickImpl implements Brick {

	private final Object data;
	private Position position;

	public BrickImpl(int start, int end, Object data) {
		this.data = data;
		this.position = new PositionImpl(start, end);
	}

	public BrickImpl(Object t) {
		this.data = t;
		this.position = new PositionImpl(0, 0);
	}

	@Override
	public Position position() {
		return position;
	}

	@Override
	public Object data() {
		return data;
	}

	@Override
	public void setPosition(Position p) {
		this.position = p;
	}

}
