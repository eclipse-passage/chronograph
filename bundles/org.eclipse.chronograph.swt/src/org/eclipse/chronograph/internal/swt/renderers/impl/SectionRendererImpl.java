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
import org.eclipse.chronograph.internal.swt.SectionStyler;
import org.eclipse.chronograph.internal.swt.renderers.api.ChronographSectionRenderer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Display;

/**
 * 
 * Render implementation for root {@link Group} object
 *
 */
public class SectionRendererImpl implements ChronographSectionRenderer {

	private final Labels labels = new Labels();

	@Override
	public void draw(GC gc, String label, Rectangle bounds, Display display, int width, int hintY) {
		gc.setAntialias(SWT.ON);
		final int fontHeight = gc.getFontMetrics().getHeight();
		final Rectangle rectangle = new Rectangle(bounds.x - GroupStyler.getGroupWith(), bounds.y, width,
				bounds.height);

		gc.setForeground(SectionStyler.SECTION_BTM_COLOR);
		gc.setBackground(SectionStyler.SECTION_TOP_COLOR);
		gc.fillRoundRectangle(rectangle.x, rectangle.y - hintY, width, rectangle.height, width, width);
		gc.drawRoundRectangle(rectangle.x, rectangle.y - hintY, width, rectangle.height, width, width);
		gc.setForeground(SectionStyler.SECTION_TOP_COLOR);

		Transform tr = new Transform(display);
		tr.translate(0, -hintY);
		tr.rotate(-90);
		gc.setTransform(tr);
		gc.setForeground(SectionStyler.SECTION_TEXT_COLOR);

		Point stringExtent = gc.stringExtent(label);
		String msg = calculateLabel(gc, label, rectangle, stringExtent);
		stringExtent = gc.stringExtent(msg);
		int x = -rectangle.height - rectangle.y;
		gc.drawString(msg, x + (rectangle.height - stringExtent.x) / 2, fontHeight / 2, true);
		gc.setBackground(SectionStyler.SECTION_BTM_COLOR);

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
