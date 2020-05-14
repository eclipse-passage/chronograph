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

/**
 * Main entry point to an API
 * 
 * @since 0.1
 *
 */
public interface Chronograph {

	/**
	 * Displays the current object
	 * 
	 * @param input the object to display
	 */
	void display(Object input);

}
