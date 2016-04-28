package scripts;

public class Round {

	private static int roundNumber = 0;
	private Game game;

	private int pot;
	private int minBet;
	/*
	 * 0 = pre flop 1 = pre turn 2 = pre river 3 = post river
	 */
	public int stageOfRound;

	public Round(Game game) {
		pot = 0;
		minBet = 0;
		stageOfRound = 0;
		this.game = game;
		roundNumber++;
		game.getUser().newHand();
		game.getComputer().newHand();
	}

	public int getRoundNumber() {
		return roundNumber;
	}

	public int getPot() {
		return pot;
	}

	public int getMinBet() {
		return minBet;
	}

	public void setPot(int amt) {
		pot = amt;
	}

	public void setMinBet(int amt) {
		minBet = amt;
	}

	public void preFlop() {
		game.getDisplay().update();
		game.takeAnte();
		// Prints Out Hand Before Flop, Then Deals Flop
		setMinBet(game.getBigBlind());

	}

	public void preTurn() {
		game.getDisplay().update();
		minBet = 500;

	}

	public void preRiver() {
		game.getDisplay().update();
		minBet = 500;
	}

	public void postRiver() {
		game.getDisplay().update();
		minBet = 500;
	}

	public void moveOn() {
		game.getDisplay().update();
		//Skips If Someone Had Raised, And Resets All Action Booleans Back To Null
		if(game.getComputer().getRaiseBoolean()||game.getUser().getRaiseBoolean()){ 
			game.getComputer().resetActionBoolean();
			game.getUser().resetActionBoolean();
			return;}

		//Proceeds To Next Round If None Of The Above Are True
		if (stageOfRound == 0) {
			stageOfRound++;
			System.out.println("Stage: " + stageOfRound);
			game.getTable().dealFlop();
			preTurn();
		} else if (stageOfRound == 1) {
			stageOfRound++;
			System.out.println("Stage: " + stageOfRound);
			game.getTable().dealTurn();
			preRiver();
		} else if (stageOfRound == 2) {
			stageOfRound++;
			System.out.println("Stage: " + stageOfRound);
			game.getTable().dealRiver();
			postRiver();
		} else {
			game.payout();
		}
	}
}
