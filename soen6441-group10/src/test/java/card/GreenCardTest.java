package card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import io.TextUserInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import gameplay.BoardArea;
import gameplay.Die;
import gameplay.Game;
import gameplay.Player;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import util.Color;
import card.city.AnkhMorporkArea;
import card.player.DiscardPile;
import card.player.GreenPlayerCard;

public class GreenCardTest {

	Game game;
	Player player;
	Player player2;
	Player player3;
	Map<Integer, BoardArea> gameBoard;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		// Code executed before the first test method
	}

	@Before
	public void setUp() throws Exception {
		game = new Game();
		String[] playerNames = { "Ross", "Smith", "Rocco"};
		try {
			game.setUp(playerNames.length, playerNames);
		} catch (Exception e) {
			fail("Exception caught");
		}
		player = game.getPlayerOfColor(Color.RED);
		player2 = game.getPlayerOfColor(Color.YELLOW);
		player3 = game.getPlayerOfColor(Color.GREEN);
		
		gameBoard = game.getGameBoard();
		TextUserInterface UI = TextUserInterface.getUI();
		UI.setGame(game);
	}

	//@Test
	public void zorgoTest() {
		System.out.println("~~~TESTING ZARGO~~~");
		game.assignPersonality(player);
		Card personalityBefore = player.getPersonality();
		GreenPlayerCard card = GreenPlayerCard.ZORGO_THE_RETRO_PHRENOLOGIST;
		card.getText().accept(player, game);
		Card personalityAfter = player.getPersonality();
		// Not sure if I can compare enums..
		assertTrue(personalityBefore != personalityAfter);
		
	}

	//@Test
	public void historyMonksTest() {
		System.out.println("~~~TESTING HISTORY MONKS~~~");
		// Set up some fake discard files
		game.discardCard(GreenPlayerCard.INIGO_SKIMMER, player);
		game.discardCard(GreenPlayerCard.HEX, player);
		game.discardCard(GreenPlayerCard.HERE_N_NOW, player);
		game.discardCard(GreenPlayerCard.HARRY_KING, player);
		
		int cardsBeforeSize = player.getPlayerCards().size();
		DiscardPile pile = game.getDiscardPile();
		int pileSizeBefore = pile.size();

		GreenPlayerCard.HISTORY_MONKS.getText().accept(player, game);

		int cardsAfterSize= player.getPlayerCards().size();
		int pileSizeAfter = pile.size();
		
		assertEquals(pileSizeAfter, pileSizeBefore - 4);
		assertEquals(cardsAfterSize, cardsBeforeSize + 4);
	}
	
	//@Test
	public void hereNowTest() {

		System.out.println("~~~TESTING NERE NOW~~~");
		// Give both players in game money
		Collection<Player> players = game.getPlayers();
		
		player.increaseMoney(10);
		Player playerTwo = game.getPlayerOfColor(Color.YELLOW);
		playerTwo.increaseMoney(10);
		gameBoard.get(1).addMinion(player);
	
		GreenPlayerCard.HERE_N_NOW.getText().accept(player, game);
	}
	
	
	/**
	 * Test size of player deck before and after
	 * Test player hand size before and after
	 */
	//@Test
	public void HexTest() {
		
		System.out.println("~~~TESTING HEX~~~");
		
		int playerDeck1 = game.getPlayerDeck().size();
		
		for(int i=0;i<3;++i){
			Optional<GreenPlayerCard> card = game.drawPlayerCard();
			player.addPlayerCard(card.get());
		}
		
		// Make sure set up ran correctly
		assertEquals(playerDeck1, game.getPlayerDeck().size()+3);
		assertEquals(player.getPlayerCards().size(), 3);
		
		GreenPlayerCard.HEX.getText().accept(player, game);
		
		// Assert player has 6 cards (3 from start, 3 green card)
		assertEquals(player.getPlayerCards().size(), 6);
		// Assert game has 48-6 cards (6 from 3 before, 3 from green card);
		assertEquals(game.getPlayerDeck().size(), 48-6);
		
	}
	
	//@Test
	public void harrKingTest(){
		
		System.out.println("~~TESTING HARRY KING~~");
		
		System.out.println("...testing cant remove any cards if dont have any");
		GreenPlayerCard.HARRY_KING.getText().accept(player, game);
		// Ensure that with no cards the player couldn't remove any
		assertEquals(player.getPlayerCards().size(),0);
		// Give player 3 cards
		for(int i=0;i<3;++i) {
			Optional<GreenPlayerCard> card = game.drawPlayerCard();
			player.addPlayerCard(card.get());
		}
		System.out.println("...testing removing cards - no restrictions");
		GreenPlayerCard.HARRY_KING.getText().accept(player, game);
		// Ensure player chose at least one card
		assertFalse(player.getPlayerCards().size() == 3);
		
		GreenPlayerCard gp = GreenPlayerCard.HARRY_KING;
		player.addPlayerCard(gp);
		System.out.println("...testing removing cards with HARRY KING as option");
		GreenPlayerCard.HARRY_KING.getText().accept(player, game);
	}
	
	//@Test
	public void operaHouseTest() {
		System.out.println("~~TESTING THE_OPERA_HOUSE ~~");
		
		// Assert that player can not get any money if has no minions
		assertEquals(player.getMoney(), 0);
		BoardArea isles = null;
		for(BoardArea ba: gameBoard.values()) {
			if(ba.getArea() == AnkhMorporkArea.ISLE_OF_GODS) {
				isles = ba;
				break;
			}
		}
		// add 3 minions to isles
		isles.addMinion(player);
		isles.addMinion(player);
		isles.addMinion(player);
		
		GreenPlayerCard.THE_OPERA_HOUSE.getText().accept(player, game);
		// Assert that the player now has 3 dollars
		assertEquals(player.getMoney(), 3);
		
		// Assert player stays at 3 dollars if bank has no money
		game.getBank().decreaseBalance(game.getBank().getBalance());
		GreenPlayerCard.THE_OPERA_HOUSE.getText().accept(player, game);
		assertEquals(player.getMoney(),3);	
	}
	
	//@Test
	public void nobbyNobbTest() {
		System.out.println("~~Testing NOBBY_NOBBS~~");
		
		// Currently player doesn't have any money
		// Assert that we didn't get any money
		GreenPlayerCard.NOBBY_NOBBS.getText().accept(player,game);
		assertEquals(player.getMoney(), 0);
		//give all players 3 dollars
		for(Player p : game.getPlayers()) {
			p.increaseMoney(3);
		}
		
		GreenPlayerCard.NOBBY_NOBBS.getText().accept(player, game);
		// Assert player got +3 dollars
		assertEquals(player.getMoney(), 6);
		// Assert that some player lost 3 dollars
		int playersLessThan3 = 0;
		for(Player p : game.getPlayers()) {
			if(p.getMoney() < 3) {
				playersLessThan3++;
			}
		}
		
		assertEquals(playersLessThan3, 1);
	}
	
	//@Test
	public void modoTest() {	
		// Test discarding with 1+ cards
		player.addPlayerCard(GreenPlayerCard.NOBBY_NOBBS);
		player.addPlayerCard(GreenPlayerCard.MODO);
		GreenPlayerCard.MODO.getText().accept(player, game);
		
		assertEquals(game.getDiscardPile().size(), 1);
		assertEquals(player.getPlayerCards().size(), 1);
		
		// Test discarding with only modo card
		player2.addPlayerCard(GreenPlayerCard.MODO);
		GreenPlayerCard.MODO.getText().accept(player, game);
		
		// No change in discard pile or players hand
		assertEquals(game.getDiscardPile().size(), 1);
		assertEquals(player2.getPlayerCards().size(), 1);
		
	}
	
	//@Test
	public void librarianTest() {
		System.out.println("~~TESTING LIBRARIAN~~~");
		// Let give player a full hand
		for(int i = 5; i>0; i--) {
			player.addPlayerCard(game.getPlayerDeck().drawCard().get());
		}
		
		GreenPlayerCard.LIBRARIAN.getText().accept(player, game);
		assertEquals(player.getPlayerCards().size(), 9);
		
		// Lets test removing all cards but 1 from the draw deck
		Deck<GreenPlayerCard> pd = game.getPlayerDeck();
		
		while(pd.size() > 1) {
			pd.drawCard();
		}
		
		GreenPlayerCard.LIBRARIAN.getText().accept(player, game);
		assertEquals(player.getPlayerCards().size(), 10);	
	}
	
	
	//@Test
	public void leonardOfQuirmTest() {
		System.out.println("~~TESTING LEONARD OF QUIRM~~");

		// Let give player a full hand
		for(int i = 5; i>0; i--) {
			player.addPlayerCard(game.getPlayerDeck().drawCard().get());
		}
		
		GreenPlayerCard.LEONARD_OF_QUIRM.getText().accept(player, game);
		assertEquals(player.getPlayerCards().size(), 9);
		
		// Lets test removing all cards but 1 from the draw deck
		Deck<GreenPlayerCard> pd = game.getPlayerDeck();
		
		while(pd.size() > 1) {
			pd.drawCard();
		}
		
		GreenPlayerCard.LEONARD_OF_QUIRM.getText().accept(player, game);
		assertEquals(player.getPlayerCards().size(), 10);
		
		
	}
	
	//@Test
	public void shonkyShopTest() {
		System.out.println("~~TESTING SHONKY_SHOP~~~");
		
		// Give the player two cards, SHONKY SHOP and one MODO
		player.addPlayerCard(GreenPlayerCard.SHONKY_SHOP);
		player.addPlayerCard(GreenPlayerCard.MODO);
		
		// The player MUST only remove MODO
		GreenPlayerCard.SHONKY_SHOP.getText().accept(player, game);
		
		// Make sure players hand is size 1
		assertEquals(player.getPlayerCards().size(), 1);
		// Make sure players one card is SHONKY SHOP
		for(GreenPlayerCard c: player.getPlayerCards()) {
			assertEquals(c, GreenPlayerCard.SHONKY_SHOP);
		}
		
		// Ensure the player now has a dollar
		assertEquals(player.getMoney(), 1);
	}
	
	//@Test
	public void sacharissaTest() {
		System.out.println("~~TESTING SACHARISSA_CRIPSLOCK~~");
	
		// No trouble on board, player should get 0
		GreenPlayerCard.SACHARISSA_CRIPSLOCK.getText().accept(player, game);
		
		assertEquals(player.getMoney(), 0);
		
		// Set 10 trouble markings
		for(int i=1; i<11; i++) {
			gameBoard.get(i).addTroubleMarker();
		}
		GreenPlayerCard.SACHARISSA_CRIPSLOCK.getText().accept(player, game);
		
		assertEquals(player.getMoney(), 10);
		
		// try giving 10 when bank has nothing
		game.getBank().decreaseBalance(game.getBank().getBalance());
		
		// Assert player doesn't get his 10 $
		GreenPlayerCard.SACHARISSA_CRIPSLOCK.getText().accept(player, game);
		assertEquals(player.getMoney(), 10);
	}
	
	//@Test
	public void rosiePalmTest() {
		System.out.println("~~TESTING ROSIE_PALM~~~");
		
		// Test if no player has more than 2$
		GreenPlayerCard.ROSIE_PALM.getText().accept(player, game);
		assertEquals(player.getMoney(), 0);
		
		// Given 2 players, and you only have the rosie card
		// Ensure that no card is chosen
		player2.increaseMoney(5);
		player.addPlayerCard(GreenPlayerCard.ROSIE_PALM);

		GreenPlayerCard.ROSIE_PALM.getText().accept(player, game);
		assertEquals(player.getMoney(), 0);
		assertEquals(player2.getMoney(), 5);
		
		// Given three players and only 1 with >2$, choose that player
		// Ensure that player card count increases and money decreases
		// Ensure your card count decreases and your money increases
		player3.increaseMoney(1);
		player.addPlayerCard(game.getPlayerDeck().drawCard().get());


		GreenPlayerCard.ROSIE_PALM.getText().accept(player, game);
		
		assertEquals(player.getMoney(), 2);
		assertEquals(player2.getMoney(), 3);
		assertEquals(player.getPlayerCards().size(), 1);
		assertEquals(player2.getPlayerCards().size(), 1);
		
	}
	
	//@Test
	public void queenMollyTest() {
		System.out.println("~~TESTING QUEEN MOLLY~~~");
		
		// Test if no player has cards to give
		GreenPlayerCard.QUEEN_MOLLY.getText().accept(player, game);
		assertEquals(player.getPlayerCards().size(), 0);
		
		// Test that players cards increase by 2
		// Test that other players cards derease by 2
		for(int i=0;i<5;++i){
			player2.addPlayerCard(game.getPlayerDeck().drawCard().get());
		}
		GreenPlayerCard.QUEEN_MOLLY.getText().accept(player, game);
		// Even though 3 players, player must select player 2
		assertEquals(player.getPlayerCards().size(), 2);
		assertEquals(player2.getPlayerCards().size(), 3);
		
	}
	
	//@Test
	public void rincewindTest() {
		System.out.println("~~~TESTING RINCEWIND~~~");
		
		// Test if player has no minions
		GreenPlayerCard.RINCEWIND.getText().accept(player, game);
		assertEquals(game.getAreasWithPlayerMinions(player).size(), 0);

		
		gameBoard.get(1).addMinion(player);
		GreenPlayerCard.RINCEWIND.getText().accept(player, game);
		// Test if player cannot player a minion if there is no trouble
		assertEquals(game.getAreasWithPlayerMinions(player).size(), 1);
		assertEquals(gameBoard.get(1).getMinionCountForPlayer(player), 1);
		
		gameBoard.get(1).addTroubleMarker();
		//Test that player can move minion
		GreenPlayerCard.RINCEWIND.getText().accept(player, game);
		// assert area has lost minion
		assertEquals(gameBoard.get(1).getMinionCountForPlayer(player), 0);
		// assert player still has minion count
		assertEquals(game.getAreasWithPlayerMinions(player).size(), 1);
		// assert minion move to a neighbouring area
		int count = 0;
		for(BoardArea ba : game.getNeighbours(gameBoard.get(1)).values()) {
			if(ba.getMinionCountForPlayer(player) == 1) {
				count++;
			}
		}
		assertEquals(count, 1);
		
	}
	
	//@Test
	public void dyskTest() {
		System.out.println("~~TESTING DYSK~~~");
		
		game.getBank().increaseBalance(100);
		
		// Assert player gets no money
		GreenPlayerCard.THE_DYSK.getText().accept(player, game);
		assertEquals(player.getMoney(), 0);
		
		BoardArea iog = gameBoard.get(AnkhMorporkArea.ISLE_OF_GODS.getAreaCode());
		iog.addMinion(player2);
		
		GreenPlayerCard.THE_DYSK.getText().accept(player, game);
		// Assert player now gets 1
		assertEquals(player.getMoney(), 1);
	}
	
	//@Test
	public void fireBrigadeTest() {
		System.out.println("~~~TESTING FIRE BRIGADE~~~");
		
		// Test what happens if no player has 5 dollars or buildings
		GreenPlayerCard.THE_FIRE_BRIGADE.getText().accept(player, game);
		assertEquals(player2.getMoney(), 0);
		assertEquals(player3.getMoney(), 0);
		assertEquals(game.getBuildingAreas(player2).size(), 0);
		assertEquals(game.getBuildingAreas(player3).size(), 0);
		
		// Test that if players have money and buildings
		gameBoard.get(1).addBuildingForPlayer(player2);
		gameBoard.get(2).addBuildingForPlayer(player3);
		player2.increaseMoney(10);
		player3.increaseMoney(10);
		
		GreenPlayerCard.THE_FIRE_BRIGADE.getText().accept(player, game);
		
		// Tricky, we dont know what actually got played, but we know that
		// a player is either 5 bucks short ...  or one building less
		// one of the players will have less money, OR less buildings
		ArrayList<Boolean> list = new ArrayList<Boolean>();
		list.add(gameBoard.get(1).getBuildingOwner() == Color.UNDEFINED);
		list.add(gameBoard.get(2).getBuildingOwner() == Color.UNDEFINED);
		list.add(player2.getMoney() == 5);
		list.add(player3.getMoney() == 5);
		
		Boolean oneTrue = false;
		// only one of the booleans should be true
		for(Boolean b : list) {
			if(b) {
				if(oneTrue) {
					fail("Only one boolean should be true");
				}
				oneTrue = true;
				
			}
		}
		assertEquals(oneTrue, true);
	}
	
	@Test
	public void drumkottTest() {
		System.out.println("~~~DRUMKNOTT TEST~~~");
		
		// Give player only drumkott card
		player.addPlayerCard(GreenPlayerCard.DRUMKNOTT);
		GreenPlayerCard.DRUMKNOTT.getText().accept(player, game);
		
		//player should not have played any cards
		assertEquals(player.getPlayerCards().size(), 1);
		
		
		// Now lets give player 2 other cards and assure he plays them
		player.addPlayerCard(GreenPlayerCard.SACHARISSA_CRIPSLOCK);
		player.addPlayerCard(GreenPlayerCard.THE_OPERA_HOUSE);
		
		// TODO figure out how to test this properly
		
		assertEquals(player.getPlayerCards().size(), 3);
		GreenPlayerCard.DRUMKNOTT.getText().accept(player, game);
		assertEquals(player.getPlayerCards().size(), 1);
		
	}
	
	//@Test
	public void theFoolsGuildTest() {
		System.out.println("~~~THE FOOLS GUILD TEST~~~");
		
		// no player has any cards or money, so player gets to give his card
		// to annother player
		
		// for testing reason we have to set currentCard
		game.setCurrentCardInPlay(GreenPlayerCard.THE_FOOLS_GUILD);
		GreenPlayerCard.THE_FOOLS_GUILD.getText().accept(player, game);
		
		//make sure one of the other players has the fools guild
		
		// check if plyer 2 has card
		boolean player2HasCard = player2.getPlayerCards().contains(GreenPlayerCard.THE_FOOLS_GUILD);
		// check if player 3 has card
		boolean player3HasCard = player3.getPlayerCards().contains(GreenPlayerCard.THE_FOOLS_GUILD);
		assertTrue(player2HasCard || player3HasCard);
		
		assertEquals(player.getPlayerCards().size(), 0);
		
		// now ensure player ccannot get rid of the card if he plays it
		Player hasCardPlayer = null;
		if(player2HasCard) {
			hasCardPlayer = player2;
		} else if(player3HasCard) {
			hasCardPlayer = player3;
		}
		
		// we need to test that card cannot be discarded
		// to do this play a card that allows cards to be discarded
		game.getBank().increaseBalance(100);
		
		hasCardPlayer.addPlayerCard(GreenPlayerCard.SHONKY_SHOP);
		GreenPlayerCard.SHONKY_SHOP.getText().accept(hasCardPlayer, game);
		// The player had to have tried to discard THE FOOLS GUILD
		// lets ensure he couldnt do it
		
		assertEquals(hasCardPlayer.getPlayerCards().size(), 2);
		
	}
	
	//@Test
	public void cmotDipplyerTest(){ 
		System.out.println("~~CMOT DIPPLER TEST~~");
		Die.getDie().setCheat(7);
		GreenPlayerCard.CMOT_DIBBLER.getText().accept(player, game);
		Die.getDie().setCheat(9);
		GreenPlayerCard.CMOT_DIBBLER.getText().accept(player, game);
		Die.getDie().setCheat(12);
		GreenPlayerCard.CMOT_DIBBLER.getText().accept(player, game);
		// assert player should now have 4*3 $
		assertEquals(player.getMoney(), 12);
		
		// on a roll of 1 you must pay 2$
		// or remove a minion
		
		Die.getDie().setCheat(1);
		// give player a minion to remove
		gameBoard.get(1).addMinion(player);
		GreenPlayerCard.CMOT_DIBBLER.getText().accept(player, game);
		
		boolean payedBank = player.getMoney() == 10;
		boolean gaveMinion = gameBoard.get(1).getMinionCount() == 0;
		if(payedBank) assertFalse(gaveMinion);
		if(gaveMinion) assertFalse(payedBank);
	
	}
	
	//@Test
	public void duckmanTest() {
		System.out.println("~~DUCKMAN TEST~~");
		

		GreenPlayerCard.THE_DUCKMAN.getText().accept(player, game);
		assertEquals(game.getAreasWithPlayerMinions(player2).size(),0);
		
		gameBoard.get(1).addMinion(player2);
		GreenPlayerCard.THE_DUCKMAN.getText().accept(player, game);
		assertEquals(game.getAreasWithPlayerMinions(player2).size(),1);
		assertEquals(gameBoard.get(1).getMinions().size(), 0);
		
	}
	
	//@Test
	public void FoulOleRonTest() {
		System.out.println("~~FOUL OLE RON TEST~~");
		

		GreenPlayerCard.FOUL_OLE_RON.getText().accept(player, game);
		assertEquals(game.getAreasWithPlayerMinions(player2).size(),0);
		
		gameBoard.get(1).addMinion(player2);
		GreenPlayerCard.FOUL_OLE_RON.getText().accept(player, game);
		assertEquals(game.getAreasWithPlayerMinions(player2).size(),1);
		assertEquals(gameBoard.get(1).getMinions().size(), 0);
		
	}	
	
	@After
	public void tearDown() throws Exception {
		// Code executed after each test
		System.out.println();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		// Code executed after the last test method
	}
}
