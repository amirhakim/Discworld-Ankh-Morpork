package bootstrap;


import gameplay.GameplayTest;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;

import card.GreenCardTest;
import card.PersonalitiesTest;
import card.SymbolTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ SymbolTest.class, GreenCardTest.class, GameplayTest.class, PersonalitiesTest.class})
public class TestSuite {
	// nothing
}	