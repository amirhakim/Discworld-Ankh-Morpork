package card;

import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import card.Deck;
import card.personality.PersonalityCard;
import card.personality.PersonalityDeck;

public class DeckTest {

	private Deck<PersonalityCard> personalityDeck;

	@BeforeClass
	public static void setUpClass() throws Exception {
		// Code executed before the first test method
	}

	@Before
	public void setUp() throws Exception {
		// Code executed before each test
		this.personalityDeck = new PersonalityDeck();
	}

	@Test
	public void testDeckShuffle() {
		// Create a large array of decks
		int size = 1000;
		PersonalityDeck[] pd = new PersonalityDeck[size];
		for (int i = 0; i < pd.length; ++i) {
			pd[i] = new PersonalityDeck();
		}

		// Get the last card title in this array of decks
		String lastCardTitle = pd[0].drawCard().get().getTitle();

		int count = 1;
		for (int i = 1; i < pd.length; ++i) {
			// Get the last card of each deck
			PersonalityCard tmp = pd[i].drawCard().get();
			// If it is the same as the last of the first, increase count
			if (lastCardTitle.equals(tmp.getTitle())) {
				count++;
			}
		}
		// If count is the same size of array of decks, it isnt being shuffled.
		assertFalse(count == size);

	}

	@After
	public void tearDown() throws Exception {
		// Code executed after each test
		this.personalityDeck = null;
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		// Code executed after the last test method
	}
}
