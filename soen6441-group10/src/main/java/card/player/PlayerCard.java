package card.player;

import gameplay.Game;
import gameplay.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import card.Card;

@SuppressWarnings("serial")
/**
 * Enum for player cards
 * Each ENUM will have a list of symbols available for that card
 * Some cards will also have text function (bottom of a card)
 */
public enum PlayerCard implements Card {

	MR_BOGGIS(
			new ArrayList<Symbol>() {{
				add(Symbol.PLACE_MINION);
			}},
			(player, game) -> {
				System.out.println("You Called MR_BOGGIS TEXT");
			}
	),

	INIGO_SKIMMER(
			new ArrayList<Symbol>() {{
				add(Symbol.ASSASINATION);
				add(Symbol.TAKE_MONEY);	// Take 2 Dollars		
			}}	
	), 
	
	HISTORY_MONKS(
			/*
			 * Shuffle the discard pile and draw four cards randomly.
			 * Place the remaining cards back as the discard pile.
			 */
			(player, game) -> {
				System.out.println("YOU CALLED HISTORY MONKS");
			},
			new ArrayList<Symbol>() {{
				add(Symbol.PLACE_MINION);		
			}}		
	),
	
	HEX(
			/*
			 * Take 3 cards from the draw deck.
			 */
			(player, game) -> {
				System.out.println("YOU CALLED HEX");
			},
			new ArrayList<Symbol>() {{
				add(Symbol.PLACE_A_BUILDING);		
			}}		
	),
	
	HERE_N_NOW(
			/*
			 * Roll the die. On a roll of '7' or more you 
			 * take $3 from a player of your choice. 
			 * On a roll of a '1' you must remove one of your 
			 * minions from the board. All other results have 
			 * no effect.
			 */
			(player, game) -> {
				System.out.println("YOU CALLED HERE'N'NOW");
			},
			new ArrayList<Symbol>() {{
				add(Symbol.PLAY_ANOTHER_CARD);		
			}}		
	),
	
	HARRY_KING(
			/*
			 * Discard as many cards as you wish and 
			 * take $2 for each one discarded.
			 */
			new ArrayList<Symbol>() {{
				add(Symbol.PLACE_MINION);		
			}},	
			(player, game) -> {
				System.out.println("YOU CALLED HARRY KING");
			}			
	), 
	
	HARGAS_HOUSE_OF_RIBS(
			new ArrayList<Symbol>() {{
				add(Symbol.TAKE_MONEY);	//3 Dollars	
				add(Symbol.PLACE_MINION);
			}}		
	),
	
	MR_GRYLE(
			new ArrayList<Symbol>() {{
				add(Symbol.ASSASINATION);	
				add(Symbol.TAKE_MONEY); // 1 Dollar
			}}		
	),
	
	THE_PEELED_NUTS(
			new ArrayList<Symbol>() {{		
			}}		
	),
	
	THE_OPERA_HOUSE(
			/*
			 * Earn $1 for each minion in THE ISLE OF GODS.
			 */
			new ArrayList<Symbol>() {{
				add(Symbol.PLACE_A_BUILDING);	
			}},
			(player, game) -> {
				System.out.println("YOU CALLED THE OPERA HOUSE");
			}
	),
	
	NOBBY_NOBBS(
			/*
			 * Take $3 from a player of your choice.
			 */
			(player, game) -> {
				System.out.println("YOU CALLED NOBBY NOBBS");
			},
			new ArrayList<Symbol>() {{
				add(Symbol.PLAY_ANOTHER_CARD);	
			}}
	),
	
	MODO(
			/*
			 * Discard one card.
			 */
			(player, game) -> {
				System.out.println("YOU CALLED MODO");
			},
			new ArrayList<Symbol>() {{
				add(Symbol.PLACE_MINION);	
			}}
	),
	
	THE_MENDED_DRUM(
			new ArrayList<Symbol>() {{
				add(Symbol.PLACE_A_BUILDING);	
				add(Symbol.TAKE_MONEY); //2 Dollars
			}}
	),
	
	LIBRARIAN(
			/*
			 * Take four cards from the draw deck.
			 */
			(player, game) -> {
				System.out.println("YOU CALLED LIBRARIAN");
			},
			new ArrayList<Symbol>() {{
				
			}}
	),
	
	LEONARD_OF_QUIRM(
			/*
			 * Take four cards from the draw deck.
			 */
			(player, game) -> {
				System.out.println("YOU CALLED LEONARD OF QUIRM");
			},
			new ArrayList<Symbol>() {{
				
			}}
	),
	
	SHONKY_SHOP(
		/*
		 * Discard as many cards as you wish and
		 * take $1 for each one discarded.
		 */
		(player, game) -> {
			System.out.println("YOU CALLED SHONKY SHOP");
		}, 
		new ArrayList<Symbol>() {{
			add(Symbol.PLACE_A_BUILDING);	
		}}		
	),
	
	SACHARISSA_CRIPSLOCK(
		/*
		 * Earn $1 for each trouble marker on the board.
		 */
		(player, game) -> {
			System.out.println("YOU CALLED SACHARISSA CRIPSLOCK");
		}, 
		new ArrayList<Symbol>() {{
			add(Symbol.PLACE_MINION);	
		}}		
	),
	
	ROSIE_PALM(
		/*
		 * Choose one player. Give them one of your cards. 
		 * They must give you $2 in return.
		 */
		new ArrayList<Symbol>() {{
			add(Symbol.PLACE_A_BUILDING);	
		}},
		(player, game) -> {
			System.out.println("YOU CALLED RORIE PALM");
		}		
	),

	//RINCEWIND(
	//	/*
	//	 * Move one of your minions from an area containing 
	//	 * a trouble marker to an adjacent area.
	//	 */
	//	new ArrayList<Symbol>() {{
	//		add(Symbol.RANDOM_EVENT);	
	//	}},
	//	(player, game) -> {
	//		System.out.println("YOU CALLED RINCEWIND");
	//	},
	//	new ArrayList<Symbol>() {{
	//		add(Symbol.PLAY_ANOTHER_CARD);	
	//	}}
	//),
	
	THE_ROYAL_MINT(
		new ArrayList<Symbol>() {{
			add(Symbol.PLACE_A_BUILDING);
			add(Symbol.TAKE_MONEY); //5 Dollars
		}}			
	),
	
	
	QUEEN_MOLLY(
		/*
		 * Select one player. They must give you 
		 * two cards of their choice.
		 */	
		new ArrayList<Symbol>() {{
			add(Symbol.PLACE_MINION);
		}},
		(player, game) -> {
			System.out.println("YOU CALLED QUEEN MOLLY");
		}			
	),
	
	PINK_PUSSYCAT_CLUB(
		new ArrayList<Symbol>() {{
			add(Symbol.TAKE_MONEY);//3 Dollars
			add(Symbol.PLAY_ANOTHER_CARD);
		}}		
	),
	
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
	
	MR_PIN_AND_MR_TULIP(
		new ArrayList<Symbol>() {{
			add(Symbol.ASSASINATION);
			add(Symbol.TAKE_MONEY);		
		}}		
	),
	
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
	
	private BiConsumer<Player, Game> text;

	private List<Symbol> symbols;

	private boolean textFirst;
	
	PlayerCard(List<Symbol> symbols, BiConsumer<Player, Game> text) {
		this.symbols = symbols;
		this.text = text;
		textFirst = false;
	}
	
	PlayerCard(BiConsumer<Player, Game> text, List<Symbol> symbols) {
		this.symbols = symbols;
		this.text = text;
		textFirst = true;
	}
	
	PlayerCard(List<Symbol> symbols) {
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
