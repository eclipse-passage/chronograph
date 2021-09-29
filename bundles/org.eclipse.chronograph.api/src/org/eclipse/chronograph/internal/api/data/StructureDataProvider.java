/*******************************************************************************
 * Copyright (c) 2021 ArSysOp
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
package org.eclipse.chronograph.internal.api.data;

import java.util.List;
import java.util.Optional;

import org.eclipse.chronograph.internal.api.graphics.Brick;
import org.eclipse.chronograph.internal.api.graphics.Group;
import org.eclipse.chronograph.internal.api.graphics.Position;

/**
 * Not for client implementation use {@link AbstractStructureDataProvider} *
 */
public interface StructureDataProvider {

	public void restructure(List<Object> input);

	// public API
	public List<Object> getRoots(List<Object> input);

	public List<Object> getGroups(Object parent);

	public List<Object> getElements(Object parent);

	// internal API
	public List<Group> groups();

	public List<Group> getChildGroups(Group group);

	public List<Brick> getElementsByGroup(Group group);

	public Optional<Position> getMaxBrickPosition();
}