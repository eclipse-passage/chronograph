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

import org.eclipse.chronograph.internal.swt.StatusStyler;
import org.eclipse.chronograph.internal.swt.renderers.api.ChronographStatusRenderer;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

/**
 * 
 * Render implementation for stage statuses
 *
 */
public class StatusRendererImpl implements ChronographStatusRenderer {

	@Override
	public void draw(GC gc, Rectangle bounds, int actual, int expired, int hintY) {

		gc.setForeground(StatusStyler.LICENSE_EXPIRED_COLOR);
		String msg = "Expired: " + expired; //$NON-NLS-1$
		Point pointExpired = gc.stringExtent(msg);
		int x = bounds.x + bounds.width - pointExpired.x - 5;
		gc.drawText(msg, x, bounds.y + pointExpired.y / 2 - hintY, true);

		gc.setForeground(StatusStyler.LICENSE_ACTUAL_COLOR);
		msg = "Actual: " + actual; //$NON-NLS-1$
		Point pointActual = gc.stringExtent(msg);
		gc.drawText(msg, x - pointActual.x - 10, bounds.y + pointActual.y / 2 - hintY, true);
	}

}
