package card.player;

import gameplay.BoardArea;
import gameplay.Game;
import gameplay.Player;
import io.TextUserInterface;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import util.Color;
import card.city.AnkhMorporkArea;
import card.random.RandomEventCard;

/**
 * 
 * <b>Enum's for each symbol
 * Each symbol takes a function (Player, game) -> {};</b>
 * 
 * @author Team 10 - SOEN6441
 * @version 2.0
 */
public enum Symbol {

	/** 
	 * <b>You take one of your minion pieces and you place
	 * it in an area on the board. </br>You must place it in
	 * either an area that you already have a minion in or in an
 	 * adjacent area. <br>There is no limit on the number of minions
	 * that can be placed in an area.<br> If you already have all of your
	 * minions on the board then you can remove one and then
	 * place it somewhere else (making sure you follow the other
	 * rules above).<br> These rules apply whenever you have to place a
	 * minion, including due to the play of an interrupt card.<br> If you
	 * do not have a minion on the board then you can place your
	 * minion in any area. </b>
	*/
	PLACE_MINION((player, game)->{
		TextUserInterface UI = new TextUserInterface();
		// Get players minion count
		int availableMinions = player.getMinionCount();

		Map<Integer, BoardArea> gameBoard = game.getGameBoard();
		if (availableMinions == Player.TOTAL_MINIONS) {
				AnkhMorporkArea chosenArea = UI.getAreaChoice(
						gameBoard.values().stream().map(BoardArea::getArea)
								.collect(Collectors.toList()),
						"All minions available. Select area to place minion",
						"Choose Area: ");
				gameBoard.get(chosenArea.getAreaCode()).addMinion(player);

		} else if (availableMinions == 0) {

			// Get areas where player has minions
			Map<Integer, BoardArea> subGameBoard = game.getAreasWithPlayerMinions(player);
			AnkhMorporkArea chosenArea = UI.getAreaChoice(
					subGameBoard.values().stream().map(BoardArea::getArea)
							.collect(Collectors.toList()),
					"All minions in play. Select area to remove minion",
					"Choose Area: ");
			subGameBoard.get(chosenArea.getAreaCode()).removeMinion(player);

			// Get areas that player CAN player on
			Map<Integer, BoardArea> possibilities = game
					.getMinionPlacementAreas(player);
			possibilities.remove(chosenArea.getAreaCode());
			chosenArea = UI.getAreaChoice(
					possibilities.values().stream().map(BoardArea::getArea)
							.collect(Collectors.toList()),
					"Select Area to place removed minion.", "Choose Area: ");
			gameBoard.get(chosenArea.getAreaCode()).addMinion(player);

		} else {

			Map<Integer, BoardArea> possibilities = game.getMinionPlacementAreas(player);
			AnkhMorporkArea chosenArea = UI
					.getAreaChoice(
							possibilities.values().stream()
									.map(BoardArea::getArea)
									.collect(Collectors.toList()),
							"Unused minions available. Select area to palce new minion",
							"Choose Area: ");
			possibilities.get(chosenArea.getAreaCode()).addMinion(player);	
		}
		
	}),
	
	
	
	/**
	 * <b>You can place one of your building pieces in an
	 * area that you have a minion in.<br> You cannot build
	 * in an area that already contains either a building or
	 * a trouble marker.<br> The cost of placing the building
	 * is shown in the area and on the matching City Area card.<br>
	 * You pay this amount of money to the bank. You then take
	 * the matching City Area card into your possession.<br> If, for any
	 * reason, the building is removed later on then you must return
	 * the City Area card to the display. Place the card in front of
	 * your position, face up.<br>
	 * You can have up to six buildings on the board. If you already
 	 * have six buildings on the board then you can choose one to
	 * remove and place in the area you wish to build in.<br> Make sure
	 * you return the City Area card for the area that you remove
	 * your building from.</b>
	 */
	PLACE_A_BUILDING((player, game)->{
		// Get players buildings
		int playerBuildings = player.getBuildings();
		TextUserInterface UI = new TextUserInterface();
		// Case 1: Must remove a building first
		if(playerBuildings == 0){
			// Choose Area to remove building from
			Map<Integer, BoardArea> playerAreas = game.getBuildingAreas(player);
			BoardArea chosenRemoveArea = UI.getAreaChoice(playerAreas, "Choose Area to remove building from", "Choose Area: ");
			// Choose Area to place building on
			Map<Integer, BoardArea> freeAreas = game.getBuildingFreeAreas();
			// Check that you can place a building
			if(freeAreas.size() == 0) {
				System.out.println("All areas have a building or a trouble marker.");
			} else {
				//Do the building removal and placement
				freeAreas.remove(chosenRemoveArea);		
				BoardArea chosenPlaceArea = UI.getAreaChoice(freeAreas, "Choose Area to place building on", "Choose Area: ");
				game.addBuilding(player, chosenPlaceArea);
				
			}
		} else {
			// Player has free buildings too place
			// Choose Area to place building on
			Map<Integer, BoardArea> freeAreas = game.getBuildingFreeAreas();
			if(freeAreas.size() == 0) {
				System.out.println("All areas either has a building or a trouble marker.");
			} else {
				BoardArea chosenPlaceArea = UI.getAreaChoice(freeAreas, "Choose Area to place building on", "Choose Area: ");
				game.addBuilding(player, chosenPlaceArea);
			}
		}
	}),
	
	
	/**
	 * <b>You remove one minion
	 * (but not your own) or troll or demon of your choice
	 * from an area that contains a trouble marker.<br>
	 * Remember that this will also remove the trouble
	 * marker from the area.</b>
	 */
	ASSASINATION((player, game)->{
		Map<Integer, BoardArea> troubleAreas = game.getTroubleAreas();
		// Ensure there is a troll, demon or minion other than your own on the area
		for(BoardArea trouble : troubleAreas.values()) {
			Map<Color, Integer> troubleMinions = trouble.getMinions();
			troubleMinions.remove(player.getColor());
			
			if(trouble.getDemonCount() < 0 && trouble.getTrollCount() < 0 
					&& troubleMinions.size() < 0 ) {
				troubleAreas.remove(trouble.getArea().getAreaCode());
			}
			
		}
		TextUserInterface textUI = new TextUserInterface();
		BoardArea trouble = textUI.getAreaChoice(troubleAreas, "Select area for assasinnation", "choice: ", true);
		textUI.assinate(trouble, player, game);
	}),
	
	
	/**
	 * <b>You remove one trouble marker from an area
	 * of your choice. </br>
	 */
	REMOVE_TROUBLE_MARKER((player, game) ->{
		Map<Integer, BoardArea> troubleAreas = game.getTroubleAreas();
		TextUserInterface textUI = new TextUserInterface();
		BoardArea trouble = textUI.getAreaChoice(troubleAreas, "Select area to remove trouble", "Choice: ");
		trouble.removeTroubleMarker();
	}),
	
	
	/**
	 * <b>The gold circle has an amount
	 * of money shown in it. You take this amount from
	 * the bank.</b>
	 */
	TAKE_MONEY((player, game) -> {
		// Get calling card
		GreenPlayerCard playerCard = game.getCurrentCardInPlay();
		if(playerCard != null) {
			Integer amount = playerCard.getMoney();
			game.getBank().decreaseBalance(amount);
			player.increaseMoney(amount);
			System.out.println("Took " + amount + " from bank");
		}
	}),
	
	
	/**
	 *<b>This is the only action that
	 * isn’t optional.<br> You must draw the top card from the
	 * Random Event deck. This will tell you which event
	 * occurs.<br> You must then check the back of the rule
	 * book to see the effect of that event.<br> Once you have
	 * completed the event you place the Random Event
	 * card to one side (i.e. each event can only occur once
	 * in the entire game).</b>
	 */
	RANDOM_EVENT((player, game) -> {
		
		RandomEventCard random = game.drawRandomEventCard().get();
		random.getGameAction().accept(game, player);

		System.out.println(random + " was played");
	}),
	
	/**
	 * <b>You play another card
	 * from your hand. You could end up playing a
	 *succession of cards that have this symbol on.</b>
	 */
	PLAY_ANOTHER_CARD((player, game) -> {
		// DONT DO ANYTHING HERE ... in game flow if this symbol is found move on
	}),
	
	/**
	 * <b> A card with this symbol on can be
	 * played at any time, even if it is not your turn.<br>
	 * Most interrupt cards protect you from something
	 * bad happening to you.<br> You may play this card at any
	 * time, even if it is not your turn. For example,
	 * somebody might try to assassinate one of your
	 * minions and you could play the ‘Gaspode’ card to
	 * stop them.<br> Playing an interrupt card in your turn
 	 * does not count as an action, Please make sure you
	 * play such a card promptly.<br> If you forget to use a card
	 * to negate the effect of another card played against
	 * you then you cannot ‘go back in time’ and play the
	 * card retrospectively.</b>
	 */
	INTERRUPT((player, game) -> {
		System.out.println("YOU CALLED INTERRUPT");
	})
	
    ;
	
	
	
	
	

	/**
	 * <b>Functional Interface<br>
	 * Use what is needed here, BiConsumer is for two params <param1, param2> and returns void
	 * gameAction is the function held in each symbol.<br>
	 * See CardTest testCard() for an example of implementation</b>
	 */
	private BiConsumer<Player, Game> gameAction;
	
	
	/** 
	 * Constructor
	 * @param gameAction
	 */
	private Symbol(BiConsumer<Player, Game> gameAction) {
		this.gameAction = gameAction;
	}
	
	/**
	 * 
	 * @return function to execute
	 */
	public BiConsumer<Player, Game> getGameAction() {
		return gameAction;
	}

	
}


