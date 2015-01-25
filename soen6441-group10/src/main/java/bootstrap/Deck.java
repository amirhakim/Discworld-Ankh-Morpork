package bootstrap;

public class Deck {
	private Card[] cards;
	//private Card card;
	
	public Deck(Card c, int size) {
		cards=new Card[size];
		for (int i=0;i<size;i++)
		{	
			this.cards[i]=c.clone();
		}
		//this.cards = c.clone();
//		this.card = c;
	}
	
	public void test() {
		
		System.out.println("1");
		this.cards[0].setTitle("zero");
		this.cards[1].setTitle("one");
		System.out.println(this.cards[0].getTitle());
		System.out.println(this.cards[1].getTitle());
	}
	
	
}
