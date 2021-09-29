/*******************************************************************************
 *	Copyright (c) 2020, 2021 ArSysOp
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
import java.util.Map.Entry;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.chronograph.internal.api.data.PositionDataProvider;
import org.eclipse.chronograph.internal.api.data.StructureDataProvider;
import org.eclipse.chronograph.internal.api.graphics.Area;
import org.eclipse.chronograph.internal.api.graphics.Brick;
import org.eclipse.chronograph.internal.api.graphics.Group;
import org.eclipse.chronograph.internal.api.graphics.Position;
import org.eclipse.chronograph.internal.base.AreaImpl;
import org.eclipse.chronograph.internal.base.PositionImpl;
import org.eclipse.chronograph.internal.base.UnitConverter;
import org.eclipse.chronograph.internal.swt.BrickStyler;
import org.eclipse.chronograph.internal.swt.GroupStyler;
import org.eclipse.chronograph.internal.swt.RulerStyler;
import org.eclipse.chronograph.internal.swt.SectionStyler;
import org.eclipse.chronograph.internal.swt.StageStyler;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.graphics.Rectangle;

public class Calculator {
	private StructureDataProvider provider;
	private final Map<Group, Area> groupsAreas;
	private final Map<Brick, Area> bricksAreas;
	private PositionDataProvider positionProvider;

	public Calculator(StructureDataProvider provider, PositionDataProvider positionProvider) {
		this.provider = provider;
		this.groupsAreas = new HashMap<>();
		this.bricksAreas = new HashMap<>();
		this.provider = provider;
		this.positionProvider = positionProvider;
	}

	public void computeBounds(Rectangle clientArea, int zoom) {
		Area frameArea = new AreaImpl(clientArea.x, // x
				clientArea.y + StageStyler.getStageHeaderHeight(), // y
				clientArea.width, // w
				clientArea.height - StageStyler.getStageHeaderHeight() - RulerStyler.RULER_DAY_HEIGHT
						- RulerStyler.RULER_MOUNTH_HEIGHT - RulerStyler.RULER_YEAR_HEIGHT);
		computeGroups(provider.groups(), frameArea, zoom);
	}

	private void computeGroups(List<Group> groups, Area area, int zoom) {
		if (groups.isEmpty()) {
			return;
		}
		computeGroupBounds(area, groups, SectionStyler.getSectionSeparatorHeight(), zoom);
		groups.forEach(g -> {
			computeGroups(provider.getChildGroups(g), getGroupAreaByGroup(g), zoom);
		});
	}

	private void computeGroupBounds(Area area, Collection<Group> rootGroups, int margine, int zoom) {
		int pY = area.y();
		for (Group group : rootGroups) {
			int groupCapacityPx = computeGroupCapacity(group, margine);
			/*
			 * Shift computed area to right by x on group width to keep left part for group
			 * title
			 */
			if (groupCapacityPx == 0) {
				continue;
			}
			Area groupArea = new AreaImpl(area.x() + GroupStyler.getGroupWith(), pY, area.width() * zoom,
					groupCapacityPx);
			addDrawingArea(group, groupArea);
			pY += groupCapacityPx + margine;
		}

	}

	private int computeGroupCapacity(Group group, int margine) {
		int capacity = 0;
		List<Group> childGroups = provider.getChildGroups(group);
		if (childGroups == null || childGroups.isEmpty()) {
			List<Brick> elements = provider.getElementsByGroup(group);
			if (elements == null || elements.isEmpty()) {
				String msg = NLS.bind("Group {0} should consits of at least one element", group.data().toString()); //$NON-NLS-1$
				Logger.getLogger(this.getClass().getName()).log(Level.WARNING, msg);
			} else {
				return elements.size() * (BrickStyler.getHeight() + BrickStyler.getHeight() / 2)
						+ BrickStyler.getHeight() / 2;
			}
		} else {
			for (Group gr : childGroups) {
				capacity += (computeGroupCapacity(gr, margine) + margine);
			}
		}
		return capacity - margine;
	}

	public Brick calculateObjectPosition(Brick brick, Area area, int hintX, int hintY, int hintWidth, int shiftY) {
		if (area == null) {
			return brick;
		}
		Position brickPosition = new PositionImpl(
				UnitConverter.localDatetoUnits(positionProvider.getStart(brick.data())),
				UnitConverter.localDatetoUnits(positionProvider.getEnd(brick.data())));
		brick.setPosition(brickPosition);
		int witdhPx = (int) brickPosition.duration() * hintWidth;
		int pX = (int) brickPosition.start() * hintWidth - (hintX * hintWidth);
		int pY = area.y() - hintY + shiftY;
		Area brickArea = new AreaImpl(pX, pY, witdhPx, BrickStyler.getHeight());
		bricksAreas.put(brick, brickArea);
		return brick;
	}

	public Optional<Brick> brickAt(int x, int y) {
		Optional<Brick> result = Optional.empty();
		Optional<Entry<Brick, Area>> entryArea = bricksAreas.entrySet().stream()//
				.filter(entry -> {
					Area area = entry.getValue();
					if (x >= area.x() && x <= area.x() + area.width()) {
						if (y >= area.y() && y <= area.y() + area.height()) {
							return true;
						}
					}
					return false;
				}).findFirst();
		if (entryArea.isPresent()) {
			return Optional.of(entryArea.get().getKey());
		}
		return result;
	}

	public Optional<Group> groupAt(int x, int y) {
		Optional<Group> result = Optional.empty();
		Optional<Entry<Group, Area>> entryArea = groupsAreas.entrySet().stream()//
				.filter(entry -> {
					Area area = entry.getValue();
					if (x >= area.x() - GroupStyler.getGroupWith() && x <= area.x()) {
						if (y >= area.y() && y <= area.y() + area.height()) {
							return true;
						}
					}
					return false;
				}).findFirst();
		if (entryArea.isPresent()) {
			return Optional.of(entryArea.get().getKey());
		}
		return result;
	}

	private void addDrawingArea(Group group, Area area) {
		groupsAreas.put(group, area);
	}

	public Area getBrickArea(Brick object) {
		return bricksAreas.get(object);
	}

	public Area getGroupAreaByGroup(Group group) {
		return groupsAreas.get(group);
	}

	public int getGroupsAreaHeight() {
		if (provider.groups() == null || provider.groups().isEmpty() || groupsAreas.isEmpty()) {
			return 0;
		}
		return provider.groups().stream().map(p -> groupsAreas.get(p)).mapToInt(Area::height).sum();
	}

	public void reset() {
		bricksAreas.clear();
	}
}
