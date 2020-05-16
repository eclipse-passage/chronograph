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
import org.eclipse.chronograph.internal.swt.BrickStyler;
import org.eclipse.chronograph.internal.swt.renderers.api.ChronographObjectContentRenderer;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

/**
 * 
 * Render implementation for selected {@link Brick} object
 *
 */
public class ObjectSelectedRendererImpl implements ChronographObjectContentRenderer<Brick> {

	@Override
	public void draw(Brick object, GC gc, Rectangle bounds, int vOffset) {
		gc.setForeground(BrickStyler.getColorTopSelected());
		gc.setBackground(BrickStyler.getColorBottomSelected());
		gc.fillRoundRectangle(bounds.x, bounds.y, bounds.width, bounds.height, 30, 30);
		gc.setBackground(BrickStyler.getColorTopSelected());
		gc.fillOval(bounds.x - bounds.height / 10, bounds.y, bounds.height, bounds.height);
	}
}
