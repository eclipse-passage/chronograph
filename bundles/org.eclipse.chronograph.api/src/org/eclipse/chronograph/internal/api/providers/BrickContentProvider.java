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
package org.eclipse.chronograph.internal.api.providers;

import java.util.List;

import org.eclipse.chronograph.internal.api.Brick;
import org.eclipse.chronograph.internal.api.Group;

/**
 * 
 * The top level parameterized interface designed to create {@link Brick} by
 * input by parameters.
 * 
 * @param <I> - type of template class.
 */
public interface BrickContentProvider<I> {

	/**
	 * The method designed to create {@link Brick} from specified parameters
	 * 
	 * @param input - typed input
	 * @param group - {@link Group} group
	 * @return List of {@link Brick}
	 */
	List<Brick> getBricksByGroup(List<I> input, Group group);

	/**
	 * The method designed to create {@link Brick} from specified parameters
	 * 
	 * @param input    - typed input
	 * @param subGroup - {@link Group} subgroup
	 * @param groupId  - identifier of {@link Group}
	 * @return List of {@link Brick}
	 */
	List<Brick> getBricksBySubGroup(List<I> input, String groupId, Group subGroup);

	/**
	 * The method designed to create {@link Brick} from specified parameters
	 * 
	 * @param input - typed input
	 * @return List of {@link Brick}
	 */
	List<Brick> getBricks(List<I> input);

}
