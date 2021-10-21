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

import java.time.LocalDate;

import org.eclipse.chronograph.internal.api.graphics.Brick;
import org.eclipse.chronograph.internal.base.UnitConverter;
import org.eclipse.chronograph.internal.swt.renderers.api.ChronographObjectContentRenderer;
import org.eclipse.chronograph.internal.swt.stylers.BrickStyler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

/**
 * 
 * Render implementation of {@link Brick} object
 *
 * @param <D> - type of data object
 */
public class ObjectContentRendererImpl<D> implements ChronographObjectContentRenderer {

	@Override
	public void draw(Brick object, GC gc, Rectangle bounds) {
		drawContent(object, gc, bounds, BrickStyler.getColorBottom());
	}

	private void drawContent(Brick object, GC gc, Rectangle bounds, Color color) {
		LocalDate now = LocalDate.now();
		LocalDate start = UnitConverter.unitsToLocalDate((int) object.position().start());
		LocalDate end = UnitConverter.unitsToLocalDate((int) object.position().end());
		gc.setAntialias(SWT.ON);
		gc.setBackground(BrickStyler.getColorBottom());

		if (now.isAfter(start) && now.isBefore(end)) {
			// active task
			gc.setForeground(color);
			gc.setBackground(color);
		} else {
			// not active
			gc.setForeground(BrickStyler.getColorTop());
			gc.setBackground(BrickStyler.getNonActiveColor());
		}
		gc.fillRoundRectangle(bounds.x, bounds.y, bounds.width, bounds.height, bounds.height, bounds.height);

		gc.setForeground(BrickStyler.getActiveColorTop());
		gc.setBackground(color);
		gc.fillOval(bounds.x, bounds.y, bounds.height, bounds.height);
		gc.setBackground(BrickStyler.getOvalColor());
		gc.fillOval(bounds.x + bounds.width - bounds.height, bounds.y, bounds.height, bounds.height);
	}

	@Override
	public void draw(Brick obj, GC gc, Rectangle bounds, Color color) {
		if (color == null) {
			draw(obj, gc, bounds);
		} else {
			drawContent(obj, gc, bounds, color);
		}
	}

	@Override
	public void drawSelected(Brick object, GC gc, Rectangle bounds, Color color, Rectangle grBounds) {
		gc.setLineStyle(SWT.LINE_DOT);
		gc.setForeground(BrickStyler.getColorBorder());
		gc.setBackground(color);
		int y1 = bounds.y + bounds.height / 2;
		int y2 = grBounds.y + grBounds.height;
		gc.drawLine(bounds.x, y1, bounds.x, y2);
		gc.drawLine(bounds.x + bounds.width, y1, bounds.x + bounds.width, y2);
		gc.setAlpha(100);
		gc.fillRoundRectangle(bounds.x, bounds.y, bounds.width, bounds.height, bounds.height, bounds.height);
		gc.drawRoundRectangle(bounds.x, bounds.y, bounds.width, bounds.height, bounds.height, bounds.height);
		gc.setAlpha(250);
	}
}
