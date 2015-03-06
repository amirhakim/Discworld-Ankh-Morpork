package card.player;

import gameplay.Game;
import gameplay.Player;

import java.util.function.BiConsumer;

/**
 * 
 * Enum's for each symbol
 * Each symbol takes a function (Player, game) -> {};
 *
 */
public enum Symbol {

	/** 
	 * RULES FOR THIS SYMBOL
	 * 
	 * took one minion piece and place it on the board
	 * only in areas that already have player minions, or adjacent minion
	 * if all minions are on board then remove one and place it elsewhere
	 * if no minions are on board, place minion anywhere
	 */
	PLACE_MINION((player, game)->{
		//do something
		//check if player has minions to place
		//if so ask area to place minion
		//validate area can have a minion placed
		//game.placeMinion(player);
		System.out.println("YOU CALLED PLACE MINION");
	}),
		
	
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


