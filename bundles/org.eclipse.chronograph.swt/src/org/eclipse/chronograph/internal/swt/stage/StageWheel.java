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

import java.time.LocalDate;

import org.eclipse.chronograph.internal.base.UnitConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

final class StageWheel implements Listener {

	private final Stage stage;

	public StageWheel(Stage stage) {
		this.stage = stage;
	}

	@Override
	public void handleEvent(Event event) {
		if (event.stateMask == SWT.CTRL) {
			LocalDate dateTime = stage.computeDateByCursor(event.x);
			if (event.count > 0) {
				stage.scaleDown();
			} else {
				stage.scaleUp();
			}
			stage.navigateToUnit(UnitConverter.localDatetoUnits(dateTime), event.x);

		} else if (event.stateMask == SWT.ALT) {
			if (event.count > 0) {
				stage.setZoomLevelDown();
			} else {
				stage.setZoomLevelUp();
			}
		} else {
			stage.verticalScroll(event);
		}
	}

}
