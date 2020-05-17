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
package org.eclipse.chronograph.internal.api.graphics;

/**
 * 
 * The Group describes typed container with sequence Brick items
 *
 */
public interface Group {

	/**
	 * 
	 * @return String identifier of group
	 */
	String id();

	/**
	 * 
	 * @return level of the group
	 */
	int level();

	/**
	 * 
	 * @return Full qualified identifier of the group
	 */
	String fqid();

	/**
	 * 
	 * @return object used for grouping
	 */
	Object data();
}
