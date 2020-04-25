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

public class RullerStyler {
	public static final int RULER_DAY_HEIGHT = 20;
	public static final int RULER_MOUNTH_HEIGHT = 20;
	public static final int RULER_YEAR_HEIGHT = 20;

	public static Color RULER_TOP_COLOR;
	public static Color RULER_BTM_COLOR;
	public static Color RULER_BRD_COLOR;
	public static Color RULER_TEXT_COLOR;

	public static Color RULER_CUREENT_DAY_COLOR_TOP;
	public static Color RULER_CUREENT_DAY_COLOR_BTM;
	private static final Display DISPLAY = Display.getDefault();

	public void darkTheme() {
		RULER_TOP_COLOR = new Color(DISPLAY, new RGB(106, 155, 164));
		RULER_BTM_COLOR = new Color(DISPLAY, new RGB(55, 99, 124));
		RULER_BRD_COLOR = new Color(DISPLAY, new RGB(220, 220, 220));
		RULER_TEXT_COLOR = new Color(DISPLAY, new RGB(220, 220, 220));
		RULER_CUREENT_DAY_COLOR_TOP = new Color(DISPLAY, new RGB(82, 148, 226));
		RULER_CUREENT_DAY_COLOR_BTM = new Color(DISPLAY, new RGB(64, 69, 82));

	}

	public void classicTheme() {
		RULER_TOP_COLOR = new Color(DISPLAY, new RGB(106, 155, 164));
		RULER_BTM_COLOR = new Color(DISPLAY, new RGB(55, 99, 124));
		RULER_BRD_COLOR = new Color(DISPLAY, new RGB(220, 220, 220));
		RULER_TEXT_COLOR = new Color(DISPLAY, new RGB(220, 220, 220));
		RULER_CUREENT_DAY_COLOR_TOP = new Color(DISPLAY, new RGB(82, 148, 226));
		RULER_CUREENT_DAY_COLOR_BTM = new Color(DISPLAY, new RGB(64, 69, 82));
	}
}
