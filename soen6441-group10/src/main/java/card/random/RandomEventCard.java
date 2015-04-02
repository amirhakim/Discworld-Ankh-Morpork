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
import card.player.GreenPlayerCard;

public enum RandomEventCard implements Card {
	
	DRAGON((game, player) -> {
		System.out.println("Dragon! Die roll to determine the affected area...");
		int areaAffected = Die.getDie().roll();
		System.out.println("Rolled a " + areaAffected + ": removing trouble and buildsings from " + 
				game.getGameBoard().get(areaAffected).getArea().name() + "...");
		game.removeAllPiecesFromArea(areaAffected);
	}),
	
	FLOOD((game, player) -> {
		System.out.println("Flood! 2 die rolls to get the affected areas (river-adjacent only): ");
		Die die = Die.getDie();
		AnkhMorporkArea firstAreaAffected = AnkhMorporkArea.forCode(die.roll());
		AnkhMorporkArea secondAreaAffected = AnkhMorporkArea.forCode(die.roll());
		boolean isOneAreaOnlyAffected = (secondAreaAffected == firstAreaAffected);
		System.out.println("Affected area(s):" + firstAreaAffected + 
				((isOneAreaOnlyAffected) ? "" : secondAreaAffected));

		List<AnkhMorporkArea> adjacentToFirst = AnkhMorporkArea.getAdjacentAreas(firstAreaAffected);
		if (!isOneAreaOnlyAffected) {
			adjacentToFirst.remove(secondAreaAffected); // can't move to another area affected
		}
		
		moveMinionsBetweenAreas(firstAreaAffected, adjacentToFirst, game, player);

		if (!isOneAreaOnlyAffected) {
			List<AnkhMorporkArea> adjacentToSecond = AnkhMorporkArea.getAdjacentAreas(secondAreaAffected);
			adjacentToSecond.remove(firstAreaAffected);
			moveMinionsBetweenAreas(secondAreaAffected, adjacentToSecond, game, player);
		}
		
	}),
	
	FIRE((game, player) -> {
		System.out.println("Fire! Areas with buildings shall burn in succession, "
				+ "spreading to adjacent areas, as long as they have buildings...");
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
		
		System.out.println("The fire has finished its work of evil...");
	}),
	
	FOG((game, player) -> {
		System.out.println("Fog! Discard the top 5 cards from the draw pile.");
		for (int i = 0; i < 5; i++) {
			Optional<GreenPlayerCard> topGreenDeckCard = game.drawPlayerCard();
			if (topGreenDeckCard.isPresent()) {
				System.out.println(topGreenDeckCard.get().name() + " was drawn.");
			}
		}
	}),
	
	RIOTS((game, player) -> {
		if (game.getTotalNumberOfTroubleMarkers() >= 8) {
			System.out.println("Riots! That shall be the end of the game my lords...");
			game.finishGameOnPoints(false);
		} else {
			System.out.println("Riots were ineffective since there were less "
					+ "than 8 trouble markers on the board.");
		}
	}),
	
	EXPLOSION((game, player) -> {
		System.out.println("Explosions! Die roll to remove a building:");
		int area = Die.getDie().roll();
		if (game.removeBuilding(area)) {
			System.out.println("Building removed from " + AnkhMorporkArea.forCode(area).name() + ".");
		}
	}),
	
	EARTHQUAKE((game, player) -> {
		System.out.println("Earthquake! Die roll to remove buildings from 1/2 areas:");
		Die die = Die.getDie();
		int firstArea = die.roll();
		int secondArea = die.roll();
		if (game.removeBuilding(firstArea)) {
			System.out.println("Building removed from " + AnkhMorporkArea.forCode(firstArea).name() + ".");
		}
		if (secondArea != firstArea && game.removeBuilding(secondArea)) {
			System.out.println("Building removed from " + AnkhMorporkArea.forCode(secondArea).name() + ".");
		}
	}),
	
	SUBSIDENCE((game, player) -> {
		System.out.println("Subsidence: Each player will pay $2 for every building "
				+ "owned on the board, otherwise the building will be removed.");
		game.handleSubsidence();
	}),
	
	BLOODY_STUPID_JOHNSON((game, player) -> {
		System.out.println("Bloody Stupid Johnson! An area's card will be disabled "
				+ "if it is in play and a minion will be removed from the same area.");
		int areaID = Die.getDie().roll();
		Optional<Player> areaOwner = game.setCityAreaCardState(areaID, 
				(p, area) -> p.disableCityAreaCard(area));
		if (areaOwner.isPresent()) {
			System.out.println("City area card disabled for " + AnkhMorporkArea.forCode(areaID).name() 
					+ ". Removing a minion from there...");
			if (game.removeMinion(areaID, areaOwner.get())) {
				System.out.println("Minion removed.");
			}
		}
	}),
	
	TROLLS((game, player) -> {
		System.out.println("Trolls! They will be placed thrice:");
		Die die = Die.getDie();
		int[] areas = { die.roll(), die.roll(), die.roll() };
		for (int area : areas) {
			if (game.placeTroll(area)) {
				System.out.println("Troll placed at " + AnkhMorporkArea.forCode(area).name());
			}
		}
	}),
	
	MYSTERIOUS_MURDERS((game, player) -> {
		System.out.println("Mysterious Murders: Each player must remove a minion "
				+ "from an area (if it has any) in succession.");
		Die die = Die.getDie();
		Color[] playerOrder = game.getPlayersFromCurrentPlayer();
		TextUserInterface UI = new TextUserInterface();

		for (Color c : playerOrder) {
			AnkhMorporkArea a = AnkhMorporkArea.forCode(die.roll());
			Optional<Map<Color, Integer>> minionsInArea = game.getMinionsInArea(a);
			if (minionsInArea.isPresent()) {
				Color minionToKill = UI.getMinionChoice(minionsInArea.get(), "The " + c + " player " +
						"will choose a minion to kill in " + a.name() + ".", "Choose a minion: ");
				if (game.removeMinion(a.getAreaCode(), game.getPlayerOfColor(minionToKill))) {
					System.out.println(minionToKill + " minion killed in " + a.name());
				}
			} else {
				System.out.println("There are no minions in " + a + ".");
			}
		}
	}),
	
	DEMONS_FROM_THE_DUNGEON_DIMENSIONS((game, player) -> {
		System.out.println("Demons from the Dungeon Dimensions: A demon will be placed "
				+ "4 times in the area rolled. A troublemarker will be added to each "
				+ "of these areas (if one doesn't exist there already).");
		Die die = Die.getDie();
		int[] areas = { die.roll(), die.roll(), die.roll(), die.roll() };
		for (int area : areas) {
			if (game.placeDemon(area)) {
				System.out.println("Demon placed in " + AnkhMorporkArea.forCode(area).name() + ".");
			}
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
