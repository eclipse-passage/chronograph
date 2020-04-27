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

import org.eclipse.chronograph.internal.api.Section;
import org.eclipse.chronograph.internal.swt.RulerStyler;
import org.eclipse.chronograph.internal.swt.SectionStyler;
import org.eclipse.chronograph.internal.swt.renderers.api.ChronographSectionRenderer;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Display;

/**
 * 
 * Render implementation for {@link Section} object
 *
 */
public class SectionRendererImpl implements ChronographSectionRenderer<Section> {

	@Override
	public void draw(GC gc, String label, Rectangle bounds, Display display, int width, int hintY) {
		gc.setForeground(SectionStyler.SECTION_TOP_COLOR);
		gc.setBackground(SectionStyler.SECTION_BTM_COLOR);
		gc.fillGradientRectangle(1, bounds.y - hintY, width, bounds.height + 1, false);
		gc.setForeground(SectionStyler.SECTION_BTM_COLOR);
		gc.drawRectangle(0, bounds.y - hintY, bounds.width + width, bounds.height);
		gc.setForeground(SectionStyler.SECTION_TOP_COLOR);
		Point lblPoint = gc.stringExtent(label);
		Transform tr = new Transform(display);
		tr.translate(0, -hintY);
		tr.rotate(-90);
		gc.setTransform(tr);
		gc.setForeground(RulerStyler.RULER_TEXT_COLOR);
		gc.drawString(label, -bounds.y - bounds.height + (bounds.height - lblPoint.x) / 2, 10, true);
		tr.dispose();
		gc.setTransform(null);
	}
}
