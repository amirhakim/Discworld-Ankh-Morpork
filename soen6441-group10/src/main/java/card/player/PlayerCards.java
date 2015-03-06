package card.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import javax.swing.text.html.Option;
import gameplay.Player;
import gameplay.Game;
public enum PlayerCards {

	/**
	 * Enum for player cards
	 * Each ENUM will have a list of symbols available for that card
	 * Some cards will also have text function (bottom of a card)
	 */
	@SuppressWarnings("serial")
	MR_BOGGIS(
			new ArrayList<Symbol>() {{
				add(Symbol.PLACE_MINION);
				
			}},
			(player, game) -> {
				System.out.println("You Called MR_BOGGIS TEXT");
			}
			
			
	),
	
	;
	
	// attributes of the enum
	private List<Symbol> symbols;
	private BiConsumer<Player, Game> text;
	
	// Constructor
	PlayerCards(List<Symbol> symbols, BiConsumer<Player, Game> text) {
		this.symbols = symbols;
		this.text = text;
	}
	
	public List<Symbol> getSymbols() {
		return this.symbols;
	}
	
	public BiConsumer<Player, Game> getText() {
		return this.text;
	}
	
	
	
}
