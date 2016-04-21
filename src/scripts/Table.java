package scripts;

import java.util.ArrayList;

public class Table extends Deck{
	//TODO create a table
	ArrayList<Card> cardsOnTable;
	
	public Table(){
		cardsOnTable = new ArrayList<>();
	}
	
	public void dealFlop(){
		cardsOnTable.add(deal());
		cardsOnTable.add(deal());
		cardsOnTable.add(deal());
	}
	public void dealTurn(){ cardsOnTable.add(deal()); }
	public void dealRiver(){ cardsOnTable.add(deal()); }
	
	public ArrayList<Card> getCardsOnTable(){
		return cardsOnTable;
	}
}
