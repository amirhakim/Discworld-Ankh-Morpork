package bootstrap;


import gameplay.GameplayTest;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;

import card.AnkhMorporkAreaTest;
import card.GreenCardTest;
import card.PersonalitiesTest;
import card.RandomEventTest;
import card.SymbolTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ SymbolTest.class, GreenCardTest.class, GameplayTest.class, PersonalitiesTest.class,AnkhMorporkAreaTest.class, RandomEventTest.class})
public class TestSuite {
	// nothing
}	