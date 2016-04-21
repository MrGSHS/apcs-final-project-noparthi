package scripts;

public class Hand {
	

	Card card1;
	Card card2;

	int strength;

	public Hand() {
		card1 = Game.getDeck().deal();
		card2 = Game.getDeck().deal();
	}

	public Card[] getHand() {
		return new Card[] { card1, card2 };
	}
/*
	public int initalHandstrength() {
		// Check High Card
		if (card1.getNumber() <= 5 || card2.getNumber() <= 5)
			strength += 1;
		else if (card1.getNumber() <= 10 || card2.getNumber() <= 10)
			strength += 3;
		else
			strength += 5;
		// Check Straight Draws
		if (Math.abs(card1.getNumber() - card2.getNumber()) <= 4) {
			if (Math.abs(card1.getNumber() - card2.getNumber()) == 1)
				strength += 2;
			else
				strength++;
		}
		// Check Flush Draws
		if (card1.getSuite() == card2.getSuite())
			strength += 2;
		// Check Pocket Pairs
		if (card1.getNumber() == card2.getNumber()) {
			if (card1.getNumber() <= 5)
				strength = 6;
			else if (card1.getNumber() <= 11)
				strength = 8;
			else
				strength = 10;
		}
		return strength;
	}

	// Update Hand strength
	public int updateHandstrength() {
		return strength;
	}

	// Checks Probability For Such Hands (1-100)
	public int fourOfAKind() {
		return -1;
	}

	public int flush() {
		return -1;
	}

	public int straight() {
		return -1;
	}

	public int fullHouse() {
		return -1;
	}

	public int trips() {
		return -1;
	}

	public int twoPair() {
		return -1;
	}
	*/
}
