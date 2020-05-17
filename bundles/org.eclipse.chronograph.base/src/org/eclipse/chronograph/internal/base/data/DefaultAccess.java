/*******************************************************************************
 * Copyright (c) 2020 ArSysOp
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0/.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     ArSysOp - initial API and implementation
 *******************************************************************************/
package org.eclipse.chronograph.internal.base.data;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.chronograph.internal.api.data.Resolution;

public abstract class DefaultAccess<I> implements Resolution<I> {

	private final Class<I> type;

	public DefaultAccess(Class<I> type) {
		this.type = type;
	}

	@Override
	public Class<I> type() {
		return type;
	}

	@Override
	public Function<Predicate<I>, List<I>> input() {
		return t -> Collections.emptyList();
	}

	@Override
	public <D> Function<D, String> identification(Class<D> domain) {
		return t -> String.valueOf(Objects.hashCode(t));
	}

	@Override
	public <G> Function<I, Optional<G>> adapt(Class<G> group) {
		return p -> Optional.empty();
	}

	@Override
	public <G> Function<I, String> grouping(Class<G> grouping) {
		return t -> Objects.toString(grouping);
	}

	@Override
	public Function<I, Integer> start() {
		return t -> 0;
	}

	@Override
	public Function<I, Integer> end() {
		return t -> 0;
	}

}
