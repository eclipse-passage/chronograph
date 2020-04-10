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

import org.eclipse.chronograph.api.Section;

/**
 * Function interface intended to organize input objects to required build
 * object for {@link Section}
 * 
 * @param <I> - input object type
 * @param <S> - output object type
 */
public interface SectionBuilder<I, S> extends Function<I, List<S>> {
}
