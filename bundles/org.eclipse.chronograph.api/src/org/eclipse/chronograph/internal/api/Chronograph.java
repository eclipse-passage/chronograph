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
package org.eclipse.chronograph.internal.api;

import java.util.List;

/**
 * Main entry point to an API
 * 
 * @since 0.1
 *
 */
public interface Chronograph {

	/**
	 * Structure data according to the given type sequence: from section to brick
	 * 
	 * @param types the types to group the input
	 */
	void structure(List<Class<?>> types);

	/**
	 * Applies the given style
	 * 
	 * @param style the style to use
	 */
	void style(Style style);

	/**
	 * Refreshes the data using current structure and style
	 */
	void refresh();

}
