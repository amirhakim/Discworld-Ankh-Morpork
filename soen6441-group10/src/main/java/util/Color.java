package util;

import java.util.HashMap;
import java.util.Map;

public enum Color {

	RED(0), YELLOW(1), GREEN(2), BLUE(3), BROWN(4), UNDEFINED(-1);
	
	private int colorCode;
	
	private static Map<Integer, Color> colorMap = new HashMap<>();
	static {
		for (Color value : values()) {
			colorMap.put(value.getColorCode(), value);
		}
	}
	
	private Color(int code_) {
		colorCode = code_;
	}
	
	public static Color forCode(int code) {
		return colorMap.get(code);
	}
	
	public int getColorCode() {
		return colorCode;
	}

}
