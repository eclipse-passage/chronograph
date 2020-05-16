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

import org.eclipse.chronograph.internal.api.graphics.Brick;
import org.eclipse.chronograph.internal.api.graphics.Position;
import org.eclipse.chronograph.internal.base.UnitConverter;
import org.eclipse.chronograph.internal.swt.BrickStyler;
import org.eclipse.chronograph.internal.swt.StageStyler;
import org.eclipse.chronograph.internal.swt.renderers.api.ChronographObjectLabelRenderer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

/**
 * 
 * Render implementation for label for {@link Brick} object
 *
 */
public class ObjectLabelRendererImpl implements ChronographObjectLabelRenderer<Brick> {

	@Override
	public void drawLabel(String label, Position brickPosition, GC gc, Rectangle objectBounds, int vOffset) {
		FontMetrics fontMetrics = gc.getFontMetrics();
		int height = fontMetrics.getHeight();
		gc.setForeground(StageStyler.STAGE_TEXT_COLOR);
		gc.setLineStyle(SWT.LINE_SOLID);
		int mediana = objectBounds.height / 2 - height / 2;
		gc.drawString(label, objectBounds.x + height / 2, objectBounds.y - height, true);
		String msg = String.valueOf(UnitConverter.unitsToLocalDate((int) brickPosition.start()));
		gc.drawString(msg, objectBounds.x + BrickStyler.getHeight(), objectBounds.y + mediana, true);
		msg = String.valueOf(UnitConverter.unitsToLocalDate((int) brickPosition.end()));
		Point msgExtent = gc.textExtent(msg);
		gc.drawString(msg, objectBounds.x + objectBounds.width - (msgExtent.x + msgExtent.y), objectBounds.y + mediana,
				true);
		gc.setLineStyle(SWT.LINE_SOLID);
		gc.setForeground(StageStyler.STAGE_TOP_COLOR);
		gc.drawLine(objectBounds.x, 0, objectBounds.x, 20);
		gc.setForeground(BrickStyler.getColorBorder());
		gc.drawLine(objectBounds.x + objectBounds.width, objectBounds.y + objectBounds.height,
				objectBounds.x + objectBounds.width, objectBounds.y + objectBounds.height);
		gc.setLineStyle(SWT.LINE_SOLID);
	}
}
