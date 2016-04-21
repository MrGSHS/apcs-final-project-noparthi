package scripts;

import java.util.ArrayList;

public class Table{
	ArrayList<Card> cardsOnTable;
	int maxBet;
	Deck deck = new Deck();
	static int tables = 0;
	int pot = 0;
	public Table(){
		tables ++;
		System.out.println(tables);
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
	
}
