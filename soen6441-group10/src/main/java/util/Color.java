package util;

import java.util.HashMap;
import java.util.Map;

public enum Color {

	RED(0, "\u001B[31m", "R"), YELLOW(1,"\u001B[33m", "Y"), GREEN(2,"\u001B[32m", "G"), BLUE(3,"\u001B[34m", "Bl"), BROWN(4, "\u001B[33m", "Br"), UNDEFINED(-1, "","");

	
	
	private int colorCode;
	private String ansiCode;
	private String abbr;
	
	private static Map<Integer, Color> colorMap = new HashMap<>();
	static {
		for (Color value : values()) {
			colorMap.put(value.getColorCode(), value);
		}
	}
	
	private Color(int code_, String ansiCode_, String abbr_) {
		colorCode = code_;
		ansiCode = ansiCode_;
		abbr = abbr_;
	}
	
	public String getAbbr() {
		return abbr;
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
