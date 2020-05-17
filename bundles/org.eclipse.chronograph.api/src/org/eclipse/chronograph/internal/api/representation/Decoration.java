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
package org.eclipse.chronograph.internal.api.representation;

import java.util.Optional;

import org.eclipse.chronograph.internal.api.graphics.Brick;
import org.eclipse.chronograph.internal.api.graphics.Group;

/**
 * The label provider designed to provide specific data for input objects
 * presented on user interface
 * 
 *
 */
public interface Decoration<D, I> {

	/**
	 * Image for input object
	 * 
	 * @param element - input object
	 * @return image
	 */
	Optional<I> brickImage(Brick<D> element);

	/**
	 * Label for input object
	 * 
	 * @param element - input object
	 * @return
	 */
	String brickText(Brick<D> element);

	/**
	 * Label for input object
	 * 
	 * @param element - input object
	 * @return
	 */
	String groupText(Group element);

}
