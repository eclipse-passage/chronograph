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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.eclipse.chronograph.internal.swt.RulerStyler;
import org.eclipse.chronograph.internal.swt.renderers.api.ChronographStageRulerRenderer;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

/**
 * 
 * Render implementation for day ruler
 *
 */
public class RulerDayRendererImpl implements ChronographStageRulerRenderer {

	private final SimpleDateFormat sdf = new SimpleDateFormat("dd"); //$NON-NLS-1$
	private final Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

	@Override
	public void draw(GC gc, Rectangle bounds, int scale, int width, int tiksOffset, int xAxis) {
		int xMaxPosition = bounds.width + bounds.x;
		int yBottomPosition = bounds.y + bounds.height - RulerStyler.RULER_DAY_HEIGHT - RulerStyler.RULER_MOUNTH_HEIGHT
				- RulerStyler.RULER_YEAR_HEIGHT;
		int xPosition = 0;
		// gc.setAntialias(SWT.ON);
		calendar.clear();
		calendar.set(Calendar.YEAR, 2019);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 01);
		calendar.add(Calendar.DATE, tiksOffset);

		gc.setForeground(RulerStyler.RULER_TOP_COLOR);
		gc.setBackground(RulerStyler.RULER_BTM_COLOR);
		gc.fillRectangle(xPosition, yBottomPosition, bounds.width, RulerStyler.RULER_DAY_HEIGHT);

		while (true) {
			if (xPosition >= bounds.x && width > 20) {
				gc.setForeground(RulerStyler.RULER_TOP_COLOR);
				gc.setBackground(RulerStyler.RULER_CUREENT_DAY_COLOR_BTM);

				gc.drawLine(xPosition, yBottomPosition, xPosition, yBottomPosition + RulerStyler.RULER_DAY_HEIGHT);
				gc.setForeground(RulerStyler.RULER_TEXT_COLOR);
				String msg = sdf.format(calendar.getTime());
				gc.drawString(msg, xPosition + 4, yBottomPosition + 3, true);
			}
			xPosition += width;
			if (xPosition > xMaxPosition) {
				break;
			}
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
	}
}
