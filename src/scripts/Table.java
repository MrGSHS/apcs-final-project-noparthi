package scripts;

import java.util.ArrayList;

public class Table{
	ArrayList<Card> cardsOnTable;
	int maxBet;
	Deck deck;
	
	public Table(){
		deck = new Deck();
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
	public int getMaxBet(){ return maxBet; }
	
	public Deck getDeck(){ return deck; }
	public Table getTable(){ return this; }
}
