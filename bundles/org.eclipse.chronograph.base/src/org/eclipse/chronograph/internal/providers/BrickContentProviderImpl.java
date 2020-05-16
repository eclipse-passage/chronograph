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
import java.util.function.Function;

import org.eclipse.chronograph.internal.api.Brick;
import org.eclipse.chronograph.internal.api.Group;
import org.eclipse.chronograph.internal.api.builders.BrickBuilder;
import org.eclipse.chronograph.internal.api.providers.BrickContentProvider;

/**
 * 
 * Template provider implementation of {@link BrickContentProvider} interface
 *
 * @param <I> - input object type
 * @param <D> - intermediate adapted object type
 */
public class BrickContentProviderImpl<I, D> implements BrickContentProvider<I> {

	private final BrickBuilder<List<I>, D> builder;
	private final Function<List<D>, List<Brick>> adapter;

	public BrickContentProviderImpl(BrickBuilder<List<I>, D> brickBuilder,
			Function<List<D>, List<Brick>> brickAdapter) {
		this.builder = brickBuilder;
		this.adapter = brickAdapter;
	}

	@Override
	public List<Brick> getBricksByGroup(List<I> input, Group group) {
		return adapter.apply(builder.applyGroup(input, group.id()));
	}

	@Override
	public List<Brick> getBricksBySubGroup(List<I> input, String groupId, Group subGroup) {
		return adapter.apply(builder.applySubGroup(input, groupId, subGroup.id()));
	}

	@Override
	public List<Brick> getBricks(List<I> input) {
		return adapter.apply(builder.apply(input));
	}

}
