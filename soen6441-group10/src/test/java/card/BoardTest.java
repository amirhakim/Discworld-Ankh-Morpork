package card;

import static org.junit.Assert.*;
import gameplay.BoardArea;

import org.junit.*;

public class BoardTest {

	private BoardArea card;

	@BeforeClass
	public static void setUpClass() throws Exception {
		// Code executed before the first test method
	}

	@Before
	public void setUp() throws Exception {
		// Code executed before each test
		this.card = new BoardArea();
	}

}
