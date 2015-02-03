package bootstrap;
import org.junit.runners.Suite;
import org.junit.runner.RunWith;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	BankTest.class,
	CardTest.class,
	CityCardTest.class,
	DeckTest.class,
	GameTest.class,
	PlayerTest.class	
})
public class TestSuite {
  //nothing
}