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
 *     Sergei Kovalchuk <sergei.kovalchuk@arsysop.ru> - initial API and implementation
 *     Alexander Fedorov <alexander.fedorov@arsysop.ru> - API improvements
 *******************************************************************************/
package org.eclipse.chronograph.internal.api.data;

import org.eclipse.chronograph.internal.api.Brick;

/**
 * Intended for adapt input objects to {@link Brick}.
 *
 * @param <D> - type of domain objects
 */
public interface BrickAdapter<D> {

	/**
	 * String identifier to use for {@link Brick}
	 * 
	 * @param object domain object
	 * @return identifier
	 */
	public String identifier(D object);

	/**
	 * Start pointer to use for {@link Brick}
	 * 
	 * @param object
	 * @return start pointer
	 */
	public int start(D object);

	/**
	 * End pointer to use for {@link Brick}
	 * 
	 * @param object
	 * @return end pointer
	 */
	public int end(D object);

}
