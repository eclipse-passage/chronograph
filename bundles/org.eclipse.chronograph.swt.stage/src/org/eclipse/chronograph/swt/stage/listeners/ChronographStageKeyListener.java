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
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

/**
 * 
 * Implementations of {@link KeyListener}
 *
 * @param <T>
 */
public class ChronographStageKeyListener<T extends ChronographStage> implements KeyListener {

	private final T stage;

	public ChronographStageKeyListener(T stage) {
		this.stage = stage;

	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (event.stateMask == SWT.ALT || event.keyCode == SWT.ALT) {

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
