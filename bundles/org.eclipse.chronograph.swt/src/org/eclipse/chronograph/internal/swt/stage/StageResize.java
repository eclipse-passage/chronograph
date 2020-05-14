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
package org.eclipse.chronograph.internal.swt.stage;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

final class StageResize<T extends ChronographStage> implements Listener {

	private final T stage;

	public StageResize(T stage) {
		this.stage = stage;
	}

	@Override
	public void handleEvent(Event event) {
		stage.calculateObjectBounds();
		stage.handleResize();
		stage.updateScrollers();
	}
}
