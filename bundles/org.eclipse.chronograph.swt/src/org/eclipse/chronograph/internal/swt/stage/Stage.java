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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.eclipse.chronograph.internal.api.data.ContentDecorationProvider;
import org.eclipse.chronograph.internal.api.data.LabelDataProvider;
import org.eclipse.chronograph.internal.api.data.PositionDataProvider;
import org.eclipse.chronograph.internal.api.data.StructureDataProvider;
import org.eclipse.chronograph.internal.api.graphics.Area;
import org.eclipse.chronograph.internal.api.graphics.Brick;
import org.eclipse.chronograph.internal.api.graphics.Group;
import org.eclipse.chronograph.internal.api.graphics.Position;
import org.eclipse.chronograph.internal.base.PositionImpl;
import org.eclipse.chronograph.internal.base.UnitConverter;
import org.eclipse.chronograph.internal.swt.AreaRectangle;
import org.eclipse.chronograph.internal.swt.BrickStyler;
import org.eclipse.chronograph.internal.swt.SectionStyler;
import org.eclipse.chronograph.internal.swt.renderers.api.ChronographStageLinesRenderer;
import org.eclipse.chronograph.internal.swt.renderers.api.ChronographStageRulerRenderer;
import org.eclipse.chronograph.internal.swt.renderers.api.ChronographToolTipRenderer;
import org.eclipse.chronograph.internal.swt.renderers.impl.ChronographManagerRenderers;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;

public final class Stage extends Canvas {

	private StructureDataProvider structureProvider;
	private PositionDataProvider positionProvider;
	private LabelDataProvider labelProvider;
	private ContentDecorationProvider decoratorProvider;

	private final AreaRectangle areaRectangle;

	private static final int VERTICAL_SCROLLBAR_PAGE_INC = 50;
	private static final int SCALE_DEF = 3;
	private static final int ZOOM_DEF = 2;
	private int pX;
	private int pY;
	private int pxHint;
	private int pyHint;
	private int pMaxHorizontal;
	private int pMaxVertical;

	private int pXMax;
	private int pxlHint = 5;

	private List<Brick> bricksSelected;

	private Rectangle boundsGlobal;

	private ScrollBar scrollBarVertical;
	private ScrollBar scrollBarHorizontal;

	private Calculator calculator;

	private int zoom;
	private int scale;

	private final ChronographManagerRenderers renderers = new ChronographManagerRenderers();
	private Map<Brick, Position> objectWithToolTips = new HashMap<>();

	public Stage(Composite parent) {
		this(parent, SWT.NO_BACKGROUND | SWT.DOUBLE_BUFFERED | SWT.V_SCROLL | SWT.H_SCROLL);
	}

	public Stage(Composite parent, int style) {
		super(parent, style);
		this.areaRectangle = new AreaRectangle();
		this.bricksSelected = new ArrayList<>();
		setLayout(new FillLayout());
	}

	public void init() {
		initScale();
		initListeners();
		initScrollBarHorizontal();
		initScrollBarVertical();
		initCalculator();
	}

	private void initScale() {
		scale = SCALE_DEF;
		zoom = ZOOM_DEF;
	}

	private void initCalculator() {
		calculator = new Calculator(structureProvider, positionProvider);
	}

	private void initScrollBarHorizontal() {
		scrollBarHorizontal = getHorizontalBar();
		scrollBarHorizontal.setVisible(true);
		scrollBarHorizontal.setPageIncrement(scale);
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
		scrollBarVertical.setMaximum(pMaxVertical);
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
			pyHint = 0;
			return;
		}
		pyHint = scrollBarVertical.getSelection();
		redraw();
	}

	protected void horizontalScroll(Event event) {
		if (event != null && event.detail == 0) {
			return;
		}
		if (!scrollBarHorizontal.isVisible()) {
			return;
		}
		setPositionByX(scrollBarHorizontal.getSelection() * pxlHint * scale);
		applyHint();
		redraw();
	}

	public void updateScrollers() {
		pMaxVertical = calculator.getGroupsAreaHeight();
		Optional<Position> optPosition = structureProvider.getMaxBrickPosition();
		if (optPosition.isPresent()) {
			pMaxHorizontal = (int) (optPosition.get().end());
		}
		scrollBarVertical.setMaximum(pMaxVertical);
		scrollBarVertical.setSelection(pyHint);
		scrollBarHorizontal.setMaximum(pMaxHorizontal);
		scrollBarHorizontal.setSelection(pxHint);
	}

	public void repaint(PaintEvent event) {
		Rectangle clientArea = super.getClientArea();
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				GC gc = event.gc;
				renderers.getDrawingStagePainter().draw(gc, clientArea);
				ChronographStageLinesRenderer stageLinesPainter = renderers.getStageLinesPainter();
				stageLinesPainter.draw(gc, clientArea, scale, pxlHint, pxHint, pX);
				calculator.resetBrickCash();
				for (Group group : structureProvider.groups()) {
					drawGroupWithChild(gc, group);
				}
				for (ChronographStageRulerRenderer painter : renderers.getDrawingRulersPainter()) {
					painter.draw(gc, clientArea, scale, pxlHint, pxHint, pX);
				}
				// tooltip
				if (!objectWithToolTips.isEmpty()) {
					ChronographToolTipRenderer toolTipRenderer = renderers.getChronographToolTipRenderer();
					for (Entry<Brick, Position> entry : objectWithToolTips.entrySet()) {
						String tt = labelProvider.getToolTip(entry.getKey().data());
						if (tt != null) {
							toolTipRenderer.draw(gc, entry.getValue(), tt);
						}
					}
				}

			}

			private void drawGroupWithChild(GC gc, Group group) {
				List<Group> groupsByGroup = structureProvider.getChildGroups(group);
				if (!groupsByGroup.isEmpty()) {
					groupsByGroup.forEach(c -> {
						drawGroupWithChild(gc, c);
					});
				}
				List<Brick> bricks = structureProvider.getElementsByGroup(group);
				Area area = calculator.getGroupAreaByGroup(group);
				if (area != null && !bricks.isEmpty()) {
					drawSceneObjects(gc, area, bricks);
				}
				if (area != null) {
					Rectangle groupRectangle = areaRectangle.apply(area);
					renderers.getDrawingGroupPainter().draw(gc, labelProvider.getText(group.data()), groupRectangle,
							getDisplay(), SectionStyler.getSectionWidth(), pyHint);
				}
			}
		});
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(0, 0, super.getBounds().width, pY);
	}

	public Rectangle getMainBounds() {
		return boundsGlobal;
	}

	void calculateObjectBounds() {
		calculator.computeBounds(this.getBounds(), zoom);
	}

	private void drawSceneObjects(final GC gc, Area area, final Collection<Brick> bricks) {
		if (area == null) {
			return;
		}
		int shiftInGroup = BrickStyler.getHeight() / 2;
		for (Brick brick : bricks) {
			calculator.calculateObjectPosition(brick, area, pxHint, pyHint, pxlHint, shiftInGroup);
			Area brickArea = calculator.getBrickArea(brick);
			if (brickArea != null) {
				Rectangle rectangleArea = areaRectangle.apply(brickArea);
				renderers.getContentPainter().draw(decoratorProvider, brick, gc, rectangleArea, pyHint);
				String label = labelProvider.getText(brick.data());
				renderers.getLabelPainter().drawLabel(label, brick.position(), gc, rectangleArea, pyHint, pxlHint,
						zoom);
				renderers.getDurationPainter().drawObjectDuration(brick, gc, pyHint);
			}
			shiftInGroup += (BrickStyler.getHeight() + BrickStyler.getHeight() / 2);
		}
	}

	public void navigateToUnit(int hint) {
		pX = hint * pxlHint * scale;
		applyHint();
		redraw();
	}

	public void clearSceneObjects() {
		checkWidget();
		pyHint = 0;
		scrollBarVertical.setSelection(0);
		redraw();
	}

	public void clearGroups() {
		checkWidget();
		pyHint = 0;
		scrollBarVertical.setSelection(0);
		redraw();
	}

	public void clearSections() {
		checkWidget();
		pyHint = 0;
		scrollBarVertical.setSelection(0);
		redraw();
	}

	public int getScale() {
		return scale;
	}

	private void updateStageScale() {
		if (scale <= 0) {
			scale = 1;
		}
		pxlHint = scale;
	}

	public void scaleUp() {
		checkWidget();
		scale--;
		updateStageScale();
		redraw();
		updateScrollers();
		navigateToUnit(pxHint);
	}

	public void scaleDown() {
		checkWidget();
		scale++;
		updateStageScale();
		redraw();
		navigateToUnit(pxHint);
	}

	@Override
	public void redraw() {
		super.redraw();
	}

	int getPositionByX() {
		return pX;
	}

	int getPositionByY() {
		return pY;
	}

	void setPositionByX(int x) {
		this.pX = x;
	}

	void setPositionByY(int y) {
		this.pY = y;
	}

	@Override
	public Rectangle getClientArea() {
		return super.getClientArea();
	}

	void applyHint() {
		pxHint = pX / (pxlHint * scale);
	}

	public Optional<Brick> brickAt(int x, int y) {
		return calculator.brickAt(x, y);
	}

	public void select(Brick brick) {
		List<Brick> markedBriks = new ArrayList<>();
		for (Brick selectedBrick : bricksSelected) {
			if (selectedBrick.equals(brick)) {
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
		redraw();
	}

	public void setZoomLevelDown() {
		this.zoom++;
		if (zoom > 0) {
			handleResize();
		}
	}

	public void setZoomLevelUp() {
		this.zoom--;
		if (this.zoom < 1) {
			this.zoom = 1;
		}
		handleResize();
	}

	public void handleResize() {
		calculateObjectBounds();
		updateScrollers();
		redraw();
	}

	public void structure(List<Object> input) {
		structureProvider.restructure(input);
		handleResize();
	}

	public void refresh() {
		// ??? TODO:
	}

	public void reset() {
		zoom = 2;
		scale = 3;
		updateStageScale();
		navigateToUnit(UnitConverter.localDatetoUnits(LocalDate.now().minusMonths(5)));
		calculateObjectBounds();
		redraw();
	}

	public void setStructureProvider(StructureDataProvider structureProvider) {
		this.structureProvider = structureProvider;
	}

	public void setLabelProvider(LabelDataProvider labelProvider) {
		this.labelProvider = labelProvider;
	}

	public void setPositionProvider(PositionDataProvider positionProvider) {
		this.positionProvider = positionProvider;
	}

	public void setDecoratorProvider(ContentDecorationProvider decorationProvider) {
		this.decoratorProvider = decorationProvider;
	}

	public void addBrickToolTip(Brick b, int x, int y) {
		Position p = new PositionImpl(x, y);
		objectWithToolTips.put(b, p);
		redraw();
	}

	public void clearToolTips() {
		objectWithToolTips.clear();
		redraw();
	}
}
