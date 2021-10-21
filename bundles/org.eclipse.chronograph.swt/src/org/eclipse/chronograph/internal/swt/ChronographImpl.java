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
import org.eclipse.chronograph.internal.swt.providers.AbstractStructureDataProvider;
import org.eclipse.chronograph.internal.swt.providers.GroupLabelProvider;
import org.eclipse.chronograph.internal.swt.providers.GroupStructuredContentProvider;
import org.eclipse.chronograph.internal.swt.stage.Stage;
import org.eclipse.chronograph.internal.swt.stylers.BrickStyler;
import org.eclipse.chronograph.internal.swt.stylers.GroupStyler;
import org.eclipse.chronograph.internal.swt.stylers.RulerStyler;
import org.eclipse.chronograph.internal.swt.stylers.SectionStyler;
import org.eclipse.chronograph.internal.swt.stylers.StageStyler;
import org.eclipse.chronograph.internal.swt.stylers.StatusStyler;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ListSelectionDialog;

/**
 * 
 * The main entry point to the SWT implementation of {@link Chronograph}
 *
 */

public class ChronographImpl implements Chronograph {
	private final Stage stage;
	private final List<Styler> stylers;

	private AbstractStructureDataProvider structureProvider;
	private LabelDataProvider labelProvider;
	private Object[] inputData;

	public ChronographImpl(Composite parent) {
		parent.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));

		GridLayout layout = new GridLayout(1, false);
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		parent.setLayout(layout);

		Composite toolsmenuCmps = new Composite(parent, SWT.NONE);
		toolsmenuCmps.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));

		GridData toolMenuGridData = new GridData(SWT.END, SWT.RIGHT_TO_LEFT, false, false, 1, 1);
		toolsmenuCmps.setLayoutData(toolMenuGridData);

		GridLayout toolslayout = new GridLayout(5, false);
		toolslayout.horizontalSpacing = 0;
		toolslayout.verticalSpacing = 0;
		toolsmenuCmps.setLayout(toolslayout);

		Button btnGroupFiltering = new Button(toolsmenuCmps, SWT.PUSH);
		btnGroupFiltering.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnGroupFiltering.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE));
		btnGroupFiltering.setToolTipText("Filtering"); //$NON-NLS-1$
		btnGroupFiltering.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				ListSelectionDialog dlg = new ListSelectionDialog(Display.getDefault().getActiveShell(), //
						inputData, //
						new GroupStructuredContentProvider(structureProvider), //
						new GroupLabelProvider(labelProvider), //
						"Select representation groups:"); //$NON-NLS-1$
				dlg.setInitialElementSelections(structureProvider.groups());
				dlg.setTitle("Representation groups");//$NON-NLS-1$
				if (dlg.open() == Dialog.OK) {
					stage.structure(dlg.getResult());
				}
			}
		});

		Button btnReset = new Button(toolsmenuCmps, SWT.PUSH);
		btnReset.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnReset.setToolTipText("Reset representation"); //$NON-NLS-1$
		btnReset.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_ETOOL_HOME_NAV));
		btnReset.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				stage.reset();
			}
		});

		Button btn_3 = new Button(toolsmenuCmps, SWT.PUSH);
		btn_3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btn_3.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_ELCL_SYNCED));

		Button btn_4 = new Button(toolsmenuCmps, SWT.PUSH);
		btn_4.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btn_4.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_INFO_TSK));

		GridData stageGridData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		this.stage = new Stage(parent);
		this.stage.setLayoutData(stageGridData);
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
	public void structure() {
		stage.init();
		stage.structure(inputData);
		stage.navigateToUnit(UnitConverter.localDatetoUnits(LocalDate.now().minusDays(20)));
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
		this.structureProvider = (AbstractStructureDataProvider) structureProvider;
		stage.setStructureProvider(structureProvider);

	}

	@Override
	public void setPositionProvider(PositionDataProvider positionDataProvider) {
		stage.setPositionProvider(positionDataProvider);
	}

	@Override
	public void setLabelProvider(LabelDataProvider labelDataProvider) {
		this.labelProvider = labelDataProvider;
		stage.setLabelProvider(labelDataProvider);
	}

	@Override
	public void setContentDecoratorProvider(ContentDecorationProvider contentDecorator) {
		stage.setDecoratorProvider(contentDecorator);
	}

	@Override
	public void setInput(Object[] objects) {
		this.inputData = objects;

	}
}
