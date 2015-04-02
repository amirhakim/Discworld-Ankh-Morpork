package card.player;

import gameplay.BoardArea;
import gameplay.Game;
import gameplay.Player;
import io.TextUserInterface;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import util.Color;
import util.Interrupt;
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
public enum Scrolls {

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
	private Scrolls(BiConsumer<Player, Game> gameAction) {
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


