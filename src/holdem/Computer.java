package holdem;

public class Computer extends Player {
	private HoldemGame game;
	private int currentHandStrength;

	public Computer(HoldemGame game, int pos) {
		super(game, pos);
		this.game = game;
	}

	public void takeAction() {
		if (game.getTable().getCardsOnTable().size() == 0)
			preFlopLogic();
		else
			logic();
	}

	public void preFlopLogic() {
		currentHandStrength = getHand().initialHandStrength();
		int maxBet = 0;
		int bet = 0;
		// Gathers Largest Bet Amount To Call
		for (Player p : game.getPlayers()) {
			bet = p.getBetAmount();
			if (bet > maxBet)
				maxBet = bet;
		}

		// Index Equals Index Of Active Player Before This Player In
		// ActivePlayers
		int index = getPosition();
		for (int i = index - 1; i >= 0; i--) {
			if (!game.getPlayers().get(i).isFolded()) {
				index = i;
				break;
			}
		}
		if (index == getPosition()) {
			// Index Equals Index Of Active Player Before This Player In Players
			for (int j = game.getPlayers().size()-1; j >= 0; j--) {
				if (!game.getPlayers().get(j).isFolded()) {
					index = j;
					break;
				}
			}
		}
		System.out.println("Index: " + index);
		// Actions Based Off Of The Player Before This

		// If Player Before Checked
		if (game.getPlayers().get(index).getCheckBoolean() || game.getPlayers().get(index).getPointsInvested() == getPointsInvested()) {
			// Otherwise Continue
			if (currentHandStrength > 7 || (int) (Math.random() * 11) + 1 <= 2) {
				raise(game.getBigBlind() * 2);
				return;
			} else {
				// For Pre-Flop, Making People Call Big Blind
				if (game.getTable().getCardsOnTable().size() == 0) {
					if (game.getPlayers().get(getPosition()).getBetAmount() < maxBet) {
						call();
						return;
					}
				}
				// Otherwise Call
				else {
					check();
					return;
				}
			}
		}
		// If Player Before Raised Or Called
		else if (game.getPlayers().get(index).getRaiseBoolean() || game.getPlayers().get(index).getCallBoolean()) {
			if (currentHandStrength == 10) {
				call();
				return;
			}
			if (game.getMaxBetAmount() > 0.50 * getPoints()) {
				if (currentHandStrength >= 9) {
					call();
					return;
				} else {
					fold();
					;
					return;
				}
			}
			if (game.getMaxBetAmount() > 0.25 * getPoints()) {
				if (currentHandStrength >= 8 || (int) (Math.random() * 11) + 1 == 1) {
					call();
					return;
				} else {
					fold();
					return;
				}
			}
			if (game.getMaxBetAmount() > 0.10 * getPoints()) {
				if (currentHandStrength >= 7 || (int) (Math.random() * 11) + 1 <= 2) {
					call();
					return;
				} else {
					fold();
					return;
				}
			} else {
				call();
				return;
			}
		}
		// If Reaches Here, The Game Broke
		else {
			System.out.println("BROKEN.");
			System.exit(1);
		}
	}

	public void logic() {
		currentHandStrength = getHand().updateHandStrength();
		// Index Equals Index Of Active Player Before This Player In
		// ActivePlayers
		int index = getPosition();
		for (int i = index - 1; i >= 0; i--) {
			if (!game.getPlayers().get(i).isFolded()) {
				index = i;
				break;
			}
		}
		if (index == getPosition()) {
			// Index Equals Index Of Active Player Before This Player In Players
			for (int j = game.getPlayers().size()-1; j >= 0; j--) {
				if (!game.getPlayers().get(j).isFolded()) {
					index = j;
					break;
				}
			}
		}
		System.out.println("Index: " + index);

		// Actions Based Off Of Player Before This
		// If Player Before Checked
		if (game.getPlayers().get(index).getCheckBoolean() || game.getPlayers().get(index).getPointsInvested() == getPointsInvested()) {
			if ((int) (Math.random() * 11) + 1 <= 1) {
				raise(game.getBigBlind() * ((int) (Math.random() * 20) + 1));
				return;
			} else if (currentHandStrength >= 7 || (int) (Math.random() * 11) + 1 <= 1) {
				raise(game.getBigBlind() * ((int) (Math.random() * 15) + 1));
				return;
			} else if (currentHandStrength == 6 || (int) (Math.random() * 11) + 1 <= 1) {
				raise(game.getBigBlind() * ((int) (Math.random() * 14) + 1));
				return;
			} else if (currentHandStrength == 5 || (int) (Math.random() * 11) + 1 <= 1) {
				raise(game.getBigBlind() * ((int) (Math.random() * 13) + 1));
				return;
			} else if (currentHandStrength == 4 || (int) (Math.random() * 11) + 1 <= 1) {
				raise(game.getBigBlind() * ((int) (Math.random() * 12) + 1));
				return;
			} else if (currentHandStrength == 3 || (int) (Math.random() * 11) + 1 <= 2) {
				raise(game.getBigBlind() * ((int) (Math.random() * 11) + 1));
				return;
			} else {
				check();
				return;
			}
		}
		// If Player Before Raised
		else if (game.getPlayers().get(index).getRaiseBoolean() || game.getPlayers().get(index).getPointsInvested() > getPointsInvested()) {
			if (currentHandStrength == 10) {
				call();
				return;
			}
			if (game.getMaxBetAmount() > 0.5 * getPoints()) {
				if (currentHandStrength >= 7) {
					call();
					return;
				} else {
					fold();
					return;
				}
			}
			if (game.getMaxBetAmount() > 0.25 * getPoints()) {
				if (currentHandStrength >= 6 || (int) (Math.random() * 11) + 1 <= 1) {
					call();
					return;
				} else {
					fold();
					return;
				}
			}
			if (game.getMaxBetAmount() > 0.1 * getPoints()) {
				if (currentHandStrength >= 5 || (int) (Math.random() * 11) + 1 <= 1) {
					call();
					return;
				} else {
					fold();
					return;
				}
			} else {
				if (currentHandStrength >= 4 || (int) (Math.random() * 11) + 1 <= 1) {
					raise(game.getMaxBetAmount() * ((int) (Math.random() * 3) + 2));
					return;
				} else if (currentHandStrength >= 2 || (int) (Math.random() * 11) + 1 <= 1) {
					call();
					return;
				} else {
					fold();
					return;
				}
			}
		}
		// If Player Before Called
		else if (game.getPlayers().get(index).getCallBoolean()) {
			call();
			return;
		}
		// If Reaches Here, The Game Broke
		else {
			System.out.println("BROKEN.");
		}
	}
}
