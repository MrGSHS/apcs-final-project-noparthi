package scripts;

import java.util.ArrayList;

public class Table{
	
	private ArrayList<Card> cardsOnTable;
	private Deck deck = new Deck();
	
	public Table(){
		cardsOnTable = new ArrayList<>();
	}
	
	public void dealFlop(){
		cardsOnTable.add(deck.deal());
		cardsOnTable.add(deck.deal());
		cardsOnTable.add(deck.deal());
	}
	public void dealTurn(){ cardsOnTable.add(deck.deal()); }
	public void dealRiver(){ cardsOnTable.add(deck.deal()); }
	
	public ArrayList<Card> getCardsOnTable(){
		return cardsOnTable;
	}
	
	public Deck getDeck(){ return deck; }
	
}
