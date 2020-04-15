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

import java.util.List;

import org.eclipse.chronograph.internal.api.Section;

/**
 * 
 * The top level parameterized interface designed to create {@link Section} by
 * input by parameters.
 * 
 * @param <I> - type of template class.
 */
public interface SectionContentProvider<I> {

	/**
	 * The method designed to create {@link Section} from specified input
	 * 
	 * @param input - typed input
	 * @return List of {@link Section}
	 */
	List<Section> getSections(List<I> input);

}
