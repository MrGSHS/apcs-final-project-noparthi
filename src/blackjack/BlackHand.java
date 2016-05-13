package blackjack;
import scripts.Card;
import scripts.Deck;
import java.util.*;
public class BlackHand {
	private static Deck deck1=new Deck();
	private ArrayList<Card> hand1;
	
	public BlackHand(){
		hand1= new ArrayList<Card>();
		hand1.add(deck1.deal());
		hand1.add(deck1.deal());
	}
	
	public ArrayList<Card> getCards(){
		return hand1;
		
	}
	public ArrayList<Card> addCard(Card card1){
		hand1.add(card1);
		return hand1;
	
	}
	public void restart(){
		deck1=new Deck();
	}
}