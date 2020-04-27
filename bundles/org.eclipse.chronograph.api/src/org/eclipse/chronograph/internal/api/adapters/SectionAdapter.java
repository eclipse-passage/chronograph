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
 *	Sergei Kovalchuk <sergei.kovalchuk@arsysop.ru> - initial API and implementation
 *									
 *******************************************************************************/
package org.eclipse.chronograph.internal.api.adapters;

import java.util.List;

import org.eclipse.chronograph.internal.api.Section;

/**
 * Intended for adapt input objects to {@link Section} container
 * 
 *
 * @param <T> - type of input objects
 * @param <S> - type of adapted objects
 */
public interface SectionAdapter<T, S> {

	public List<S> adapt(List<T> inputObjects);

}