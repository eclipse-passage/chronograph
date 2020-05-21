/*******************************************************************************
 * Copyright (c) 2020 ArSysOp
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0/.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     ArSysOp - initial API and implementation
 *******************************************************************************/
package org.eclipse.chronograph.internal.swt.renderers.impl;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

public class Labels {

	String fit(String label, int limit, GC gc) {
		StringBuilder builder = new StringBuilder();
		String ellipsis = "..."; //$NON-NLS-1$
		Point point = gc.stringExtent(ellipsis);
		int consumed = 0;
		for (char ch : label.toCharArray()) {
			consumed += gc.getCharWidth(ch);
			if (consumed < limit - point.x) {
				builder.append(ch);
			} else {
				builder.append(ellipsis);
				break;
			}
		}
		return builder.toString();
	}
}
