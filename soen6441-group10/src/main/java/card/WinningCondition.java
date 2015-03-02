package card;

import java.util.Objects;
import java.util.function.Function;

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
