package scripts;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	
	private ArrayList<Card> deck = new ArrayList<>();
	public Deck(){
		make();
		System.out.println("Number of cards in deck: " + deck.size());
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
		shuffle();
		return deck;
	}
	
	public int cardNum(){return deck.size();}
}
