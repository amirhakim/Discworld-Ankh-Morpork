package card.random;

import gameplay.Die;
import gameplay.Game;
import gameplay.Player;

import java.util.function.BiConsumer;

import card.Area;

public enum RandomEventCards {
	
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
		while (Area.areAreasAdjacent(areaOnFire, nextArea) && game.burnBuilding(areaOnFire)) {
			nextArea = die.roll();
		}
	}),
	
	FOG((game, player) -> {
		// TODO Draw the top five cards from the draw pile
	}),
	
	RIOTS((game, player) -> {
		if (game.getTotalNumberOfTroubleMarkers() >= 8) {
			game.finishOnPoints();
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
	
	private RandomEventCards(BiConsumer<Game, Player> gameAction) {
		this.gameAction = gameAction;
	}
	
	public BiConsumer<Game, Player> getGameAction() {
		return gameAction;
	}

}
