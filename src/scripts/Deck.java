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
	}

	// Deals Top Card In The Deck
	public Card deal() {
		return deck.remove(0);
	}

	// Creates A Deck Of 52 Different Cards And Shuffles It
	public ArrayList<Card> make() {
		deck = new ArrayList<>();
		for (int i = 0; i < 52; i++) {
			deck.add(new Card());
		}
		shuffle();
		return deck;
	}

	public int cardNum() {
		return deck.size();
	}
}
