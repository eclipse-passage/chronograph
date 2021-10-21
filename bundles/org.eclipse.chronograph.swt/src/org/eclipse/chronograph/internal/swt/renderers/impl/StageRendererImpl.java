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

import org.eclipse.chronograph.internal.swt.renderers.api.ChronographStageRenderer;
import org.eclipse.chronograph.internal.swt.stylers.StageStyler;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

/**
 * 
 * Render implementation for stage
 *
 */
public class StageRendererImpl implements ChronographStageRenderer {

	@Override
	public void draw(GC gc, Rectangle bounds) {
		gc.setForeground(StageStyler.STAGE_TOP_COLOR);
		gc.setBackground(StageStyler.STAGE_BG_COLOR);
		gc.fillRectangle(bounds);
		gc.drawRectangle(bounds);
	}

}
