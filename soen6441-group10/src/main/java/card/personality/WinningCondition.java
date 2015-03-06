package card.personality;

import java.util.Objects;
import java.util.function.Function;

/**
 * A functional interface that captures the possible winning condition checks that
 * we can perform for personality cards. Unfortunately in Java we are really constrained
 * in terms of function types that we can have - if you want to have an arbitrary
 * function type that has an interface other than those contained in the standard library,
 * you have to resort to tricks like this.
 * 
 * @author (nothing)
 *
 * @param <Integer> the number of players in the game
 * @param <Player> the player for whom we will check the winning condition
 * @param <Game> the global game object
 * @param <Boolean> 
 */
@SuppressWarnings("hiding")
@FunctionalInterface
public interface WinningCondition<Integer, Player, Game, Boolean> {
	
	Boolean apply(Integer numberOfPlayers, Player player, Game game);
	
	default <V> WinningCondition<Integer, Player, Game, V> andThen(
			Function<? super Boolean, ? extends V> after) {
				Objects.requireNonNull(after);
				return (Integer numberOfPlayers, Player player, Game game)
						-> after.apply(apply(numberOfPlayers, player, game));
			}
	
}
