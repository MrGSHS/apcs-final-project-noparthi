package scripts;

public class Player extends Hand{

	Hand hand;
	
	int points;
	int meter = 0; // Meter Goes From 1 - 10

	public Player() {
		newHand();
		points = 10000;
	}
	public int getPoints(){ return points;}
	public void newHand(){
		hand = new Hand();
	}

}


