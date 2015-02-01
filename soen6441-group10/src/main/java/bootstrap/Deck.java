package bootstrap;

import java.util.Collections;
import java.util.Stack;
import java.util.function.Function;

import card.AnkhMorporkCard;

/**
 * A generic class for the different card decks used in the game.
 * 
 * @param <E>
 */
public class Deck<W extends CardWrapper<? extends AnkhMorporkCard>> {

	private Stack<W> cards;
	private static boolean hasCreatedInstance;

	private <T> Deck(T[] cardSet, Function<T, W> wrapperConstructor) {
		for (T cardName : cardSet) {
			cards.push(wrapperConstructor.apply(cardName));
		}
	}

	public static <E extends AnkhMorporkCard, W1 extends CardWrapper<E>> Deck<W1> getDeck(
			E[] cardSet, Function<E, W1> wrapperConstructor) {
		if (!hasCreatedInstance) {
			hasCreatedInstance = true;
			return new Deck<W1>(cardSet, wrapperConstructor);
		} else {
			// Cannot create more than two instances;
			// Java won't allow a static instance field in a class
			// which is parameterized with non-static types
			throw new IllegalStateException();
		}
	}

	/**
	 * Removes the Card object sitting currently on top of the deck and returns
	 * it.
	 * 
	 * @return The Card object sitting on top of the deck.
	 */
	public W pop() {
		return cards.pop();
	}

	/**
	 * Shuffles the deck in-place.
	 */
	public void shuffle() {
		Collections.shuffle(cards);
	}

	/**
	 * @return The current size of the deck.
	 */
	public int size() {
		return cards.size();
	}

}
