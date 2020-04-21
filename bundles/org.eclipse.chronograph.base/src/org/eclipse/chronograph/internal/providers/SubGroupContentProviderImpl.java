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

import org.eclipse.chronograph.internal.api.Group;
import org.eclipse.chronograph.internal.api.Section;
import org.eclipse.chronograph.internal.api.adapters.SubGroupAdapter;
import org.eclipse.chronograph.internal.api.builders.SubGroupBuilder;
import org.eclipse.chronograph.internal.api.providers.SubGroupContentProvider;

/**
 * 
 * Template provider implementation of {@link SubGroupContentProvider} interface
 *
 * @param <I> - input object type
 * @param <D> - intermediate adapted object type
 */
public class SubGroupContentProviderImpl<I, D> implements SubGroupContentProvider<I> {
	private final SubGroupBuilder<List<I>, D> builder;
	private final SubGroupAdapter<D, Group> adapter;

	public SubGroupContentProviderImpl(SubGroupBuilder<List<I>, D> groupBuilder,
			SubGroupAdapter<D, Group> groupAdapter) {
		this.builder = groupBuilder;
		this.adapter = groupAdapter;
	}

	@Override
	public List<Group> getGroupsBySectionGroup(List<I> input, Section section, Group group) {
		return adapter.adapt(builder.applySectionGroup(input, section.id(), group.id()), group);
	}
}