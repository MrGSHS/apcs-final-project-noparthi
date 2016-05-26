package blackjack;

/**
* Extension of BlackPlayer with logic
* for computers. 
* 
* @author  Deepak Moparthi, Wilson Zhu
* @version 1.0
* @since   2016-5-26
*/


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