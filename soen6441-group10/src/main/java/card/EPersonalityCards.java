package card;

import gameplay.Game;
import gameplay.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * An enumeration of the Personality Cards available in the game, which also
 * contains functionality for checking the winning conditions of each of them, 
 * encoded in functions.
 * @author (nothing)
 */
public enum EPersonalityCards {
	
	LORD_VETINARI((playerCount, player, game) ->  
		game.getTotalNumberOfMinions(player) >= WinningConditionHelper.getMinimumRequiredMinions(playerCount)
	),
	
	LORD_SELACHII(WinningConditionHelper::hasWonByControlledAreas),
	
	LORD_RUST(WinningConditionHelper::hasWonByControlledAreas),
	
	LORD_DE_WORDE(WinningConditionHelper::hasWonByControlledAreas),
	
	DRAGON_KING_OF_ARMS((playerCount, player, game) -> game.getTotalNumberOfTroubleMarkers() == 8),
	
	CHRYSOPRASE((playerCount, player, game) -> player.getTotalWorth() >= 50),
	
	COMMANDER_VIMES((playerCount, player, game) -> game.hasPlayerCardsLeft());
	;
	
	private WinningCondition<Integer, Player, Game, Boolean> winningConditionChecker;
	
	private EPersonalityCards(WinningCondition<Integer, Player, Game, Boolean> winningConditionChecker) {
		this.winningConditionChecker = winningConditionChecker;
	}
	
	public WinningCondition<Integer, Player, Game, Boolean> getWinningConditionChecker() {
		return winningConditionChecker;
	}

	
	/**
	 * This can be used as an aid for lookups as needed by different personality cards.
	 * @author gkentr
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
