package bootstrap;

import gameplay.BankTest;
import gameplay.GameTest;
import gameplay.PlayerTest;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;

import card.CardTest;
import card.BoardTest;
import card.DeckTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ BankTest.class, CardTest.class, BoardTest.class,
		DeckTest.class, GameTest.class, PlayerTest.class })
public class TestSuite {
	// nothing
}