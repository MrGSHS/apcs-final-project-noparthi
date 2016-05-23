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
		// ActionPlayers
		int indexBefore = -1;
		for (int i = 0; i < game.getActionsOrder().size(); i++) {
			if (game.getActionsOrder().get(i) == game.getPlayers().get(getPosition())) {
				indexBefore = i - 1;
				if (indexBefore < 0)
					indexBefore = game.getActionsOrder().size() - 1;
				while (game.getActionsOrder().get(indexBefore).isFolded()) {
					indexBefore -= 1;
					if (indexBefore < 0)
						indexBefore = game.getActionsOrder().size() - 1;
				}
				break;
			}
		}
		
		// Actions Based Off Of The Player Before This
		// If Player Before Checked
		if (game.getActionsOrder().get(indexBefore).getCheckBoolean()
				|| game.getPlayers().get(getPosition()).getFirstAction()) {
			if (currentHandStrength > 7 || (int) (Math.random() * 11) + 1 <= 2)
				raise(game.getBigBlind() * 2);
			else {
				// For Pre-Flop, Making People Call Big Blind
				if (game.getTable().getCardsOnTable().size() == 0) {
					if (game.getPlayers().get(getPosition()).getBetAmount() < maxBet)
						call();
					// Otherwise Check
					else
						check();
				}
			}
			return;
		}
		// If Player Before Raised Or Called
		else if (game.getActionsOrder().get(indexBefore).getRaiseBoolean()
				|| game.getActionsOrder().get(indexBefore).getCallBoolean()) {
			if (currentHandStrength == 10)
				call();
			else if (game.getMaxBetAmount() > 0.50 * getPoints()) {
				if (currentHandStrength >= 9)
					call();
				else
					fold();
			} else if (game.getMaxBetAmount() > 0.25 * getPoints()) {
				if (currentHandStrength >= 8 || (int) (Math.random() * 11) + 1 == 1)
					call();
				else
					fold();
			} else if (game.getMaxBetAmount() > 0.10 * getPoints()) {
				if (currentHandStrength >= 7 || (int) (Math.random() * 11) + 1 <= 2)
					call();
				else
					fold();
			} else
				call();
			return;
		}
		// If Reaches Here, The Game Broke
		else {
			System.out.println("BROKEN.");
		}
	}

	public void logic() {
		currentHandStrength = getHand().updateHandStrength();
		// Index Equals Index Of Active Player Before This Player In
		// ActionPlayers
		int indexBefore = -100;
		for (int i = 0; i < game.getActionsOrder().size(); i++) {
			if (game.getActionsOrder().get(i) == game.getPlayers().get(getPosition())) {
				indexBefore = i - 1;
				if (indexBefore < 0)
					indexBefore = game.getActionsOrder().size() - 1;
				while (game.getActionsOrder().get(indexBefore).isFolded()) {
					indexBefore -= 1;
					if (indexBefore < 0)
						indexBefore = game.getActionsOrder().size() - 1;
				}
				break;
			}
		}

		// Actions Based Off Of Player Before This
		// If Player Before Checked
		if (game.getActionsOrder().get(indexBefore).getCheckBoolean()
				|| game.getPlayers().get(getPosition()).getFirstAction()) {
			if ((int) (Math.random() * 11) + 1 <= 1)
				raise(game.getBigBlind() * ((int) (Math.random() * 20) + 1));
			else if (currentHandStrength >= 7 || (int) (Math.random() * 11) + 1 <= 1)
				raise(game.getBigBlind() * ((int) (Math.random() * 15) + 1));
			else if (currentHandStrength == 6 || (int) (Math.random() * 11) + 1 <= 1)
				raise(game.getBigBlind() * ((int) (Math.random() * 14) + 1));
			else if (currentHandStrength == 5 || (int) (Math.random() * 11) + 1 <= 1)
				raise(game.getBigBlind() * ((int) (Math.random() * 13) + 1));
			else if (currentHandStrength == 4 || (int) (Math.random() * 11) + 1 <= 1)
				raise(game.getBigBlind() * ((int) (Math.random() * 12) + 1));
			else if (currentHandStrength == 3 || (int) (Math.random() * 11) + 1 <= 2)
				raise(game.getBigBlind() * ((int) (Math.random() * 11) + 1));
			else
				check();
			return;
		}
		// If Player Before Raised
		else if (game.getActionsOrder().get(indexBefore).getRaiseBoolean()) {
			if (currentHandStrength == 10)
				call();
			else if (game.getMaxBetAmount() > 0.5 * getPoints()) {
				if (currentHandStrength >= 7)
					call();
				else
					fold();
			} else if (game.getMaxBetAmount() > 0.25 * getPoints()) {
				if (currentHandStrength >= 6 || (int) (Math.random() * 11) + 1 <= 1)
					call();
				else
					fold();
			} else if (game.getMaxBetAmount() > 0.1 * getPoints()) {
				if (currentHandStrength >= 5 || (int) (Math.random() * 11) + 1 <= 1)
					call();
				else
					fold();
			} else {
				if (currentHandStrength >= 4 || (int) (Math.random() * 11) + 1 <= 1)
					raise(game.getMaxBetAmount() * ((int) (Math.random() * 3) + 2));
				else if (currentHandStrength >= 2 || (int) (Math.random() * 11) + 1 <= 1)
					call();
				else
					fold();
			}
			return;
		}
		// If Player Before Called
		else if (game.getActionsOrder().get(indexBefore).getCallBoolean()) {
			call();
			return;
		}
		// If Reaches Here, The Game Broke
		else {
			System.out.println("BROKEN.");
		}
	}
}
