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

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.chronograph.internal.api.graphics.Area;
import org.eclipse.chronograph.internal.api.graphics.Brick;
import org.eclipse.chronograph.internal.api.graphics.Group;
import org.eclipse.chronograph.internal.api.graphics.Section;
import org.eclipse.chronograph.internal.base.AreaImpl;
import org.eclipse.chronograph.internal.base.PlainData;
import org.eclipse.chronograph.internal.swt.BrickStyler;
import org.eclipse.chronograph.internal.swt.GroupStyler;
import org.eclipse.chronograph.internal.swt.RulerStyler;
import org.eclipse.chronograph.internal.swt.SectionStyler;
import org.eclipse.chronograph.internal.swt.StageStyler;
import org.eclipse.swt.graphics.Rectangle;

public class Calculator<D> {
	private final PlainData<D> registry;
	private final Map<String, Area> groupsAreas;
	private final Map<String, Area> bricksAreas;
	private final Map<Area, Brick<D>> areaBricks;

	public Calculator(PlainData<D> registry) {
		this.registry = registry;
		this.groupsAreas = new HashMap<>();
		this.bricksAreas = new HashMap<>();
		this.areaBricks = new HashMap<>();
	}

	public void calculateObjectBounds(Rectangle clientArea, int pYhint, int zoom) {
		Area visiableArea = new AreaImpl(clientArea.x, clientArea.y, clientArea.width, clientArea.height);
		Area frameArea = new AreaImpl(visiableArea.x(), visiableArea.y() + StageStyler.getStageHeaderHeight() - pYhint,
				visiableArea.width() - 10,
				visiableArea.height() - StageStyler.getStageHeaderHeight() - RulerStyler.RULER_DAY_HEIGHT
						- RulerStyler.RULER_MOUNTH_HEIGHT - RulerStyler.RULER_YEAR_HEIGHT);
		List<Section> sections = registry.getSections();
		calculateSectionBounds(frameArea, sections, SectionStyler.getSectionSeparatorHeight(), zoom);
		for (Section section : sections) {
			List<Group> groupsBySection = registry.getGroupBySection(section);
			calculateGroupBounds(groupsBySection, groupsAreas.get(section.id()));
		}

	}

	private void calculateSectionBounds(Area area, Collection<Section> sections, int sectionSpace, int zoom) {
		int y = area.y();
		for (Section section : sections) {
			int lenghtOfGroups = 0;
			List<Group> groups = registry.getGroupBySection(section);
			for (Group group : groups) {
				List<Group> subGroups = registry.getSubGroupByGroupSection(group);
				if (subGroups.isEmpty()) {

					lenghtOfGroups += GroupStyler.GROUP_HEIGHT_DEFAULT;
				} else {
					lenghtOfGroups += subGroups.size() * GroupStyler.GROUP_HEIGHT_DEFAULT;
				}
			}
			Area sectionArea = new AreaImpl(area.x(), y, area.width() * zoom, lenghtOfGroups * zoom);
			groupsAreas.put(section.id(), sectionArea);
			y += lenghtOfGroups * zoom + sectionSpace;
		}
	}

	private void calculateGroupBounds(List<Group> groups, Area area) {
		if (area == null) {
			return;
		}
		int heightDelta = area.height() / groups.size();
		for (Group group : groups) {
			int groupIndex = groups.indexOf(group);
			Area areaGroup = new AreaImpl(area.x() + 30, area.y() + (groupIndex * heightDelta), area.width() + 30,
					heightDelta);
			groupsAreas.put(transformKey(group), areaGroup);
			List<Group> subGroups = registry.getSubGroupByGroupSection(group);
			for (Group subgroup : subGroups) {
				int subGroupIndex = subGroups.indexOf(subgroup);
				Area areaSubGroup = new AreaImpl(areaGroup.x() + 30,
						areaGroup.y() + (subGroupIndex * areaGroup.height() / subGroups.size()), areaGroup.width() + 30,
						areaGroup.height() / subGroups.size());
				addDrawingArea(subgroup, areaSubGroup);
			}
		}
	}

	public Brick<D> calculateObjectPosition(Brick<D> brick, Area area, int hintX, int hintY, int hintWidth) {
		if (area == null) {
			return brick;
		}
		int pixelWitdh = (int) brick.position().duration() * hintWidth;
		int pointX = (int) brick.position().start() * hintWidth - (hintX * hintWidth);
		int pointY = area.y() + (area.height() - BrickStyler.getHeight()) / 2 - hintY;
		Area brickArea = new AreaImpl(pointX, pointY, pixelWitdh, BrickStyler.getHeight());
		bricksAreas.put(brick.id(), brickArea);
		areaBricks.put(area, brick);
		return brick;
	}

	private void addDrawingArea(Group group, Area area) {
		groupsAreas.put(transformKey(group), area);
	}

	private String transformKey(Group group) {
		return group.fqid();
	}

	public Area getGroupAreaBySectionId(String id) {
		return groupsAreas.get(id);
	}

	public Area getBrickAreaById(String id) {
		return bricksAreas.get(id);
	}

	public Area getGroupAreaByGroup(Group group) {
		return groupsAreas.get(transformKey(group));
	}

	public Optional<Brick<D>> brickAt(int x, int y) {
		return areaBricks.keySet().stream()//
				.filter(a -> x >= a.x() && y >= a.y() && x <= a.x() + a.width() && y <= a.y() + a.height())//
				.findFirst()//
				.map(areaBricks::get);
	}

}
