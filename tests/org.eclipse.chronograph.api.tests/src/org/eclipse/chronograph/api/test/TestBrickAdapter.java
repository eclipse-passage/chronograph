///*******************************************************************************
// * Copyright (c) 2020 ArSysOp
// *
// * This program and the accompanying materials are made available under the
// * terms of the Eclipse Public License 2.0 which is available at
// * https://www.eclipse.org/legal/epl-2.0/.
// *
// * SPDX-License-Identifier: EPL-2.0
// *
// * Contributors:
// *     Sergei Kovalchuk <sergei.kovalchuk@arsysop.ru> - initial API and implementation
// *******************************************************************************/
//package org.eclipse.chronograph.api.test;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//import org.eclipse.chronograph.internal.api.graphics.Brick;
//import org.eclipse.chronograph.internal.base.BrickImpl;
//
//public class TestBrickAdapter implements Function<List<TestInputObject>, List<Brick<TestInputObject>>> {
//
//	@Override
//	public List<Brick<TestInputObject>> apply(List<TestInputObject> inputObjects) {
//		Map<TestInputObject, Brick<TestInputObject>> objectsToBricks = new HashMap<>();
//		List<Brick<TestInputObject>> bricks = inputObjects.stream()
//				.map(p -> objectsToBricks.computeIfAbsent(p, new Function<TestInputObject, Brick<TestInputObject>>() {
//					@Override
//					public Brick<TestInputObject> apply(TestInputObject t) {
//						Brick<TestInputObject> brick = new BrickImpl<>(t.id, t.start, t.end, t);
//						return brick;
//					}
//				})).collect(Collectors.toList());
//		return bricks;
//	}
//
//}
