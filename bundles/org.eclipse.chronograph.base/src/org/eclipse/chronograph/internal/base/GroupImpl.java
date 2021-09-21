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
package org.eclipse.chronograph.internal.base;

import org.eclipse.chronograph.internal.api.graphics.Group;

/**
 * 
 * Implementation of {@link Group} interface
 *
 */
public class GroupImpl implements Group {

	private final Object data;

	public GroupImpl(Object t) {
		this.data = t;
	}

	@Override
	public Object data() {
		return data;
	}

}
