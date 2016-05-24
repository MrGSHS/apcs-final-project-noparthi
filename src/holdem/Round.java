package holdem;

import java.util.Collections;

public class Round {

	private static int roundNumber = 0;
	private HoldemGame game;

	private int pot;
	private int minBet;

	public int stageOfRound;

	public Round(HoldemGame game) {
		pot = 0;
		minBet = 0;
		stageOfRound = 0;
		this.game = game;
		roundNumber++;
		for (Player p : game.getPlayers()) {
			if (p.getPoints() <= 0) {
				//game.getPlayers().remove(p);
				game.getActionsOrder().remove(p);
			} else {
				p.newHand();
			}
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
		for (Player p : game.getActionsOrder()) {
			if (!p.isFolded()) {
				p.setFirstAction(true);
				p.setIsTurn(true);
				break;
			}
		}
		game.allPlayersTakeAction();
	}

	public void preTurn() {
		minBet = game.getBigBlind();
		game.getActionsOrder().get(0).setFirstAction(false);
		Collections.rotate(game.getActionsOrder(), -3);
		for (Player p : game.getActionsOrder()) {
			if (!p.isFolded()) {
				p.setFirstAction(true);
				p.setIsTurn(true);
				break;
			}
		}
		game.allPlayersTakeAction();
	}

	public void preRiver() {
		minBet = game.getBigBlind();
		for (Player p : game.getActionsOrder()) {
			if (!p.isFolded()) {
				p.setFirstAction(true);
				p.setIsTurn(true);
				break;
			}
		}
		game.allPlayersTakeAction();
	}

	public void postRiver() {
		minBet = game.getBigBlind();
		for (Player p : game.getActionsOrder()) {
			if (!p.isFolded()) {
				p.setFirstAction(true);
				p.setIsTurn(true);
				break;
			}
		}
		game.allPlayersTakeAction();
	}

	// Check To See If Should Move To Next Round
	public boolean moveOn() {
		int highestBetAmount = 0;
		int totalActive = 0;
		int check = 0;
		int call = 0;
		int raise = 0;
		int fold = 0;
		for (Player p : game.getActionsOrder()) {
			if (!p.isFolded()) {
				if (p.getBetAmount() > highestBetAmount) {
					highestBetAmount = p.getBetAmount();
				}
				if (p.getCheckBoolean()) {
					check++;
				} else if (p.getCallBoolean()) {
					call++;
				} else if (p.getRaiseBoolean()) {
					raise++;
				}
				totalActive++;
			} else {
				fold++;
			}
		}

		for (Player p : game.getActionsOrder()) {
			if (!p.isFolded()) {
				if (p.getBetAmount() == highestBetAmount)
					totalActive--;
			}
		}

		if (totalActive != 0 || ((call + raise + fold) != game.getActionsOrder().size()
				&& (check + fold) != game.getActionsOrder().size())) {
			return false;
		}

		// Else Reset Stuff
		game.resetPlayerBetAmount();
		for (Player p : game.getPlayers()) {
			p.resetActionBoolean();
			p.setIsTurn(false);
		}

		// And Proceeds To Next Round
		if (stageOfRound == 0) {
			stageOfRound++;
			if (!game.isPayout()) {
				System.out.println("Stage: " + stageOfRound);
				game.getTable().dealFlop();
				preTurn();
			} else {
				Collections.rotate(game.getActionsOrder(), -3);
			}
		} else if (stageOfRound == 1) {
			stageOfRound++;
			if (!game.isPayout()) {
				System.out.println("Stage: " + stageOfRound);
				game.getTable().dealTurn();
				preRiver();
			}
		} else if (stageOfRound == 2) {
			stageOfRound++;
			if (!game.isPayout()) {
				System.out.println("Stage: " + stageOfRound);
				game.getTable().dealRiver();
				postRiver();
			}
		} else{
			game.payout();
		}
		return true;
	}
}
