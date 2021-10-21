/*******************************************************************************
 * Copyright (c) 2020, 2021 ArSysOp
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0/.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     ArSysOp - initial API and implementation
 *******************************************************************************/
package org.eclipse.chronograph.internal.api;

import org.eclipse.chronograph.internal.api.data.ContentDecorationProvider;
import org.eclipse.chronograph.internal.api.data.LabelDataProvider;
import org.eclipse.chronograph.internal.api.data.PositionDataProvider;
import org.eclipse.chronograph.internal.api.data.StructureDataProvider;
import org.eclipse.chronograph.internal.api.representation.Style;

/**
 * Main entry point to an API
 * 
 * @since 0.1
 *
 */
public interface Chronograph {

	/**
	 * Structure data according to the given type sequence
	 * 
	 * @param types the types to group the input
	 */
	void structure();

	void setInput(Object[] objects);

	/**
	 * Applies the given style
	 * 
	 * @param style the style to use
	 */
	void style(Style style);

	void setStructureProvider(StructureDataProvider structureProvider);

	void setPositionProvider(PositionDataProvider positionDataProvider);

	void setLabelProvider(LabelDataProvider decoratorDataProvider);

	void setContentDecoratorProvider(ContentDecorationProvider contentDecorator);

}
