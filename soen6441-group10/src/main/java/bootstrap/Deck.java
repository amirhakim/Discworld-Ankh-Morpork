package bootstrap;

public class Deck {
	private Card[] cards;
	//private Card card;
	
	public Deck(Card c, int size) {
		this.cards = new Card[size];
//		this.card = c;
	}
	
	public void test() {
		
		System.out.println("1");
		System.out.println(this.cards[0].getTitle());
	}
	
	
}
