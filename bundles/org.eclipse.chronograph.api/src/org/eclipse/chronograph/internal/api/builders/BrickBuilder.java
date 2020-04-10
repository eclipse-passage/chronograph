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
package org.eclipse.chronograph.internal.api.builders;

import java.util.List;
import java.util.function.Function;

import org.eclipse.chronograph.api.Brick;
import org.eclipse.chronograph.api.Group;

/**
 * Function interface intended to organize input objects to required build object for {@link Brick}  
 * 
 * @param <I> - input object type 	
 * @param <S> - output object type
 */
public interface BrickBuilder<I, S> extends Function<I, List<S>> {

	/**
	 * 
	 * Organize elements on input arguments
	 *  
	 * @param input - input object type
	 * @param groupId - filtering parameter
	 * @return - list of output objects
	 */
	List<S> applyGroup(I input, String groupId);

	/**
	 * 
	 * Organize elements on input arguments
	 *  
	 * @param input - input object type
	 * @param subGroupId - identifier of nested {@link Group} object 
	 * @return - list of output objects
	 */
	List<S> applySubGroup(I input, String subGroupId);

}
