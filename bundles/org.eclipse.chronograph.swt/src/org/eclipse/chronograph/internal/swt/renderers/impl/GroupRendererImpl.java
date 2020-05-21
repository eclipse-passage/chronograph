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
 */
public class GroupRendererImpl implements ChronographGroupRenderer {

	private final Labels labels = new Labels();

	@Override
	public void draw(GC gc, String label, Rectangle groupBound, Display display, int width, int hintY) {
		int fontHeight = gc.getFontMetrics().getHeight();
		final Rectangle groupRectangle = new Rectangle(groupBound.x, groupBound.y - hintY, width, groupBound.height);
		gc.setForeground(GroupStyler.GROUP_TOP_COLOR);
		gc.setBackground(GroupStyler.GROUP_BTM_COLOR);
		gc.setAntialias(SWT.ON);
		gc.setForeground(GroupStyler.GROUP_BTM_COLOR);
		gc.setBackground(GroupStyler.GROUP_TOP_COLOR);
		gc.fillRoundRectangle(groupRectangle.x, groupRectangle.y, groupRectangle.width, groupRectangle.height, width,
				width);
		gc.drawRoundRectangle(groupRectangle.x, groupRectangle.y, groupRectangle.width, groupRectangle.height, width,
				width);
		gc.setForeground(GroupStyler.GROUP_BTM_COLOR);
		gc.drawRoundRectangle(groupBound.x, groupBound.y - hintY, groupBound.width, groupBound.height, width, width);

		Point stringExtent = gc.stringExtent(label);

		String msg = calculateLabel(gc, label, groupRectangle, stringExtent);

		stringExtent = gc.stringExtent(msg);
		Transform tr = new Transform(display);
		tr.translate(groupRectangle.x, groupRectangle.y);
		tr.rotate(-90);
		gc.setTransform(tr);
		gc.setForeground(GroupStyler.GROUP_TEXT_COLOR);
		gc.drawString(msg, -groupRectangle.height + (groupRectangle.height - stringExtent.x) / 2, fontHeight / 2, true);

		tr.dispose();
		gc.setTransform(null);
	}

	private String calculateLabel(GC gc, String label, Rectangle rectangle, Point extent) {
		int limit = rectangle.height;
		if (extent.x > limit) {
			return labels.fit(label, limit, gc);
		} else {
			return label;
		}
	}

}
