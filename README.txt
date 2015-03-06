SOEN 6441 Project

Group 10 Memebers:
Ross Smith - 9072659
Katayoon Noparast - 7398387
Amir Hakim - 4050711
Ashar Hussain - 6386687
Georgios Kentritas - 27738226


FILES
-------------------------------------------

	src/main/java	::	main java classes
		bootstrap	::	main package
			MainAnkhMorporkStarter.java :: main class
			
		card
			Area.java	::	Represents area of game board
			Card.java	::	Interface for all game cards
			CityDeck	::	Deck representing Area card
			Deck.java	::	Parent for all decks of Cards
			PlayerCard.java	::	Represents the games player cards
			PersonalityDeck.java	::	Deck of personality cards
			PlayerDeck.java		::	Deck of player cards
			PersonalityCard.java	::	Represents the personality cards
			RandomEventCard.java	::	Represents Random Event game card
			RandomEventDeck.java	::	Deck of random event cards
		error
			InvalidGameStateException.java	::	Game exception
		gameplay
			Bank.java	::	Represents Bank object for the game
			Controller.java	::	Handles requests from views
			Game.java	::	Handles game set up and turns
			GameStatus.java	::	Enum for game status
			Player.java	::	Represents each player of the game
		io
			FileManager	::	
			FileObject	::	
			JSONFileManager	::
			TextUserInterface.java	::	Handles view for CLI
			UserOption 	::	Enum for user choices
		util			
			Color.java
		io
			FileManager.java
			FileObject.java
			JSONFileManager.java
	src/test/java	::	test java classes			
		bootstrap	::	main package
			BankTest.java	::	Test bank class
			CardTest.java	::	Test card (personality card)
			CityCardTest.java	::	Test card (city card)
			DeckTest.java	::	Test decks
			GameTest.java	::	Test game class
			PlayerTest.java	::	Test player class
			TestSuite.java	::	Test suite of all tests
		io
			FileManagerTest.java
	src/resources	::	IO files from game

INSTALL
-------------------------------------------
1. Clone the project at your machine
2. Make sure you have the m2e plugin installed, it's what Eclipse uses to integrate with Maven. 
If you don't have it search for it in the Eclipse Marketplace (Help -> Eclipse Marketplace, find it there and install it)
3. Import the project into Eclipse: File -> Import -> (General -> Existing Projects into Workspace) 
or (Maven -> Existing Maven Projects)

JAVADOCS
-------------------------------------------
http://162.209.98.223/soen6441/build1/
