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
package org.eclipse.chronograph.internal.api.builders;

import java.util.List;
import java.util.function.Function;

import org.eclipse.chronograph.internal.api.Group;
import org.eclipse.chronograph.internal.api.Section;

/**
 * Function interface intended to organize input objects to required build
 * object that nested {@link Group}
 * 
 * @param <I> - input object type
 * @param <S> - output object type
 */
public interface SubGroupBuilder<I, S> extends Function<I, List<S>> {
	/**
	 * Function interface intended to organize input objects to required build
	 * object for {@link Group}
	 * 
	 * @param <I>       - input object type
	 * @param sectionId - identifier of {@link Section} object
	 * @param groupId   - identifier of {@link Group} object
	 * @param <S>       - output object type
	 */
	List<S> applySectionGroup(I input, String sectionId, String groupId);
}
