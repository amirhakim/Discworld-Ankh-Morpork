
package bootstrap;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

public class TextUserInterface {

	 Controller controller = new Controller();
	 Scanner scanner;
	
	public void StartGame() {
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
        		this.newGame();
        	} else if(action.equals("l")) {
        		System.out.println("WE HAVENT DONE THIS YET!!!");
        	
        	} else if(action.equals("s")) {
        		this.status();
        	
        	} else if(action.equals("q")){
        		System.out.println("Goodbye");
        		return;
        	}
        }
        

	}

	/*
	 * Start a new game.
	 */
	public  void newGame() {
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
			
			
			controller.simulate();
			
			while(true) {
				System.out.println("t for next turn, e to end, s to save, l to load, s to display Game's Status");
				String action = scanner.nextLine();
				if(action.equals("e")) {
					return;
				} else if(action.equals("t")) {
					controller.nextTurn();
				} else if(action.equalsIgnoreCase("s")){
					this.status();
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
	public  void status() {
		// If game has not been initiated catch error.
		System.out.println(String.format("%-20s%10s%30s%30s%30s%10s", "Area","Buildings","Minions","Trolls","Demons","Trouble"));
		if(controller.gameExists()) {
			
			for(CityCard c: controller.getCities()) {
				System.out.print(String.format("%-20s",c.getTitle()));
				Player p = c.getBuilding();
				if(p == null) {
					System.out.print(String.format("%10s", "NON"));
				} else {
					//System.out.print(" Building by: ");
					System.out.print(String.format("%10s", p.getName()));
				}
				Map<Player, Integer> minions = c.getMinions();
				String minionsAll = "";
				for (Map.Entry<Player, Integer> entry : minions.entrySet()) {
				    Player key = entry.getKey();
				    Integer value = entry.getValue();
				    minionsAll+= String.format("%5s%1s%1s%1s",key.getName(),"(",String.valueOf(value),")");				    
				}
				System.out.format("%30s",minionsAll);
				
				System.out.format("%30s",String.valueOf(c.getTrolls()));
				System.out.format("%30s",String.valueOf(c.getDemons()));
				
				//System.out.print(" Has Trouble? ");
				System.out.format("%10s",c.hasTroubleMaker());
				
				System.out.println();
				
			}
				
			Player[] players = controller.getPlayers();
			// Print players details
			for(int i=0; i<players.length; ++i) {
			    System.out.println(System.getProperty("line.separator"));
				System.out.print(players[i].getName());
				System.out.print(" has personality ");
				System.out.println(players[i].getPersonality().getTitle());
				System.out.println(" And has " + String.valueOf(players[i].getMinions()) + " minions left");
				System.out.println(" And has " + String.valueOf(players[i].getBuildings()) + " buildings left");
				System.out.println(" And has " + String.valueOf(players[i].getAmount()) + " money left");
			    System.out.println(System.getProperty("line.separator"));


				for(PlayerCard c: players[i].getPlayerCards()) {
					System.out.println("PLayer card: " + c.getTitle());
				}

				for(PlayerCard c: players[i].getPlayerCards()) {
					System.out.println("PLayer card: " + c.getTitle());
				}

				for(PlayerCard c: players[i].getPlayerCards()) {
					System.out.println("PLayer card: " + c.getTitle());
				}
			}
		    System.out.println(System.getProperty("line.separator"));	
			Bank bank = controller.getBank();
			System.out.println(" Bank has balance of " + Integer.toString(bank.getBalance()));
			System.out.print(" Current turn is ");
			System.out.println(controller.getCurrentTurn().getName());
		    System.out.println(System.getProperty("line.separator"));

		} else {
		    System.out.println(System.getProperty("line.separator"));
			System.out.println(" No game started");
		    System.out.println(System.getProperty("line.separator"));

		}
	}
	
}
