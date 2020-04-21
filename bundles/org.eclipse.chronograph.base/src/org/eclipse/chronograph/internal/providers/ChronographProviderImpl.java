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
package org.eclipse.chronograph.internal.providers;

import java.util.List;

import org.eclipse.chronograph.internal.api.providers.BrickContentProvider;
import org.eclipse.chronograph.internal.api.providers.ConteinerProvider;
import org.eclipse.chronograph.internal.api.providers.GroupContentProvider;
import org.eclipse.chronograph.internal.api.providers.SectionContentProvider;
import org.eclipse.chronograph.internal.api.providers.StageLabelProvider;
import org.eclipse.chronograph.internal.api.providers.SubGroupContentProvider;

/**
 * 
 * Aggregated provider implementation of {@link ConteinerProvider}
 *
 * @param <I> - provider type
 */
public class ChronographProviderImpl<I> implements ConteinerProvider<I> {

	private final SectionContentProvider<I> sectionProvider;
	private final GroupContentProvider<I> groupProvider;
	private final SubGroupContentProvider<I> subGroupProvider;
	private final BrickContentProvider<I> brickProvider;
	private final StageLabelProvider labelProvider;
	private List<I> input;

	public ChronographProviderImpl(List<I> input, SectionContentProvider<I> sectionProvider, //
			GroupContentProvider<I> groupProvider, //
			SubGroupContentProvider<I> subGroupProvider, //
			BrickContentProvider<I> brickProvider, //
			StageLabelProvider labelProvider) {
		super();
		this.sectionProvider = sectionProvider;
		this.groupProvider = groupProvider;
		this.subGroupProvider = subGroupProvider;
		this.brickProvider = brickProvider;
		this.labelProvider = labelProvider;
		this.input = input;
	}

	@Override
	public SectionContentProvider<I> getSectionContentProvider() {
		return sectionProvider;
	}

	@Override
	public GroupContentProvider<I> getGroupContentProvider() {
		return groupProvider;
	}

	@Override
	public SubGroupContentProvider<I> getSubGroupContentProvider() {
		return subGroupProvider;
	}

	@Override
	public BrickContentProvider<I> getBrickContentProvider() {
		return brickProvider;
	}

	@Override
	public StageLabelProvider getLabelProvider() {
		return labelProvider;
	}

	@Override
	public List<I> getInput() {
		return input;
	}

	@Override
	public void updateInput(List<I> input) {
		this.input = input;
	}

}
