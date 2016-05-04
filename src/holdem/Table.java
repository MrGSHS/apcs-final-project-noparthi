package holdem;

import java.util.ArrayList;

import scripts.Card;
import scripts.Deck;

public class Table{
	
	private ArrayList<Card> cardsOnTable;
	private Deck deck;
	
	public Table(){
		deck = new Deck();
		cardsOnTable = new ArrayList<>();
	}
	
	//Deals Flop
	public void dealFlop(){
		cardsOnTable.add(deck.deal());
		cardsOnTable.add(deck.deal());
		cardsOnTable.add(deck.deal());
	}
	//Deals Turn
	public void dealTurn(){ cardsOnTable.add(deck.deal()); }
	//Deals River
	public void dealRiver(){ cardsOnTable.add(deck.deal()); }
	
	public ArrayList<Card> getCardsOnTable(){
		return cardsOnTable;
	}
	
	public Deck getDeck(){ return deck; }
	
}
