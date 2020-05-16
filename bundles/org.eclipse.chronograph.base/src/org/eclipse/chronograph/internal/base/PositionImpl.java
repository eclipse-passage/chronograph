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

import org.eclipse.chronograph.internal.api.graphics.Position;

/**
 * 
 * Implementation of {@link Position} interface intended to store period of time
 *
 */
public class PositionImpl implements Position {
	private final long start;
	private final long end;

	public PositionImpl(long start, long end) {
		this.start = start;
		this.end = end;
	}

	@Override
	public long start() {
		return start;
	}

	@Override
	public long end() {
		return end;
	}

	@Override
	public long duration() {
		return end - start;
	}

}
