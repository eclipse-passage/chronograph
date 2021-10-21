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
import org.eclipse.swt.widgets.Group;

/**
 * 
 * Styler class for coloring {@link Group}
 *
 */
public class GroupStyler implements Styler {
	private static final Display DISPLAY = Display.getDefault();

	public static final int GROUP_HEIGHT_DEFAULT = 50;

	public static Color GROUP_BORDER_COLOR;
	public static Color GROUP_CONTENT_COLOR;
	public static Color GROUP_TEXT_COLOR;
	public static Color GROUP_SELECTION_COLOR;
	public static Color GROUP_SELECTION_1_COLOR;
	public static Color GROUP_SELECTION_2_COLOR;
	public static Color GROUP_SELECTION_3_COLOR;
	public static Color GROUP_SHADOW_COLOR;

	@Override
	public void initClassicTheme() {
		GROUP_BORDER_COLOR = new Color(DISPLAY, new RGB(240, 220, 250));
		GROUP_CONTENT_COLOR = new Color(DISPLAY, new RGB(170, 170, 170));
		GROUP_TEXT_COLOR = new Color(DISPLAY, new RGB(10, 10, 10));
		GROUP_SELECTION_COLOR = new Color(DISPLAY, new RGB(240, 248, 255));
		GROUP_SELECTION_1_COLOR = new Color(DISPLAY, new RGB(240, 248, 255));
		GROUP_SELECTION_2_COLOR = new Color(DISPLAY, new RGB(30, 129, 176));
		GROUP_SELECTION_3_COLOR = new Color(DISPLAY, new RGB(75, 207, 224));
		GROUP_SHADOW_COLOR = new Color(DISPLAY, new RGB(233, 228, 255));

	}

	@Override
	public void initDarkTheme() {
		GROUP_BORDER_COLOR = new Color(DISPLAY, new RGB(88, 110, 117));
		GROUP_CONTENT_COLOR = new Color(DISPLAY, new RGB(0, 100, 117));
		GROUP_TEXT_COLOR = new Color(DISPLAY, new RGB(253, 246, 227));
	}

	public static int getGroupSeparatorHeight() {
		return 5;
	}

	public static int getGroupWith() {
		return 30;
	}
}
