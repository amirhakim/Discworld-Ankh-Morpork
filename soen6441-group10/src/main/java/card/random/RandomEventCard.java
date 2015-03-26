package card.random;

import gameplay.Die;
import gameplay.Game;
import gameplay.Player;
import io.TextUserInterface;

import java.util.List;
import java.util.function.BiConsumer;

import util.Color;
import card.Card;
import card.city.AnkhMorporkArea;

public enum RandomEventCard implements Card {
	
	DRAGON((game, player) -> {
		System.out.println("Dragon! Die roll to determine the affected area...");
		int areaAffected = Die.getDie().roll();
		game.removeAllPiecesFromArea(areaAffected);
	}),
	
	FLOOD((game, player) -> {
		System.out.println("Flood! 2 die rolls to get the affected areas (river-adjacent only): ");
		Die die = Die.getDie();
		AnkhMorporkArea firstAreaAffected = AnkhMorporkArea.forCode(die.roll());
		AnkhMorporkArea secondAreaAffected = AnkhMorporkArea.forCode(die.roll());
		boolean isOneAreaOnlyAffected = (secondAreaAffected == firstAreaAffected);

		List<AnkhMorporkArea> adjacentToFirst = AnkhMorporkArea.getAdjacentAreas(firstAreaAffected);
		if (!isOneAreaOnlyAffected) {
			adjacentToFirst.remove(secondAreaAffected); // can't move to another area affected
		}
		
		moveMinionsBetweenAreas(firstAreaAffected, adjacentToFirst, game, player);

		if (!isOneAreaOnlyAffected) {
			List<AnkhMorporkArea> adjacentToSecond = AnkhMorporkArea.getAdjacentAreas(secondAreaAffected);
			adjacentToSecond.remove(firstAreaAffected);
		}
	}),
	
	FIRE((game, player) -> {
		Die die = Die.getDie();
		int areaOnFire = die.roll();
		int nextArea = areaOnFire;
		while (AnkhMorporkArea.areAreasAdjacent(areaOnFire, nextArea) && 
				game.burnBuilding(areaOnFire)) {
			nextArea = die.roll();
			// TODO Make fire expand
		}
	}),
	
	FOG((game, player) -> {
		for (int i = 0; i < 5; i++) {
			game.drawPlayerCard();
		}
	}),
	
	RIOTS((game, player) -> {
		if (game.getTotalNumberOfTroubleMarkers() >= 8) {
			game.getWinnersByPoints();
		}
	}),
	
	EXPLOSIONS((game, player) -> {
		int area = Die.getDie().roll();
		game.removeBuilding(player, area);
	}),
	
	EARTHQUAKE((game, player) -> {
		Die die = Die.getDie();
		int firstArea = die.roll();
		int secondArea = die.roll();
		game.removeBuilding(player, firstArea);
		game.removeBuilding(player, secondArea);
	}),
	
	SUBSIDENCE((game, player) -> {
		game.handleSubsidence();
	}),
	
	BLOODY_STUPID_JOHNSON((game, player) -> {
		int area = Die.getDie().roll();
		game.disableAreaCard(area);
		game.removeMinion(area, player);
	}),
	
	TROLLS((game, player) -> {
		Die die = Die.getDie();
		int[] areas = { die.roll(), die.roll(), die.roll() };
		for (int area : areas) {
			if (game.placeTroll(area)) {
				game.addTroubleMarker(area);
			}
		}
	}),
	
	MYSTERIOUS_MURDERS((game, player) -> {
		System.out.println("Mysterious Murders: A demon will be placed in 4 areas.");
		Die die = Die.getDie();
		// TODO: Complete
		System.out.println("TODO: Mysterious Murders");
	}),
	
	DEMONS_FROM_THE_DUNGEON_DIMENSIONS((game, player) -> {
		System.out.println("Demons from the Dungeon Dimensions: A demon will be placed in 4 areas.");
		Die die = Die.getDie();
		int[] areas = { die.roll(), die.roll(), die.roll(), die.roll() };
		for (int area : areas) {
			game.placeDemon(area);
		}
	});
	
	private BiConsumer<Game, Player> gameAction;
	
	private RandomEventCard(BiConsumer<Game, Player> gameAction) {
		this.gameAction = gameAction;
	}
	
	public BiConsumer<Game, Player> getGameAction() {
		return gameAction;
	}
	
	private static void moveMinionsBetweenAreas(AnkhMorporkArea area, List<AnkhMorporkArea> adjacentAreas,
			Game game, Player player) {
		TextUserInterface UI = new TextUserInterface();
		Color[] order = game.getPlayersFromCurrentPlayer();
		for (Color c : order) {
			if (game.hasMinionInArea(area, c)) {
				Player p = game.getPlayerOfColor(c);
				AnkhMorporkArea a = UI.getAreaChoice(adjacentAreas,
						"Select an area to which your minion will be moved:",
						">");
				game.removeMinion(a.getAreaCode(), p);
				game.addMinion(a.getAreaCode(), p);
			}
		}
	}

}
