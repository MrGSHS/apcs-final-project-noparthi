package blackjack;

import scripts.Card;

public class BlackComp extends BlackPlayer {
	
	public boolean takeAction() {
		int total = 0;
		for (Card card : getHand().getCards()) {
			total += card.getNumber();
		}
	
		if (calc() >= 17) {
			stand();
			return true;
		} else {
			hit();
			return false;
		}
	}

	public void stand() {
		setStand(true);
	}
}