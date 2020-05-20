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

	@Override
	public void draw(GC gc, String label, Rectangle bounds, Display display, int width, int hintY) {
		gc.setAntialias(SWT.ON);
		int fontHeight = gc.getFontMetrics().getHeight();
		final Rectangle sectionRectangle = new Rectangle(bounds.x, bounds.y, width, bounds.height);
		gc.setForeground(SectionStyler.SECTION_BTM_COLOR);
		gc.setBackground(SectionStyler.SECTION_TOP_COLOR);
		gc.fillRoundRectangle(0, sectionRectangle.y - hintY, width, sectionRectangle.height, width, width);
		gc.drawRoundRectangle(0, sectionRectangle.y - hintY, width, sectionRectangle.height, width, width);

		gc.setForeground(SectionStyler.SECTION_TOP_COLOR);
		Point lblPoint = gc.stringExtent(label);
		Transform tr = new Transform(display);
		tr.translate(0, -hintY);
		tr.rotate(-90);
		gc.setTransform(tr);
		gc.setForeground(SectionStyler.SECTION_TEXT_COLOR);

		Point stringExtent = gc.stringExtent(label);
		String msg = calculateLabel(gc, label, sectionRectangle, stringExtent);
		stringExtent = gc.stringExtent(msg);
		int x = -sectionRectangle.height - sectionRectangle.y;
		gc.drawString(msg, x + (sectionRectangle.height - stringExtent.x) / 2, fontHeight / 2, true);
		gc.setBackground(SectionStyler.SECTION_BTM_COLOR);
		// gc.fillOval(-bounds.y - bounds.height, 0, width, width);

		tr.dispose();
		gc.setTransform(null);
	}

	private String calculateLabel(GC gc, String label, final Rectangle rectangle, Point stringExtent) {
		String msg = ""; //$NON-NLS-1$
		String ends = "..."; //$NON-NLS-1$
		Point endsExt = gc.stringExtent(ends);
		int chWidths = 0;
		if (stringExtent.x > rectangle.height) {
			for (char ch : label.toCharArray()) {
				chWidths += gc.getCharWidth(ch);
				if (chWidths < rectangle.height - endsExt.x) {
					msg += ch;
				}
			}
			msg += ends;
		} else {
			msg = label;
		}
		return msg;
	}

}
