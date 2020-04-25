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
package org.eclipse.chronograph.internal.swt.renderers.impl.base;

import org.eclipse.chronograph.internal.api.Brick;
import org.eclipse.chronograph.internal.api.Group;
import org.eclipse.chronograph.internal.api.Position;
import org.eclipse.chronograph.internal.base.UnitConverter;
import org.eclipse.chronograph.internal.swt.BrickStyler;
import org.eclipse.chronograph.internal.swt.StageStyler;
import org.eclipse.chronograph.internal.swt.renderers.api.ChronographObjectLabelRenderer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

/**
 * 
 * Render implementation for label for {@link Brick} object
 *
 */
public class ObjectLabelRendererImpl implements ChronographObjectLabelRenderer<Brick> {

	@Override
	public void drawLabel(String label, Position brickPosition, GC gc, Rectangle objectBounds, int vOffset) {
		gc.setForeground(StageStyler.STAGE_TEXT_COLOR);
		gc.setLineStyle(SWT.LINE_SOLID);
		gc.drawString(label, objectBounds.x + 5, objectBounds.y - 15, true);

		String msg = String.valueOf(UnitConverter.unitsToLocalDate((int) brickPosition.start()));
		gc.drawString(msg, objectBounds.x + 5, objectBounds.y + objectBounds.height + 5, true);

		msg = String.valueOf(UnitConverter.unitsToLocalDate((int) brickPosition.end()));
		gc.drawString(msg, objectBounds.x + objectBounds.width - msg.length() - 55,
				objectBounds.y + objectBounds.height / 6, true);
		gc.setLineStyle(SWT.LINE_SOLID);
		gc.setForeground(StageStyler.STAGE_TOP_COLOR);
		gc.drawLine(objectBounds.x, 0, objectBounds.x, 20);
		gc.setForeground(BrickStyler.getColorBorder());
		gc.drawLine(objectBounds.x, objectBounds.y - 20, objectBounds.x, objectBounds.y + objectBounds.height + 20);
		gc.drawLine(objectBounds.x + objectBounds.width, objectBounds.y + objectBounds.height,
				objectBounds.x + objectBounds.width, objectBounds.y + objectBounds.height);
		gc.setLineStyle(SWT.LINE_SOLID);
	}
}
