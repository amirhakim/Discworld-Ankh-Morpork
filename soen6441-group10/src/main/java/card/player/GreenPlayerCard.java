package card.player;

import gameplay.BoardArea;
import gameplay.Die;
import gameplay.Game;
import gameplay.Player;
import io.TextUserInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.function.BiConsumer;

import util.Color;
import card.Card;


@SuppressWarnings("serial")
/**
 * <b>Enum for player cards
 * Each ENUM will have a list of symbols available for that card
 * Some cards will also have text function (bottom of a card)</b>
 * 
 * @author Team 10 - SOEN6441
 * @version 2.0
 */

public enum GreenPlayerCard implements Card {


	INIGO_SKIMMER(
			new ArrayList<Symbol>() {{
				add(Symbol.ASSASINATION);
				add(Symbol.TAKE_MONEY);	// Take 2 Dollars		
			}},	
			2
	), 
	
	HISTORY_MONKS(
			/*
			 * Shuffle the discard pile and draw four cards randomly.
			 * Place the remaining cards back as the discard pile.
			 */
			(player, game) -> {
				System.out.println("Playing text -> getting four cards from discard pile");
				DiscardPile pile = game.getDiscardPile();
				pile.shuffle();
				game.drawDiscardCards(player, 4);
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
				//System.out.println("YOU CALLED HEX");
				System.out.println("Playing text -> taking 3 cards from draw deck");
				game.drawPlayerCard(player,3);			
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
				int dieRoll = Die.getDie().roll();
				System.out.println("Dice rolled: " + dieRoll);

				TextUserInterface textUI = new TextUserInterface();
				if(dieRoll == 7) {
					Collection<Player> players = game.getPlayers();
					Map<Color, Player> playerMap = new HashMap<Color, Player>();
					for(Player p : players) {
						if(p.getColor() == player.getColor()) continue;
						if(p.getMoney() < 3) continue;
						playerMap.put(p.getColor(), p);
					}
					if(playerMap.size() == 0 ) {
						System.out.println("All players are broke.");
						return;
					}
					Player chosenPlayer = textUI.getPlayer(playerMap);
					chosenPlayer.decreaseMoney(3);
					player.increaseMoney(3);
					
					
				} else if(dieRoll == 1) {
					BoardArea chosenArea = textUI.getAreaChoice(game.getAreasWithPlayerMinions(player), "Choose area to remove minion", "Choose: ");
					chosenArea.removeMinion(player);
				} else {
					System.out.println("No Action");
				}
				
				
				
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
				System.out.println("NOT IMPLEMENTED: YOU CALLED HARRY KING TEXT");
			}			
	), 
	
	HARGAS_HOUSE_OF_RIBS(
			new ArrayList<Symbol>() {{
				add(Symbol.TAKE_MONEY);	//3 Dollars	
				add(Symbol.PLACE_MINION);
			}},		
			3
	),
	
	MR_GRYLE(
			new ArrayList<Symbol>() {{
				add(Symbol.ASSASINATION);	
				add(Symbol.TAKE_MONEY); // 1 Dollar
			}},		
			1
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
				System.out.println("NOT IMPLEMENTED: YOU CALLED THE OPERA HOUSE TEXT");
			}
	),
	
	NOBBY_NOBBS(
			/*
			 * Take $3 from a player of your choice.
			 */
			(player, game) -> {
				System.out.println("NOT IMPLEMENTED: YOU CALLED NOBBY NOBBS TEXT");
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
				System.out.println("NOT IMPLEMENTED: YOU CALLED MODO TEXT");
			},
			new ArrayList<Symbol>() {{
				add(Symbol.PLACE_MINION);	
			}}
	),
	
	THE_MENDED_DRUM(
			new ArrayList<Symbol>() {{
				add(Symbol.PLACE_A_BUILDING);	
				add(Symbol.TAKE_MONEY); //2 Dollars
			}},
			2
	),
	
	LIBRARIAN(
			/*
			 * Take four cards from the draw deck.
			 */
			(player, game) -> {
				System.out.println("NOT IMPLEMENTED: YOU CALLED LIBRARIAN TEXT");
			},
			new ArrayList<Symbol>() {{
				
			}}
	),
	
	LEONARD_OF_QUIRM(
			/*
			 * Take four cards from the draw deck.
			 */
			(player, game) -> {
				System.out.println("NOT IMPLEMENTED: YOU CALLED LEONARD OF QUIRM TEXT");
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
			System.out.println("NOT IMPLEMENTED: YOU CALLED SHONKY SHOP TEXT");
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
			System.out.println("NOT IMPLEMENTED: YOU CALLED SACHARISSA CRIPSLOCK TEXT");
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
			System.out.println("NOT IMPLEMENTED: YOU CALLED RORIE PALM TEXT");
		}		
	),

	RINCEWIND(
		/*
		 * Move one of your minions from an area containing 
		 * a trouble marker to an adjacent area.
		 */
		//TODO text area should be in middle
		new ArrayList<Symbol>() {{
			add(Symbol.RANDOM_EVENT);	
			add(Symbol.PLAY_ANOTHER_CARD);	
		}},
		(player, game) -> {
			System.out.println("NOT IMPLEMENTED: YOU CALLED RINCEWIND TEXT");
		}
	),
	
	THE_ROYAL_MINT(
		new ArrayList<Symbol>() {{
			add(Symbol.PLACE_A_BUILDING);
			add(Symbol.TAKE_MONEY); //5 Dollars
		}},
		5
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
			System.out.println("NOT IMPLEMENTED: YOU CALLED QUEEN MOLLY TEXT");
		}			
	),
	
	PINK_PUSSYCAT_CLUB(
		new ArrayList<Symbol>() {{
			add(Symbol.TAKE_MONEY);//3 Dollars
			add(Symbol.PLAY_ANOTHER_CARD);
		}},
		3
	),
	
	ZORGO_THE_RETRO_PHRENOLOGIST(
		/*
		 * You may exchange your Personality Card
		 * with one drawn randomly from those
		 * not in use
		 */
		(player, game) -> {
			// Get another personality card
			System.out.println("Assigning new personality card!");
			game.assignPersonality(player);
			
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
			System.out.println("NOT IMPLEMENTED: YOU CALLED DR WHITEFACE TEXT");
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
			System.out.println("NOT IMPLEMENTED: YOU CALLED WALLACE SONKY TEXT");			
		}
	),
	
	THE_SEAMSTRESS_GUILD(
		(player, game) -> {
			/*
			 * Choose one player.  Give them
			 * one of your cards.  They must 
			 * give you 2$ in return
			 */
			System.out.println("NOT IMPLEMENTED: YOU CALLED THE SEAMSTRESS GUILD TEXT");	
		},
		new ArrayList<Symbol>() {{
			add(Symbol.PLACE_MINION);
		}}
	),
	
	MR_PIN_AND_MR_TULIP(
		new ArrayList<Symbol>() {{
			add(Symbol.ASSASINATION);
			add(Symbol.TAKE_MONEY);		
		}},
		1
	),
	
	THE_THIEVES_GUILD(
		(player, game) -> {
			/*
			 * Take $2, if possible, from
			 * every other player.
			 */
			System.out.println("NOT IMPLEMENTED: YOU CALLED THE THIEVES GUILD TEXT");
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
		System.out.println("NOT IMPLEMENTED: MR_BOGGIS: Take $2 if possible "
				+ "from every other player");
	}
	
	
),
MR_BENT(
	new ArrayList<Symbol>(){{
//		add(Symbol.SCROLL);
		add(Symbol.PLAY_ANOTHER_CARD);
	}},
	(player, game) -> {
		System.out.println("NOT IMPLEMENTED: MR_BENT: place this card infront of you and "
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
		System.out.println("NOT IMPLEMENTED: Select one player, they must give you "
				+ "two cards of their choice");
	}
),
THE_BANK_OF_ANKH_MORPORK(
	new ArrayList<Symbol>(){{
//		add(Symbol.SCROLL);
		add(Symbol.PLAY_ANOTHER_CARD);
	}},
	(player, game) -> {
		System.out.println("NOT IMPLEMENTED: THE_BANK_OF_ANKH_MORPORK: place this card infront of you and "
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
		System.out.println("NOT IMPLEMENTED: THE_ANKH_MORPORK_SUNSHINE_DRAGON_SANCTUARY: "
				+ "each player must give you either $1 or one of their cards");
	}
),
SERGANT_ANGUA(
	new ArrayList<Symbol>(){{
		add(Symbol.REMOVE_TROUBLE_MARKER);
		add(Symbol.PLAY_ANOTHER_CARD);
	}},
	(player, game) -> {
		System.out.println("NOT IMPLEMENTED: SERGANT_ANGUA");
	}
),
THE_AGONY_AUNTS(
	new ArrayList<Symbol>() {{
		add(Symbol.ASSASINATION);
		add(Symbol.TAKE_MONEY);
		add(Symbol.PLACE_MINION);
	}},
	(player, game) -> {
		System.out.println("NOT IMPLEMENTED: THE_AGONY_AUNTS");
	},
	2
	
	
),
THE_DYSK(
	new ArrayList<Symbol>(){{
		add(Symbol.PLACE_A_BUILDING);
//		add(Symbol.SCROLL);
	}},
	(player, game) -> {
		System.out.println("NOT IMPLEMENTED: THE_DYSK: earn $1 for each minion in the Isle of Gods");
	}
),
THE_DUCKMAN(
	new ArrayList<Symbol>(){{
//		add(Symbol.SCROLL);
	}},
	(player, game) -> {
		System.out.println("NOT IMPLEMENTED: THE_DUCKMAN: move a minion belonging to "
				+ "another player from one area "
				+ "to an adjacent area");
	}
),
DRUMKNOTT(
	new ArrayList<Symbol>(){{
//		add(Symbol.SCROLL);
	}},
	(player, game) -> {
		System.out.println("NOT IMPLEMENTED: DRUMKNOTT: play any two other cards from your hand");
	}
),
COMT_DIBBLER(
	new ArrayList<Symbol>(){{
//		add(Symbol.SCROLL);
		add(Symbol.PLAY_ANOTHER_CARD);
	}},
	(player, game) -> {
		System.out.println("NOT IMPLEMENTED: COMT_DIBBLER: Roll the die. on the role of 7 or more"
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
		System.out.println("NOT IMPLEMENTED: DR_CRUCES");
	},
	3
),
CAPTAIN_CARROT(
	new ArrayList<Symbol>() {{
		add(Symbol.PLACE_MINION);
		add(Symbol.REMOVE_TROUBLE_MARKER);
		add(Symbol.TAKE_MONEY);
	}},
	(player, game) -> {
		System.out.println("NOT IMPLEMENTED: CAPTAIN_CARROT");
	},
	1
),
MRS_CAKE(
	new ArrayList<Symbol>(){{
//		add(Symbol.SCROLL);
		add(Symbol.TAKE_MONEY);
		add(Symbol.PLACE_A_BUILDING);
	}},
	(player, game) -> {
		System.out.println("NOT IMPLEMENTED: MRS_CAKE: look at all but one of the"
				+ "unused personality cards");
	},
	2
),
GROAT(
	new ArrayList<Symbol>() {{
		add(Symbol.PLACE_MINION);
	}},
	(player, game) -> {
		System.out.println("NOT IMPLEMENTED: GROAT");
	}	
),
GIMLETS_DWARF_DELICATESSEN(
	new ArrayList<Symbol>() {{
		add(Symbol.TAKE_MONEY);
		add(Symbol.PLACE_MINION);				
	}},
	(player, game) -> {
		System.out.println("NOT IMPLEMENTED: GIMLETS_DWARF_DELICATESSEN");
	},
	3
),
GASPODE(
	new ArrayList<Symbol>() {{
		add(Symbol.INTERRUPT);
	}},
	(player, game) -> {
		System.out.println("NOT IMPLEMENTED: GASPODE: stop a player from moving or"
				+ "removing one of your minions");
	}	
),
THE_FRESH_START_CLUB(
	new ArrayList<Symbol>() {{
		add(Symbol.INTERRUPT);
	}},
	(player, game) -> {
		System.out.println("NOT IMPLEMENTED: THE_FRESH_START_CLUB: if you have a minion removed you can"
				+ "place him in a different area");
	}	
),
FOUL_OLE_RON(
	new ArrayList<Symbol>(){{
//		add(Symbol.SCROLL);
		add(Symbol.PLAY_ANOTHER_CARD);
	}},
	(player, game) -> {
		System.out.println("NOT IMPLEMENTED: FOUL_OLE_RON: move a minion belonging to"
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
		System.out.println("NOT IMPLEMENTED: THE_FOOLS_GUILD: Select another player. if they do not"
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
		System.out.println("NOT IMPLEMENTED: THE_FIRE_BRIGADE: choose a player. if he does not pay"
				+ "you $5 then you can remove one of his buildings from the board");
	}
),
	;
	
	private BiConsumer<Player, Game> text;

	private List<Symbol> symbols;

	private boolean textFirst;

	private Integer money;
	
	GreenPlayerCard(List<Symbol> symbols, BiConsumer<Player, Game> text) {
		this.symbols = symbols;
		this.text = text;
		textFirst = false;
	}
	
	GreenPlayerCard(List<Symbol> symbols, BiConsumer<Player, Game> text, Integer money) {
		this.symbols = symbols;
		this.text = text;
		this.textFirst = false;
		this.money = money;
	}
	
	GreenPlayerCard(BiConsumer<Player, Game> text, List<Symbol> symbols) {
		this.symbols = symbols;
		this.text = text;
		textFirst = true;
	}
	
	GreenPlayerCard(BiConsumer<Player, Game> text, List<Symbol> symbols, Integer money) {
		this.symbols = symbols;
		this.text = text;
		this.textFirst = true;
		this.money = money;
	}
	
	GreenPlayerCard(List<Symbol> symbols) {
		this.symbols = symbols;
		this.text = null;
		this.textFirst = false;
	}
	
	GreenPlayerCard(List<Symbol> symbols, Integer money) {
		this.symbols = symbols;
		this.text = null;
		this.textFirst = false;
		this.money = money;
	}
	
	public List<Symbol> getSymbols() {
		return this.symbols;
	}
	
	public BiConsumer<Player, Game> getText() {
		return this.text;
	}
	
	public boolean isTextFirst() {
		return this.textFirst;
	}

	public Integer getMoney() {
		return this.money;
	}
}
