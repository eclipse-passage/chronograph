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

package org.eclipse.chronograph.internal.swt;

import org.eclipse.chronograph.internal.api.graphics.Brick;
import org.eclipse.chronograph.internal.api.representation.Styler;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * 
 * Styler class dedicated to coloring {@link Brick}
 *
 */
public class BrickStyler implements Styler {

	private static final Display DISPLAY = Display.getDefault();
	private static Color DEF_TOP_COLOR;
	private static Color DEF_BOTTOM_COLOR;
	private static Color ACTIVE_TOP_COLOR;
	private static Color ACTIVE_BOTTOM_COLOR;
	private static Color SELECTED_TOP_COLOR;
	private static Color SELECTED_BOTTOM_COLOR;
	private static Color BORDER_COLOR;
	private static Color TEXT_COLOR;
	private static Color COLOR_CALL_OUT;
	private static Color OVAL_COLOR;
	private static Color DESCR_BCKG_COLOR;

	@Override
	public void initClassicTheme() {
		DEF_TOP_COLOR = new Color(DISPLAY, new RGB(195, 245, 180));
		DEF_BOTTOM_COLOR = new Color(DISPLAY, new RGB(100, 150, 90));
		SELECTED_TOP_COLOR = new Color(DISPLAY, new RGB(110, 190, 82));
		SELECTED_BOTTOM_COLOR = new Color(DISPLAY, new RGB(80, 140, 82));
		ACTIVE_TOP_COLOR = new Color(DISPLAY, new RGB(110, 190, 82));
		ACTIVE_BOTTOM_COLOR = new Color(DISPLAY, new RGB(80, 140, 82));
		BORDER_COLOR = new Color(DISPLAY, new RGB(95, 95, 95));
		TEXT_COLOR = new Color(DISPLAY, new RGB(10, 10, 10));
		COLOR_CALL_OUT = new Color(DISPLAY, new RGB(100, 100, 100));
		OVAL_COLOR = new Color(DISPLAY, new RGB(213, 219, 219));
		DESCR_BCKG_COLOR = new Color(DISPLAY, new RGB(240, 242, 252));
	}

	@Override
	public void initDarkTheme() {
		DEF_TOP_COLOR = new Color(DISPLAY, new RGB(136, 105, 184));
		DEF_BOTTOM_COLOR = new Color(DISPLAY, new RGB(64, 36, 89));
		SELECTED_TOP_COLOR = new Color(DISPLAY, new RGB(136, 105, 184));
		SELECTED_BOTTOM_COLOR = new Color(DISPLAY, new RGB(64, 36, 89));
		ACTIVE_TOP_COLOR = new Color(DISPLAY, new RGB(149, 140, 184));
		ACTIVE_BOTTOM_COLOR = new Color(DISPLAY, new RGB(104, 136, 89));
		BORDER_COLOR = new Color(DISPLAY, new RGB(190, 155, 184));
		TEXT_COLOR = new Color(DISPLAY, new RGB(220, 220, 220));
		COLOR_CALL_OUT = new Color(DISPLAY, new RGB(220, 220, 220));
	}

	public static int getHeight() {
		GC gc = new GC(DISPLAY);
		FontMetrics fontMetrics = gc.getFontMetrics();
		int height = fontMetrics.getHeight();
		gc.dispose();
		return height * 2;
	}

	public static Color getColorTop() {
		return DEF_TOP_COLOR;
	}

	public static Color getColorBottom() {
		return DEF_BOTTOM_COLOR;
	}

	public static Color getColorTopSelected() {
		return SELECTED_TOP_COLOR;
	}

	public static Color getColorBottomSelected() {
		return SELECTED_BOTTOM_COLOR;
	}

	public static Color getColorBorder() {
		return BORDER_COLOR;
	}

	public static Color getColorText() {
		return TEXT_COLOR;
	}

	public static Color getColorCallout() {
		return COLOR_CALL_OUT;
	}

	public static Color getActiveColorTop() {
		return ACTIVE_TOP_COLOR;
	}

	public static Color getOvalColor() {
		return OVAL_COLOR;
	}

	public static Color getActiveColorBottom() {
		return ACTIVE_BOTTOM_COLOR;
	}

	public static Color getDescriptionColor() {
		return DESCR_BCKG_COLOR;
	}

}
