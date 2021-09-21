package org.eclipse.chronograph.internal.swt.renderers.api;

import org.eclipse.chronograph.internal.api.graphics.Position;
import org.eclipse.swt.graphics.GC;

public interface ChronographToolTipRenderer {
	void draw(GC gc, Position p, String toolTip);
}
