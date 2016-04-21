package scripts;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Player {
	
	Game game;
	Hand hand;
	
	int points;
	int meter = 0; // Meter Goes From 1 - 10

	public Player(Game game) {
		this.game = game;
		newHand();
		points = 10000;
	}
	
	public int getPoints() {
		return points;
	} 

	public void newHand() {
		hand = new Hand(game, game.getTable().getDeck().deal(), game.getTable().getDeck().deal());
	}

	public void fold() {
		hand = null;
	}

	public boolean check() {
		return true;
	}

	public boolean raise(int amt) {
		if(amt > points){
			game.getRound().setPot(points);
			points = 0;
			return false;
		}
		game.getRound().setPot(game.getRound().getPot() + amt);
		points -= amt;
		return true;
	}
	
	public Card[] getCurrentHand() {
		return hand.getHand();
	}
}
