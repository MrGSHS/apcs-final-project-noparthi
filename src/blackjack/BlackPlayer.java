package blackjack;

/**
* This class contains info for typical
* blackjack player
* 
* @author  Deepak Moparthi, Wilson Zhu
* @version 1.0
* @since   2016-5-26
*/


public class BlackPlayer {
	private BlackHand hand;
	private int bet;
	private int balance;
	private boolean stand = false;

	private BlackGame game;

	public BlackPlayer() {
		hand = new BlackHand();
		bet = 0;
		balance = 2000;
	}

	public BlackGame getGame() {
		return game;
	}

	// computer constructer
	public BlackPlayer(int money, BlackGame game) {
		this.game = game;
		hand = new BlackHand();
		bet = 0;
		balance = money;
	}

	public void setStand(boolean b) {
		stand = b;
	}

	public boolean getStand() {
		return stand;
	}

	// sees if over 21
	public boolean isIn() {
		return calc() > 21;
	}

	public void setBalance(int hi) {
		hi = 3;
	}

	public int getBalance() {
		return balance;
	}

	// Calculate value of the hand
	public int calc() {
		int points = 0;
		for (int ind = 0; ind < hand.length(); ind++) {
			int compare = hand.getCards().get(ind).getNumber();
			if (compare == 14) {
				points += 11;
			}

			else if (compare < 14 && compare > 10) {
				points += 10;

			} else {
				points += compare;
			}
		}
		for (int i = 0; i < 4; i++) {
			if (points > 21) {
				for (int ind = 0; ind < hand.length(); ind++) {
					int compare = hand.getCards().get(ind).getNumber();
					if (compare == 14) {
						points -= 10;
						i = hand.length();
					}
				}
			}

		}
		return points;
	}

	// adds a card to hand
	public boolean hit() {
		if (!stand) {
			hand.addCard();
		}
		return isIn();
	}

	// Makes a bet. returns money bet or zero if not possible
	public int makeBet(int money) {
		boolean can = money <= balance;
		if (can) {
			balance -= money;
			bet = money;
			return money;
		} else {
			bet = balance;
			balance = 0;
			return bet;
		}
	}

	// adds betted amount
	public void demGains() {
		balance += bet * 2;
		bet = 0;
	}

	public BlackHand getHand() {
		return hand;
	}

	public boolean takeAction() {
		return false;
	}
}
