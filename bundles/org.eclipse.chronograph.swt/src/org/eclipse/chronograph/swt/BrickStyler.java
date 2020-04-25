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

package org.eclipse.chronograph.swt;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class BrickStyler {

	private static final Display DISPLAY = Display.getDefault();
	private static int BRICK_HEIGHT_DEFAULT;
	private static Color COLOR_TOP;
	private static Color COLOR_BOTTOM;
	private static Color SELECTED_COLOR_TOP;
	private static Color SELECTED_COLOR_BOTTOM;
	private static Color COLOR_BORDER;
	private static Color COLOR_TEXT;
	private static Color COLOR_CALL_OUT;
	private static Color ACTIVE_COLOR_TOP;
	private static Color ACTIVE_COLOR_BOTTOM;

	public static void classicTheme() {
		COLOR_TOP = new Color(DISPLAY, new RGB(195, 245, 180));
		COLOR_BOTTOM = new Color(DISPLAY, new RGB(100, 150, 90));
		SELECTED_COLOR_TOP = new Color(DISPLAY, new RGB(100, 200, 110));
		SELECTED_COLOR_BOTTOM = new Color(DISPLAY, new RGB(100, 130, 152));
		ACTIVE_COLOR_TOP = new Color(DISPLAY, new RGB(80, 250, 82));
		ACTIVE_COLOR_BOTTOM = new Color(DISPLAY, new RGB(80, 140, 82));
		COLOR_BORDER = new Color(DISPLAY, new RGB(95, 95, 95));
		COLOR_TEXT = new Color(DISPLAY, new RGB(220, 220, 220));
		COLOR_CALL_OUT = new Color(DISPLAY, new RGB(20, 20, 20));
		BRICK_HEIGHT_DEFAULT = 26;
	}

	public void darkTheme() {
		COLOR_TOP = new Color(DISPLAY, new RGB(136, 105, 184));
		COLOR_BOTTOM = new Color(DISPLAY, new RGB(64, 36, 89));
		SELECTED_COLOR_TOP = new Color(DISPLAY, new RGB(136, 105, 184));
		SELECTED_COLOR_BOTTOM = new Color(DISPLAY, new RGB(64, 36, 89));
		ACTIVE_COLOR_TOP = new Color(DISPLAY, new RGB(236, 50, 184));
		ACTIVE_COLOR_BOTTOM = new Color(DISPLAY, new RGB(104, 136, 89));
		COLOR_BORDER = new Color(DISPLAY, new RGB(190, 155, 184));
		COLOR_TEXT = new Color(DISPLAY, new RGB(220, 220, 220));
		COLOR_CALL_OUT = new Color(DISPLAY, new RGB(220, 220, 220));
		BRICK_HEIGHT_DEFAULT = 26;
	}

	public static int getHeight() {
		return BRICK_HEIGHT_DEFAULT;
	}

	public static Color getColorTop() {
		return COLOR_TOP;
	}

	public static Color getColorBottom() {
		return COLOR_BOTTOM;
	}

	public static Color getColorTopSelected() {
		return SELECTED_COLOR_TOP;
	}

	public static Color getColorBottomSelected() {
		return SELECTED_COLOR_BOTTOM;
	}

	public static Color getColorBorder() {
		return COLOR_BORDER;
	}

	public static Color getColorText() {
		return COLOR_TEXT;
	}

	public static Color getColorCallout() {
		return COLOR_CALL_OUT;
	}

	public static Color getActiveColorTop() {
		return ACTIVE_COLOR_TOP;
	}

	public static Color getActiveColorBottom() {
		return ACTIVE_COLOR_BOTTOM;
	}

}
