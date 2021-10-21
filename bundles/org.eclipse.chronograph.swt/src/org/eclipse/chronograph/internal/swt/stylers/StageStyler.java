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

package org.eclipse.chronograph.internal.swt.stylers;

import org.eclipse.chronograph.internal.api.representation.Styler;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * 
 * Styler class dedicated to coloring main stage
 *
 */
public class StageStyler implements Styler {
	private final static Display DISPLAY = Display.getDefault();
	public static final int DEFAULT_ZOOM_VALUE = 1;
	public static final int DEFAULT_SCALE_VALUE = 1;
	private static int STAGE_HEADER_HEIGHT = 10;
	public static Color STAGE_BG_COLOR;
	public static Color STAGE_TOP_COLOR;
	public static Color STAGE_TEXT_COLOR;

	public static int getStageHeaderHeight() {
		return STAGE_HEADER_HEIGHT;
	}

	@Override
	public void initClassicTheme() {
		STAGE_BG_COLOR = new Color(DISPLAY, new RGB(253, 254, 254));
		STAGE_TOP_COLOR = new Color(DISPLAY, new RGB(253, 254, 254));
		STAGE_TEXT_COLOR = new Color(DISPLAY, new RGB(10, 10, 10));
	}

	@Override
	public void initDarkTheme() {
		STAGE_BG_COLOR = new Color(DISPLAY, new RGB(39, 39, 39));
		STAGE_TOP_COLOR = new Color(DISPLAY, new RGB(88, 110, 117));
		STAGE_TEXT_COLOR = new Color(DISPLAY, new RGB(253, 246, 227));
	}

}
