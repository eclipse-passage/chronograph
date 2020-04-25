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

import static org.eclipse.chronograph.swt.GroupStyler.GROUP_HEIGHT_DEFAULT;

import org.eclipse.chronograph.internal.api.Section;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class SectionStyler {
	private static final Display DISPLAY = Display.getDefault();
	private static final int SECTION_HEIGHT = 100;
	private static final int SECTION_WIDTH = 30;
	private static final int SECTION_SEPARATOR_HEIGTH = 5;

	public static Color SECTION_TOP_COLOR;
	public static Color SECTION_BTM_COLOR;
	public static Color SECTION_TEXT_COLOR;

	public static int getSectionHeight(Section section) {
		if (section.groups() != null && section.groups().isEmpty()) {
			return section.groups().size() * GROUP_HEIGHT_DEFAULT;
		}
		return SECTION_HEIGHT;
	}

	public static int getSectionSeparatorHeight() {
		return SECTION_SEPARATOR_HEIGTH;
	}

	public static int getSectionWidth() {
		return SECTION_WIDTH;
	}

	public static void classicTheme() {
		SECTION_TOP_COLOR = new Color(DISPLAY, new RGB(220, 220, 220));
		SECTION_BTM_COLOR = new Color(DISPLAY, new RGB(150, 150, 150));
		SECTION_TEXT_COLOR = new Color(DISPLAY, new RGB(10, 10, 10));
	}

	public static void darkTheme() {
		SECTION_TOP_COLOR = new Color(DISPLAY, new RGB(88, 110, 117));
		SECTION_BTM_COLOR = new Color(DISPLAY, new RGB(0, 100, 117));
		SECTION_TEXT_COLOR = new Color(DISPLAY, new RGB(253, 246, 227));
	}
}
