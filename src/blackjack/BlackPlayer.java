package blackjack;
import java.util.*;
import scripts.Card; 

public class BlackPlayer {
	private BlackHand hand;
	private static int bet;
	private static int record=0;
	private int balance;
	
	public BlackPlayer(){
		hand= new BlackHand();
		bet=0;
		balance=2000;
	}
	public int calc(){
		int points=0;
		for (int ind=0;ind<hand.length();ind++){
			int compare=hand.getCards().get(ind).getNumber();
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
	public int makeBet(int money){
		boolean can = money<=balance;
		if(can){
			balance-=money;
			return money;
		}
		else{
			int temp = balance;
			balance=0;
			return temp;
		}
		
		
	}
	public void demGains(int money){
		balance+=money;		
	}


}
