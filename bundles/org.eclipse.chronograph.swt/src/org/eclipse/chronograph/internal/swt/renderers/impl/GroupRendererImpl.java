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

import org.eclipse.chronograph.internal.api.graphics.Group;
import org.eclipse.chronograph.internal.swt.GroupStyler;
import org.eclipse.chronograph.internal.swt.renderers.api.ChronographGroupRenderer;
import org.eclipse.swt.SWT;
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
		int fontHeight = gc.getFontMetrics().getHeight();
		final Rectangle groupNameRectangle = new Rectangle(groupBound.x, groupBound.y - hintY, width,
				groupBound.height);
		gc.setForeground(GroupStyler.GROUP_TOP_COLOR);
		gc.setBackground(GroupStyler.GROUP_BTM_COLOR);
		gc.setAntialias(SWT.ON);
		gc.setForeground(GroupStyler.GROUP_BTM_COLOR);
		gc.setBackground(GroupStyler.GROUP_TOP_COLOR);
		gc.fillRoundRectangle(groupNameRectangle.x, groupNameRectangle.y, groupNameRectangle.width,
				groupNameRectangle.height, width, width);
		gc.drawRoundRectangle(groupNameRectangle.x, groupNameRectangle.y, groupNameRectangle.width,
				groupNameRectangle.height, width, width);
		gc.setForeground(GroupStyler.GROUP_BTM_COLOR);
		gc.drawRoundRectangle(groupBound.x, groupBound.y - hintY, groupBound.width, groupBound.height, width, width);

		Point stringExtent = gc.stringExtent(label);
		String msg = label; // $NON-NLS-1$
		if (stringExtent.x > groupNameRectangle.height) {
			msg = label.substring(0, 5) + "..."; //$NON-NLS-1$
		}
		stringExtent = gc.stringExtent(msg);
		Transform tr = new Transform(display);
		tr.translate(groupNameRectangle.x, groupNameRectangle.y);
		tr.rotate(-90);
		gc.setTransform(tr);
		gc.setForeground(GroupStyler.GROUP_TEXT_COLOR);
		gc.drawString(msg, -groupNameRectangle.height + (groupNameRectangle.height - stringExtent.x) / 2,
				fontHeight / 2, true);
		// gc.setBackground(GroupStyler.GROUP_BTM_COLOR);
		// gc.fillOval(-groupNameRectangle.height, 0, width, width);

		tr.dispose();
		gc.setTransform(null);
	}

}
