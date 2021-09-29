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
package org.eclipse.chronograph.internal.swt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.chronograph.internal.api.Chronograph;
import org.eclipse.chronograph.internal.api.data.ContentDecorationProvider;
import org.eclipse.chronograph.internal.api.data.LabelDataProvider;
import org.eclipse.chronograph.internal.api.data.PositionDataProvider;
import org.eclipse.chronograph.internal.api.data.StructureDataProvider;
import org.eclipse.chronograph.internal.api.representation.Style;
import org.eclipse.chronograph.internal.api.representation.Styler;
import org.eclipse.chronograph.internal.base.UnitConverter;
import org.eclipse.chronograph.internal.swt.stage.Stage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * 
 * The main entry point to the SWT implementation of {@link Chronograph}
 *
 */

public class ChronographImpl implements Chronograph {
	private final Stage stage;
	private final List<Styler> stylers;
	private List<Object> input = new ArrayList<>();

	public ChronographImpl(Composite parent) {
		this.stage = new Stage(parent);
		this.stage.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		stylers = new ArrayList<>();
		stylers.add(new BrickStyler());
		stylers.add(new StageStyler());
		stylers.add(new GroupStyler());
		stylers.add(new SectionStyler());
		stylers.add(new RulerStyler());
		stylers.add(new StatusStyler());
		initClassicStyle();

	}

	public void initClassicStyle() {
		stylers.stream().forEach(Styler::initClassicTheme);
		this.stage.redraw();
	}

	public void initDarkStyle() {
		stylers.stream().forEach(Styler::initDarkTheme);
		this.stage.redraw();
	}

	@Override
	public void style(Style style) {
		style.apply(stage);
	}

	@Override
	public void structure(List<Object> inputData) {
		this.input = inputData;
		this.stage.init();
		stage.structure(this.input);
		this.stage.navigateToUnit(UnitConverter.localDatetoUnits(LocalDate.now().minusDays(7)));
	}

	public void reset() {
		stage.reset();
	}

	public void zoomUp() {
		stage.setZoomLevelUp();
	}

	public void zoomDown() {
		stage.setZoomLevelDown();
	}

	@Override
	public void setStructureProvider(StructureDataProvider structureProvider) {
		stage.setStructureProvider(structureProvider);

	}

	@Override
	public void setPositionProvider(PositionDataProvider positionDataProvider) {
		stage.setPositionProvider(positionDataProvider);

	}

	@Override
	public void setLabelProvider(LabelDataProvider labelDataProvider) {
		stage.setLabelProvider(labelDataProvider);

	}

	@Override
	public void setContentDecoratorProvider(ContentDecorationProvider contentDecorator) {
		stage.setDecoratorProvider(contentDecorator);
	}
}
