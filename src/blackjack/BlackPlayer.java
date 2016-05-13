package blackjack;
import java.util.*;
import scripts.Card; 

public class BlackPlayer {
	private Card initShown1;
	private Card initShown2;
	private ArrayList<Card> hand;
	private int bet;
	private bet
	
	
	public BlackPlayer(){
		
	}
	public int calc(){
		int points=0;
		for (Card a:hand){
			
			if (a.getNumber()==14 && (11+points<=21))){
				points+=11;
			}
			else if ((a.getNumber()==14) && (11+points>21)) {
				points+=1;
			}
			else if (a.getNumber()<14 && a.getNumber()>10){
				points+=10;
				
			}
			else {
				points+=a.getNumber();
			}
			
		}
		return points;
	}




}
