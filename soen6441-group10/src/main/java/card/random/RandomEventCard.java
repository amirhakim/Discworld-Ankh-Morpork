package card.random;

import gameplay.Die;
import gameplay.Game;
import gameplay.Player;

import java.util.function.BiConsumer;

import card.AnkhMorporkArea;
import card.Card;

public enum RandomEventCard implements Card {
	
	DRAGON((game, player) -> {
		int areaAffected = Die.getDie().roll();
		game.removeAllPiecesFromArea(areaAffected);
	}),
	
	FLOOD((game, player) -> {
		Die die = Die.getDie();
		int firstAreaAffected = die.roll();
		int secondAreaAffected = die.roll();
		game.floodAreas(firstAreaAffected, secondAreaAffected);
	}),
	
	FIRE((game, player) -> {
		Die die = Die.getDie();
		int areaOnFire = die.roll();
		int nextArea = areaOnFire;
		while (AnkhMorporkArea.areAreasAdjacent(areaOnFire, nextArea) && game.burnBuilding(areaOnFire)) {
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
		game.removeBuilding(area);
	}),
	
	EARTHQUAKE((game, player) -> {
		Die die = Die.getDie();
		int firstArea = die.roll();
		int secondArea = die.roll();
		game.removeBuilding(firstArea);
		game.removeBuilding(secondArea);
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
		Die die = Die.getDie();
		// TODO Implement the murder logic here
	}),
	
	DEMONS_FROM_THE_DUNGEON_DIMENSIONS((game, player) -> {
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

}
