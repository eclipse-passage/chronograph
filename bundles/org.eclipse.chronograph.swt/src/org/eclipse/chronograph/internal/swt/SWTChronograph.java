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
import org.eclipse.chronograph.internal.api.Style;
import org.eclipse.chronograph.internal.api.Styler;
import org.eclipse.chronograph.internal.api.data.Access;
import org.eclipse.chronograph.internal.api.representation.StageLabelProvider;
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
//FIXME: it looks like this type is not really needed
public class SWTChronograph<I> implements Chronograph {
	private final Stage<I> stage;
	private final List<Styler> stylers;

	public SWTChronograph(Composite parent, Access<I> access, StageLabelProvider provider) {
		this.stage = new Stage<>(parent, access, provider);
		this.stage.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		this.stage.navigateToUnit(UnitConverter.localDatetoUnits(LocalDate.now().minusDays(7)));
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
	public void structure(List<Class<?>> types) {
		stage.structure(types);
	}

	@Override
	public void refresh() {
		stage.refresh();
	}
}
