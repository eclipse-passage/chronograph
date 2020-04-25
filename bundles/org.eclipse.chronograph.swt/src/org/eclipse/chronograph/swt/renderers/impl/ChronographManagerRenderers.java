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
package org.eclipse.chronograph.swt.renderers.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chronograph.internal.api.Brick;
import org.eclipse.chronograph.internal.api.Group;
import org.eclipse.chronograph.internal.api.Section;
import org.eclipse.chronograph.swt.renderers.api.ChronographGroupRenderer;
import org.eclipse.chronograph.swt.renderers.api.ChronographObjectContentRenderer;
import org.eclipse.chronograph.swt.renderers.api.ChronographObjectExtRenderer;
import org.eclipse.chronograph.swt.renderers.api.ChronographObjectLabelRenderer;
import org.eclipse.chronograph.swt.renderers.api.ChronographSectionRenderer;
import org.eclipse.chronograph.swt.renderers.api.ChronographStageRenderer;
import org.eclipse.chronograph.swt.renderers.api.ChronographStageRulerRenderer;
import org.eclipse.chronograph.swt.renderers.api.ChronographStatusRenderer;
import org.eclipse.chronograph.swt.renderers.impl.base.GroupRendererImpl;
import org.eclipse.chronograph.swt.renderers.impl.base.ObjectContentRendererImpl;
import org.eclipse.chronograph.swt.renderers.impl.base.ObjectExtensionRendererImpl;
import org.eclipse.chronograph.swt.renderers.impl.base.ObjectLabelRendererImpl;
import org.eclipse.chronograph.swt.renderers.impl.base.ObjectSelectedRendererImpl;
import org.eclipse.chronograph.swt.renderers.impl.base.RulerDayRendererImpl;
import org.eclipse.chronograph.swt.renderers.impl.base.RulerMonthRendererImpl;
import org.eclipse.chronograph.swt.renderers.impl.base.RulerYearRendererImpl;
import org.eclipse.chronograph.swt.renderers.impl.base.SectionRendererImpl;
import org.eclipse.chronograph.swt.renderers.impl.base.StageRendererImpl;
import org.eclipse.chronograph.swt.renderers.impl.base.StatusRendererImpl;

public class ChronographManagerRenderers {

	public static ChronographManagerRenderers INSTANCE = null;
	private ChronographObjectExtRenderer<Brick> objectExtensionRenderer;
	private ChronographObjectLabelRenderer<Brick> objectLabelRenderer;
	private ChronographObjectContentRenderer<Brick> objectContentRenderer;
	private ChronographObjectContentRenderer<Brick> objectSelectedRenderer;

	private ChronographGroupRenderer<Group> chronographGroupRenderer;
	private List<ChronographStageRulerRenderer> chronographStageRulerRenderers;
	private ChronographStageRenderer chronographStageRenderer;
	private ChronographStatusRenderer chronographStatusRenderer;
	private ChronographSectionRenderer<Section> sectionPinter;

	private ChronographManagerRenderers() {
		this.objectContentRenderer = new ObjectContentRendererImpl();
		this.objectLabelRenderer = new ObjectLabelRendererImpl();
		this.objectExtensionRenderer = new ObjectExtensionRendererImpl();
		this.objectSelectedRenderer = new ObjectSelectedRendererImpl();
		this.chronographStageRulerRenderers = new ArrayList<>();
		this.chronographStageRulerRenderers.add(new RulerDayRendererImpl());
		this.chronographStageRulerRenderers.add(new RulerMonthRendererImpl());
		this.chronographStageRulerRenderers.add(new RulerYearRendererImpl());

		this.chronographStageRenderer = new StageRendererImpl();
		this.chronographStatusRenderer = new StatusRendererImpl();
		this.chronographGroupRenderer = new GroupRendererImpl<Group>();
		this.sectionPinter = new SectionRendererImpl();

	}

	public static synchronized ChronographManagerRenderers getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ChronographManagerRenderers();
		}
		return INSTANCE;
	}

	public ChronographObjectContentRenderer<Brick> getContentPainter() {
		return this.objectContentRenderer;
	}

	public <V extends ChronographObjectExtRenderer> ChronographObjectExtRenderer<Brick> getDurationPainter() {
		return this.objectExtensionRenderer;
	}

	public <V extends ChronographObjectLabelRenderer> ChronographObjectLabelRenderer<Brick> getLabelPainter() {
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

	public ChronographGroupRenderer<Group> getDrawingGroupPainter() {
		return chronographGroupRenderer;
	}

	public ChronographSectionRenderer<Section> getDrawingSectionPainter() {
		return sectionPinter;
	}

	public ChronographObjectContentRenderer<Brick> getSelectedContentPainter() {
		return objectSelectedRenderer;
	}

}
