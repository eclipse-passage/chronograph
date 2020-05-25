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
import java.util.List;
import java.util.Optional;

import org.eclipse.chronograph.internal.api.data.Resolution;
import org.eclipse.chronograph.internal.api.graphics.Area;
import org.eclipse.chronograph.internal.api.graphics.Brick;
import org.eclipse.chronograph.internal.api.graphics.Group;
import org.eclipse.chronograph.internal.api.graphics.Position;
import org.eclipse.chronograph.internal.api.representation.Decoration;
import org.eclipse.chronograph.internal.base.PlainData;
import org.eclipse.chronograph.internal.base.UnitConverter;
import org.eclipse.chronograph.internal.base.query.ActualBricks;
import org.eclipse.chronograph.internal.base.query.ExpiredBricks;
import org.eclipse.chronograph.internal.swt.AreaRectangle;
import org.eclipse.chronograph.internal.swt.SectionStyler;
import org.eclipse.chronograph.internal.swt.renderers.api.ChronographStageRulerRenderer;
import org.eclipse.chronograph.internal.swt.renderers.impl.ChronographManagerRenderers;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;

public final class Stage<D> extends Canvas {

	private final Resolution<D> access;
	private final AreaRectangle areaRectangle;
	private final ActualBricks<D> actualBricks;
	private final ExpiredBricks<D> expiredBricks;

	private static final int VERTICAL_SCROLLBAR_PAGE_INC = 50;
	private static final int SCALE_DEF = 3;
	private static final int ZOOM_DEF = 2;
	private int pX;
	private int pY;
	private int pXMax;
	private int pxlHint = 5;
	private int pxHint;
	private int pyHint;
	private int pMaxHorizontal;
	private int pMaxVertical;

	private List<Brick<D>> bricksSelected;

	private PlainData<D> registry;
	private Rectangle boundsGlobal;

	private ScrollBar scrollBarVertical;
	private ScrollBar scrollBarHorizontal;
	private Decoration<D, Image> labelProvider;
	private Calculator<D> calculator;

	private int zoom;
	private int scale;

	private final ChronographManagerRenderers<D> renderers = new ChronographManagerRenderers<>();

	public Stage(Composite parent, Resolution<D> access, Decoration<D, Image> provider) {
		this(parent, SWT.NO_BACKGROUND | SWT.DOUBLE_BUFFERED | SWT.V_SCROLL | SWT.H_SCROLL, access, provider);
	}

	public Stage(Composite parent, int style, Resolution<D> access, Decoration<D, Image> provider) {
		super(parent, style);
		this.access = access;
		this.areaRectangle = new AreaRectangle();
		this.actualBricks = new ActualBricks<>();
		this.expiredBricks = new ExpiredBricks<>();

		this.labelProvider = provider;
		bricksSelected = new ArrayList<>();
		setLayout(new FillLayout());
		initScale();
		updateStageScale();
		initListeners();
		initScrollBarHorizontal();
		initScrollBarVertical();
		initRegistry();
		initCalculator();
		calculateObjectBounds();
	}

	private void initScale() {
		scale = SCALE_DEF;
		zoom = ZOOM_DEF;
	}

	private void initRegistry() {
		registry = new PlainData<>(access);
	}

	private void initCalculator() {
		calculator = new Calculator<>(registry);
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
		StageMouse<D> mouse = new StageMouse<>(this);
		addMouseListener(mouse);
		addMouseMoveListener(mouse);
		addMouseTrackListener(mouse);
		addListener(SWT.MouseWheel, new StageWheel<>(this));
		addListener(SWT.Resize, new StageResize<>(this));
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
		Optional<Position> optPosition = registry.getMaxBrickPosition();
		if (optPosition.isPresent()) {
			pMaxHorizontal = (int) (optPosition.get().end());
		}
		scrollBarVertical.setMaximum(pMaxVertical);
		scrollBarVertical.setSelection(pyHint);
		scrollBarHorizontal.setMaximum(pMaxHorizontal);
		scrollBarHorizontal.setSelection(pxHint);
	}

	public void handleResize() {
	}

	public void repaint(PaintEvent event) {
		Rectangle clientArea = super.getClientArea();
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				GC gc = event.gc;
				renderers.getDrawingStagePainter().draw(gc, clientArea);
				List<ChronographStageRulerRenderer> list = renderers.getDrawingRulersPainter();
				for (ChronographStageRulerRenderer painter : list) {
					painter.draw(gc, clientArea, scale, pxlHint, pxHint, pX);
				}
				for (Group section : registry.groups()) {
					List<Group> groupsBySection = registry.subGroups(section);
					for (Group group : groupsBySection) {
						List<Group> subGroups = registry.getSubGroupByGroupSection(group);
						for (Group subgroup : subGroups) {
							Area area = calculator.getGroupAreaByGroup(subgroup);
							if (area == null) {
								continue;
							}
							List<Brick<D>> bricks = registry.getBrickBySubgroup(subgroup.id(), group.id(),
									section.id());
							if (bricks != null) {
								Collection<Brick<D>> selectedBriks = getSelectedObject();
								Collection<Brick<D>> markedBricks = filterBricksBySeleted(bricks, selectedBriks);
								drawSceneObjects(gc, area, bricks);
								if (!markedBricks.isEmpty()) {
									drawSelectedObjects(gc, area, markedBricks);
								}
							}
							Rectangle groupRectangle = areaRectangle.apply(area);
							renderers.getDrawingGroupPainter().draw(gc, labelProvider.groupText(subgroup),
									groupRectangle, getDisplay(), SectionStyler.getSectionWidth(), pyHint);
						}
						Area area = calculator.getGroupAreaByGroup(group);
						if (area == null) {
							continue;
						}
						Rectangle groupRectangle = areaRectangle.apply(area);
						renderers.getDrawingGroupPainter().draw(gc, labelProvider.groupText(group), groupRectangle,
								getDisplay(), SectionStyler.getSectionWidth(), pyHint);
					}
					Area area = calculator.getGroupAreaByGroup(section);
					if (area == null) {
						continue;
					}

					Rectangle sectionRectangle = areaRectangle.apply(area);
					renderers.getDrawingSectionPainter().draw(gc, labelProvider.groupText(section), sectionRectangle,
							getDisplay(), SectionStyler.getSectionWidth(), pyHint);
				}
				// status line
				renderers.getDrawingStatusPainter().draw(gc, clientArea, registry.query(actualBricks).size(),
						registry.query(expiredBricks).size(), pyHint);
			}
		});

	}

	private Collection<Brick<D>> filterBricksBySeleted(Collection<Brick<D>> bricks,
			Collection<Brick<D>> selectedBriks) {
		List<Brick<D>> markedBricks = new ArrayList<>();
		for (Brick<D> selectedBrick : selectedBriks) {
			for (Brick<D> brick : bricks) {
				if (brick.id().equals(selectedBrick.id())
						&& brick.position().start() == selectedBrick.position().start()) {
					markedBricks.add(brick);
				}
			}
		}
		return markedBricks;
	}

	private List<Brick<D>> getSelectedObject() {
		return bricksSelected;
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

	void calculateObjectBounds() {
		calculator.calculateObjectBounds(super.getBounds(), zoom);

	}

	private void drawSceneObjects(final GC gc, Area area, final Collection<Brick<D>> bricks) {
		if (area == null) {
			return;
		}
		bricks.stream().forEach(brick -> {
			calculator.calculateObjectPosition(brick, area, pxHint, pyHint, pxlHint);
			Area brickArea = calculator.getBrickAreaById(brick.id());
			if (brickArea != null) {
				Rectangle rectangleArea = areaRectangle.apply(brickArea);
				renderers.getContentPainter().draw(brick, gc, rectangleArea, pyHint);
				String label = labelProvider.brickText(brick);
				renderers.getLabelPainter().drawLabel(label, brick.position(), gc, rectangleArea, pyHint, pxlHint,
						zoom);
				renderers.getDurationPainter().drawObjectDuration(brick, gc, pyHint);
			}
		});
	}

	private void drawSelectedObjects(final GC gc, Area area, final Collection<Brick<D>> bricks) {
		if (area == null) {
			return;
		}
		for (Brick<D> brick : bricks) {
			calculator.calculateObjectPosition(brick, area, pxHint, pyHint, pxlHint);
			Area brickArea = calculator.getBrickAreaById(brick.id());
			if (brickArea == null) {
				continue;
			}
			Rectangle rectangleArea = areaRectangle.apply(brickArea);
			renderers.getSelectedContentPainter().draw(brick, gc, rectangleArea, pyHint);
			String label = labelProvider.brickText(brick);
			renderers.getLabelPainter().drawLabel(label, brick.position(), gc, rectangleArea, pyHint, pxlHint, zoom);
			renderers.getDurationPainter().drawObjectDuration(brick, gc, pyHint);
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

	public Optional<Brick<D>> brickAt(int x, int y) {
		return calculator.brickAt(x, y);
	}

	public void select(Brick<D> brick) {
		List<Brick<D>> markedBriks = new ArrayList<Brick<D>>();
		for (Brick<D> selectedBrick : bricksSelected) {
			if (selectedBrick.id().equals(brick.id())) {
				markedBriks.add(selectedBrick);
			}
		}
		if (!markedBriks.isEmpty()) {
			for (Brick<D> marked : markedBriks) {
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
			calculateObjectBounds();
			handleResize();
			updateScrollers();
			redraw();
		}

	}

	public void setZoomLevelUp() {
		this.zoom--;
		if (this.zoom < 1) {
			this.zoom = 1;
		}
		calculateObjectBounds();
		handleResize();
		updateScrollers();
		redraw();
	}

	public void structure(List<Class<?>> types) {
		registry = new PlainData<>(access);
		calculator = new Calculator<>(registry);
		registry.restructure(types);
		calculateObjectBounds();

		handleResize();
		updateScrollers();
		redraw();
	}

	public void refresh() {
		structure(registry.structure());
	}

	public void reset() {
		zoom = 2;
		scale = 3;
		updateStageScale();
		navigateToUnit(UnitConverter.localDatetoUnits(LocalDate.now().minusMonths(5)));
		calculateObjectBounds();
		redraw();
	}
}
