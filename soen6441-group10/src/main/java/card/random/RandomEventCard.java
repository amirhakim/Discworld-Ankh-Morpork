package card.random;

import gameplay.Die;
import gameplay.Game;
import gameplay.Player;
import io.TextUserInterface;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
		System.out.println("Fire! Areas with buildings shall burn in succession: ");
		Die die = Die.getDie();
		int areaOnFire = die.roll();
		int previousAreaOnFire = areaOnFire;

		// Recall that an area is considered to be adjacent to itself by condition
		while (AnkhMorporkArea.areAreasAdjacent(areaOnFire, previousAreaOnFire) && 
				game.removeBuilding(areaOnFire)) {
			System.out.println("Burnt building in: " + AnkhMorporkArea.forCode(areaOnFire));
			previousAreaOnFire = areaOnFire;
			areaOnFire = die.roll();
		}
	}),
	
	FOG((game, player) -> {
		System.out.println("Fog! Discard the top 5 cards from the draw pile.");
		for (int i = 0; i < 5; i++) {
			game.drawPlayerCard();
		}
	}),
	
	RIOTS((game, player) -> {
		System.out.println("Riots! That shall be the end of the game my lords...");
		if (game.getTotalNumberOfTroubleMarkers() >= 8) {
			game.finishGameOnPoints(false);
		}
	}),
	
	EXPLOSION((game, player) -> {
		System.out.println("Explosions! Die roll to remove a building:");
		int area = Die.getDie().roll();
		game.removeBuilding(area);
	}),
	
	EARTHQUAKE((game, player) -> {
		System.out.println("Earthquake! Die roll to remove buildings from 1/2 areas:");
		Die die = Die.getDie();
		int firstArea = die.roll();
		int secondArea = die.roll();
		game.removeBuilding(firstArea);
		if (secondArea != firstArea) {
			game.removeBuilding(secondArea);
		}
	}),
	
	SUBSIDENCE((game, player) -> {
		System.out.println("Subsidence! Pay up for your properties ladies and gentlemen...");
		game.handleSubsidence();
	}),
	
	BLOODY_STUPID_JOHNSON((game, player) -> {
		System.out.println("Bloody Stupid Johnson! An area's card will be disabled "
				+ "if it is in play and a minion will be removed from the same area.");
		int areaID = Die.getDie().roll();
		Optional<Player> areaOwner = game.setCityAreaCardState(areaID, 
				(p, area) -> p.disableCityAreaCard(area));
		if (areaOwner.isPresent()) {
			System.out.println("City area card disabled. Removing a minion from there...");
			game.removeMinion(areaID, areaOwner.get());
		}
	}),
	
	TROLLS((game, player) -> {
		System.out.println("Trolls! They will be placed thrice:");
		Die die = Die.getDie();
		int[] areas = { die.roll(), die.roll(), die.roll() };
		for (int area : areas) {
			game.placeTroll(area);
		}
	}),
	
	MYSTERIOUS_MURDERS((game, player) -> {
		System.out.println("Mysterious Murders: Each player must remove a minion from an area.");
		Die die = Die.getDie();
		Color[] playerOrder = game.getPlayersFromCurrentPlayer();
		TextUserInterface UI = new TextUserInterface();

		for (Color c : playerOrder) {
			AnkhMorporkArea a = AnkhMorporkArea.forCode(die.roll());
			Optional<Map<Color, Integer>> minionsInArea = game.getMinionsInArea(a);
			if (minionsInArea.isPresent()) {
				Color minionToKill = UI.getMinionChoice(minionsInArea.get(), "The " + c + " player " +
						" will choose a minion to kill in " + a + ".", "Choose a minion: ");
				game.removeMinion(a.getAreaCode(), game.getPlayerOfColor(minionToKill));
			} else {
				System.out.println("There are no minions in " + a + ".");
			}
		}
	}),
	
	DEMONS_FROM_THE_DUNGEON_DIMENSIONS((game, player) -> {
		System.out.println("Demons from the Dungeon Dimensions: A demon will be placed "
				+ "4 times in an area.");
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
				game.removeMinion(area.getAreaCode(), p);
				game.addMinion(a.getAreaCode(), p);
			}
		}
	}

}
