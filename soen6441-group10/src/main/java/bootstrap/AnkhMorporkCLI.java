package bootstrap;

import java.util.Scanner;

public class AnkhMorporkCLI {

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
        		AnkhMorporkCLI.newGame();
        	} else if(action.equals("l")) {
        		System.out.println("WE HAVENT DONE THIS YET!!!");
        	
        	} else if(action.equals("s")) {
        		AnkhMorporkCLI.status();
        	
        	} else if(action.equals("q")){
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
        String playerName;
        
        System.out.println("Enter player ones name");
        playerName = scanner.nextLine();
        
        System.out.println("Enter number of players");
        numberOfPlayers = scanner.nextInt(); 
		
        
		if (controller.newGame(numberOfPlayers, playerName)) {
			System.out.println("Game Started");
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
			Player[] players = controller.getStatus();
			// Print players details
			for(int i=0; i<players.length; ++i) {
				System.out.print(players[i].getName());
				System.out.print(" has personality ");
				System.out.println(players[i].getPersonality().getCard());
			}
		} catch (Exception e){
			// Game status indicates game has not started.
			System.out.println("No Game is underway");
		}
	}
	
}
