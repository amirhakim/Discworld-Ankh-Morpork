package bootstrap;

public class Deck {
	
	private Card[] c;
	
	public Deck(Card[] c) {
		this.c = c;
	}
	
	public void test() {
		System.out.println("here");
		System.out.println(this.c[0].getClass());
		this.c[0].test();
	}
}
