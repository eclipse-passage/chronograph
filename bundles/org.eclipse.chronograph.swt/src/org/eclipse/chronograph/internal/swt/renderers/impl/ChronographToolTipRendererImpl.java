package org.eclipse.chronograph.internal.swt.renderers.impl;

import org.eclipse.chronograph.internal.api.graphics.Position;
import org.eclipse.chronograph.internal.swt.renderers.api.ChronographToolTipRenderer;
import org.eclipse.chronograph.internal.swt.stylers.BrickStyler;
import org.eclipse.swt.graphics.GC;

public class ChronographToolTipRendererImpl implements ChronographToolTipRenderer {

	@Override
	public void draw(GC gc, Position p, String toolTip) {
		gc.setForeground(BrickStyler.getColorText());
		gc.setBackground(BrickStyler.getDescriptionColor());
		int width = gc.textExtent(toolTip).x + BrickStyler.getHeight();
		int y = (int) p.end() - BrickStyler.getHeight();
		gc.fillRoundRectangle((int) p.start(), y, width, BrickStyler.getHeight(), 20, 20);
		int i = BrickStyler.getHeight() / 4;
		gc.drawText(toolTip, (int) p.start() + i, y + i);
	}
}
