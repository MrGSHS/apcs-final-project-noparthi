package holdem;

import scripts.Card;

/**
 * This player class contains actions and variables that any player uses.
 * 
 * @author Jerry Zhou, Jonathan Xue
 * @version 1.0
 * @since 2016-5-26
 */

public class Player {

	private HoldemGame game;
	private int position;
	private Hand hand;

	private int points;
	private int betAmount;
	private int pointsInvested;

	private String name;

	private boolean folded;
	private boolean bigBlind;
	private boolean smallBlind;
	private boolean firstAction = false;
	private boolean checkBoolean = false;
	private boolean raiseBoolean = false;
	private boolean callBoolean = false;
	private boolean isAllIn = false;
	private boolean isTurn = false;

	public Player(HoldemGame game, int position) {
		this.game = game;
		this.position = position;
		points = 1000;
	}

	// Overridden By Computer
	public void takeAction() {
	}

	public String getName() {
		return name;
	}

	public boolean isTurn() {
		return isTurn;
	}
	
	public boolean isAllIn(){
		return isAllIn;
	}

	public boolean getCheckBoolean() {
		return checkBoolean;
	}

	public boolean getRaiseBoolean() {
		return raiseBoolean;
	}

	public boolean getCallBoolean() {
		return callBoolean;
	}

	public boolean getFirstAction() {
		return firstAction;
	}

	public int getPosition() {
		return position;
	}

	public int getPoints() {
		return points;
	}

	public int getPointsInvested() {
		return pointsInvested;
	}

	public Hand getHand() {
		return hand;
	}

	public Card[] getCurrentHand() {
		return hand.getHand();
	}

	public boolean isBigBlind() {
		return bigBlind;
	}

	public boolean isSmallBlind() {
		return smallBlind;
	}

	public boolean isFolded() {
		return folded;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFirstAction(boolean sfa) {
		firstAction = sfa;
	}

	public void setIsTurn(boolean sit) {
		isTurn = sit;
	}
	
	public void setIsAllIn(boolean siai){
		isAllIn = siai;
	}

	public void setPoints(int num) {
		points = num;
	}

	public void setBigBlind(boolean bb) {
		bigBlind = bb;
	}

	public void setSmallBlind(boolean sb) {
		smallBlind = sb;
	}

	public void setPointsInvested(int num) {
		pointsInvested = num;
	}

	public void resetPointsInvested() {
		pointsInvested = 0;
	}

	public void newHand() {
		hand = new Hand(game, game.getTable().getDeck().deal(), game.getTable().getDeck().deal());
	}

	// Fold Method
	// Reset The Action Booleans
	public void resetActionBoolean() {
		checkBoolean = false;
		raiseBoolean = false;
		callBoolean = false;
	}

	public void fold() {
		folded = true;
		isTurn = false;
		betAmount = 0;
		resetActionBoolean();
		game.isRoundActive();
	}

	public void unFold() {
		folded = false;
	}

	// Check Method
	public void check() {
		resetActionBoolean();
		betAmount = 0;
		checkBoolean = true;
		isTurn = false;
		firstAction = false;
	}

	// Call Method
	public void call() {
		int maxBet = 0;
		int bet = 0;
		// Gathers Largest Bet Amount To Call
		for (Player p : game.getPlayers()) {
			bet = p.getBetAmount();
			if (bet > maxBet)
				maxBet = bet;
		}
		maxBet -= betAmount;
		game.getRound().setPot(game.getRound().getPot() + maxBet);

		// Sets Points Invested, BetAmount And Removes Points
		betAmount += maxBet;
		pointsInvested += maxBet;
		points -= maxBet;

		// Reset Stuff
		resetActionBoolean();
		callBoolean = true;
		isTurn = false;
		firstAction = false;
		if(points <= 0){
			isAllIn = true;
		}
	}

	// Raise Method
	public boolean raise(int amt) {
		// Subtracts Bet Amount If Made Earlier From This Time's Bet
		int swapAmt = amt;
		amt -= betAmount;

		// Guarantees That Raise Is Legal
		if (amt >= game.getRound().getMinBet()) {
			// All-In
			if (amt >= points) {
				isAllIn = true;
				betAmount = points;
				game.getRound().setPot(game.getRound().getPot() + points);
				pointsInvested += points;
				points = 0;

				resetActionBoolean();
				raiseBoolean = true;
				isTurn = false;
				firstAction = false;
				return true;
			}
			// Not All-In
			betAmount = swapAmt;
			game.getRound().setPot(game.getRound().getPot() + amt);
			game.getRound().setMinBet(amt);
			pointsInvested += amt;
			points -= amt;

			resetActionBoolean();
			raiseBoolean = true;
			isTurn = false;
			firstAction = false;
			return true;
		}
		// Not Legal Message
		else {
			System.out.println("Must raise at least something greater than or equal to " + game.getRound().getMinBet());
			call();
			return false;
		}
	}

	public void setBetAmount(int amt) {
		betAmount = amt;
	}

	public int getBetAmount() {
		return betAmount;
	}
}
