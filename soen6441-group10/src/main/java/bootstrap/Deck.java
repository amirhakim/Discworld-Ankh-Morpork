package bootstrap;

public class Deck {
	private Card[] cards;
	
	public Deck(Card c, int size) {
		cards=new Card[size];
		
		for (int i=0;i<size;i++) {	
			this.cards[i]=c.clone();
		}
	}
	
	
	
	
}
