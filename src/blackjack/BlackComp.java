package blackjack;

public class BlackComp extends BlackPlayer {
	
	public boolean takeAction() {
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