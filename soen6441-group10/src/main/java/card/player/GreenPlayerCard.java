package card.player;

import gameplay.BoardArea;
import gameplay.Die;
import gameplay.Game;
import gameplay.Player;
import io.TextUserInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.function.BiConsumer;

import util.Color;
import card.Card;
import card.city.AnkhMorporkArea;


/**
 * Enum for player cards
 * Each ENUM will have a list of symbols available for that card
 * Some cards will also have text function (bottom of a card)
 * 
 * @author Team 10 - SOEN6441
 * @version 2.0
 */
@SuppressWarnings("serial")
public enum GreenPlayerCard implements Card {

	INIGO_SKIMMER(	
			new ArrayList<Symbol>() {{
				add(Symbol.ASSASINATION);
				add(Symbol.TAKE_MONEY);	// Take 2 Dollars		
			}},
			(player,game) ->{},
			// Money
			2,
			// ID
			1,
			// DESC
			""
	), 
	
	HISTORY_MONKS(
			/*
			 * Shuffle the discard pile and draw four cards randomly.
			 * Place the remaining cards back as the discard pile.
			 */
			(player, game) -> {
				System.out.println("Playing text -> getting four cards from discard pile");
				DiscardPile pile = game.getDiscardPile();
				if(pile.size() == 0) {
					System.out.println("Discard pile is 0");
					return;
				}
				pile.shuffle();
				game.drawDiscardCards(player, 4);
				System.out.println("Drew 4 cards from discard pile");
			},
			new ArrayList<Symbol>() {{
				add(Symbol.PLACE_MINION);		
			}},
			// Money
			0,
			// ID
			2,
			// DESC
			"SCROLL: SHUFFLE DISCARD PILE AND DRAW FOUR CARDS RANDOMLY"
	),
	
	HEX(
			/*
			 * Take 3 cards from the draw deck.
			 */
			(player, game) -> {
				//System.out.println("YOU CALLED HEX");
				System.out.println("Playing text -> taking 3 cards from draw deck");
				game.addPlayerCard(player,3);			
			},
			new ArrayList<Symbol>() {{
				add(Symbol.PLACE_A_BUILDING);		
			}},
			// Money
			0,
			// ID
			3,
			// DESC
			"SCROLL: TAKE 3 CARDS FROM DRAW DECK"
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

				//TextUserInterface textUI = new TextUserInterface();
				TextUserInterface textUI = TextUserInterface.getUI();
				
				if(dieRoll >= 7) {
					Map<Color, Player> players = game.getPlayersMap();
					
						
					ArrayList<Color> excludeList = new ArrayList<Color>();
					excludeList.add(player.getColor());
					for(Player p: players.values()) {
						if(p.getMoney() < 3) excludeList.add(p.getColor());
					}
					
					Player chosenPlayer = textUI.getPlayer(players, excludeList, true);
					if(chosenPlayer == null) {
						return;
					}
					
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
			}},
			// Money
			0,
			// ID
			4,
			// DESC
			"SCROLL: ROLL DICE IF 7 OR MORE TAKE 3$ FROM PLAYER OF YOUR CHOICE. IF 1 REMOVE ONE OF YOUR MINIONS"
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
//				TextUserInterface UI = new TextUserInterface();
				TextUserInterface UI = TextUserInterface.getUI();
				
				// Find out if player has cards to play
				Set<GreenPlayerCard> playerCards = player.getPlayerCards();
				
				
				// Can play if have other cards than Harry King
				while(playerCards.size() > 1) {
					GreenPlayerCard discardCard = null;
					while(discardCard == null || discardCard.getID() == 5) {
						discardCard = UI.getCardChoice(playerCards, 
								"Choose a card to discard: ");
						// Use game.discardCard instead of player.removePlayerCard
						if(discardCard.getID() == 5) {
							System.out.println("Can't play the current hand");
						}
					}

					game.discardCard(discardCard, player);
					// Keep playing ?
					if(!UI.getUserYesOrNoChoice("Remove another card?")) {
						break;
					}
					playerCards = player.getPlayerCards();
				}
				
				if(playerCards.size() == 1) {
					System.out.println("No cards in hand to play.");
				}
				
			},
			// Money
			0,
			// ID
			5,
			// DESC
			"SCROLL: DISCARD CARDS AND TAKE 2$ FOR EACH DISCARDED CARD"
	), 
	
	HARGAS_HOUSE_OF_RIBS(
			new ArrayList<Symbol>() {{
				add(Symbol.TAKE_MONEY);	//3 Dollars	
				add(Symbol.PLACE_MINION);
			}},
			(player,game) ->{},
			// Money
			3,
			// ID
			6,
			// DESC
			""
	),
	
	MR_GRYLE(
			new ArrayList<Symbol>() {{
				add(Symbol.ASSASINATION);	
				add(Symbol.TAKE_MONEY); // 1 Dollar
			}},	
			(player,game) ->{},
			// Money
			1,
			// ID
			7,
			// DESC
			""
	),
	
	THE_PEELED_NUTS(
			new ArrayList<Symbol>() {{		
				
			}},
			(player,game) ->{},
			// Money
			0,
			// ID
			8,
			// DESC
			""
	),
	
	THE_OPERA_HOUSE(
			/*
			 * Earn $1 for each minion in THE ISLE OF GODS.
			 */
			new ArrayList<Symbol>() {{
				add(Symbol.PLACE_A_BUILDING);	
			}},
			(player, game) -> {
				int minionCount=game.getMinionCountForArea(AnkhMorporkArea.ISLE_OF_GODS);
				if(game.getBank().decreaseBalance(minionCount)){
					player.increaseMoney(minionCount);
					System.out.println("Took " + minionCount + " from bank");
				} else {
					System.out.println("Bank is too poor to be able to take that money");
				}
			},
			// Money
			0,
			// ID
			9,
			// DESC
			"SCROLL: EARN 1$ FOR EACH MINION IN ISLE OF GODS"
	),
	
	NOBBY_NOBBS(
			/*
			 * Take $3 from a player of your choice.
			 */
			(player, game) -> {
//				TextUserInterface UI = new TextUserInterface();
				TextUserInterface UI = TextUserInterface.getUI();
				Map<Color,Player> myPlayersMap = game.getPlayersMap();
				
				ArrayList<Color> excludeList = new ArrayList<Color>();
				excludeList.add(player.getColor());
				
				
				Player choosenPlayer = UI.getPlayer(myPlayersMap, excludeList, true);
				if(choosenPlayer == null) {
					return;
				}
				
				if(choosenPlayer.decreaseMoney(3)){
					player.increaseMoney(3);
		}
				else{
					System.out.println("That Player don't have $3, sorry action can't be completed");
				}
			},
			new ArrayList<Symbol>() {{
				add(Symbol.PLAY_ANOTHER_CARD);	
			}},
			// Money
			0,
			// ID
			10,
			// DESC
			"SCROLL: TAKE 3$ FROM A PLAYER OF YOUR CHOICE"
	),
	
	MODO(
			/*
			 * Discard one card.
			 */
			(player, game) -> {
			//	TextUserInterface UI = new TextUserInterface();
				TextUserInterface UI = TextUserInterface.getUI();
				Set<GreenPlayerCard> playerCards = player.getPlayerCards();
				if(playerCards.size() == 1) {
					System.out.println("Only have 1 card and thats modo, so can't discard one");
					return;
				}
				
				
				GreenPlayerCard discardCard = null;
				while(discardCard == null || discardCard.getID() == 11) {
					discardCard = UI.getCardChoice(playerCards, 
							"Choose a card to discard: ");
					if(discardCard.getID() == 11) {
						System.out.println("You are playing that card and it cannot be removed");
					}
				}
				// USE GAME DISCARD CARD
//				player.removePlayerCard(discardCard);
				game.discardCard(discardCard, player);
			},
			new ArrayList<Symbol>() {{
				add(Symbol.PLACE_MINION);	
			}},
			// Money
			0,
			// ID
			11,
			// DESC
			"SCROLL: DISCARD A CARD"
	),
	
	THE_MENDED_DRUM(
			new ArrayList<Symbol>() {{
				add(Symbol.PLACE_A_BUILDING);	
				add(Symbol.TAKE_MONEY); //2 Dollars
			}},
			(player,game) ->{},
			// Money
			2,
			// ID
			12,
			// DESC
			""
	),
	
	LIBRARIAN(
			/*
			 * Take four cards from the draw deck.
			 */
			(player, game) -> {
				game.addPlayerCard(player,4);
			},
			new ArrayList<Symbol>() {{
				
			}},
			// Money
			0,
			// ID
			13,
			// DESC
			"SCROLL: TAKE 4 CARDS FROM DRAW DECK"
	),
	
	LEONARD_OF_QUIRM(
			/*
			 * Take four cards from the draw deck.
			 */
			(player, game) -> {
				game.addPlayerCard(player,4);
			},
			new ArrayList<Symbol>() {{
				
			}},
			// Money
			0,
			// ID
			14,
			// DESC
			"SCROLL: TAKE 4 CARDS FROM DRAW DECK"
	),
	
	
	SHONKY_SHOP(
		/**
		 * Discard as many cards as you wish and
		 * take $1 for each one discarded.
		 */
		(player, game) -> {
			boolean haveCards = true;
			int discardedCount=0;
			while (haveCards && player.getPlayerCards().size() > 1) {
			//	TextUserInterface UI = new TextUserInterface();
				TextUserInterface UI = TextUserInterface.getUI();
				
				GreenPlayerCard discardCard = UI.getCardChoice(player.getPlayerCards(), 
						"Choose a card to discard: ");
				while(discardCard.getID() == 15) {
					System.out.println("Cannot discard current card!");
					discardCard = UI.getCardChoice(player.getPlayerCards(), 
							"Choose a card to discard: ");
				}
				if(game.discardCard(discardCard, player)) discardedCount ++;
				else {
					System.out.println("can't remove any more cards");
					break;
				}
				if (player.getHandSize()==0) haveCards=false;
				else {
					if(!UI.getUserYesOrNoChoice("Discard another card?")) {
						haveCards = false;
					}
				}
			}
			if(player.getPlayerCards().size() == 1) {
				System.out.println("No more cards to play");
			}
			// player gets $1 for each discarded card
			player.increaseMoney(discardedCount);
		}, 
		new ArrayList<Symbol>() {{
			add(Symbol.PLACE_A_BUILDING);	
		}},
		// Money
		0,
		// ID
		15,
		// DESC
		"SCROLL: DISCARD CARDS AND TAKE 1$ FOR EACH DISCARDED CARD"
	),
	
	SACHARISSA_CRIPSLOCK(
		/*
		 * Earn $1 for each trouble marker on the board.
		 */
		(player, game) -> {
			int numberTrouble = game.getTotalNumberOfTroubleMarkers();
			if (game.getBank().decreaseBalance(numberTrouble) && player.increaseMoney(numberTrouble)) {
				System.out.println("You earned " + numberTrouble);
			} else {
				System.out.println("Not enough funds to pay player");
			}
		}, 
		new ArrayList<Symbol>() {{
			add(Symbol.PLACE_MINION);
		}},
		// Money
		0,
		// ID
		16,
		// DESC
		"SCROLL: EARN 1$ FOR EACH TROUBLE MARKER"
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
//			TextUserInterface UI = new TextUserInterface();
			TextUserInterface UI = TextUserInterface.getUI();
			Map<Color,Player> myPlayersMap;
			myPlayersMap = game.getPlayersMap();
			
			boolean validChoice = false;
			// Ensure at least one player has 2$
			for(Player p : myPlayersMap.values()) {
				if(p.getMoney() > 1 && p.getColor() != player.getColor()) {
					validChoice = true;
					break;
				}
			}
			if(!validChoice) {
				System.out.println("No other player in game has 2$, sorry");
				return;
			}
			
			// Ensure player has cards to give
			if(player.getPlayerCards().size() == 1) {
				System.out.println("You have no other cards to give");
				return;
			}
			
			ArrayList<Color> excludeList = new ArrayList<Color>();
			excludeList.add(player.getColor());
			for(Player p: myPlayersMap.values()) {
				if(p.getMoney() < 2) {
					excludeList.add(p.getColor());
				}
			}
			
			// Chose a valid player
			Player choosenPlayer = UI.getPlayer(myPlayersMap, excludeList, true);
			if(choosenPlayer == null) {
				return;
			}

			// Make selection, cannot get rid of this card
			GreenPlayerCard card = UI.getCardChoice(player.getPlayerCards(),"choose a card to give to the choosen player");
			while(card.getID() == 17) {
				System.out.println("Cannot choose current card in player");
				card = UI.getCardChoice(player.getPlayerCards(),"choose a card to give to the choosen player");
			}
			
			player.removePlayerCard(card);
			choosenPlayer.addPlayerCard(card);
			choosenPlayer.decreaseMoney(2);
			player.increaseMoney(2);
				
		},
		// Money
		0,
		// ID
		17,
		// DESC
		"SCROLL: EXCAHNGE A CARD FOR 2$ FROM ANOTHER PLAYER"
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
			// Ensure player has minions in trouble area
			// if so remove it and place it on adjacent area
			Map<Integer, BoardArea> minionAreas = game.getAreasWithPlayerMinions(player);
			Map<Integer, BoardArea> troubleMinionAreas = new HashMap<Integer, BoardArea>();
			if(minionAreas.size() == 0) {
				System.out.println("You have no minions to move");
				return;
			}
			
			for(BoardArea ba: minionAreas.values()) {
				if(ba.hasTroubleMarker()) {
					troubleMinionAreas.put(ba.getArea().getAreaCode(), ba);
				}
			}
			
			if(troubleMinionAreas.size() == 0) {
				System.out.println("No minion areas contain trouble");
				return;
			}
			
			// Get remove minion area
			//TextUserInterface UI = new TextUserInterface();
			TextUserInterface UI = TextUserInterface.getUI();
			
			BoardArea removeArea = UI.getAreaChoice(troubleMinionAreas, "Choose area to remove minion", "Choose area", true);
			// Get nighbouring areas
			Map<Integer, BoardArea> neighbours = game.getNeighbours(removeArea);
			ArrayList<Integer> excludeList = new ArrayList<Integer>();
			excludeList.add(removeArea.getArea().getAreaCode());
			// get placecment area
			BoardArea chosenArea = UI.getAreaChoice(neighbours, "Choose area to place minion", "Choose area", true, excludeList);
			
			// Actually do the movement
			removeArea.removeMinion(player);
			chosenArea.addMinion(player);
			
		},
		// Money
		0,
		// ID
		18,
		// DESC
		"SCROLL: MOVE MINION IN TROUBLE AREA"
	),
	
	THE_ROYAL_MINT(
		new ArrayList<Symbol>() {{
			add(Symbol.PLACE_A_BUILDING);
			add(Symbol.TAKE_MONEY); //5 Dollars
		}},
		(player,game) ->{},
		// Money
		5,
		// ID
		19,
		// DESC
		""
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
			
			// Ensure one player has two cards to give
			boolean validChoice = false;
			for(Player p: game.getPlayers()) {
				if(p.getPlayerCards().size() > 2 && p.getColor() != player.getColor()){
					validChoice = true;
				}
			}
			if(!validChoice) {
				System.out.println("No other player has 2 cards to give - sorry");
				return;
			}
			
			//TextUserInterface UI = new TextUserInterface();
			TextUserInterface UI = TextUserInterface.getUI();
			
			ArrayList<Color> excludeList = new ArrayList<Color>();
			excludeList.add(player.getColor());
			for(Player p: game.getPlayersMap().values()) {
				if(p.getPlayerCards().size() < 2){
					excludeList.add(p.getColor());
				}
			}
			Player selectedPlayer = UI.getPlayer(game.getPlayersMap(), excludeList, true);
			if(selectedPlayer == null) {
				return;
			}
			
			for(int i =0; i<2; i++){
				System.out.println("Important!!! Change palyers - " + selectedPlayer.getName() +" has to choose two of his cards to give away!!");
				GreenPlayerCard chosenCard = UI.getCardChoice(selectedPlayer.getPlayerCards(), selectedPlayer.getName() + " choose a card to give away");
				player.addPlayerCard(chosenCard);
				selectedPlayer.removePlayerCard(chosenCard);
			}
		},
		// Money
		0,
		// ID
		20,
		// DESC
		"SCROLL: ANOTHER PLAYER MUST GIVE YOU TWO CARDS"
	),
	
	PINK_PUSSYCAT_CLUB(
		new ArrayList<Symbol>() {{
			add(Symbol.TAKE_MONEY);//3 Dollars
			add(Symbol.PLAY_ANOTHER_CARD);
		}},
		(player, game) -> {},
		// Money
		3,
		// ID
		21,
		// DESC
		""
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
		}},
		// Money
		0,
		// ID
		22,
		// DESC
		"SCROLL: EXCHANGE PERSONALITY CCARD WITH ONE FROM DECK"
			
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
			TextUserInterface UI = TextUserInterface.getUI();
			Map<Color,Player> myPlayersMap;
			myPlayersMap = game.getPlayersMap();
			ArrayList<Color> excludeList = new ArrayList<Color>();
			excludeList.add(player.getColor());
			// Chose a valid player
			Player choosenPlayer = UI.getPlayer(myPlayersMap, excludeList, true);

			if(UI.getUserYesOrNoChoice("do you want to give "+player.getName()+" $5? (other wise your card count will be reduced)")){
				if(choosenPlayer.hasMoney(5)){
					if(choosenPlayer.decreaseMoney(5)) player.increaseMoney(5);
				}
			}
			else choosenPlayer.addUnplayableCard(game.getCurrentCardInPlay());
		}, 
		new ArrayList<Symbol>() {{
			add(Symbol.PLACE_MINION);
		}},
		// Money
		0,
		// ID
		23,
		// DESC
		"SCROLL: ANOTHER PLAYER MUST GIVE YOU 5$ OR TAKE THIS CARD AND NEVER DISCARD IT"
			
	),
	
	WALLACE_SONKY(
		new ArrayList<Symbol>() {{
			add(Symbol.INTERRUPT);
		}},
		/*
		 * You cannot be affected by the text on a card 
		 * played by another player
		 */
		(player, game) -> {},
		// Money
		0,
		// ID
		24,
		// DESC
		"SCROLL: INTERRUPT A PLAYER PLAYING SCROLL"
	),
	
	THE_SEAMSTRESS_GUILD(
		(player, game) -> {
			/*
			 * Choose one player.  Give them
			 * one of your cards.  They must 
			 * give you 2$ in return
			 */
			//TextUserInterface UI = new TextUserInterface();
			TextUserInterface UI = TextUserInterface.getUI();
			
			Map<Color,Player> myPlayersMap;
			myPlayersMap = game.getPlayersMap();
			
			boolean validChoice = false;
			// Ensure at least one player has 2$
			for(Player p : myPlayersMap.values()) {
				if(p.getMoney() > 1 && p.getColor() != player.getColor()) {
					validChoice = true;
					break;
				}
			}
			if(!validChoice) {
				System.out.println("No other player in game has 2$, sorry");
				return;
			}
			
			// Ensure player has cards to give
			if(player.getPlayerCards().size() == 1) {
				System.out.println("You have no other cards to give");
				return;
			}
			
			ArrayList<Color> excludeList = new ArrayList<Color>();
			excludeList.add(player.getColor());
			for(Player p: myPlayersMap.values()) {
				if(p.getMoney() < 2) excludeList.add(p.getColor());
			}
			
			// Chose a valid player
			Player choosenPlayer = UI.getPlayer(myPlayersMap, excludeList, true);
			if(choosenPlayer == null ){
				return;
			}
			
			// Make selection, cannot get rid of this card
			GreenPlayerCard card = UI.getCardChoice(player.getPlayerCards(),"choose a card to give to the choosen player");
			while(card.getID() == 17) {
				System.out.println("Cannot choose current card in player");
				card = UI.getCardChoice(player.getPlayerCards(),"choose a card to give to the choosen player");
			}
			
			player.removePlayerCard(card);
			choosenPlayer.addPlayerCard(card);
			choosenPlayer.decreaseMoney(2);
			player.increaseMoney(2);
		},
		new ArrayList<Symbol>() {{
			add(Symbol.PLACE_MINION);
		}},
		// Money
		0,
		// ID
		25,
		// DESC
		"SCROLL: EXCAHNGE YOUR CARD FOR ANOTHER PLAYERS 2$"
	),
	
	MR_PIN_AND_MR_TULIP(
		new ArrayList<Symbol>() {{
			add(Symbol.ASSASINATION);
			add(Symbol.TAKE_MONEY);		
		}},
		(player,game) ->{},
		// Money
		1,
		// ID
		26,
		// DESC
		""
	),
	
	THE_THIEVES_GUILD(
		(player, game) -> {
			/*
			 * Take $2, if possible, from
			 * every other player.
			 */
			Map<Color,Player> myPlayersMap = game.getPlayersMap();
			myPlayersMap.remove(player.getColor());
			for (Entry<Color, Player>  entry : myPlayersMap.entrySet())
			{
			   if(entry.getValue().getMoney()>=2) {
				   entry.getValue().decreaseMoney(2);
				   player.increaseMoney(2);
				   System.out.println("Took $2 from "+entry.getValue().getName());
			   }
			   else continue;
			}
		},
		new ArrayList<Symbol>() {{
			add(Symbol.PLACE_MINION);
		}},
		// Money
		0,
		// ID
		27,
		// DESC
		"SCROLL: TAKE 2$ FROM EVERY OTHER PLAYER"
			
	),
	MR_BOGGIS(
		new ArrayList<Symbol>() {{
			add(Symbol.PLACE_MINION);
		}},
		(player, game) -> {
			Map<Color,Player> myPlayersMap = game.getPlayersMap();
			myPlayersMap.remove(player.getColor());
			for (Entry<Color, Player>  entry : myPlayersMap.entrySet())
			{
			   if(entry.getValue().getMoney()>=2) {
				   entry.getValue().decreaseMoney(2);
				   player.increaseMoney(2);
				   System.out.println("Took $2 from "+entry.getValue().getName());
			   }
			   else continue;
			}
		},
		// Money
		0,
		// ID
		28,
		// DESC
		"SCROLL: TAKE 2$ FROM EVERY OTHER PLAYER"
	),
	MR_BENT(
		new ArrayList<Symbol>(){{
			add(Symbol.PLAY_ANOTHER_CARD);
		}},
		(player, game) -> {
				game.givePlayerMoneyFromBank(player,10);
				player.addUnplayableCard(game.getCurrentCardInPlay());
		},
		// Money
		0,
		// ID
		29,
		// DESC
		"SCROLL: LOAD THIS CARD FOR 10$ MUST REPAY AT END OF GAME"
	),
	
	THE_BEGGARS_GUILD(
		new ArrayList<Symbol>(){{
			add(Symbol.PLACE_MINION);
		}},
		(player, game) -> {
			//calling the singleton
			TextUserInterface UI = TextUserInterface.getUI();
			
			//Get a list of player and exclude self from it
			Map<Color,Player> myPlayersMap = game.getPlayersMap();
			ArrayList<Color> excludeList = new ArrayList<Color>();
			excludeList.add(player.getColor());
			
			// Chose a valid player
			Player choosenPlayer = UI.getPlayer(myPlayersMap, excludeList, true);
			if(choosenPlayer == null ) {
				 return;
			}
			
			for (int i=0;i<2;i++){
				GreenPlayerCard giveAwayCard = UI.getCardChoice(choosenPlayer.getPlayerCards(),"Choose a card to give away");
				game.removePlayerCard(giveAwayCard,choosenPlayer);
				game.addPlayerCard(player,giveAwayCard);
			}
		},
		// Money
		0,
		// ID
		30,
		// DESC
		"SCROLL: ANOTHER PLAYER MUST GIVE YOU TWO OF THEIR CARDS"
	),

	THE_BANK_OF_ANKH_MORPORK(
		new ArrayList<Symbol>(){{
			add(Symbol.PLAY_ANOTHER_CARD);
		}},
		(player, game) -> {
			game.givePlayerMoneyFromBank(player,10);
			player.addUnplayableCard(game.getCurrentCardInPlay());
		},
		// Money
		0,
		// ID
		31,
		// DESC
		"SCROLL: TAKE 10$ LOAN FROM THE BANK, MAY PAY BACK AT END OF GAME"
	),
	
	THE_ANKH_MORPORK_SUNSHINE_DRAGON_SANCTUARY(
		new ArrayList<Symbol>(){{
			add(Symbol.PLAY_ANOTHER_CARD);
		}},
		(player, game) -> {
			//TextUserInterface UI = new TextUserInterface();
			TextUserInterface UI = TextUserInterface.getUI();
			for(Player p: game.getPlayers()){
				boolean choiceMade = false;
				Set<GreenPlayerCard> playerCards = p.getPlayerCards();
				while(!choiceMade){
					if(UI.getUserYesOrNoChoice(p.getName()+" do you want to give one of your cards? (otherwise you will pay $1)")){
						game.addPlayerCard(p,UI.getCardChoice(p.getPlayerCards(),"Choose a card to give away"));
						choiceMade = true;
					};
					if(UI.getUserYesOrNoChoice("do you want to give $1 instead of a card? (otherwise you have to give up one card)")){
						   if(p.getMoney()>=1) {
							   if(p.decreaseMoney(1)&player.increaseMoney(1))
							   System.out.println("Took $1 from "+p.getName());
							   choiceMade = true;
						   }
						   else choiceMade = true;
					};
				}
			}
		},
		// Money
		0,
		// ID
		32,
		// DESC
		"SCROLL: EACH PLAYER MUST GIVE YOU 1$ OR ONE OF THEIR CARDS"
	),
	
	SERGANT_ANGUA(
		new ArrayList<Symbol>(){{
			add(Symbol.REMOVE_TROUBLE_MARKER);
			add(Symbol.PLAY_ANOTHER_CARD);
		}},
		(player, game) -> {},
		// Money
		0,
		// ID
		33,
		// DESC
		""
	),
	
	THE_AGONY_AUNTS(
		new ArrayList<Symbol>() {{
			add(Symbol.ASSASINATION);
			add(Symbol.TAKE_MONEY);
			add(Symbol.PLACE_MINION);
		}},
		(player, game) -> {},
		// Money
		2,
		// ID
		34,
		// DESC
		""
	),
	
	/** 
	 * Earn 1 dollar for each minion in the isles of gods
	 */
	THE_DYSK(
		new ArrayList<Symbol>(){{
			add(Symbol.PLACE_A_BUILDING);
		}},
		(player, game) -> {
			int minions = game.getMinionCountForArea(AnkhMorporkArea.ISLE_OF_GODS);
			System.out.println("Giving " + minions + " to player");
			player.increaseMoney(minions);
			
		},
		// Money
		0,
		// ID
		35,
		// DESC
		"SCROLL: EARN 1$ FOR EACH MINION IN ISLES OF GODS"
	),
	
	THE_DUCKMAN(
		new ArrayList<Symbol>(){{
		}},
		(player, game) -> {
			//System.out.println("NOT IMPLEMENTED: THE_DUCKMAN: move a minion belonging to "
			//+ "another player from one area "
			//+ "to an adjacent area");
			
		//TextUserInterface UI = new TextUserInterface();
			TextUserInterface UI = TextUserInterface.getUI();
		Map<Color,Player> myPlayersMap = game.getPlayersMap();
		Map<Color, Player> playerWithMinion = new HashMap<Color, Player>();
		
		for(Player p : myPlayersMap.values()) {
			if(p.getColor() == player.getColor()) continue;
			if(game.getAreasWithPlayerMinions(p).size() == 0) continue;
			else {
				playerWithMinion.put(p.getColor(), p);
			}
		}
		
		if(playerWithMinion.size() == 0) {
			System.out.println("No players have minions you can remove");
			return;
		}
		
		
		ArrayList<Color> excludeList = new ArrayList<Color>();
		excludeList.add(player.getColor());
		
		
		// Chose a valid player
		System.out.println("Choose player to remove minion from:");
		Player choosenPlayer = UI.getPlayer(playerWithMinion, excludeList, true);
		if(choosenPlayer == null) {
			return;
		}
			
		// Ensure player has minions
		// if so remove it and place it on adjacent area
		Map<Integer, BoardArea> minionAreas = game.getAreasWithPlayerMinions(choosenPlayer);
		if(minionAreas.size() == 0) {
			System.out.println("She/He has no minions to move");
			return;
		}
						
		// Get remove minion area
		BoardArea removeArea = UI.getAreaChoice(minionAreas, "Choose area to remove her/his minion", "Choose area", true);
		
		// Get nighbouring areas
		Map<Integer, BoardArea> neighbours = game.getNeighbours(removeArea);
		ArrayList<Integer> excludeListArea = new ArrayList<Integer>();
		excludeListArea.add(removeArea.getArea().getAreaCode());
		
		// get placement area
		BoardArea chosenArea = UI.getAreaChoice(neighbours, "Choose area to place minion", "Choose area", true, excludeListArea);					
		
		// Actually do the movement
		removeArea.removeMinion(choosenPlayer);
		chosenArea.addMinion(choosenPlayer);
								
		},
		// Money
		0,
		// ID
		36,
		// DESC
		"SCROLL: MOVE ANOTHER PLAYER MINION"
	),
	
	/**
	 * Play any two other cards from your hand
	 */
	DRUMKNOTT(
		new ArrayList<Symbol>(){{
		}},
		(player, game) -> {

			Set<GreenPlayerCard> playerCards = player.getPlayerCards();
			if(playerCards.size() < 3) {
				System.out.println("You do not have enough cards");
			} else {
				//TextUserInterface UI = new TextUserInterface();
				for(int i=0;i<2;++i){
					TextUserInterface UI = TextUserInterface.getUI();

					GreenPlayerCard c = UI.getCardChoice(player.getPlayerCards(), "Choose a card to play: ");
					while(c.getID() == 37) {
						System.out.println("You cannot play this card");
						c = UI.getCardChoice(player.getPlayerCards(), "Choose a card to play: ");
					}
					UI.playPlayerCard(c, player);
				}
			}
		},
		// Money 
		0,
		// ID
		37,
		// DESC
		"SCROLL: PLAY ANY TWO OTHER CARDS FROM HAND"
	),
	
	@SuppressWarnings("resource")
	CMOT_DIBBLER(
		new ArrayList<Symbol>(){{
			add(Symbol.PLAY_ANOTHER_CARD);
		}},
		(player, game) -> {
			//System.out.println("NOT IMPLEMENTED: COMT_DIBBLER: Roll the die. on the role of 7 or more"
			//	+ "you take $4 from the bank. on a roll"
			//	+ "of 1 you must pay $2 to the bank"
			//	+ "or remove one of your minions from"
			//	+ "the board, all other results have"
			//	+ "no effect");
			
			int dieRoll = Die.getDie().roll();
			System.out.println("Dice rolled: " + dieRoll);

//			TextUserInterface textUI = new TextUserInterface();
			TextUserInterface textUI = TextUserInterface.getUI();
			
			if(dieRoll >= 7) {	
				System.out.println("Giving 4$");
				game.givePlayerMoneyFromBank(player,4);							
			} 
			else if(dieRoll == 1) {
				
				Scanner scanner = new Scanner(System.in);
				String optionChoice = null;
				
				System.out.println("\tYou have 2 options:");
				System.out.println("\t1- Paying 2$ to the bank, enter 1");
				System.out.println("\t2- Removing a minion, enter 2");
				System.out.print("Choice: ");
				optionChoice = scanner.nextLine();
			
				switch (optionChoice){
				case "1":
				{
					game.giveBankMoneyFromPlayer(player,2);
				}
					break;			
				case "2":
				{
					BoardArea chosenArea = textUI.getAreaChoice(game.getAreasWithPlayerMinions(player), "Choose area to remove minion", "Choose: ");
					chosenArea.removeMinion(player);	
				}
					break;
					
				default: System.out.println("\tPlease enter your choice:");
				    break;
				}
			} else {	
				System.out.println("No Action");
			}
		},
		// Money
		0,
		// ID
		38,
		// DESC
		"SCROLL: DICE ROLL 7+ TAKE 4$ FROM BANK, DICE ROLL 1 PAY 2$ OR REMOVE YOUR MINION"
	),
	
	DR_CRUCES(
		new ArrayList<Symbol>() {{
			add(Symbol.ASSASINATION);
			add(Symbol.TAKE_MONEY);
		}},
		(player, game) -> {},
		// Money
		3,
		// ID
		39,
		// DESC
		""
	),
	
	CAPTAIN_CARROT(
		new ArrayList<Symbol>() {{
			add(Symbol.PLACE_MINION);
			add(Symbol.REMOVE_TROUBLE_MARKER);
			add(Symbol.TAKE_MONEY);
		}},
		(player, game) -> {},
		// Money
		1,
		// ID 
		40,
		// DESC
		""
	),
	
	
	MRS_CAKE(
		new ArrayList<Symbol>(){{
			add(Symbol.TAKE_MONEY);
			add(Symbol.PLACE_A_BUILDING);
		}},
		(player, game) -> {
/*			PersonalityDeck personalityDeck = game.getPersonalityDeck();
			int visibleSize = personalityDeck.size() -1;
			if (visibleSize < 0) {
				System.out.println("Only one personality left in deck...");
			} else {
				int count = 0;
				System.out.println("Showing " + (visibleSize + 1) + " unused personality cards...");
				for(PersonalityCard card: personalityDeck.getDeck()) {
					if(count >= visibleSize) break;
					System.out.println(card);
				}
			}
*/		},
		// Money
		2,
		// ID
		41,
		// DESC
		"SCROLL: LOOK AT ALL BUT ONE UNUSED PERSONALITY CARDS"
	),
	
	
	GROAT(
		new ArrayList<Symbol>() {{
			add(Symbol.PLACE_MINION);
		}},
		(player, game) -> {
			System.out.println("No Text on card");
		},
		// Money
		0,
		// ID
		42,
		// DESC
		""
	),
	
	GIMLETS_DWARF_DELICATESSEN(
		new ArrayList<Symbol>() {{
			add(Symbol.TAKE_MONEY);
			add(Symbol.PLACE_MINION);				
		}},
		(player, game) -> {
			System.out.println("No Text on Card");
		},
		// Money
		3,
		// ID
		43,
		// DESC
		""
	),
	
	GASPODE(
		new ArrayList<Symbol>() {{
			add(Symbol.INTERRUPT);
		}},
		(player, game) -> {
			// No need to implement .. this is an interrupt card, handled in game flow
			
		},
		// Money
		0,
		// ID
		44,
		// DESC
		"SCROLL: INTERRUPT PLAYER MOVING YOUR MINION"
	),
	
	THE_FRESH_START_CLUB(
		new ArrayList<Symbol>() {{
			add(Symbol.INTERRUPT);
		}},
		(player, game) -> {
			// No need to implement .. this is an interrupt card, handled in game flow
		},
		// Money
		0,
		// ID
		45,
		// DESC
		""
	),
	
	FOUL_OLE_RON(
		new ArrayList<Symbol>(){{
			add(Symbol.PLAY_ANOTHER_CARD);
		}},
		(player, game) -> {
			//System.out.println("NOT IMPLEMENTED: FOUL_OLE_RON: move a minion belonging to"
			//	+ "another player from one area"
			//	+ "to an adjacent area");

//			TextUserInterface UI = new TextUserInterface();
			TextUserInterface UI = TextUserInterface.getUI();
			Map<Color,Player> myPlayersMap = game.getPlayersMap();
			Map<Color, Player> playerWithMinion = new HashMap<Color, Player>();
			
			for(Player p : myPlayersMap.values()) {
				if(p.getColor() == player.getColor()) continue;
				if(game.getAreasWithPlayerMinions(p).size() == 0) continue;
				else {
					playerWithMinion.put(p.getColor(), p);
				}
			}
			
			if(playerWithMinion.size() == 0) {
				System.out.println("No players have minions you can remove");
				return;
			}
			
			
			ArrayList<Color> excludeList = new ArrayList<Color>();
			excludeList.add(player.getColor());
			
			
			// Chose a valid player
			System.out.println("Choose player to remove minion from:");
			Player choosenPlayer = UI.getPlayer(playerWithMinion, excludeList, true);
			
			if(choosenPlayer == null) {
				return;
			}
			
			// Ensure player has minions
			// if so remove it and place it on adjacent area
			Map<Integer, BoardArea> minionAreas = game.getAreasWithPlayerMinions(choosenPlayer);
			if(minionAreas.size() == 0) {
				System.out.println("She/He has no minions to move");
				return;
			}
							
			// Get remove minion area
			BoardArea removeArea = UI.getAreaChoice(minionAreas, "Choose area to remove her/his minion", "Choose area", true);
			
			// Get nighbouring areas
			Map<Integer, BoardArea> neighbours = game.getNeighbours(removeArea);
			ArrayList<Integer> excludeListArea = new ArrayList<Integer>();
			excludeListArea.add(removeArea.getArea().getAreaCode());
			
			// get placement area
			BoardArea chosenArea = UI.getAreaChoice(neighbours, "Choose area to place minion", "Choose area", true, excludeListArea);					
			
			// Actually do the movement
			removeArea.removeMinion(choosenPlayer);
			chosenArea.addMinion(choosenPlayer);
						
			
		},
		// Money
		0,
		// ID
		46,
		// DESC
		"SCROLL: MOVE A PLAYERS MINION"
	),
	
	/**
	 * Select another player.  If they do not give you $5 then
	 * place this card in front of them.  This card now counts towards
	 * their hand size of five cards when they come to refil their hand.
	 * They cannot get rid of this card
	 */
	THE_FOOLS_GUILD(
		new ArrayList<Symbol>() {{
			add(Symbol.PLACE_MINION);
		}},
		(player, game) -> {
//			TextUserInterface UI = new TextUserInterface();
			TextUserInterface UI = TextUserInterface.getUI();
			Map<Color,Player> myPlayersMap = game.getPlayersMap();
			
			System.out.println("Choose a player to give you 5 dollars");
			
			
			ArrayList<Color> excludeList = new ArrayList<Color>();
			excludeList.add(player.getColor());
			
			
			// Chose a valid player
			Player choosenPlayer = UI.getPlayer(myPlayersMap, excludeList, true);
			if(choosenPlayer == null) {
				return;
			}
			
			boolean hasMoney = choosenPlayer.getMoney() > 5;
			boolean wantsToGive = false;
			if(!hasMoney) {
				System.out.println("Damn, " + choosenPlayer.getName() + " doesn't have 5 dollars");
			} else {
				wantsToGive = UI.getUserYesOrNoChoice(choosenPlayer.getName() + " do you want to give " + player.getName() + 
						" $5");
				if(wantsToGive) {
					choosenPlayer.decreaseMoney(5);
					player.increaseMoney(5);
				}
			}
			
			// Ok, lets have some fun
			// give this card to the player, make sure he cant get rid of it
			if(!hasMoney || !wantsToGive) {
				// Why do we use current card in play??? because we cant reference this enum
				// since it isn't created
				if(game.getCurrentCardInPlay().getID() != 47) {
					//TODO throw error
					System.out.println("ISSUE IN THE FOOLS GUILD");
				}
				
				// Give card to other player and say he cant get rid of it
				// Unless currentPlayer got this card via this means
				if(!player.getUnplayableCards().contains(game.getCurrentCardInPlay())){
					game.addPlayerCard(choosenPlayer, game.getCurrentCardInPlay());
					choosenPlayer.addUnplayableCard(game.getCurrentCardInPlay());
					game.removePlayerCard(game.getCurrentCardInPlay(), player);
				}
			}
		},
		// Money
		0,
		// ID
		47,
		// DESC
		"SCROLL: 5$ FROM PLAYER OR FOREVER HAVE THIS CARD"
	),
	
	/**
	 * Choose a player, if he does not pay you 5$ then you can remove one of his buildings from the baord
	 */
	// TODO NEEDS INTERRUPT
	THE_FIRE_BRIGADE(
		new ArrayList<Symbol>(){{
			add(Symbol.PLAY_ANOTHER_CARD);
		}},
		(player, game) -> {
//			TextUserInterface UI = new TextUserInterface();
			TextUserInterface UI = TextUserInterface.getUI();
			Map<Color,Player> myPlayersMap = game.getPlayersMap();
			
			System.out.println("Choose a player to give you 5 dollars");
			
			
			ArrayList<Color> excludeList = new ArrayList<Color>();
			excludeList.add(player.getColor());
			
			
			// Chose a valid player
			Player choosenPlayer = UI.getPlayer(myPlayersMap, excludeList, true);
			if(choosenPlayer == null) {
				return;
			}
			
			
			boolean hasMoney = choosenPlayer.getMoney() > 5;
			boolean wantsToGive = false;
			if(!hasMoney) {
				System.out.println("Damn, " + choosenPlayer.getName() + " doesn't have 5 dollars");
			} else {
				System.out.println(choosenPlayer.getColor().getAnsi());
				wantsToGive = UI.getUserYesOrNoChoice(choosenPlayer.getName() + " do you want to give " + player.getName() + 
						" $5");
				System.out.println(game.getPlayerOfCurrentTurn().getColor().getAnsi());
				if(wantsToGive) {
					choosenPlayer.decreaseMoney(5);
					player.increaseMoney(5);
				}
			}
			
			if(!hasMoney || !wantsToGive) {
				System.out.println("...ok ... " + player.getName() + " will choose a building to remove...");
				Map<Integer, BoardArea> buildings = game.getBuildingAreas(choosenPlayer);
				if(buildings.size() == 0) {
					System.out.println("No buildings to be removed, sorry");
				} else {
					BoardArea chosenArea = UI.getAreaChoice(buildings, "Select an area: ", "Choice: ");
					chosenArea.removeBuilding();
				}
			}
		},
		// Money
		0,
		// ID
		48,	
		// DESC
		"SCROLL: 5$ FROM PLAYER OR REMOVE BUILDING"
	)
	;
	
	private BiConsumer<Player, Game> text;

	private List<Symbol> symbols;

	private boolean textFirst;

	private Integer money;
	
	private Integer id;
	
	private String desc;
	
	
	GreenPlayerCard(List<Symbol> symbols, BiConsumer<Player, Game> text, Integer money, Integer id, String desc) {
		this.symbols = symbols;
		this.text = text;
		this.textFirst = false;
		this.money = money;
		this.id = id;
		this.desc = desc;
	}
	
	
	
	GreenPlayerCard(BiConsumer<Player, Game> text, List<Symbol> symbols, Integer money, Integer id, String desc) {
		this.symbols = symbols;
		this.text = text;
		this.textFirst = true;
		this.money = money;
		this.id = id;
		this.desc = desc;
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
	
	public Integer getID() {
		return this.id;
	}
	
	public String getDesc() {
		return this.desc;
	}
	
	@Override
	public String toString() {
		String rtn =  name() + "\n";
		if(textFirst) {
			rtn += "\t" +getDesc() + "\n";
		}
		for(Symbol s: getSymbols()) {
			rtn += "\t"+s + "\n";
		}
		if(!textFirst) {
			rtn += "\t"+getDesc() + "\n";
		}
		rtn += "";
	
		return rtn;
	}
	
	


}
