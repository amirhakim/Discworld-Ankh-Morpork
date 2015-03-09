package card;

import java.util.HashMap;
import java.util.Map;

/**
 * An enumeration of all the areas available in the game. Each area is uniquely
 * identified by a number (which is visible on the physical game board).
 * 
 * @author gkentr
 */
public enum AnkhMorporkArea {

	DOLLY_SISTERS(1, 6),

	UNREAL_ESTATE(2, 18),

	DRAGONS_LANDING(3, 12),

	SMALL_GODS(4, 18),

	THE_SCOURS(5, 6),

	THE_HIPPO(6, 12),

	THE_SHADES(7, 6),

	DIMWELL(8, 6),

	LONGWALL(9, 12),

	ISLE_OF_GODS(10, 12),

	SEVEN_SLEEPERS(11, 18),

	NAP_HILL(12, 12);

	private static final Map<Integer, AnkhMorporkArea> codeToAreaMap = new HashMap<>();
	static {
		for (AnkhMorporkArea a : AnkhMorporkArea.values()) {
			codeToAreaMap.put(a.getAreaCode(), a);
		}
	}

	private static final int[][] ADJACENCY_MATRIX = new int[][] {
			{ 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, // DOLLY_SISTERS
			{ 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1 }, // UNREAL_ESTATE
			{ 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, // DRAGONS_LANDING
			{ 0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0 }, // SMALL_GODS
			{ 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0 }, // THE_SCOURS
			{ 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0 }, // THE_HIPPO
			{ 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0 }, // THE_SHADES
			{ 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0 }, // DIMWELL
			{ 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0 }, // LONGWALL
			{ 0, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0 }, // ISLE_OF_GODS
			{ 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 }  // NAP_HILL
	};

	private final int areaCode;

	private final int buildingCost;

	private AnkhMorporkArea(int code, int cost) {
		areaCode = code;
		buildingCost = cost;
	}

	public int getAreaCode() {
		return areaCode;
	}

	public int getBuildingCost() {
		return buildingCost;
	}

	public static AnkhMorporkArea forCode(int areaCode) {
		return codeToAreaMap.get(areaCode);
	}

	public boolean isNeighboringWith(AnkhMorporkArea otherArea) {
		return ADJACENCY_MATRIX[getAreaCode() - 1][otherArea.getAreaCode() - 1] == 1;
	}
	
	public static boolean areAreasAdjacent(int firstAreaCode, int secondAreaCode) {
		return ADJACENCY_MATRIX[firstAreaCode - 1][secondAreaCode - 1] == 1;
	}

}
