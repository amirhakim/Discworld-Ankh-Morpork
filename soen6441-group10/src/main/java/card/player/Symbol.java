package card.player;

import gameplay.Game;
import gameplay.Player;

import java.util.Collection;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiConsumer;

import card.AnkhMorporkArea;
import card.BoardArea;

/**
 * 
 * Enum's for each symbol
 * Each symbol takes a function (Player, game) -> {};
 *
 */
public enum Symbol {

	/** 
	 * You take one of your minion pieces and you place
	 * it in an area on the board. You must place it in
	 * either an area that you already have a minion in or in an
 	 * adjacent area. There is no limit on the number of minions
	 * that can be placed in an area. If you already have all of your
	 * minions on the board then you can remove one and then
	 * place it somewhere else (making sure you follow the other
	 * rules above). These rules apply whenever you have to place a
	 * minion, including due to the play of an interrupt card. If you
	 * do not have a minion on the board then you can place your
	 * minion in any area. 
	*/
	PLACE_MINION((player, game)->{
		Scanner scanner = new Scanner(System.in);
		// Get players minion count
		int availableMinions = player.getMinionCount();
		Map<Integer, BoardArea> gameBoard = game.getGameBoard();
		if(availableMinions == player.TOTAL_MINIONS) {
			System.out.println("Pick area to place minion");
			
			for(BoardArea ba : gameBoard.values()) {
				System.out.println(ba.getArea().getAreaCode() + ": " + ba.getArea());
			}
			
			int action = scanner.nextInt();
			BoardArea chosenArea = gameBoard.get(action);
			chosenArea.addMinion(player);
		} else if(availableMinions == 0) {
			// Get areas where player has minions
			Map<Integer, BoardArea> subGameBoard = game.getAreasWithPlayerMinions(player);
			
			System.out.println("Pick area to remove minion");
			
			for(BoardArea ba : subGameBoard.values()) {
				System.out.println(ba.getArea().getAreaCode() + ": " + ba.getArea());
			}
			
			int action = scanner.nextInt();
			BoardArea chosenArea = gameBoard.get(action);
			chosenArea.removeMinion(player);
			// Get areas that player CAN player on
			Map<Integer, BoardArea> possibilities = game.getMinionPlacementAreas(player);

			System.out.println("Pick area to place minion");
			for(BoardArea ba : possibilities.values()) {
				System.out.println(ba.getArea().getAreaCode() + ": " + ba.getArea().getAreaCode());
			}
			action = scanner.nextInt();
			chosenArea = gameBoard.get(action);
			chosenArea.addMinion(player);
			
		} else {
			int action = scanner.nextInt();
			Map<Integer, BoardArea> possibilities = game.getMinionPlacementAreas(player);

			System.out.println("Pick area to place minion");
			for(BoardArea ba : possibilities.values()) {
				System.out.println(ba.getArea().getAreaCode() + ": " + ba.getArea().getAreaCode());
			}
			action = scanner.nextInt();
			BoardArea chosenArea = gameBoard.get(action);
			chosenArea.addMinion(player);
			
		}
	}),
	
	
	
	/**
	 * You can place one of your building pieces in an
	 * area that you have a minion in. You cannot build
	 * in an area that already contains either a building or
	 * a trouble marker. The cost of placing the building
	 * is shown in the area and on the matching City Area card.
	 * You pay this amount of money to the bank. You then take
	 * the matching City Area card into your possession. If, for any
	 * reason, the building is removed later on then you must return
	 * the City Area card to the display. Place the card in front of
	 * your position, face up.
	 * You can have up to six buildings on the board. If you already
 	 * have six buildings on the board then you can choose one to
	 * remove and place in the area you wish to build in. Make sure
	 * you return the City Area card for the area that you remove
	 * your building from.
	 */
	PLACE_A_BUILDING((player, game)->{
		System.out.println("YOU CALLED PLACE A BUILDING");
	}),
	
	/**
	 *  You remove one minion
	 * (but not your own) or troll or demon of your choice
	 * from an area that contains a trouble marker.
	 * Remember that this will also remove the trouble
	 * marker from the area.
	 */
	ASSASINATION((player, game)->{
		System.out.println("YOU CALLED ASSASINATION");
	}),
	
	
	/**
	 * You remove one trouble marker from an area
	 * of your choice. 
	 */
	REMOVE_TROUBLE_MARKET((player, game) ->{
		System.out.println("YOU CALLED TROUBLE MARKER");
	}),
	
	
	/**
	 * The gold circle has an amount
	 * of money shown in it. You take this amount from
	 * the bank.
	 */
	TAKE_MONEY((player, game) -> {
		System.out.println("YOU CALLED TAKE MONEY");
	}),
	
	
	/**
	 *This is the only action that
	 * isn’t optional. You must draw the top card from the
	 * Random Event deck. This will tell you which event
	 * occurs. You must then check the back of the rule
	 * book to see the effect of that event. Once you have
	 * completed the event you place the Random Event
	 * card to one side (i.e. each event can only occur once
	 * in the entire game).
	 */
	RANDOM_EVENT((player, game) -> {
		System.out.println("YOU CALLED RANDOM EVENT");
	}),
	
	/**
	 * You play another card
	 * from your hand. You could end up playing a
	 *succession of cards that have this symbol on.
	 */
	PLAY_ANOTHER_CARD((player, game) -> {
		System.out.println("YOU CALLED PLAY ANOTHER CARD");
	}),
	
	/**
	 *  A card with this symbol on can be
	 * played at any time, even if it is not your turn.
	 * Most interrupt cards protect you from something
	 * bad happening to you. You may play this card at any
	 * time, even if it is not your turn. For example,
	 * somebody might try to assassinate one of your
	 * minions and you could play the ‘Gaspode’ card to
	 * stop them. Playing an interrupt card in your turn
 	 * does not count as an action, Please make sure you
	 * play such a card promptly. If you forget to use a card
	 * to negate the effect of another card played against
	 * you then you cannot ‘go back in time’ and play the
	 * card retrospectively
	 */
	INTERRUPT((player, game) -> {
		System.out.println("YOU CALLED INTERRUPT");
	})
	
    ;
	
	
	
	
	

	/**
	 * Functional Interface
	 * Use what is needed here, BiConsumer is for two params <param1, param2> and returns void
	 * gameAction is the function held in each symbol
	 * See CardTest testCard() for an example of implementation
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
	 * @return function to exectue
	 */
	public BiConsumer<Player, Game> getGameAction() {
		return gameAction;
	}

}


