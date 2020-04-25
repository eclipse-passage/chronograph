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
package org.eclipse.chronograph.swt.stage;

import java.util.List;

import org.eclipse.chronograph.internal.api.Area;
import org.eclipse.chronograph.internal.api.Brick;
import org.eclipse.chronograph.internal.api.providers.ContainerProvider;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Event;

public interface ChronographStage extends Drawable {

	public void setInput(List<?> objects);

	public List<? extends Brick> getDrawingObjects();

	public void setCursor(Cursor cursor);

	public void setPositionByX(int x);

	public int getPositionByX();

	public void setLayoutData(GridData data);

	public Rectangle getClientArea();

	public Rectangle getMainBounds();

	public int getScale();

	public void redraw();

	public void updateScrollers();

	public void verticalScroll(Event event);

	public void scaleUp();

	public void scaleDown();

	public void getHint();

	public void handleResize();
	
	public void calculateObjectBounds();

	public void navigateToUnit(int unit);

	public Area getBrickArea(Brick to);

	public void addRemoveSelected(Brick brick);

	public void setProvider(ContainerProvider provider);

}
