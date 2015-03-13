SOEN 6441 Project
Group 10 Memebers:

Ross Smith - 9072659
Katayoon Noparast - 7398387
Amir Hakim - 4050711
Ashar Hussain - 6386687
Georgios Kentritas – 27738226

FILES:
-------------------------------------------
src/main/java :: main java classes
		Card
				Card: This is a marker interface that all (enumerated) card types used in the game must implement.
				Deck: This class represents different decks used throughout the game. Underlying deck structures are typically mutated/modified in-place
			Card/City
				AnkhMorporkArea: An enumeration of all the areas available in the game. Note that this area 
				CityAreaCard: This is a wrapper around the board areas to hold the state of a city card. 
			Card/Personality
				WinningCondition: A functional interface that captures the possible winning condition checks.
				PersonalityCard: An enumeration of the Personality Cards available in the game.
				PersonalityDeck: implements deck of personality cards of the game.
			Card/ Player
				Symbol: Enum's for each symbol. 
				GreenPlayerCard: That is the Enum for player cards. 
				PlayerDeck: This class implements deck of player cards of the game.
				DiscardPile: Represents the discard pile of the game. 
			Card/Random
				RandomEventDeck: implements the deck of Random Event cards. 
				RandomEventCard: This is the ENUM for random event cards.
		GamePlay
				Bank: represents the bank of game.
				BoardAreas: implements areas on the board.
				Controller: This class represents the Controller layer of MVC pattern.
				Die: implement one global point of access to determine user’s turn. 
				Game: represents the bulk of the actions available in the game. 
				GameStatus: Enum for game status
				Player: represents the players participating in the game, including the pieces they hold as well as their hands.
		IO
				TextUserInterface: makes a command line interface to communicate with the players.
				FileObject: encapsulates an object and the filename it is mapped to for saving/loading.
				JSONFileManager: utility class for loading and saving an object's state from and to files in JSON format.
		Util/Color
				Color: ENUM used to identify a player's color uniquely.

src/test/java :: test java classes

		test/bootstrap 
					Tests main package
		test/card
					CardTest.java :: Tests cards 
					DeckTest.java :: Tests decks
					GreenCardTest.java :: Tests player’s green cards
					SymbolTest.java :: Tests card symbols
		test/gameplay
					BankTest.java :: Tests bank class
					GameTest.java :: Test game class
					PlayerTest.java :: Test player class
		test/io
					TestSuite.java :: Test suite of all tests


INSTALL
-------------------------------------------
1. Clone the project at your machine
2. Make sure you have the m2e plugin installed, it's what Eclipse uses to integrate with Maven.
If you don't have it search for it in the Eclipse Marketplace (Help -> Eclipse Marketplace, find it there and install it)
3. Import the project into Eclipse: File -> Import -> (General -> Existing Projects into Workspace)
or (Maven -> Existing Maven Projects)

JAVADOCS
-------------------------------------------
http://162.209.98.223/soen6441/build2/
