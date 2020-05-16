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
 *     Sergei Kovalchuk <sergei.kovalchuk@arsysop.ru> - initial API and implementation
 *******************************************************************************/
package org.eclipse.chronograph.api.test;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Test input faced
 *
 */
public class TestInputObjects {
	private static List<TestInputObject> input = new ArrayList<>();
	static {
		input.add(new TestInputObject("test.id.1", "test.container.id.1", "test.subcontainer.id.1", "test.parent.id.1", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				"test.name.1", "test.description.1", 1, 2)); //$NON-NLS-1$ //$NON-NLS-2$
		input.add(new TestInputObject("test.id.2", "test.container.id.2", "test.subcontainer.id.2", "test.parent.id.2", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				"test.name.2", "test.description.2", 2, 3)); //$NON-NLS-1$ //$NON-NLS-2$
		input.add(new TestInputObject("test.id.3", "test.container.id.3", "test.subcontainer.id.3", "test.parent.id.3", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				"test.name.3", "test.description.3", 3, 4)); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public static List<TestInputObject> getInput() {
		return input;
	}

}
