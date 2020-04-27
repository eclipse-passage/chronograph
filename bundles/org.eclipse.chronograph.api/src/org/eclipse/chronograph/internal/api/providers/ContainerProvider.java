package org.eclipse.chronograph.internal.api.providers;

import java.util.List;

/**
 * 
 * The aggregator for content providers parameterized by input type
 *
 * @param <I> - input type
 */
public interface ContainerProvider<I> {

	/**
	 * The method designed to provide {@link SectionContentProvider} provider
	 * 
	 * @return provider {@link SectionContentProvider}
	 */
	SectionContentProvider<I> getSectionContentProvider();

	/**
	 * The method designed to provide {@link GroupContentProvider} provider
	 * 
	 * @return provider {@link GroupContentProvider}
	 */
	GroupContentProvider<I> getGroupContentProvider();

	/**
	 * The method designed to provide {@link SubGroupContentProvider} provider
	 * 
	 * @return provider {@link SubGroupContentProvider}
	 */
	SubGroupContentProvider<I> getSubGroupContentProvider();

	/**
	 * The method designed to provide {@link BrickContentProvider} provider
	 * 
	 * @return provider {@link BrickContentProvider}
	 */
	BrickContentProvider<I> getBrickContentProvider();

	/**
	 * The method designed to provide {@link StageLabelProvider} provider
	 * 
	 * @return provider {@link StageLabelProvider}
	 */
	StageLabelProvider getLabelProvider();

	/**
	 * The method designed to provide typed input
	 * 
	 * @return - List of typed input
	 */
	List<I> getInput();

	/**
	 * The method designed to update input
	 * 
	 * @param input - typed input
	 */
	void updateInput(List<I> input);

}
