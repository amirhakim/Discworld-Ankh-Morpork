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
			
	),
	MR_BOGGIS(
	new ArrayList<Symbol>() {{
//		add(Symbol.SCROLL);
		add(Symbol.PLACE_MINION);
	}},
	(player, game) -> {
		System.out.println("MR_BOGGIS: Take $2 if possible "
				+ "from every other player");
	}
	
	
),
MR_BENT(
	new ArrayList<Symbol>(){{
//		add(Symbol.SCROLL);
		add(Symbol.PLAY_ANOTHER_CARD);
	}},
	(player, game) -> {
		System.out.println("MR_BENT: place this card infront of you and "
				+ "take $10 loan from the bank,"
				+ " at the end of the game you must pay "
				+ "back $12 or loose 15 points");
	}
),
THE_BEGGARS_GUILD(
	new ArrayList<Symbol>(){{
//		add(Symbol.SCROLL);
		add(Symbol.PLACE_MINION);
	}},
	(player, game) -> {
		System.out.println("Select one player, they must give you "
				+ "two cards of their choice");
	}
),
THE_BANK_OF_ANKH_MORPORK(
	new ArrayList<Symbol>(){{
//		add(Symbol.SCROLL);
		add(Symbol.PLAY_ANOTHER_CARD);
	}},
	(player, game) -> {
		System.out.println("THE_BANK_OF_ANKH_MORPORK: place this card infront of you and "
				+ "take $10 loan from the bank,"
				+ " at the end of the game you must pay "
				+ "back $12 or loose 15 points");
	}
),
THE_ANKH_MORPORK_SUNSHINE_DRAGON_SANCTUARY(
	new ArrayList<Symbol>(){{
//		add(Symbol.SCROLL);
		add(Symbol.PLAY_ANOTHER_CARD);
	}},
	(player, game) -> {
		System.out.println("THE_ANKH_MORPORK_SUNSHINE_DRAGON_SANCTUARY: "
				+ "each player must give you either $1 or one of their cards");
	}
),
SERGANT_ANGUA(
	new ArrayList<Symbol>(){{
		add(Symbol.REMOVE_TROUBLE_MARKET);
		add(Symbol.PLAY_ANOTHER_CARD);
	}},
	(player, game) -> {
		System.out.println("SERGANT_ANGUA");
	}
),
THE_AGONY_AUNTS(
	new ArrayList<Symbol>() {{
		add(Symbol.ASSASINATION);
		add(Symbol.TAKE_MONEY);
		add(Symbol.PLACE_MINION);
	}},
	(player, game) -> {
		System.out.println("THE_AGONY_AUNTS");
	}
	
	
),
THE_DYSK(
	new ArrayList<Symbol>(){{
		add(Symbol.PLACE_A_BUILDING);
//		add(Symbol.SCROLL);
	}},
	(player, game) -> {
		System.out.println("THE_DYSK: earn $1 for each minion in the Isle of Gods");
	}
),
THE_DUCKMAN(
	new ArrayList<Symbol>(){{
//		add(Symbol.SCROLL);
	}},
	(player, game) -> {
		System.out.println("THE_DUCKMAN: move a minion belonging to "
				+ "another player from one area "
				+ "to an adjacent area");
	}
),
DRUMKNOTT(
	new ArrayList<Symbol>(){{
//		add(Symbol.SCROLL);
	}},
	(player, game) -> {
		System.out.println("DRUMKNOTT: play any two other cards from your hand");
	}
),
COMT_DIBBLER(
	new ArrayList<Symbol>(){{
//		add(Symbol.SCROLL);
		add(Symbol.PLAY_ANOTHER_CARD);
	}},
	(player, game) -> {
		System.out.println("COMT_DIBBLER: Roll the die. on the role of 7 or more"
				+ "you take $4 from the bank. on a roll"
				+ "of 1 you must pay $2 to the bank"
				+ "or remove one of your minions from"
				+ "the board, all other results have"
				+ "no effect");
	}
),
DR_CRUCES(
	new ArrayList<Symbol>() {{
		add(Symbol.ASSASINATION);
		add(Symbol.TAKE_MONEY);
	}},
	(player, game) -> {
		System.out.println("DR_CRUCES");
	}
),
CAPTAIN_CARROT(
	new ArrayList<Symbol>() {{
		add(Symbol.PLACE_MINION);
		add(Symbol.REMOVE_TROUBLE_MARKET);
		add(Symbol.TAKE_MONEY);
	}},
	(player, game) -> {
		System.out.println("CAPTAIN_CARROT");
	}	
),
MRS_CAKE(
	new ArrayList<Symbol>(){{
//		add(Symbol.SCROLL);
		add(Symbol.TAKE_MONEY);
		add(Symbol.PLACE_A_BUILDING);
	}},
	(player, game) -> {
		System.out.println("MRS_CAKE: look at all but one of the"
				+ "unused personality cards");
	}
),
GROAT(
	new ArrayList<Symbol>() {{
		add(Symbol.PLACE_MINION);
	}},
	(player, game) -> {
		System.out.println("GROAT");
	}	
),
GIMLETS_DWARF_DELICATESSEN(
	new ArrayList<Symbol>() {{
		add(Symbol.TAKE_MONEY);
		add(Symbol.PLACE_MINION);				
	}},
	(player, game) -> {
		System.out.println("GIMLETS_DWARF_DELICATESSEN");
	}	
),
GASPODE(
	new ArrayList<Symbol>() {{
		add(Symbol.INTERRUPT);
	}},
	(player, game) -> {
		System.out.println("GASPODE: stop a player from moving or"
				+ "removing one of your minions");
	}	
),
THE_FRESH_START_CLUB(
	new ArrayList<Symbol>() {{
		add(Symbol.INTERRUPT);
	}},
	(player, game) -> {
		System.out.println("THE_FRESH_START_CLUB: if you have a minion removed you can"
				+ "place him in a different area");
	}	
),
FOUL_OLE_RON(
	new ArrayList<Symbol>(){{
//		add(Symbol.SCROLL);
		add(Symbol.PLAY_ANOTHER_CARD);
	}},
	(player, game) -> {
		System.out.println("FOUL_OLE_RON: move a minion belonging to"
				+ "another player from one area"
				+ "to an adjacent area");
	}
),
THE_FOOLS_GUILD(
	new ArrayList<Symbol>() {{
//		add(SYMBOL.SCROLL);
		add(Symbol.PLACE_MINION);
	}},
	(player, game) -> {
		System.out.println("THE_FOOLS_GUILD: Select another player. if they do not"
				+ "give you $5 then place this card in front of them. this card now counts towards"
				+ "their hand size of five cards when they"
				+ "come to refill their hand. they cannot"
				+ "get rid of this card");
	}	
),
THE_FIRE_BRIGADE(
	new ArrayList<Symbol>(){{
//		add(Symbol.SCROLL);
		add(Symbol.PLAY_ANOTHER_CARD);
	}},
	(player, game) -> {
		System.out.println("THE_FIRE_BRIGADE: choose a player. if he does not pay"
				+ "you $5 then you can remove one of his buildings from the board");
	}
),
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
