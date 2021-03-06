package blackjack;
import scripts.Card;
import scripts.Deck;
import java.util.*;

/**
* This class contains info stored in a 
* black jack hand.
* 
* @author  Deepak Moparthi, Wilson Zhu
* @version 1.0
* @since   2016-5-26
*/


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
	
	public int sumOfCards(){
		int tot = 0;
		for(Card card : hand1)
		{
			tot += card.getNumber();
		}
		return tot;
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
	public String toString(){
		String cards="{";
		for(Card c: hand1){
			
			if(c.getNumber()==11)
				cards+="Jack";
			else if(c.getNumber()==12)
				cards+="Queen";
			else if(c.getNumber()==13)
				cards+="King";
			else if(c.getNumber()==14)
				cards+="Ace";
			else
				cards+=c.getNumber();
			cards+=" of ";
			cards+=c.getSuiteValue()+"... ";
		}
		cards+="\b}";
		return cards;
	}
}