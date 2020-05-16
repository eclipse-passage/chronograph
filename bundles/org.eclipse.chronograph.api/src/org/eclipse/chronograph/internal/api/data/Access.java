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
package org.eclipse.chronograph.internal.api.data;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 
 * Provides access to a remote storage with data objects
 *
 */
public interface Access {

	/**
	 * Provides function to retrieve domain objects of the given type with given
	 * predicate from the remote storage
	 * 
	 * @param <I>
	 * @param type
	 * @return
	 */
	<I> Function<Predicate<I>, Iterable<I>> data(Class<I> type);

	/**
	 * Identification function for the
	 * 
	 * @param <D>
	 * @param type
	 * @return
	 */
	<D> Function<D, String> identification(Class<D> type);

	/**
	 * "Content" function for the given input to use for grouping
	 * 
	 * @param <P>
	 * @param <C>
	 * @param parent
	 * @param child
	 * @return
	 */
	<P, C> Function<P, Iterable<C>> content(Class<P> parent, Class<C> child);

	/**
	 * Start pointer function to use for representation
	 * 
	 * @param <D>
	 * @param type
	 * @return
	 */
	<D> Function<D, Integer> start(Class<D> type);

	/**
	 * End pointer function to use for representation
	 * 
	 * @param <D>
	 * @param type
	 * @return
	 */
	<D> Function<D, Integer> end(Class<D> type);

}
