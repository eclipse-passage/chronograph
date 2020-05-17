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
package org.eclipse.chronograph.internal.swt.renderers.impl;

import org.eclipse.chronograph.internal.api.graphics.Brick;
import org.eclipse.chronograph.internal.swt.renderers.api.ChronographObjectExtRenderer;
import org.eclipse.swt.graphics.GC;

/**
 * 
 * Render implementation for extension of {@link Brick} object
 *
 */
public class ObjectExtensionRendererImpl<D> implements ChronographObjectExtRenderer<D> {

	@Override
	public void drawObjectDuration(Brick<D> to, GC gc, int vOffset) {
	}
}
