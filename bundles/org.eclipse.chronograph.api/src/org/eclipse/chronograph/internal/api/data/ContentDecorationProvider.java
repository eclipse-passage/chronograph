package org.eclipse.chronograph.internal.api.data;

public interface ContentDecorationProvider {

	/**
	 * 
	 * @param obj
	 * @return array of R,G,B
	 */
	int[] getRGBContentColor(Object obj);

	/**
	 * 
	 * @param obj
	 * @return
	 */
	int[] getRGBBorderColor(Object obj);

	/**
	 * s
	 * 
	 * @param obj
	 * @return
	 */
	int[] getRGBSelectionColor(Object obj);

}
