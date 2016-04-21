package scripts;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	
	ArrayList<Card> deck = new ArrayList<>();
		
	public Deck(){
		make();
		for(Card card:deck){
			System.out.println("Card number: " + card.getNumber() + "\t Type: " + card.getSuite());
		}
		System.out.println(Card.id);
	}	
	
	public void shuffle(){
		Collections.shuffle(deck);
	}
	
	public Card deal(){
		return deck.remove(0);
	}
	
	public ArrayList<Card> make(){
		deck = new ArrayList<>();
		for(int i = 0; i < 52; i++){
			deck.add(new Card());
		}
		return deck;
	}
}
