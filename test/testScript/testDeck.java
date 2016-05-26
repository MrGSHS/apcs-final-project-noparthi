package testScript;
import scripts.*;

public class testDeck {
	Deck deck;
	public testDeck(){
		deck = new Deck();
		for(Card c1 : deck.getDeck()){
			System.out.println("Number: " + c1.getNumber() + " Suite: " + c1.getSuiteValue());
		}
	}
	
	public static void main(String args[]){
		new testDeck();
	}
}
