package bootstrap;


import gameplay.GameplayTest;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;

import card.GreenCardTest;
import card.SymbolTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ SymbolTest.class, GreenCardTest.class, GameplayTest.class })
public class TestSuite {
	// nothing
}	