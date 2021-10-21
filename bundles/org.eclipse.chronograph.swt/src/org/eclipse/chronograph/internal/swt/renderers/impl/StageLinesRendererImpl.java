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

import java.util.Calendar;
import java.util.TimeZone;

import org.eclipse.chronograph.internal.base.UnitConverter;
import org.eclipse.chronograph.internal.swt.renderers.api.ChronographStageLinesRenderer;
import org.eclipse.chronograph.internal.swt.stylers.GroupStyler;
import org.eclipse.chronograph.internal.swt.stylers.RulerStyler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

/**
 * 
 * Render implementation for stage lines
 *
 */
public class StageLinesRendererImpl implements ChronographStageLinesRenderer {

	private final Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
	private final Calendar currentDate = Calendar.getInstance(TimeZone.getDefault());

	@Override
	public void draw(GC gc, Rectangle bounds, int scale, int width, int tiksOffset, int xAxis) {
		int xMaxPosition = bounds.width + bounds.x;
		int yBottomPosition = bounds.y + bounds.height - RulerStyler.RULER_MOUNTH_HEIGHT
				- RulerStyler.RULER_YEAR_HEIGHT;
		int x = bounds.x;
		int y = bounds.y + GroupStyler.getGroupSeparatorHeight();
		// gc.setAntialias(SWT.ON);
		calendar.clear();
		calendar.set(Calendar.YEAR, 2019);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 01);
		calendar.add(Calendar.DATE, tiksOffset);
		Calendar xAxisCalendar = UnitConverter.unitsToCalendar(tiksOffset + (xAxis / width));

		while (true) {
			if (currentDate.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
					&& currentDate.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR)) {
				gc.setForeground(RulerStyler.RULER_CUREENT_DAY_COLOR_BTM);
				gc.setBackground(RulerStyler.RULER_CUREENT_DAY_COLOR_TOP);
				gc.setAlpha(100);
				gc.fillRectangle(x, y, width, bounds.height);
			}

			if (xAxisCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
					&& xAxisCalendar.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR)) {
				gc.setForeground(RulerStyler.RULER_CONTENT_2_COLOR);
				gc.setBackground(RulerStyler.RULER_CONTENT_2_COLOR);
				gc.setAlpha(100);
				gc.fillRectangle(x, y, width, bounds.height);
			}

			gc.setForeground(RulerStyler.RULER_CONTENT_2_COLOR);
			gc.setLineStyle(SWT.LINE_DOT);
			if (scale < 2) {
				if (calendar.get(Calendar.DAY_OF_YEAR) == 1) {
					if (x > GroupStyler.getGroupWith()) {
						gc.drawLine(x, y, x, yBottomPosition);
					}
				}
			}
			if (scale >= 2) {
				if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
					if (x > GroupStyler.getGroupWith()) {
						gc.setAlpha(200);
						gc.drawLine(x, y, x, yBottomPosition);
					}
				}
				if (scale > 20) {
					if (x > GroupStyler.getGroupWith()) {
						gc.setAlpha(100);
						gc.drawLine(x, y, x, yBottomPosition);
					}
				}
			}
			gc.setAlpha(255);
			x += width;
			if (x > xMaxPosition) {
				break;
			}
			calendar.add(Calendar.DAY_OF_MONTH, 1);

		}
	}
}
