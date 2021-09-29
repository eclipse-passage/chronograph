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

import org.eclipse.chronograph.internal.api.representation.Styler;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * 
 * Styler class dedicated to coloring rulers
 * 
 *
 */
public class RulerStyler implements Styler {
	public static final int RULER_DAY_HEIGHT = 20;
	public static final int RULER_MOUNTH_HEIGHT = 20;
	public static final int RULER_YEAR_HEIGHT = 20;

	public static Color RULER_CONTENT_COLOR;
	public static Color RULER_CONTENT_2_COLOR;
	public static Color RULER_BORDER_COLOR;
	public static Color RULER_TEXT_COLOR;

	public static Color RULER_CUREENT_DAY_COLOR_TOP;
	public static Color RULER_CUREENT_DAY_COLOR_BTM;
	private static final Display DISPLAY = Display.getDefault();

	@Override
	public void initClassicTheme() {
		RULER_CONTENT_COLOR = new Color(DISPLAY, new RGB(245, 240, 250));
		RULER_CONTENT_2_COLOR = new Color(DISPLAY, new RGB(240, 220, 250));
		RULER_BORDER_COLOR = new Color(DISPLAY, new RGB(220, 220, 220));
		RULER_TEXT_COLOR = new Color(DISPLAY, new RGB(50, 80, 130));
		RULER_CUREENT_DAY_COLOR_TOP = new Color(DISPLAY, new RGB(240, 120, 80));
		RULER_CUREENT_DAY_COLOR_BTM = new Color(DISPLAY, new RGB(220, 220, 220));
	}

	@Override
	public void initDarkTheme() {
		RULER_CONTENT_COLOR = new Color(DISPLAY, new RGB(106, 155, 164));
		RULER_CONTENT_2_COLOR = new Color(DISPLAY, new RGB(55, 99, 124));
		RULER_BORDER_COLOR = new Color(DISPLAY, new RGB(0, 100, 117));
		RULER_TEXT_COLOR = new Color(DISPLAY, new RGB(220, 220, 220));
		RULER_CUREENT_DAY_COLOR_TOP = new Color(DISPLAY, new RGB(255, 148, 0));
		RULER_CUREENT_DAY_COLOR_BTM = new Color(DISPLAY, new RGB(55, 99, 120));

	}
}
