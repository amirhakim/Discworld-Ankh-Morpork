package io;

import gameplay.Bank;
import gameplay.Controller;
import gameplay.Game;
import gameplay.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import util.Color;
import card.BoardArea;
import card.player.GreenPlayerCard;

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
				} else if (action
						.equals(UserOption.NEXT_TURN.getOptionString())) {
					controller.nextTurn();
				} else if (action.equalsIgnoreCase(UserOption.GAME_STATUS
						.getOptionString())) {
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
						+ String.valueOf(p.getAmount())
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
			System.out.println(controller.getCurrentTurn().getName());
			System.out.println(System.getProperty("line.separator"));

		} else {
			System.out.println(System.getProperty("line.separator"));
			System.out.println(" No game started yet!");
			System.out.println(System.getProperty("line.separator"));
		}

	}
	
	public BoardArea getAreaChoice(Map<Integer, BoardArea> gameBoard, String outputMsg, String inputMsg) {
		System.out.println(outputMsg);
		for(BoardArea ba : gameBoard.values()) {
			System.out.println(ba.getArea().getAreaCode() + ": " + ba.getArea());
		}
		
		Scanner scanner = new Scanner(System.in);
		System.out.print(inputMsg);
		int action = scanner.nextInt();
		return gameBoard.get(action);
		
	}

}
