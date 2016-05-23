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
	public ArrayList<Card> addCard(){
		hand1.add(deck1.deal());
		return hand1;
	}
	public int length(){
		return hand1.size();
		
	}
	
	public void restart(){
		deck1=new Deck();
	}
	public void reset(){
		hand1= new ArrayList<Card>();
		hand1.add(deck1.deal());
		hand1.add(deck1.deal());
	}
	
}