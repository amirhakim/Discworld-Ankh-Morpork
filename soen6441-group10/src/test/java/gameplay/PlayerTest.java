package gameplay;

import static org.junit.Assert.*;
import gameplay.Player;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlayerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	    }

	  	
	Player player = new Player();
	
	@Test
	
	/*
	 * Checks validity of the name
	 */
	
	public void nameValidityTest() {
			
	String name = "Player";
	//boolean isValid = player.setName(name);
	assertNotNull("Name hasn't been entered!", name.equals(null));
	assertTrue("The name is not a valid one!", player.setName(name));
		
	}
	
	
	@Test
	
	/*
	 * Checks that the amount does'nt exceed of bounds
	 */
	public void amountBoundryCheck() {
		int incAmount = 25;
		int decAmount = 10;
		assertTrue("The amount passed the upper boundry!", player.increaseMoney(incAmount));
		
		assertTrue("The amount passed the lower boundry!", player.decreaseMoney(decAmount));
		
	}
	
	
	@Test
	
	/*
	 * Checks that the minions do'nt exceed of bounds
	 */
	public void minionBoundryCheck() {
		
		assertTrue("The number of minions passed the lower boundry!", player.decreaseMinions());
		assertEquals("Not equals!", player.getMinionCount(),11);
		assertTrue("The number of minions passed the upper boundry!", player.increaseMinions());

	}
	

	  @After
	    public void tearDown() throws Exception {
	        // Code executed after each test  
	    	this.player = null;
	    }
	

}

