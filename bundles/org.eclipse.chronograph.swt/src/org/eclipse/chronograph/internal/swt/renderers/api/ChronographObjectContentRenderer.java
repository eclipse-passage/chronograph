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
package org.eclipse.chronograph.internal.swt.renderers.api;

import org.eclipse.chronograph.internal.api.data.ContentDecorationProvider;
import org.eclipse.chronograph.internal.api.graphics.Brick;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

/**
 *
 * Interface intended to render {@link Brick}
 *
 * @param <D> - type of data object
 */
public interface ChronographObjectContentRenderer {

	public void draw(Brick obj, GC gc, Rectangle bounds, int vOffset);

	public void draw(ContentDecorationProvider provider, Brick obj, GC gc, Rectangle bounds, int vOffset,
			boolean isSelected);

}
