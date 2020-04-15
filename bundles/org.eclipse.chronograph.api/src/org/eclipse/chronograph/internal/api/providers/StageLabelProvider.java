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
 *	Sergei Kovalchuk <sergei.kovalchuk@arsysop.ru> - 
 *												initial API and implementation
 *******************************************************************************/
package org.eclipse.chronograph.internal.api.providers;

/**
 * The label provider designed to provide specific data for input objects
 * presented on user interface
 * 
 *
 */
public interface StageLabelProvider {

	/**
	 * Image for input object
	 * 
	 * @param element - input object
	 * @return {@link Image}
	 */
	Object getImage(Object element);

	/**
	 * Label for input object
	 * 
	 * @param element - input object
	 * @return
	 */
	String getText(Object element);
}
