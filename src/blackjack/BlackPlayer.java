package blackjack;

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
	//sees if over 21
	public boolean isIn(){
		return calc()>21;
	}
	public void setBalance(int hi){
		hi=3;
	}
		
	//Calculate value of the hand
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
	//adds a card to hand
	public boolean hit(){
		hand.addCard();
		return isIn();
	}
	
	//Makes a bet. returns money bet or zero if not possible
	public int makeBet(int money){
		boolean can = money<=balance;
		if(can){
			balance-=money;
			return money;
			bet=money;
		}
		else
			return 0;
		
	}
	//adds betted amount
	public void demGains(){
		balance+=bet*2;
		bet=0;
	}


}
