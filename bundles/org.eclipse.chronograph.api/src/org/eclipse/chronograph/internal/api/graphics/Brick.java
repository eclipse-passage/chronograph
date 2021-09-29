/*******************************************************************************
 * Copyright (c) 2020, 2021 ArSysOp
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
package org.eclipse.chronograph.internal.api.graphics;

/**
 * 
 * The Brick describes the drawing element on the stage
 *
 */
public interface Brick extends Drawing {

	/**
	 * 
	 * @return original Brick position
	 */
	Position position();

	/**
	 * Set element position
	 * 
	 * @param position
	 */
	void setPosition(Position p);
}
