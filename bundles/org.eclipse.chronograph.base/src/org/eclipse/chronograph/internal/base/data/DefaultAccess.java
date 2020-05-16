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
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.chronograph.internal.api.data.Access;

public abstract class DefaultAccess implements Access {

	@Override
	public <I> Function<Predicate<I>, Iterable<I>> data(Class<I> type) {
		return t -> Collections.emptyList();
	}

	@Override
	public <D> Function<D, String> identification(Class<D> type) {
		return t -> String.valueOf(Objects.hashCode(t));
	}

	@Override
	public <P, C> Function<P, Iterable<C>> content(Class<P> parent, Class<C> child) {
		return p -> Collections.emptyList();
	}

	@Override
	public <D> Function<D, Integer> start(Class<D> type) {
		return t -> 0;
	}

	@Override
	public <D> Function<D, Integer> end(Class<D> type) {
		return t -> 0;
	}

}
