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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.chronograph.internal.api.Area;
import org.eclipse.chronograph.internal.api.Brick;
import org.eclipse.chronograph.internal.api.Group;
import org.eclipse.chronograph.internal.api.Section;
import org.eclipse.chronograph.internal.api.providers.ContainerProvider;
import org.eclipse.chronograph.internal.api.providers.StageLabelProvider;
import org.eclipse.chronograph.internal.base.AreaImpl;
import org.eclipse.chronograph.internal.base.DataRegistry;
import org.eclipse.chronograph.internal.swt.AreaRectangle;
import org.eclipse.chronograph.internal.swt.SectionStyler;
import org.eclipse.chronograph.internal.swt.renderers.api.ChronographStageRulerRenderer;
import org.eclipse.chronograph.internal.swt.renderers.impl.ChronographManagerRenderers;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;

public final class ChronographCanvas extends Canvas {

	private final AreaRectangle areaRectangle;

	private static final int VERTICAL_SCROLLBAR_PAGE_INC = 15;
	private static final int SCALE_DEFAULT = 5;
	private int pX;
	private int pY;
	private int pXMax;
	private int pxlHint = 5;
	private int pXhint;
	private int pYhint;
	private int stageScale = 5;
	private List<Brick> bricksSelected;

	private DataRegistry registry;
	private Rectangle boundsGlobal;
	private Area visiableArea;
	private final Point originalPosition = new Point(0, 0);
	private ScrollBar scrollBarVertical;
	private ScrollBar scrollBarHorizontal;
	private StageLabelProvider labelProvider;
	private ContainerProvider dataProvider;
	private ChronographCalculator calculator;
	private int zoom = 1;

	private static ChronographManagerRenderers INSTANCE = ChronographManagerRenderers.getInstance();

	public ChronographCanvas(Composite parent, ContainerProvider provider) {
		this(parent, SWT.NO_BACKGROUND | SWT.DOUBLE_BUFFERED | SWT.V_SCROLL | SWT.H_SCROLL, provider);
	}

	public ChronographCanvas(Composite parent, int style, ContainerProvider provider) {
		super(parent, style);
		this.areaRectangle = new AreaRectangle();
		this.dataProvider = provider;
		this.labelProvider = provider.getLabelProvider();
		bricksSelected = new ArrayList<>();
		setLayout(new FillLayout());
		updateStageScale();
		initListeners();
		initScrollBarHorizontal();
		initScrollBarVertical();
		initRegistry();
		initCalculator();
		calculateObjectBounds();
	}

	private void initRegistry() {
		registry = new DataRegistry(dataProvider);
		registry.createRegistry();
	}

	private void initCalculator() {
		calculator = new ChronographCalculator(registry);
	}

	private void initScrollBarHorizontal() {
		scrollBarHorizontal = getHorizontalBar();
		scrollBarHorizontal.setVisible(true);
		scrollBarHorizontal.setPageIncrement(stageScale);
		scrollBarHorizontal.setMaximum(pXMax);
		scrollBarHorizontal.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				horizontalScroll(event);
			}
		});
	}

	private void initScrollBarVertical() {

		scrollBarVertical = getVerticalBar();
		scrollBarVertical.setPageIncrement(VERTICAL_SCROLLBAR_PAGE_INC);
		scrollBarVertical.setVisible(true);
		scrollBarVertical.setMaximum(0);
		scrollBarVertical.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				verticalScroll(event);
			}
		});

	}

	private void initListeners() {
		addPaintListener(new StagePaint(this));
		StageMouse mouse = new StageMouse(this);
		addMouseListener(mouse);
		addMouseMoveListener(mouse);
		addMouseTrackListener(mouse);
		addListener(SWT.MouseWheel, new StageWheel(this));
		addListener(SWT.Resize, new StageResize(this));
	}

	public void verticalScroll(Event event) {
		if (event != null && event.detail == 0) {
			return;
		}
		if (!scrollBarVertical.isVisible()) {
			pYhint = 0;
			return;
		}
		pYhint = scrollBarVertical.getSelection();
		redraw();
	}

	protected void horizontalScroll(Event event) {
		if (event != null && event.detail == 0) {
			return;
		}
		if (!scrollBarHorizontal.isVisible()) {
			return;
		}
		setPositionByX(scrollBarHorizontal.getSelection() * pxlHint * stageScale);
		getHint();
		redraw();
	}

	public void updateScrollers() {
		int maxPosition = getMaxBrickPosition();
		scrollBarHorizontal.setMaximum(maxPosition);
		scrollBarHorizontal.setSelection(pXhint);
	}

	public void handleResize() {
		Rectangle rect = getBounds();
		Rectangle client = getClientArea();
		scrollBarVertical.setMaximum(3000);
		scrollBarVertical.setThumb(Math.min(rect.height, client.height));
		int page = rect.height - client.height;
		int selection = scrollBarVertical.getSelection();
		if (selection >= page) {
			if (page <= 0) {
				selection = 0;
			}
			originalPosition.y = -selection;
		}
	}

	public void redraw(Rectangle rectangle) {
		redraw(rectangle.x, rectangle.y, rectangle.width, rectangle.height, false);
	}

	public void repaint(PaintEvent event) {
		GC gc = event.gc;
		// drawing
		Rectangle clientArea = super.getClientArea();
		visiableArea = new AreaImpl(clientArea.x, clientArea.y, clientArea.width, clientArea.height);
		Rectangle stageRectangle = areaRectangle.apply(visiableArea);
		INSTANCE.getDrawingStagePainter().draw(gc, stageRectangle);
		for (ChronographStageRulerRenderer painter : INSTANCE.getDrawingRulersPainter()) {
			painter.draw(gc, stageRectangle, stageScale, pxlHint, pXhint, pX);
		}
		for (Section section : registry.getSections()) {
			List<Group> groupsBySection = registry.getGroupBySection(section);
			for (Group group : groupsBySection) {
				List<Group> subGroups = registry.getSubGroupByGroupSection(group);
				for (Group subgroup : subGroups) {
					Area area = getDrawingArea(subgroup);
					if (area == null) {
						continue;
					}
					List<Brick> bricks = registry.getBrickBySubgroup(subgroup.id(), group.id(), section.id());
					if (bricks != null) {
						Collection<? extends Brick> selectedBriks = getSelectedObject();
						Collection<? extends Brick> markedBricks = filterBricksBySeleted(bricks, selectedBriks);
						drawSceneObjects(gc, area, bricks);
						if (!markedBricks.isEmpty()) {
							drawSelectedObjects(gc, area, markedBricks);
						}
					}
					Rectangle groupRectangle = areaRectangle.apply(area);
					INSTANCE.getDrawingGroupPainter().draw(gc, labelProvider.getText(subgroup), groupRectangle,
							getDisplay(), SectionStyler.getSectionWidth(), pYhint);
				}
				Area area = getDrawingArea(group);
				if (area == null) {
					continue;
				}
				Rectangle groupRectangle = areaRectangle.apply(area);
				INSTANCE.getDrawingGroupPainter().draw(gc, labelProvider.getText(group), groupRectangle, getDisplay(),
						SectionStyler.getSectionWidth(), pYhint);
			}
			Area area = calculator.getGroupAreaBySectionId(section.id());// groupsAreas.get(section.id());
			if (area == null) {
				continue;
			}
			Rectangle sectionRectangle = areaRectangle.apply(area);
			INSTANCE.getDrawingSectionPainter().draw(gc, labelProvider.getText(section), sectionRectangle, getDisplay(),
					SectionStyler.getSectionWidth(), pYhint);
		}
		// status line
		INSTANCE.getDrawingStatusPainter().draw(gc, stageRectangle, registry.getBrickActual().size(),
				registry.getBrickExpired().size(), pYhint);

	}

	private Collection<? extends Brick> filterBricksBySeleted(Collection<? extends Brick> bricks,
			Collection<? extends Brick> selectedBriks) {
		List<Brick> markedBricks = new ArrayList<Brick>();
		for (Brick selectedBrick : selectedBriks) {
			for (Brick brick : bricks) {
				if (brick.id().equals(selectedBrick.id()) && brick.position().start() == brick.position().start()) {
					markedBricks.add(brick);
				}
			}
		}
		return markedBricks;
	}

	private List<Brick> getSelectedObject() {
		return bricksSelected;
	}

	private Area getDrawingArea(Group group) {
		return calculator.getGroupAreaByGroup(group);// groupsAreas.get(transformKey(group));
	}

	private Area getDrawingArea(Brick brick) {
		return calculator.getBrickAreaById(brick.id());
	}

	@Override
	public Rectangle getBounds() {
		if (boundsGlobal == null) {
			return super.getBounds();
		}
		return new Rectangle(0, 0, super.getBounds().width, pY);
	}

	public Rectangle getMainBounds() {
		return boundsGlobal;
	}

	public void calculateObjectBounds() {

		calculator.calculateObjectBounds(super.getBounds(), pYhint, zoom);
	}

	private void drawSceneObjects(final GC gc, Area area, final Collection<? extends Brick> bricks) {
		if (area == null) {
			return;
		}
		bricks.stream().forEach(brick -> {
			calculator.calculateObjectPosition(brick, area, pXhint, pYhint, pxlHint);
			Area brickArea = getDrawingArea(brick);
			if (brickArea != null) {
				Rectangle rectangleArea = areaRectangle.apply(brickArea);
				INSTANCE.getContentPainter().draw(brick, gc, rectangleArea, pYhint);
				String label = labelProvider.getText(brick);
				INSTANCE.getLabelPainter().drawLabel(label, brick.position(), gc, rectangleArea, pYhint, pxlHint);
				INSTANCE.getDurationPainter().drawObjectDuration(brick, gc, pYhint);
			}
		});
	}

	private void drawSelectedObjects(final GC gc, Area area, final Collection<? extends Brick> bricks) {
		if (area == null) {
			return;
		}
		for (Brick brick : bricks) {
			calculator.calculateObjectPosition(brick, area, pXhint, pYhint, pxlHint);
			Area brickArea = getDrawingArea(brick);
			if (brickArea == null) {
				continue;
			}
			Rectangle rectangleArea = areaRectangle.apply(brickArea);
			INSTANCE.getSelectedContentPainter().draw(brick, gc, rectangleArea, pYhint);
			String label = labelProvider.getText(brick);
			INSTANCE.getLabelPainter().drawLabel(label, brick.position(), gc, rectangleArea, pYhint, pxlHint);
			INSTANCE.getDurationPainter().drawObjectDuration(brick, gc, pYhint);
		}
	}

	public void navigateToUnit(int hint) {
		pX = hint * pxlHint * stageScale;
		getHint();
		redraw();
	}

	public void clearSceneObjects() {
		checkWidget();
		pYhint = 0;
		scrollBarVertical.setSelection(0);
		redraw();
	}

	public void clearGroups() {
		checkWidget();
		pYhint = 0;
		scrollBarVertical.setSelection(0);
		redraw();
	}

	public void clearSections() {
		checkWidget();
		pYhint = 0;
		scrollBarVertical.setSelection(0);
		redraw();
	}

	public int getScale() {
		return stageScale;
	}

	public List<? extends Brick> getDrawingObjects() {
		return registry.getBricks();
	}

	private void updateStageScale() {
		if (stageScale <= 0) {
			stageScale = 1;

		}
		pxlHint = stageScale;
//		if (stageScale == 2 || stageScale == 3) {
//			pxlHint = stageScale * 1;
//		}
//		if (stageScale == 4) {
//			pxlHint = stageScale * 2;
//		}
//		if (stageScale > 4) {
//			pxlHint = stageScale * SCALE_DEFAULT;
//		}
	}

	public void scaleUp() {
		checkWidget();
		stageScale--;
		updateStageScale();
		redraw();
		updateScrollers();
		navigateToUnit(pXhint);
	}

	public void scaleDown() {
		checkWidget();
		stageScale++;
		updateStageScale();
		redraw();
		navigateToUnit(pXhint);
	}

	@Override
	public void redraw() {
		super.redraw();
	}

	public int getPositionByX() {
		return pX;
	}

	public void setPositionByX(int x) {
		this.pX = x;
	}

	@Override
	public Rectangle getClientArea() {
		return super.getClientArea();
	}

	public void getHint() {
		pXhint = pX / (pxlHint * stageScale);
	}

	public Area getBrickArea(Brick brick) {
		return getDrawingArea(brick);
	}

	public void addSelected(Brick brick) {
		bricksSelected.add(brick);
	}

	public void removeSelected(Brick brick) {
		bricksSelected.remove(brick);
	}

	public void addRemoveSelected(Brick brick) {
		List<Brick> markedBriks = new ArrayList<Brick>();
		for (Brick selectedBrick : bricksSelected) {
			if (selectedBrick.id().equals(brick.id())) {
				markedBriks.add(selectedBrick);
			}
		}
		if (!markedBriks.isEmpty()) {
			for (Brick marked : markedBriks) {
				bricksSelected.remove(marked);
			}
		} else {
			bricksSelected.add(brick);
		}
	}

	public void setProvider(ContainerProvider provider) {
		this.dataProvider = provider;
		this.labelProvider = provider.getLabelProvider();
		this.registry = new DataRegistry(dataProvider);
		this.registry.createRegistry();
		this.calculateObjectBounds();
		this.redraw();
	}

	public void setZoomLevelDown() {
		this.zoom++;
		this.calculateObjectBounds();
		this.redraw();

	}

	public void setZoomLevelUp() {
		this.zoom--;
		if (this.zoom < 1) {
			this.zoom = 1;
		}
		this.calculateObjectBounds();
		this.redraw();
	}

	public void show(Object input) {
		// FIXME: here we need to clear all caches and start showing new input
		// TODO Auto-generated method stub
		redraw();
	}

	private int getMaxBrickPosition() {
		long max = 0;
		for (Brick brick : registry.getBricks()) {
			if (brick.position().end() > max) {
				max = brick.position().end();
			}
		}
		return (int) max;
	}
}
