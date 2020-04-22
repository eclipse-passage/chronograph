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
package org.eclipse.chronograph.internal.providers;

import java.util.List;

import org.eclipse.chronograph.internal.api.Section;
import org.eclipse.chronograph.internal.api.adapters.SectionAdapter;
import org.eclipse.chronograph.internal.api.builders.SectionBuilder;
import org.eclipse.chronograph.internal.api.providers.SectionContentProvider;

/**
 * 
 * Template provider implementation of {@link SectionContentProvider} interface
 *
 * @param <I> - input object type
 * @param <D> - intermediate adapted object type
 */
public class SectionContentProviderImpl<I, D> implements SectionContentProvider<I> {

	private final SectionBuilder<List<I>, D> builder;
	private final SectionAdapter<D, Section> adapter;

	public SectionContentProviderImpl(SectionBuilder<List<I>, D> sectionBuilder, SectionAdapter<D, Section> adapter) {

		this.builder = sectionBuilder;
		this.adapter = adapter;
	}

	@Override
	public List<Section> getSections(List<I> input) {
		return adapter.adapt(builder.apply(input));
	}

}
