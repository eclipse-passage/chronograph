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
package org.eclipse.chronograph.swt.stage.listeners;

import org.eclipse.chronograph.internal.swt.stage.ChronographStage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class ChronographStageMouseWheelListener<T extends ChronographStage> implements Listener {

	private final T stage;

	public ChronographStageMouseWheelListener(T stage) {
		this.stage = stage;
	}

	@Override
	public void handleEvent(Event event) {
		if (event.stateMask == SWT.MOD1) {
			if (event.count > 0) {
				stage.scaleDown();
			} else {
				stage.scaleUp();
			}
		} else {
			stage.verticalScroll(event);
		}
	}

}
