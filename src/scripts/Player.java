package scripts;

public class Player {

	Hand hand;
	
	int points;
	int meter = 0; // Meter Goes From 1 - 10

	public Player() {
		newHand();
		points = 10000;
	}
	public int getPoints(){ return points;}
	public void newHand(){
		fold();
		hand = new Hand();
	}
	
	public void fold(){
		hand = null;
	}
	
	public boolean check(){
		return true;
	}
	
	public void raise(int amt){
		points -= amt;
	}
	
	public Card[] getCurrentHand(){ return hand.getHand(); }
	

}


