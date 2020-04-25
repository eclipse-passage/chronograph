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

public class GroupStyler {
	public static final int GROUP_HEIGHT_DEFAULT = 100;
	private static final Display DISPLAY = Display.getDefault();
	public static Color GROUP_TOP_COLOR;
	public static Color GROUP_BTM_COLOR;
	public static Color TEXT_COLOR;

	public static void darkTheme() {
		GROUP_TOP_COLOR = new Color(DISPLAY, new RGB(88, 110, 117));
		GROUP_BTM_COLOR = new Color(DISPLAY, new RGB(0, 100, 117));
		TEXT_COLOR = new Color(DISPLAY, new RGB(253, 246, 227));
	}

	public static void classicTheme() {
		GROUP_TOP_COLOR = new Color(DISPLAY, new RGB(220, 220, 220));
		GROUP_BTM_COLOR = new Color(DISPLAY, new RGB(170, 170, 170));
		TEXT_COLOR = new Color(DISPLAY, new RGB(10, 10, 10));
	}
}
