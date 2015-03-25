package util;

import java.util.HashMap;
import java.util.Map;

public enum Interrupt {
	ASSASINATION(0),TAKE_MONEY(1), CARD_FOR_MONEY(2), REMOVE_CARD(3)
	
	;
	
	private int interruptCode;
	
	private static Map<Integer, Interrupt> interruptMap = new HashMap<>();
	static {
		for (Interrupt value : values()) {
			interruptMap.put(value.getInterruptCode(), value);
		}
	}
	
	private Interrupt(int interrupt) {
		interruptCode = interrupt;
	}
	
	public static Interrupt forCode(int interrupt) {
		return interruptMap.get(interrupt);
	}
	
	public int getInterruptCode() {
		return interruptCode;
	}
}
