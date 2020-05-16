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
 *     ArSysOp - initial API and implementation
 *******************************************************************************/
package org.eclipse.chronograph.internal.base.query;

import java.time.LocalDate;
import java.util.function.Predicate;

import org.eclipse.chronograph.internal.api.graphics.Brick;
import org.eclipse.chronograph.internal.base.UnitConverter;

/**
 * 
 * Accepts bricks that are expired for the given data
 *
 */
public class ExpiredBricks implements Predicate<Brick> {

	private final LocalDate local;

	public ExpiredBricks() {
		this.local = LocalDate.now();
	}

	@Override
	public boolean test(Brick brick) {
		LocalDate start = UnitConverter.unitsToLocalDate((int) brick.position().start());
		LocalDate end = UnitConverter.unitsToLocalDate((int) brick.position().end());
		return local.isAfter(end) && local.isBefore(start);
	}

}
