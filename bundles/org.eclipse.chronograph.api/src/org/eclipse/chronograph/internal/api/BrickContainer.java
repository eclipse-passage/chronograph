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

import java.util.List;

/**
 * 
 * Container for Brick typed elements
 *
 */
public interface BrickContainer {

	/**
	 * 
	 * @return stored Brick elements in List data structure 
	 */
	List<? extends Brick> bricks();
}
