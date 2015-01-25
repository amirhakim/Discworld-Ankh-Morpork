package bootstrap;

import java.io.*;
import java.util.Scanner;

public class textUserInterface {

	static Controller controller = new Controller();
	
	public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int numberOfPlayers;
        String playerName;
        System.out.println("Enter player ones name");
        playerName = scanner.nextLine();
        System.out.println("Enter number of players");
        numberOfPlayers = scanner.nextInt(); 
		
		// TODO Auto-generated method stub
		if (controller.newGame(numberOfPlayers)) {
			System.out.println("Game Started");
		} else {
			System.out.println("Sorry only 2 to 4 players can play this game!");
		}
		

	}

}
