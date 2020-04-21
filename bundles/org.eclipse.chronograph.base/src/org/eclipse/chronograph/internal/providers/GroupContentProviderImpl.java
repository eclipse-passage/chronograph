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
import org.eclipse.chronograph.internal.api.adapters.GroupAdapter;
import org.eclipse.chronograph.internal.api.builders.GroupBuilder;
import org.eclipse.chronograph.internal.api.providers.GroupContentProvider;

/**
 * 
 * Template provider implementation of {@link GroupContentProvider} interface
 *
 * @param <I> - input object type
 * @param <D> - intermediate adapted object type
 */
public class GroupContentProviderImpl<I, D> implements GroupContentProvider<I> {

	private final GroupBuilder<List<I>, D> builder;
	private final GroupAdapter<D, Group> adapter;

	public GroupContentProviderImpl(GroupBuilder<List<I>, D> groupBuilder, GroupAdapter<D, Group> groupAdapter) {
		this.builder = groupBuilder;
		this.adapter = groupAdapter;
	}

	@Override
	public List<Group> getGroupsBySection(List<I> input, Section section) {
		return adapter.adapt(builder.applySection(input, section.id()), section);
	}
}
