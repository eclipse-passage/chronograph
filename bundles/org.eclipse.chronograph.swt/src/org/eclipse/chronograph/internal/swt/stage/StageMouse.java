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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tracker;

/**
 * 
 * Grouping of implementations for {@link MouseListener}'s
 *
 * @param <T>
 */
final class StageMouse implements MouseListener, MouseMoveListener, MouseTrackListener {

	private Stage stage;
	private static final Cursor CURSOR_NONE = new Cursor(Display.getDefault(), SWT.NONE);
	private static final Cursor CURSOR_HAND = new Cursor(Display.getDefault(), SWT.CURSOR_HAND);
	private static final Cursor CURSOR_NAVIGATION = new Cursor(Display.getDefault(), SWT.CURSOR_SIZEWE);
	private Point startPoint;
	private Tracker tracker;
	private boolean isMouseDown;
	private int xPosition = 0;

	public StageMouse(Stage stage) {
		this.stage = stage;
	}

	@Override
	public void mouseMove(MouseEvent me) {

		try {
			// reset tooltip
			stage.setCursor(CURSOR_NONE);
			stage.clearToolTips();
			if (isMouseDown) {
				if (startPoint == null) {
					startPoint = new Point(me.x, me.y);
				}
				int xDiff = me.x - startPoint.x;
				int deltaXPosition = xPosition - (xDiff * stage.getScale());
				if (deltaXPosition < 0) {
					deltaXPosition = 0;
				}

				stage.setPositionByX(deltaXPosition);
				stage.applyHint();
				stage.redraw();
			}

		} catch (final Exception error) {
			SWT.error(SWT.ERROR_UNSPECIFIED, error);
		}
	}

	@Override
	public void mouseEnter(MouseEvent e) {
	}

	@Override
	public void mouseExit(MouseEvent e) {
	}

	@Override
	public void mouseHover(MouseEvent me) {
		if (me.stateMask != 0) {
			return;
		}
		if (!isMousePositionInBounds(me)) {
			return;
		}

		stage.groupAt(me.x, me.y).ifPresent(g -> {
			stage.setCursor(CURSOR_HAND);
			stage.setToolTipForObject(g, me.x, me.y);
			return;
		});

		stage.brickAt(me.x, me.y).ifPresent(b -> {
			stage.setCursor(CURSOR_HAND);
			stage.setToolTipForObject(b, me.x, me.y);
		});

	}

	private boolean isMousePositionInBounds(MouseEvent me) {
		Rectangle mainBounds = stage.getClientArea();
		if (mainBounds == null || me.x < mainBounds.x) {
			return false;
		}
		return true;
	}

	@Override
	public void mouseDoubleClick(final MouseEvent me) {
		isMouseDown = false;
	}

	@Override
	public void mouseDown(final MouseEvent me) {
		if (me.button == 1) {
			isMouseDown = true;
			xPosition = stage.getPositionByX();

		}
		if (!isMousePositionInBounds(me)) {
			return;
		}
		stage.groupAt(me.x, me.y).ifPresent(g -> {
			stage.setCursor(CURSOR_HAND);
			stage.select(g);
			return;
		});

		stage.brickAt(me.x, me.y).ifPresent(b -> {
			stage.setCursor(CURSOR_HAND);
			stage.select(b);
		});

	}

	@Override
	public void mouseUp(final MouseEvent event) {
		isMouseDown = false;
		if (tracker != null && !tracker.isDisposed()) {
			tracker.dispose();
		}
		endEverything();
	}

	public void endEverything() {
		if (tracker != null && !tracker.isDisposed()) {
			tracker.dispose();
			isMouseDown = false;
		}
		if (isMouseDown) {
			return;
		}
		isMouseDown = false;
		startPoint = null;
		stage.setCursor(CURSOR_NONE);

	}

}
