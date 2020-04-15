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
package org.eclipse.chronograph.internal.api;

/**
 * 
 * The Area describes the drawing region from left top point, width and height.
 *
 */
public interface Area {
	/**
	 * 
	 * @return value by X axis, by default considered the left top point 
	 */
	int x();

	/**
	 * 
	 * @return value by Y axis, by default considered the left top point
	 */

	int y();

	/**
	 * 
	 * @return width value
	 */
	int width();

	/**
	 * 
	 * @return height value
	 */
	int height();

}
