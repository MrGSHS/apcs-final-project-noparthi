package scripts;

import java.util.ArrayList;

public class Table extends Game{
	ArrayList<Card> cardsOnTable;
	int maxBet = 0;
	
	public Table(){
		System.out.println("Table is initialized!");
		cardsOnTable = new ArrayList<>();
	}
	
	public void dealFlop(){
		cardsOnTable.add(getDeck().deal());
		cardsOnTable.add(getDeck().deal());
		cardsOnTable.add(getDeck().deal());
	}
	public void dealTurn(){ cardsOnTable.add(getDeck().deal()); }
	public void dealRiver(){ cardsOnTable.add(getDeck().deal()); }
	
	public ArrayList<Card> getCardsOnTable(){
		return cardsOnTable;
	}
	public int getMaxBet(){ return maxBet; }
}
