package bootstrap;

import java.io.*;
import java.util.Scanner;

public class textUserInterface {

	static Controller controller = new Controller();
	static Scanner scanner;
	
	public static void main(String[] args) {
		System.out.println("~~~~~~~~~~~~~~~~~~~~");
    	System.out.println("Welcome to our game!");
		System.out.println("~~~~~~~~~~~~~~~~~~~~");
		
        scanner = new Scanner(System.in);
        String action;
        
        // Loop until quit is pushed.
        while(true){
        	System.out.println("Enter n for new game, l for load, s for status, q to quit");

        	action = scanner.nextLine();
        	if(action.equals("n")) { 	
        		textUserInterface.newGame();
        	} else if(action.equals("l")) {
        		System.out.println("WE HAVENT DONE THIS YET!!!");
        	
        	} else if(action.equals("s")) {
        		textUserInterface.status();
        	
        	} else if(action.equals("q")){
        		System.out.println("Goodbye");
        		return;
        	}
        }
        

	}

	/*
	 * Start a new game.
	 */
	public static void newGame() {
		// Get players name.
		// Get number of players.
        int numberOfPlayers;
        
        System.out.println("Enter number of players");
        numberOfPlayers = scanner.nextInt(); 
        scanner.nextLine();
        String[] playerNames = new String[numberOfPlayers];
        
        
        for(int i=0;i<numberOfPlayers;++i){
            System.out.println("Enter player " + String.valueOf(i) + " name");
            playerNames[i] = scanner.nextLine();	
        }        
     
    	
        
		if (controller.newGame(numberOfPlayers, playerNames)) {
			System.out.println("Game Started");
			while(true) {
				System.out.println("t for next turn, e to end, s to save, l to load");
				String action = scanner.nextLine();
				if(action.equals("e")) {
					return;
				} else if(action.equals("t")) {
					controller.nextTurn();
				}
			
			}
			
		} else {
			// Too many or too few players in game.
			System.out.println("Sorry only 2 to 4 players can play this game!");
		}
		
	}
	
	/*
	 * Display the status of the board and the game
	 */
	public static void status() {
		// If game has not been initiated catch error.
		try {
			Player[] players = controller.getPlayers();
			// Print players details
			for(int i=0; i<players.length; ++i) {
				System.out.print(players[i].getName());
				System.out.print(" has personality ");
				System.out.println(players[i].getPersonality().getTitle());
			}
			
			Bank bank = controller.getBank();
			System.out.println("Bank has balance of " + Integer.toString(bank.getBalance()));
			System.out.print("Current turn is ");
			System.out.println(controller.getCurrentTurn().getName());
			
		} catch (Exception e){
			// Game status indicates game has not started.
			System.out.println("No Game is underway");
		}
	}
	
}
