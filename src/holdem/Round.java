package holdem;

import java.util.Collections;

public class Round {

	private static int roundNumber = 0;
	private HoldemGame game;

	private int pot;
	private int minBet;
	/*
	 * 0 = pre flop 1 = pre turn 2 = pre river 3 = post river
	 */
	public int stageOfRound;

	public Round(HoldemGame game) {
		pot = 0;
		minBet = 0;
		stageOfRound = 0;
		this.game = game;
		roundNumber++;
		for (Player p : game.getPlayers()) {
			p.newHand();
		}
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
		Collections.rotate(game.getActionsOrder(), -1);
		game.getActionsOrder().get(0).setFirstAction(true);
		game.allComputersTakeAction();
		minBet = game.getBigBlind();
	}

	public void preTurn() {
		game.getUser().setUserMove(false);
		Collections.rotate(game.getActionsOrder(), -3);
		for (Player p : game.getActionsOrder())
			p.setFoldBoolean(false);
		game.getActionsOrder().get(0).setFirstAction(true);
		game.allComputersTakeAction();
		minBet = game.getBigBlind();
	}

	public void preRiver() {
		game.getUser().setUserMove(false);
		game.allComputersTakeAction();
		minBet = game.getBigBlind();
	}

	public void postRiver() {
		game.getUser().setUserMove(false);
		game.allComputersTakeAction();
		minBet = game.getBigBlind();
	}

	// Check To See If Should Move To Next Round
	public boolean moveOn() {
		// Skips If Someone Had Raised, And Resets All Action Booleans Back To
		// Null
		int check = 0;
		int call = 0;
		int raise = 0;
		int fold = 0;
		for (Player p : game.getActionsOrder()) {
			if (p.getCheckBoolean())
				check++;
			if (p.getCallBoolean())
				call++;
			if (p.getRaiseBoolean())
				raise++;
			if (p.isFolded())
				fold++;
		}

		if (check + fold != game.getActionsOrder().size()
				&& ((call + raise + fold) != game.getActionsOrder().size() || raise > 1)) {
			// System.out.println("Failed Move-On");
			return false;
		}

		// System.out.println("Move-On Successful");

		// Else Reset Stuff
		game.resetPlayerBetAmount();
		for (Player p : game.getPlayers())
			p.resetActionBoolean();

		for (Player p : game.getPlayers()) {
			p.setBigBlind(false);
			p.setSmallBlind(false);
		}

		// And Proceeds To Next Round If None Of The Above Are True
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
		} else
			game.payout();
		return true;
	}
}
