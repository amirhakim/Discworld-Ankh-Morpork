package card.personality;

import gameplay.Game;
import gameplay.Player;

import java.util.HashMap;
import java.util.Map;

import card.Card;

/**
 * <b>An enumeration of the Personality Cards available in the game, which also
 * contains functionality for checking the winning conditions of each of them, 
 * encoded in functions.</b>
 * 
 * @author Team 10 - SOEN6441
 * @version 2.0
 */

public enum PersonalityCard implements Card {
	
	LORD_VETINARI((playerCount, player, game) ->  
		game.getTotalMinionCountForPlayer(player) >= WinningConditionHelper.getMinimumRequiredMinions(playerCount),
		"WIN BY CONTROLLING 11 AREAS (2 PLAYERS), 10 AREAS (3 PLAYERS), 9 AREAS (4 PLAYERS)"
	),
	
	LORD_SELACHII(WinningConditionHelper::hasWonByControlledAreas,
	"WIN BY CONTROLLING 7 AREAS (2 PLAYERS), 5 AREAS (3 PLAYERS), 4 AREAS (4 PLAYERS)"
	),

	
	LORD_RUST(WinningConditionHelper::hasWonByControlledAreas,
	"WIN BY CONTROLLING 7 AREAS (2 PLAYERS), 5 AREAS (3 PLAYERS), 4 AREAS (4 PLAYERS)"
	),

	
	LORD_DE_WORDE(WinningConditionHelper::hasWonByControlledAreas,
	"WIN BY CONTROLLING 7 AREAS (2 PLAYERS), 5 AREAS (3 PLAYERS), 4 AREAS (4 PLAYERS)"
	),

	
	DRAGON_KING_OF_ARMS((playerCount, player, game) -> game.getTotalNumberOfTroubleMarkers() == 8,
	"WIN BY 8+ TROUBLE MARKERS ON THE BOARD"
	),
	
	CHRYSOPRASE((playerCount, player, game) -> player.getTotalWorth() >= 50,
	"WIN BY ACCUMULATING 50$ (MONEY AND BUILDINGS AFTER LOANS)"
	),

	COMMANDER_VIMES((playerCount, player, game) -> !game.hasPlayerCardsLeft(),
	"WIN BY NO MORE PLAYER CARDS"
	)
	;
	
	private WinningCondition<Integer, Player, Game, Boolean> winningConditionChecker;
	
	private String desc;
	
	private PersonalityCard(WinningCondition<Integer, Player, Game, Boolean> winningConditionChecker, String desc_) {
		this.winningConditionChecker = winningConditionChecker;
		this.desc = desc_;
	}
	
	public WinningCondition<Integer, Player, Game, Boolean> getWinningConditionChecker() {
		return winningConditionChecker;
	}
	
	/**
	 * Checks if the player holding this personality card has won the game. It
	 * also prints a message on stdout to indicate this.
	 * 
	 * @param numberOfPlayers
	 * @param player
	 * @param game
	 * @return true if the player holding this personality card has won the game
	 *         by meeting the card's conditions, false otherwise.
	 */
	public boolean hasWon(int numberOfPlayers, Player player, Game game) {
		boolean hasPlayerWon = winningConditionChecker.apply(numberOfPlayers,
				player, game);
		if (hasPlayerWon) {
			System.out.println(player.getName() + " has won the game with " +
					name());
		}
		return hasPlayerWon;
	}
	
	public String getDesc() {
		return desc;
	}
	
	/**
	 * This can be used as an aid for lookups as needed by different personality cards.
	 */
	private static class WinningConditionHelper {
		
		private static final int CONDITION_COUNT = 3;

		/**
		 * Depending on the number of players you have to have a certain number
		 * of minions in different areas on the board.
		 */
		private static final Map<Integer, Integer> minimumRequiredMinions = 
				new HashMap<>(CONDITION_COUNT);
		static {
			minimumRequiredMinions.put(2, 11);
			minimumRequiredMinions.put(3, 10);
			minimumRequiredMinions.put(4, 9);
		}
		
		private static final Map<Integer, Integer> minimumControlledAreas = 
				new HashMap<>(CONDITION_COUNT);
		static {
			minimumControlledAreas.put(2, 7);
			minimumControlledAreas.put(3, 5);
			minimumControlledAreas.put(4, 4);
		}
		
		private WinningConditionHelper() {};
		
		private static Integer getMinimumRequiredMinions(Integer numberOfPlayers) {
			return minimumRequiredMinions.get(numberOfPlayers);
		}
		
		private static Boolean hasWonByControlledAreas(Integer numberOfPlayers, Player player, Game game) {
			return game.getNumberOfAreasControlled(player) >= minimumControlledAreas.get(numberOfPlayers);
		}

	}

}
