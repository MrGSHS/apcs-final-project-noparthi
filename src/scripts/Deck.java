package scripts;

import java.util.ArrayList;

public class Deck {
	
	ArrayList<Card> cards = new ArrayList<>();
		
	public Deck(){
		for(int i = 0; i < 52; i++){
			cards.add(new Card());
		}
		for(Card card:cards){
			System.out.println("Card number: " + card.getNumber() + "\t Type: " + card.getSuite());
		}
		System.out.println(Card.id);
	}	
}
