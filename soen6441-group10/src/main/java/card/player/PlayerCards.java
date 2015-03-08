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
//	@SuppressWarnings("serial")
//	MR_BOGGIS(
//			new ArrayList<Symbol>() {{
//				add(SYMBOL.SCROLL);
//				add(Symbol.PLACE_MINION);
//			}},
//			(player, game) -> {
//				System.out.println("MR_BOGGIS: Take $2 if possible "
//						+ "from every other player");
//			}
//			
//			
//	),
//	MR_BENT(
//			new ArrayList<Symbol>(){{
//				add(Symbol.SCROLL);
//				add(Symbol.PLAY_ANOTHER_CARD);
//			}},
//			(player, game) -> {
//				System.out.println("MR_BENT: place this card infront of you and "
//						+ "take $10 loan from the bank,"
//						+ " at the end of the game you must pay "
//						+ "back $12 or loose 15 points");
//			}
//	),
//	THE_BEGGARS_GUILD(
//			new ArrayList<Symbol>(){{
//				add(Symbol.SCROLL);
//				add(Symbol.PLACE_MINION);
//			}},
//			(player, game) -> {
//				System.out.println("Select one player, they must give you "
//						+ "two cards of their choice");
//			}
//	),
//	THE_BANK_OF_ANKH_MORPORK(
//			new ArrayList<Symbol>(){{
//				add(Symbol.SCROLL);
//				add(Symbol.PLAY_ANOTHER_CARD);
//			}},
//			(player, game) -> {
//				System.out.println("THE_BANK_OF_ANKH_MORPORK: place this card infront of you and "
//						+ "take $10 loan from the bank,"
//						+ " at the end of the game you must pay "
//						+ "back $12 or loose 15 points");
//			}
//	),
//	THE_ANKH_MORPORK_SUNSHINE_DRAGON_SANCTUARY(
//			new ArrayList<Symbol>(){{
//				add(Symbol.SCROLL);
//				add(Symbol.PLAY_ANOTHER_CARD);
//			}},
//			(player, game) -> {
//				System.out.println("THE_ANKH_MORPORK_SUNSHINE_DRAGON_SANCTUARY: "
//						+ "each player must give you either $1 or one of their cards");
//			}
//	),
//	SERGANT_ANGUA(
//			new ArrayList<Symbol>(){{
//				add(Symbol.REMOVE_ONE_TROUBLE_MARKER);
//				add(Symbol.PLAY_ANOTHER_CARD);
//			}},
//			(player, game) -> {
//				System.out.println("SERGANT_ANGUA");
//			}
//	),
//	THE_AGONY_AUNTS(
//			new ArrayList<Symbol>() {{
//				add(Symbol.ASSASSINATION);
//				add(Symbol.TAKE_ONE);
//				add(Symbol.PLACE_MINION);
//			}},
//			(player, game) -> {
//				System.out.println("THE_AGONY_AUNTS");
//			}
//			
//			
//	),
//	THE_DYSK(
//			new ArrayList<Symbol>(){{
//				add(Symbol.PLACE_BUILDING);
//				add(Symbol.SCROLL);
//			}},
//			(player, game) -> {
//				System.out.println("THE_DYSK: earn $1 for each minion in the Isle of Gods");
//			}
//	),
//	THE_DUCKMAN(
//			new ArrayList<Symbol>(){{
//				add(Symbol.SCROLL);
//			}},
//			(player, game) -> {
//				System.out.println("THE_DUCKMAN: move a minion belonging to "
//						+ "another player from one area "
//						+ "to an adjacent area");
//			}
//	),
//	DRUMKNOTT(
//			new ArrayList<Symbol>(){{
//				add(Symbol.SCROLL);
//			}},
//			(player, game) -> {
//				System.out.println("DRUMKNOTT: play any two other cards from your hand");
//			}
//	),
//	COMT_DIBBLER(
//			new ArrayList<Symbol>(){{
//				add(Symbol.SCROLL);
//				add(Symbol.PLAY_ANOTHER_CARD);
//			}},
//			(player, game) -> {
//				System.out.println("COMT_DIBBLER: Roll the die. on the role of 7 or more"
//						+ "you take $4 from the bank. on a roll"
//						+ "of 1 you must pay $2 to the bank"
//						+ "or remove one of your minions from"
//						+ "the board, all other results have"
//						+ "no effect");
//			}
//	),
//	DR_CRUCES(
//			new ArrayList<Symbol>() {{
//				add(Symbol.ASSASSINATION);
//				add(Symbol.TAKE_THREE);
//			}},
//			(player, game) -> {
//				System.out.println("DR_CRUCES");
//			}
//	),
//	CAPTAIN_CARROT(
//			new ArrayList<Symbol>() {{
//				add(Symbol.PLACE_MINION);
//				add(Symbol.REMOVE_ONE_TROUBLE_MARKER);
//				add(Symbol.TAKE_ONE);
//			}},
//			(player, game) -> {
//				System.out.println("CAPTAIN_CARROT");
//			}	
//	),
//	MRS_CAKE(
//			new ArrayList<Symbol>(){{
//				add(Symbol.SCROLL);
//				add(Symbol.TAKE_TWO);
//				add(Symbol.PLACE_BUILDING);
//			}},
//			(player, game) -> {
//				System.out.println("MRS_CAKE: look at all but one of the"
//						+ "unused personality cards");
//			}
//	),
//	GROAT(
//			new ArrayList<Symbol>() {{
//				add(Symbol.PLACE_MINION);
//			}},
//			(player, game) -> {
//				System.out.println("GROAT");
//			}	
//	),
//	GIMLETS_DWARF_DELICATESSEN(
//			new ArrayList<Symbol>() {{
//				add(Symbol.TAKE_THREE);
//				add(Symbol.PLACE_MINION);				
//			}},
//			(player, game) -> {
//				System.out.println("GIMLETS_DWARF_DELICATESSEN");
//			}	
//	),
//	GASPODE(
//			new ArrayList<Symbol>() {{
//				add(Symbol.INTERRUPT);
//			}},
//			(player, game) -> {
//				System.out.println("GASPODE: stop a player from moving or"
//						+ "removing one of your minions");
//			}	
//	),
//	THE_FRESH_START_CLUB(
//			new ArrayList<Symbol>() {{
//				add(Symbol.INTERRUPT);
//			}},
//			(player, game) -> {
//				System.out.println("THE_FRESH_START_CLUB: if you have a minion removed you can"
//						+ "place him in a different area");
//			}	
//	),
//	FOUL_OLE_RON(
//			new ArrayList<Symbol>(){{
//				add(Symbol.SCROLL);
//				add(Symbol.PLAY_ANOTHER_CARD);
//			}},
//			(player, game) -> {
//				System.out.println("FOUL_OLE_RON: move a minion belonging to"
//						+ "another player from one area"
//						+ "to an adjacent area");
//			}
//	),
//	THE_FOOLS_GUILD(
//			new ArrayList<Symbol>() {{
//				add(SYMBOL.SCROLL);
//				add(Symbol.PLACE_MINION);
//			}},
//			(player, game) -> {
//				System.out.println("THE_FOOLS_GUILD: Select another player. if they do not"
//						+ "give you $5 then place this card in front of them. this card now counts towards"
//						+ "their hand size of five cards when they"
//						+ "come to refill their hand. they cannot"
//						+ "get rid of this card");
//			}	
//	),
//	THE_FIRE_BRIGADE(
//			new ArrayList<Symbol>(){{
//				add(Symbol.SCROLL);
//				add(Symbol.PLAY_ANOTHER_CARD);
//			}},
//			(player, game) -> {
//				System.out.println("THE_FIRE_BRIGADE: choose a player. if he does not pay"
//						+ "you $5 then you can remove one of his buildings from the board");
//			}
//	),
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
