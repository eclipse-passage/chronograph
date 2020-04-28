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

package org.eclipse.chronograph.swt.internal.stage;

import org.eclipse.chronograph.internal.api.Area;
import org.eclipse.swt.graphics.Rectangle;

/**
 * 
 * Utility class intended to convert {@link Area} to {@link Rectangle}
 *
 */
public class ChronographStageUtil {
	public static Rectangle areaToRectangle(Area area) {
		return new Rectangle(area.x(), area.y(), area.width(), area.height());
	}
}
