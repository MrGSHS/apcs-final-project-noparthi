package blackjack;
import java.util.*;
import scripts.Card; 

public class BlackPlayer {
	private BlackHand hand;
	private static int bet;

	
	public BlackPlayer(){
		hand= new BlackHand();
		bet=0;
	}
	public int calc(){
		int points=0;
		for (Card a:hand){
			int compare=a.getNumber();
			if (compare==14){
				points+=11;
			}
			
			else if (compare<14 && compare>10){
				points+=10;
				
			}
			else {
				points+=compare;
			}
			if ((compare==14) && (points>21)) {
				points-=10;
			}
			
		}
		return points;
	}




}
