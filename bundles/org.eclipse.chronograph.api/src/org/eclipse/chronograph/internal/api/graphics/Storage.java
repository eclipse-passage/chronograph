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
package org.eclipse.chronograph.internal.api.graphics;

import java.util.List;
import java.util.function.Predicate;

/**
 * 
 * Encapsulates the storage of objects to be displayed
 *
 */
public interface Storage<D> {

	/**
	 * 
	 * @param predicate the predicate to filter the bricks
	 * @return
	 */
	// FIXME: not sure that we should use interface from "graphics" here
	List<Brick<D>> query(Predicate<Brick<D>> predicate);

}
