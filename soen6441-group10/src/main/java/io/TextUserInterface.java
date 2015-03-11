package io;

import gameplay.Bank;
import gameplay.BoardArea;
import gameplay.Controller;
import gameplay.Game;
import gameplay.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.function.BiConsumer;

import util.Color;
import card.city.AnkhMorporkArea;
import card.player.GreenPlayerCard;
import card.player.Symbol;

/**
 * <b> This class makes a command line interface to communicate with the players. <b> 
 * 
 * @author Team 10 - SOEN6441
 * @version 1.0
 */
public class TextUserInterface {

	Controller controller = new Controller();
	FileManager<Game> fm = new JSONFileManager<>(Game.class);
	FileObject<Game> currentGameFileObj;
	Scanner scanner;

	/**
	 * This method implements an interactive interface to:</br>
	 * Choose one of the following:</br>
	 * 	1) n to start a new game </br>
	 *  2) l to load a previously saved game</br>
	 *  3) o for an overview of the current game's status</br>
	 *  4) s to save the current game</br>
	 *  5) q to quit the game</br>
	 */
	public void runMainMenu() {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Welcome to Ankh-Morpork!");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");

		scanner = new Scanner(System.in);
		String action = "";

		// Main menu loop
		while (!action.equals(UserOption.QUIT.getOptionString())) {
			System.out
					.println("\n Choose one of the following:\n"
							+ "1) n to start a new game\n"
							+ "2) l to load a previously saved game\n"
							+ "3) o for an overview of the current game's status\n"
							+ "4) s to save the current game\n"
							+ "5) q to quit the game\n");
			System.out.print("> ");
			action = scanner.nextLine();

			if (action.equals(UserOption.NEW_GAME.getOptionString())) {
				runGame();
			} else if (action.equals(UserOption.LOAD.getOptionString())) {
				// Hold on to that reference to save later
				Optional<FileObject<Game>> gameWrap = loadGame();
				if (gameWrap.isPresent()) {
					currentGameFileObj = gameWrap.get();
					controller = new Controller(currentGameFileObj.getPOJO());
				}
			} else if (action.equals(UserOption.GAME_STATUS.getOptionString())) {
				printGameStatus();
			} else if (action.equals(UserOption.SAVE.getOptionString())) {
				saveGame();
			}

		}
		
		System.out.println("See ya!");
	}

	
	/**
	 * This method starts a new game by getting the number of players and their names.
	 */
	private void runGame() {

		// Get the number of players and their names.
		int numberOfPlayers;

		System.out.print("Enter the number of players: ");
		numberOfPlayers = scanner.nextInt();
		scanner.nextLine();
		String[] playerNames = new String[numberOfPlayers];

		for (int i = 0; i < numberOfPlayers; ++i) {
			System.out.print("Enter the name of player #" + String.valueOf(i) + ":" );
			playerNames[i] = scanner.nextLine();
		}

		if (controller.newGame(numberOfPlayers, playerNames)) {
			System.out.println("Game Started!");

			controller.simulate();

			String action = "";
			while (!action.equals(UserOption.QUIT.getOptionString())) {
				System.out
						.println("\nChoose one of the following:\n"
								+ "1) t to move to the next turn\n"
								+ "2) l to load a previously saved game\n"
								+ "3) o for the game's overview\n"
								+ "4) s to save the current game\n"
								+ "5) e to exit and go back to the main menu");
				System.out.print("> ");
				action = scanner.nextLine();

				if (action.equals(UserOption.EXIT.getOptionString())) {
					return;
				} else if (action.equals(UserOption.NEXT_TURN.getOptionString())) {
					if (!controller.isGameOver()) {

						playTurn(controller.advanceToNextTurn());
					} else {
						System.out.println("The game has finished!");
						break;
					}
				} else if (action.equalsIgnoreCase(UserOption.GAME_STATUS.getOptionString())) {
					printGameStatus();
				} else if (action.equals(UserOption.LOAD.getOptionString())) {
					// Hold on to that reference to save later
					Optional<FileObject<Game>> gameWrap = loadGame();
					if (gameWrap.isPresent()) {
						currentGameFileObj = gameWrap.get();
						controller = new Controller(
								currentGameFileObj.getPOJO());
					}
				} else if (action.equals(UserOption.SAVE.getOptionString())) {
					saveGame();
				} 
			}

		} else {
			// Too many or too few players in game.
			System.out.println("Sorry, only 2 to 4 players can play this game!");
		}

	}
	
	/**
	 * Runs the given player's turn which consists of drawing a card (or more,
	 * if applicable), performing selectively the symbols on the card 
	 * (except for Random Events, which are mandatory) and restoring the hand
	 * back to 5 cards (if applicable).
	 * @param p
	 */
	private void playTurn(Player p) {
		System.out.println("Player " + p.getName() + "turn!");
		GreenPlayerCard c = getCardChoice(p.getPlayerCards(), "Choose a card to play: ");
		controller.getGame().setCurrentCardInPlay(c);
		
		// Determine which needs to be completed first
		// Symbols or text
		if(c.isTextFirst()) {
			playText(c, p);
			playSymbols(c, p);
		} else {
			playSymbols(c, p);
			playText(c, p);
		}
		

		controller.getGame().setCurrentCardInPlay(null);
		controller.restorePlayerHand(p);
	}
	
	/**
	 * 
	 * @param c GreenPlayerCard being played
	 * @param p Player who's turn it is
	 */
	private void playText(GreenPlayerCard c, Player p) {
		System.out.println("Playing Text on the card");
		BiConsumer<Player, Game> textAction = c.getText();
		
		if(textAction != null){
			textAction.accept(p, controller.getGame());
		}
	}
	
	/**
	 * 
	 * @param c GreenPlayerCard currently in use
	 * @param p Player who turn it is
	 */
	private void playSymbols(GreenPlayerCard c, Player p) {
		System.out.println("Playing Symbols on the card");
		// Perform the symbols on the cards selectively
		for (Symbol s : c.getSymbols()) {
			// Only Random Events are mandatory
			if (s != Symbol.RANDOM_EVENT) {
				System.out.println("Do you want to perform " + s + "? (yes/no)");
				System.out.print("> ");
				//Extra next line seems to be needed ... not sure why
				scanner.nextLine();
				String choice = scanner.nextLine();
				if (UserOption.YES.name().equalsIgnoreCase(choice)) {
					if(s == Symbol.PLAY_ANOTHER_CARD) {
						playTurn(p);
						return;
					}
					controller.performSymbolAction(p, s);
				}
			} else {
				controller.performSymbolAction(p, s);
			}
		}
		
	}
	
	
	public GreenPlayerCard getCardChoice(Collection<GreenPlayerCard> cards, String message) {
		Map<Integer, GreenPlayerCard> cardMap = new HashMap<>();
		System.out.println(message);
		int i = 1;
		for (GreenPlayerCard c : cards) {
			System.out.println(i + ") " + c.name());
			cardMap.put(i, c);
			i++;
		}
		
		// TODO Won't bother now with bound checks, will do it later
		return cardMap.get(scanner.nextInt());
	}

	/**
	 * This method saves the game providing that the user enters a new file name or load the previous game.
	 */
	private void saveGame() {
		System.out.println("Provide one of the following:");
		System.out
				.println("1) A filename where your current game state will be "
						+ "saved (e.g. game1.json)");
		System.out
				.println("2) 's' to save to the same file (must be playing a previously saved game");
		System.out.println("3) Blank to go back to the main menu");

		String fileName = scanner.nextLine();
		if (UserOption.BACK.getOptionString().equals(fileName)) {
			return;
		}

		// To save to the same file we have to have a game file already open
		if (currentGameFileObj == null) {
			while (UserOption.SAVE.getOptionString().equals(fileName)) {
				System.out
						.println("You are playing a previously unsaved game - "
								+ "specify a filename where your game will be saved:");
				fileName = scanner.nextLine();
				if (UserOption.BACK.getOptionString().equals(fileName)) {
					return;
				}
			}
		}

		// Save to the same fileName
		if (UserOption.SAVE.getOptionString().equals(fileName)) {
			fm.save(currentGameFileObj);
			return;
		}

		// Save as (with a new filename)
		currentGameFileObj = new FileObject<Game>(controller.getGame(),
				fileName);
		fm.saveAs(currentGameFileObj, fileName);
	}

	/**
	 * This method loads a game.
	 * @return game as object
	 */
	private Optional<FileObject<Game>> loadGame() {
		System.out
				.println("\nWhich game to load? Game files are stored under "
						+ "src/resources - give only the filename (e.g. game1.json). Give "
						+ "a blank filename to go back to the main menu.");
		String fileName = scanner.nextLine();
		if (UserOption.BACK.getOptionString().equals(fileName)) {
			return Optional.empty();
		}

		Optional<FileObject<Game>> f = fm.open(fileName);
		while (!f.isPresent()) {
			System.out.println(fileName
					+ " doesn't exist! Try another one (or enter "
					+ "blank to go back to the main menu): ");
			fileName = scanner.nextLine();
			if (UserOption.BACK.getOptionString().equals(fileName)) {
				return Optional.empty();
			}
			f = fm.open(fileName);
		}

		return f;
	}

	/**
	 * This method displays the status of the board and the game.
	 */
	private void printGameStatus() {

		if (controller.gameExists()) {
			System.out.println(String.format("%-20s%10s%30s%30s%30s%10s", "Area",
					"Buildings", "Minions", "Trolls", "Demons", "Trouble"));

			for (BoardArea a : controller.getBoard()) {
				System.out.print(String.format("%-20s", a.getArea().name()));

				Player p = controller.getPlayerForColor(a.getBuildingOwner());
				if (p == null) {
					System.out.print(String.format("%10s", "NONE"));
				} else {
					System.out.print(String.format("%10s", p.getName()));
				}

				Map<Color, Integer> minions = a.getMinions();
				String minionsAll = UserOption.BACK.getOptionString();

				for (Map.Entry<Color, Integer> entry : minions.entrySet()) {
					Color color = entry.getKey();
					Integer value = entry.getValue();
					// TODO Change this once we put the players into a map
					minionsAll += String.format("%5s%1s%1s%1s", controller
							.getGame().getPlayerOfColor(color).getName(), "(",
							String.valueOf(value), ")");
				}
				System.out.format("%30s", minionsAll);

				System.out.format("%30s", String.valueOf(a.getTrollCount()));
				System.out.format("%30s", String.valueOf(a.getDemonCount()));

				System.out.format("%10s", a.hasTroubleMarker());

				System.out.println();
			}

			// Print player details
			Collection<Player> players = controller.getPlayers();
			for (Player p : players) {
				System.out.println(System.getProperty("line.separator"));
				System.out.print(p.getName());
				System.out.print(" has personality ");
				System.out.println(p.getPersonality().name());
				System.out.println(p.getName() + " is color "
						+ p.getColor());
				System.out.println(" And has "
						+ String.valueOf(p.getMinionCount())
						+ " minions left");
				System.out.println(" And has "
						+ String.valueOf(p.getBuildings())
						+ " buildings left");
				System.out.println(" And has "
						+ String.valueOf(p.getMoney())
						+ " money left");
				System.out.print(" And has Player cards: ");

				for (GreenPlayerCard c : p.getPlayerCards()) {
					System.out.print(c.name() + ", ");
				}
			}

			System.out.println(System.getProperty("line.separator"));
			Bank bank = controller.getBank();
			System.out.println(" Bank has balance of "
					+ Integer.toString(bank.getBalance()));
			System.out.print(" Current turn is ");
			System.out.println(controller.getPlayerOfCurrentTurn().getName());
			System.out.println(System.getProperty("line.separator"));

		} else {
			System.out.println(System.getProperty("line.separator"));
			System.out.println(" No game started yet!");
			System.out.println(System.getProperty("line.separator"));
		}

	}
	
	public AnkhMorporkArea getAreaChoice(Collection<AnkhMorporkArea> availableAreas, 
			String outputMsg, String inputMsg) {

		System.out.println(outputMsg);
		for (AnkhMorporkArea a : availableAreas) {
			System.out.println(a.getAreaCode() + ": " + a); 
		}
		
		scanner = new Scanner(System.in);
		System.out.print(inputMsg);

		int action = scanner.nextInt();
		while (AnkhMorporkArea.forCode(action) == null ) {
			System.out.println("Invalid selection.  "  + inputMsg);
			action = scanner.nextInt();
		}

		return AnkhMorporkArea.forCode(action);
	}
	
	/**
	 * Same as above but using boardAreas
	 * @param availableAreas
	 * @param outputMsg
	 * @param inputMsg
	 * @return
	 */
	public BoardArea getAreaChoice(Map<Integer, BoardArea> availableAreas, 
		
		String outputMsg, String inputMsg) {

		System.out.println(outputMsg);
		for (BoardArea a : availableAreas.values()) {
			System.out.println(a.getArea().getAreaCode() + ": " + a.getArea()); 
		}
		
		scanner = new Scanner(System.in);
		System.out.print(inputMsg);

		int action = scanner.nextInt();
		while (AnkhMorporkArea.forCode(action) == null ) {
			System.out.println("Invalid selection.  "  + inputMsg);
			action = scanner.nextInt();
		}

		return availableAreas.get(action);
	}
	
	/**
	 * Same as above but displays extra information
	 * @param availableAreas
	 * @param outputMsg
	 * @param inputMsg
	 * @param details
	 * @return
	 */
	public BoardArea getAreaChoice(Map<Integer, BoardArea> availableAreas,
			String outputMsg, String inputMsg, boolean details) {
		
		System.out.println(outputMsg);
		if(details) {
			for(BoardArea a: availableAreas.values()) {
				System.out.println(a.getArea().getAreaCode() + ": " + a.getArea());
				System.out.println("\tWith " + a.getDemonCount() + " demons");
				System.out.println("\tWith " + a.getTrollCount() + " trolls");
			
				Map<Color, Integer> minions = a.getMinions();
				for (Map.Entry<Color, Integer> entry : minions.entrySet()) {
					System.out.println("\tWith " + entry.getValue() + " from player " + entry.getKey());
				}
			}
		}
		System.out.print(inputMsg);
		scanner = new Scanner(System.in);
		int action = scanner.nextInt();
		while(availableAreas.get(action) == null) {
			System.out.print("Invalid selection: " + inputMsg);
		}
		return availableAreas.get(action);
	}
	
	/**
	 * Remove a troll, demon or minion from a baordArea
	 * @param trouble
	 * @param killer
	 */
	public void assinate(BoardArea trouble, Player killer) {
		scanner = new Scanner(System.in);
		
		// Display all assination options
	    if(trouble.getDemonCount() > 0) {
	    	System.out.println("\tPress d for demon");
	    }
	    if(trouble.getTrollCount() > 0) {
	    	System.out.println("\tPress t for troll");
	    }
	    // Display minions available for assination
		Map<Color, Integer> troubleMinions = trouble.getMinions();
		Iterator<Entry<Color, Integer>> it = troubleMinions.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<Color, Integer> pair = (Map.Entry<Color, Integer>)it.next();
	        System.out.println("\tType " + pair.getKey() + " for minion of player " + pair.getValue());
	    }
	    
	    String actionKill = null;
	    boolean removed = false;
	    while(!removed) {
	    	System.out.print("Choice: ");
	    	actionKill = scanner.nextLine();	
	    
	    	if(actionKill.equals("t") && trouble.getTrollCount() > 0){
	    		trouble.removeTroll();
	    		removed = true;
	    	} else if(actionKill.equals("d") && trouble.getDemonCount() > 0) {
	    		trouble.removeDemon();
	    		removed = true;
	    	} else {
	    		try {
	    			// Make sure minion being assinated is valid and isn't your own
	    			Player losingMinion = controller.getGame().getPlayerOfColor(Color.valueOf(actionKill));
	    			if(losingMinion != null &&
	    					Color.valueOf(actionKill) != killer.getColor() &&
	    					trouble.getMinionCountForPlayer(losingMinion) != 0) {
	    				
	    				trouble.removeMinion(losingMinion);
	    				removed = true;
	    			}
	    		}catch(IllegalArgumentException e) {
	    			continue;
	    		}
	    		
	    	}
	    }
	}
	


}
