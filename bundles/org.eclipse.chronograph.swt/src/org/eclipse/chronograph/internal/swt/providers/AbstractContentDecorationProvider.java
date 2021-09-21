package org.eclipse.chronograph.internal.swt.providers;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.chronograph.internal.api.data.ContentDecorationProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public abstract class AbstractContentDecorationProvider implements ContentDecorationProvider {

	Map<Integer, Color> colorPallete = new HashMap<>();
	public static Color CONTENT_DEF_COLOR = new Color(Display.getDefault(), new RGB(220, 220, 220));
	public static Color BORDER_DEF_COLOR = new Color(Display.getDefault(), new RGB(150, 150, 150));

	public Color getContentColor(Object obj) {
		int[] RGB = getRGBContentColor(obj);
		Color contentColor = computeColor(RGB);
		if (contentColor == null) {
			contentColor = CONTENT_DEF_COLOR;
		}
		return contentColor;
	}

	public Color getBorderColor(Object obj) {
		int[] RGB = getRGBBorderColor(obj);
		Color borderColor = computeColor(RGB);
		if (borderColor == null) {
			borderColor = BORDER_DEF_COLOR;
		}
		return borderColor;
	}

	private Color computeColor(int[] RGB) {
		if (RGB.length == 3) {
			int index = RGB[0] + RGB[1] + RGB[2];
			Color color = colorPallete.get(index);
			if (color == null) {
				color = new Color(new RGB(RGB[0], RGB[1], RGB[2]));
				colorPallete.put(index, color);
			}
			return color;
		}
		return null;
	}

}
