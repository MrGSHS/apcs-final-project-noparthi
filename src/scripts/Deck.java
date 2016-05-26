package scripts;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

	private ArrayList<Card> deck = new ArrayList<>();

	public Deck() {
		make();
		System.out.println("Number of cards in deck: " + deck.size());
	}

	// Shuffles The Deck
	public void shuffle() {
		Collections.shuffle(deck);
		Collections.shuffle(deck);
		Collections.shuffle(deck);
	}

	// Deals Top Card In The Deck
	public Card deal() {
		return deck.remove(0);
	}
	
	public ArrayList<Card> getDeck(){
		return deck;
	}

	// Creates A Deck Of 52 Different Cards And Shuffles It
	public ArrayList<Card> make() {
		deck = new ArrayList<Card>();
		for (int sui = 0; sui < 4; sui++) {
			for (int num = 2; num < 15; num++) {
				deck.add(new Card(num,sui));
			}
		}
		shuffle();
		return deck;
	}

	public int cardNum() {
		return deck.size();
	}
}
