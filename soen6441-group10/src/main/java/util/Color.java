package util;

import java.util.HashMap;
import java.util.Map;

public enum Color {

	RED(0, "\u001B[31m"), YELLOW(1,"\u001B[33m"), GREEN(2,"\u001B[32m"), BLUE(3,"\u001B[34m"), BROWN(4, "\u001B[33m"), UNDEFINED(-1, "");

	
	
	private int colorCode;
	private String ansiCode;
	
	private static Map<Integer, Color> colorMap = new HashMap<>();
	static {
		for (Color value : values()) {
			colorMap.put(value.getColorCode(), value);
		}
	}
	
	private Color(int code_, String ansiCode_) {
		colorCode = code_;
		ansiCode = ansiCode_;
	}
	
	public String getAnsi() {
		return ansiCode;
	}
	
	public static Color forCode(int code) {
		return colorMap.get(code);
	}
	
	public int getColorCode() {
		return colorCode;
	}

}
