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
package org.eclipse.chronograph.internal.swt.renderers.impl.base;

import org.eclipse.chronograph.internal.api.Group;
import org.eclipse.chronograph.internal.swt.BrickStyler;
import org.eclipse.chronograph.internal.swt.GroupStyler;
import org.eclipse.chronograph.internal.swt.renderers.api.ChronographGroupRenderer;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Display;

/**
 * 
 * Render implementation of {@link Group} object
 *
 * @param <T> - type of object
 */
public class GroupRendererImpl<T extends Group> implements ChronographGroupRenderer<T> {

	@Override
	public void draw(GC gc, String label, Rectangle groupBound, Display display, int width, int hintY) {

		final Rectangle groupNameRectangle = new Rectangle(groupBound.x, groupBound.y - hintY, width,
				groupBound.height);
		gc.setBackground(GroupStyler.GROUP_BTM_COLOR);
		gc.setForeground(GroupStyler.GROUP_TOP_COLOR);
		gc.fillGradientRectangle(groupNameRectangle.x, groupNameRectangle.y, groupNameRectangle.width,
				groupNameRectangle.height, false);
		gc.setForeground(BrickStyler.getColorBorder());
		gc.drawRectangle(groupNameRectangle);
		gc.drawRectangle(groupBound.x, groupBound.y - hintY, groupBound.width, groupBound.height);
		Point stringExtent = gc.stringExtent(label);
		Transform tr = new Transform(display);
		tr.translate(groupNameRectangle.x, groupNameRectangle.y);
		tr.rotate(-90);
		gc.setTransform(tr);
		gc.setForeground(GroupStyler.TEXT_COLOR);
		gc.drawString(label, -groupNameRectangle.height + (groupNameRectangle.height - stringExtent.x) / 2, 10, true);
		tr.dispose();
		gc.setTransform(null);
	}

}
