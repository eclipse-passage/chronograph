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

import org.eclipse.chronograph.internal.api.Group;
import org.eclipse.chronograph.internal.api.Section;

/**
 * 
 * The top level parameterized interface designed to create {@link Group} by
 * input by parameters.
 * 
 * @param <I> - type of template class.
 */
public interface GroupContentProvider<I> {

	/**
	 * The method designed to create {@link Group} from specified parameters
	 * 
	 * @param input   - typed input
	 * @param section - {@link Section} section
	 * @return List of {@link Group}
	 */
	List<Group> getGroupsBySection(List<I> input, Section section);

}
