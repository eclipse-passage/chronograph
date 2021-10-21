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
 * Styler class dedicated to coloring statuses on the stage
 *
 */
public class StatusStyler implements Styler {
	private static final Display DISPLAY = Display.getDefault();
	public static Color LICENSE_EXPIRED_COLOR;
	public static Color LICENSE_ACTUAL_COLOR;

	@Override
	public void initClassicTheme() {
		LICENSE_EXPIRED_COLOR = new Color(DISPLAY, new RGB(165, 10, 10));
		LICENSE_ACTUAL_COLOR = new Color(DISPLAY, new RGB(0, 102, 0));
	}

	@Override
	public void initDarkTheme() {
		LICENSE_EXPIRED_COLOR = new Color(DISPLAY, new RGB(165, 10, 10));
		LICENSE_ACTUAL_COLOR = new Color(DISPLAY, new RGB(0, 102, 0));
	}
}
