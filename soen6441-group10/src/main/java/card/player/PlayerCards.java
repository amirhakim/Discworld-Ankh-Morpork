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
	
	
	@SuppressWarnings("serial")
	ZERO_THE_RETRO_PHRENOLOGIST(
		/*
		 * You may exchange your Personality Card
		 * with one drawn randomly from those
		 * not in use
		 */
		(player, game) -> {
			System.out.println("YOU CALLED ZERO");
		}, 
		new ArrayList<Symbol>() {{
			add(Symbol.PLACE_A_BUILDING);
			
		}}
			
	),
	
	@SuppressWarnings("serial")
	DR_WHIEFACE(
		/*
		 * Select another player, if they do not want
		 * to give you $5 then place this card in front
		 * of them. This card now counts towards their hand size 
		 * of five cards and when they come to refil their hand. 
		 * They cannot get rid of this card.	
		 */
		(player, game) -> {
			System.out.println("YOU CALLED DR WHITEFACE");
		}, 
		new ArrayList<Symbol>() {{
			add(Symbol.PLACE_MINION);
		}}
			
	),
	
	@SuppressWarnings("serial")
	WALLACE_SONKY(
		new ArrayList<Symbol>() {{
			add(Symbol.INTERRUPT);
		}},
		/*
		 * You cannot be affected by the text on a card 
		 * played by another player
		 */
		(player, game) -> {
			System.out.println("YOU CALLED WALLACE SONKY");			
		}
	),
	
	@SuppressWarnings("serial")
	THE_SEAMSTRESS_GUILD(
		(player, game) -> {
			/*
			 * Choose one player.  Give them
			 * one of your cards.  They must 
			 * give you 2$ in return
			 */
			System.out.println("YOU CALLED THE SEAMSTRESS GUILD");	
		},
		new ArrayList<Symbol>() {{
			add(Symbol.PLACE_MINION);
		}}
	),
	
	@SuppressWarnings("serial")
	MR_PIN_AND_MR_TULIP(
		new ArrayList<Symbol>() {{
			add(Symbol.ASSASINATION);
			add(Symbol.TAKE_MONEY);		
		}}		
	),
	
	@SuppressWarnings("serial")
	THE_THIEVES_GUILD(
		(player, game) -> {
			/*
			 * Take $2, if possible, from
			 * every other player.
			 */
			System.out.println("YOU CALLED THE THIEVES GUILD");
		},
		new ArrayList<Symbol>() {{
			add(Symbol.PLACE_MINION);
		}}
			
			
	)
		
	;
	
	// attributes of the enum
	private BiConsumer<Player, Game> text;
	private List<Symbol> symbols;
	// Boolean indicating if text is acted out before symbols or after
	private Boolean textFirst;
	
	// Constructor
	PlayerCards(List<Symbol> symbols, BiConsumer<Player, Game> text) {
		this.symbols = symbols;
		this.text = text;
		textFirst = false;
	}
	
	PlayerCards(BiConsumer<Player, Game> text, List<Symbol> symbols) {
		this.symbols = symbols;
		this.text = text;
		textFirst = true;
	}
	
	PlayerCards(List<Symbol> symbols) {
		this.symbols = symbols;
		this.text = null;
		this.textFirst = false;
		
	}
	
	public List<Symbol> getSymbols() {
		return this.symbols;
	}
	
	public BiConsumer<Player, Game> getText() {
		return this.text;
	}
	
	public boolean textFirst() {
		return this.textFirst;
		
	}
	
	
	
}
