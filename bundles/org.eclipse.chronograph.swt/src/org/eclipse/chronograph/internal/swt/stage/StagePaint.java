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

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;

final class StagePaint implements PaintListener {

	private final ChronographCanvas sceneComposer;

	public StagePaint(ChronographCanvas sceneComposer) {
		this.sceneComposer = sceneComposer;
	}

	@Override
	public void paintControl(PaintEvent event) {
		sceneComposer.repaint(event);
	}

}
