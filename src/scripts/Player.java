package scripts;

public class Player extends Deck {

	Card card1;
	Card card2;
	int points;

	public Player() {
		card1 = deal();
		card2 = deal();
		points = 10000;
	}

	public int handMeter(){	
		int meter = 1; // Meter Goes From 1 - 10
		if (card1.getNumber() == card2.getNumber()) {
			if (card1.getNumber() <= 5) meter = 5;
			else if (card1.getNumber() <= 10) meter = 7;
			else meter = 9;
		}
		return meter;
	}
}


