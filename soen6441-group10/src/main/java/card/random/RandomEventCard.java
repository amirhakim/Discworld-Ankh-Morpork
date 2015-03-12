package card.random;

import gameplay.Die;
import gameplay.Game;
import gameplay.Player;
import io.TextUserInterface;

import java.util.List;
import java.util.function.BiConsumer;

import card.Card;
import card.city.AnkhMorporkArea;

public enum RandomEventCard implements Card {
	
	DRAGON((game, player) -> {
		int areaAffected = Die.getDie().roll();
		System.out.println("Dragon! Removing all pieces from area #" + areaAffected);
		game.removeAllPiecesFromArea(areaAffected);
	}),
	
	FLOOD((game, player) -> {
		Die die = Die.getDie();
		AnkhMorporkArea firstAreaAffected = AnkhMorporkArea.forCode(die.roll());
		AnkhMorporkArea secondAreaAffected = AnkhMorporkArea.forCode(die.roll());
		System.out.println("Flood! Flooding areas " + firstAreaAffected + ", " +
				secondAreaAffected);
		List<AnkhMorporkArea> adjacentToFirst = AnkhMorporkArea.getAdjacentAreas(firstAreaAffected);
		List<AnkhMorporkArea> adjacentToSecond = AnkhMorporkArea.getAdjacentAreas(secondAreaAffected);

		TextUserInterface UI = new TextUserInterface();
		// TODO: Finish this
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
		Die die = Die.getDie();
		System.out.println("TODO: Mysterious Murders");
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
