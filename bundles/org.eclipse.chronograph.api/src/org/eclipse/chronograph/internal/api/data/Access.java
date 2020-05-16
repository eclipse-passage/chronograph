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

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 
 * Provides access to a remote storage with data objects
 *
 */
public interface Access<I> {

	Class<I> type();

	/**
	 * Provides function to retrieve input domain objects with given predicate from
	 * the remote storage
	 * 
	 * @return function to retrieve filtered input
	 */
	Function<Predicate<I>, List<I>> input();

	/**
	 * Identification function for the domain object
	 * 
	 * @param <D>  the type of domain object
	 * @param type the domain object type
	 * @return
	 */
	<D> Function<D, String> identification(Class<D> type);

	/**
	 * "map" function for the given type to define groups
	 * 
	 * @param <G>
	 * @param grouping
	 * @return
	 */
	<G> Function<I, Optional<G>> map(Class<G> grouping);

	/**
	 * "grouping" function to collect by groups
	 * 
	 * @param <C>
	 * @param child
	 * @return
	 */
	<G> Function<I, String> grouping(Class<G> grouping);

	/**
	 * Start pointer function to use for representation
	 * 
	 * @return function to get start pointer
	 */
	Function<I, Integer> start();

	/**
	 * End pointer function to use for representation
	 * 
	 * @param <D>
	 * @param type
	 * @return function to get end pointer
	 */
	Function<I, Integer> end();

}
