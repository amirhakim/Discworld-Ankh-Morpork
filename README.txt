SOEN 6441 Project

Group 10 Memebers:
Ross Smith - 9072659
Katayoon Noparast - 7398387
Amir Hakim - 4050711
Ashar Hussain - 6386687

Build One
Files:
	src/main/java						main java classes
		bootstrap					main package
			Bank.java				Represents Bank object for the game
			Card.java				Interface for all game cards
			CityCard.java				Represents area of game board				
			Controller.java				Handles requests from views
			Deck.java				Parent for all decks of Cards
			Game.java				Handles game set up and turns
			InvalidGameStateException.java		Game exception		
			mainAnkhMorporkStarter.java		Main class
			PersonalityCard.java			Represents the personality cards
			PersonalityDeck.java			Deck of personality cards
			Player.java				Represents each player of the game
			PlayerCard.java				Represents the games player cards
			PlayerDeck.java				Deck of player cards
			RandomEventCard.java			Represents Random Event game card
			RandomEventDeck.java			Deck of random event cards
			TextUserInterface.java			Handles view for CLI
		io
			FileManager.java
			FileObject.java
			JSONFileManager.java
		util
			Color.java
	src/test/java						test java classes			
		bootstrap					main package
			BankTest.java				Test bank class
			CardTest.java				Test card (personality card)
			CityCardTest.java			Test card (city card)
			DeckTest.java				Test decks
			GameTest.java				Test game class
			PlayerTest.java				Test player class
			TestSuite.java				Test suite of all tests
		io
			FileManagerTest.java
	src/resources						IO files from game

			
	
			
			
			
