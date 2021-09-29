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
package org.eclipse.chronograph.internal.swt.renderers.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chronograph.internal.swt.renderers.api.ChronographGroupRenderer;
import org.eclipse.chronograph.internal.swt.renderers.api.ChronographObjectContentRenderer;
import org.eclipse.chronograph.internal.swt.renderers.api.ChronographObjectExtRenderer;
import org.eclipse.chronograph.internal.swt.renderers.api.ChronographObjectLabelRenderer;
import org.eclipse.chronograph.internal.swt.renderers.api.ChronographSectionRenderer;
import org.eclipse.chronograph.internal.swt.renderers.api.ChronographStageLinesRenderer;
import org.eclipse.chronograph.internal.swt.renderers.api.ChronographStageRenderer;
import org.eclipse.chronograph.internal.swt.renderers.api.ChronographStageRulerRenderer;
import org.eclipse.chronograph.internal.swt.renderers.api.ChronographStatusRenderer;

/**
 * 
 * Class intended to aggregate the renders
 *
 */
public class ChronographManagerRenderers {

	private final ChronographObjectExtRenderer objectExtensionRenderer;
	private final ChronographObjectLabelRenderer objectLabelRenderer;
	private final ChronographObjectContentRenderer objectContentRenderer;
	private final ChronographGroupRenderer chronographGroupRenderer;
	private final List<ChronographStageRulerRenderer> chronographStageRulerRenderers;
	private final ChronographStageRenderer chronographStageRenderer;
	private final ChronographStatusRenderer chronographStatusRenderer;
	private final ChronographSectionRenderer sectionPinter;
	private final ChronographStageLinesRenderer chronographStageLinesRenderer;
	private final ChronographToolTipRendererImpl chronographToolTipRenderer;

	public ChronographManagerRenderers() {
		this.objectContentRenderer = new ObjectContentRendererImpl();
		this.objectLabelRenderer = new ObjectLabelRendererImpl();
		this.objectExtensionRenderer = new ObjectExtensionRendererImpl();
		this.chronographStageRulerRenderers = new ArrayList<>();
		this.chronographStageLinesRenderer = new StageLinesRendererImpl();
		this.chronographStageRulerRenderers.add(new RulerDayRendererImpl());
		this.chronographStageRulerRenderers.add(new RulerMonthRendererImpl());
		this.chronographStageRulerRenderers.add(new RulerYearRendererImpl());
		this.chronographStageRenderer = new StageRendererImpl();
		this.chronographStatusRenderer = new StatusRendererImpl();
		this.chronographGroupRenderer = new GroupRendererImpl();
		this.sectionPinter = new SectionRendererImpl();
		this.chronographToolTipRenderer = new ChronographToolTipRendererImpl();

	}

	public ChronographObjectContentRenderer getContentPainter() {
		return this.objectContentRenderer;
	}

	public ChronographObjectExtRenderer getDurationPainter() {
		return this.objectExtensionRenderer;
	}

	public ChronographObjectLabelRenderer getLabelPainter() {
		return this.objectLabelRenderer;
	}

	public List<ChronographStageRulerRenderer> getDrawingRulersPainter() {
		return chronographStageRulerRenderers;
	}

	public ChronographStageRenderer getDrawingStagePainter() {
		return chronographStageRenderer;
	}

	public ChronographStatusRenderer getDrawingStatusPainter() {
		return chronographStatusRenderer;
	}

	public ChronographGroupRenderer getDrawingGroupPainter() {
		return chronographGroupRenderer;
	}

	public ChronographSectionRenderer getDrawingSectionPainter() {
		return sectionPinter;
	}

	public ChronographStageLinesRenderer getStageLinesPainter() {
		return chronographStageLinesRenderer;
	}

	public ChronographToolTipRendererImpl getChronographToolTipRenderer() {
		return chronographToolTipRenderer;
	}

}
