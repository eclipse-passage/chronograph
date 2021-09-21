/*******************************************************************************
 * Copyright (c) 2020 ArSysOp and Others
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 *
 * Contributors:
 *     Sergei Kovalchuk<sergei.kovalchuk@arsysop.ru> - initial API and implementation
 *******************************************************************************/
package org.eclipse.chronograph.internal.base;

import org.eclipse.chronograph.internal.api.graphics.Area;

/**
 * 
 * Implementation of {@link Area} interface intended to store graphical object
 * location
 */
public class AreaImpl implements Area {

	private final int x;
	private final int y;
	private final int width;
	private final int height;

	public AreaImpl(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public int width() {
		return width;
	}

	@Override
	public int height() {
		return height;
	}

	@Override
	public int x() {
		return x;
	}

	@Override
	public int y() {
		return y;
	}

	@Override
	public String toString() {
		return String.format("Area: x=%d, y=%d, w=%d, h=%d ,dx=%d ,dy=%d", x, y, width, height, x + width, y + height); //$NON-NLS-1$
	}
}
