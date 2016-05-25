package blackjack;

import scripts.Deck;

public class BlackRound {
	private static int roundNumber = 0;
	private int bet;
	private Deck use;
	private boolean whoWins;

	public BlackRound(int playerBet) {
		System.out.println("NEW ROUND!!!!!!");
		bet = playerBet;
		use = new Deck();
		roundNumber++;
	}

	public int getRound() {
		return roundNumber;
	}

}
