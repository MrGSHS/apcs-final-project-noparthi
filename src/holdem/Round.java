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
		minBet = game.getBigBlind();
		Collections.rotate(game.getActionsOrder(), -1);
		game.getActionsOrder().get(0).setFirstAction(true);
		game.getActionsOrder().get(0).setIsTurn(true);
		game.allComputersTakeAction();
	}

	public void preTurn() {
		minBet = game.getBigBlind();
		game.getActionsOrder().get(0).setFirstAction(false);
		Collections.rotate(game.getActionsOrder(), -3);
		game.getActionsOrder().get(0).setFirstAction(true);
		game.getActionsOrder().get(0).setIsTurn(true);
		game.allComputersTakeAction();
		;
	}

	public void preRiver() {
		minBet = game.getBigBlind();
		game.getActionsOrder().get(0).setIsTurn(true);
		game.allComputersTakeAction();
	}

	public void postRiver() {
		minBet = game.getBigBlind();
		game.getActionsOrder().get(0).setIsTurn(true);
		game.allComputersTakeAction();
	}

	// Check To See If Should Move To Next Round
	public boolean moveOn() {
		// Skips If Someone Had Raised, And Resets All Action Booleans Back To
		// Null
		int highestBetAmount = 1;
		int totalActive = 0;
		for (Player p : game.getActionsOrder()) {
			if (!p.isFolded()) {
				if (p.getBetAmount() > highestBetAmount) {
					highestBetAmount = p.getBetAmount();
				}
				totalActive++;
			}
		}
		for (Player p : game.getActionsOrder()) {
			if (!p.isFolded()) {
				if (p.getBetAmount() == highestBetAmount)
					totalActive--;
			}
		}
		
		if(totalActive != 0){
			return false;
		}
		// Else Reset Stuff
		game.resetPlayerBetAmount();
		for (Player p : game.getPlayers()) {
			p.resetActionBoolean();
			p.setIsTurn(false);
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
		} else {
			game.payout();
		}
		return true;
	}
}
