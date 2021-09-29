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

import org.eclipse.chronograph.internal.api.data.ContentDecorationProvider;
import org.eclipse.chronograph.internal.api.graphics.Brick;
import org.eclipse.chronograph.internal.base.UnitConverter;
import org.eclipse.chronograph.internal.swt.BrickStyler;
import org.eclipse.chronograph.internal.swt.providers.AbstractContentDecorationProvider;
import org.eclipse.chronograph.internal.swt.renderers.api.ChronographObjectContentRenderer;
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
	public void draw(Brick object, GC gc, Rectangle bounds, int vOffset) {
		drawContent(object, gc, bounds, BrickStyler.getColorBottom());
	}

	private void drawContent(Brick object, GC gc, Rectangle bounds, Color color) {
		LocalDate now = LocalDate.now();
		LocalDate start = UnitConverter.unitsToLocalDate((int) object.position().start());
		LocalDate end = UnitConverter.unitsToLocalDate((int) object.position().end());
		gc.setAntialias(SWT.ON);
		gc.setBackground(BrickStyler.getColorBottom());

		if (now.isAfter(start) && now.isBefore(end)) {
			gc.setForeground(color);
			gc.setBackground(color);
		} else {
			gc.setForeground(BrickStyler.getColorTop());
			gc.setBackground(BrickStyler.getColorBottom());
		}
		gc.fillRoundRectangle(bounds.x, bounds.y, bounds.width, bounds.height, bounds.height, bounds.height);

		gc.setForeground(BrickStyler.getActiveColorTop());
		gc.setBackground(BrickStyler.getOvalColor());
		gc.fillOval(bounds.x - bounds.height / 10, bounds.y, bounds.height, bounds.height);
	}

	@Override
	public void draw(ContentDecorationProvider provider, Brick obj, GC gc, Rectangle bounds, int vOffset,
			boolean isSelected) {
		if (provider instanceof AbstractContentDecorationProvider) {
			AbstractContentDecorationProvider decoratorProvider = (AbstractContentDecorationProvider) provider;
			Color contentColor = decoratorProvider.getContentColor(obj.data());
			if (isSelected) {
				contentColor = decoratorProvider.getSelectionColor(obj.data());
			}
			drawContent(obj, gc, bounds, contentColor);
		}
	}
}
