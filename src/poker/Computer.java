package poker;

public class Computer extends Player {
	private Game game;
	private int currentHandStrength;

	public Computer(Game game, int pos) {
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

		// Index Equals Index Of Active Player Before This Player In
		// ActivePlayers
		int index = getPosition() - 1;
		for (int i = 1; i < game.getActivePlayers().size(); i++) {
			if (game.getActivePlayers().get(i) == game.getPlayers().get(getPosition())) {
				index = i;
				break;
			}
		}
		index--;

		// Index Equals Index Of Active Player Before This Player In Players
		for (int j = 1; j < game.getPlayers().size(); j++) {
			if (game.getPlayers().get(j) == game.getActivePlayers().get(index)) {
				index = j;
				break;
			}
		}
		
		System.out.println("Index: " + index);
		
		// Actions Based Off Of The Player Before This
		// If Player Before Checked
		if (game.getPlayers().get(index).getCheckBoolean()) {
			if (currentHandStrength > 6 || (int) (Math.random() * 11) + 1 <= 2) {
				raise(game.getBigBlind() * 2);
				return;
			} else {
				check();
				System.out.println("Comp. Checks");
				return;
			}
		}
		// If Player Before Raised Or Called
		else if (game.getPlayers().get(index).getRaiseBoolean() || game.getPlayers().get(index).getCallBoolean()) {
			if (currentHandStrength == 10) {
				call();
				System.out.println("Comp. Calls");
				return;
			}
			if (game.getMaxBetAmount() > 0.50 * getPoints()) {
				if (currentHandStrength >= 9) {
					call();
					System.out.println("Comp. Calls");
					return;
				} else {
					fold();
					System.out.println("Comp. Folded");
					return;
				}
			}
			if (game.getMaxBetAmount() > 0.25 * getPoints()) {
				if (currentHandStrength >= 8 || (int) (Math.random() * 11) + 1 == 1) {
					call();
					System.out.println("Comp. Calls");
					return;
				} else {
					fold();
					System.out.println("Comp. Folded");
					return;
				}
			}
			if (game.getMaxBetAmount() > 0.10 * getPoints()) {
				if (currentHandStrength >= 7 || (int) (Math.random() * 11) + 1 <= 2) {
					call();
					System.out.println("Comp. Calls");
					return;
				} else {
					fold();
					System.out.println("Comp. Folded");
					return;
				}
			} else {
				call();
				System.out.println("Comp. Calls");
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
		int index = getPosition() - 1;
		for (int i = 1; i < game.getActivePlayers().size(); i++) {
			if (game.getActivePlayers().get(i) == game.getPlayers().get(getPosition())) {
				index = i;
				break;
			}
		}
		index--;

		// Index Equals Index Of Active Player Before This Player In Players
		for (int j = 1; j < game.getPlayers().size(); j++) {
			if (game.getPlayers().get(j) == game.getActivePlayers().get(index)) {
				index = j;
				break;
			}
		}

		System.out.println("Index: " + index);

		// Actions Based Off Of Player Before This
		// If Player Before Checked
		if (game.getPlayers().get(index).getCheckBoolean()) {
			if ((int) (Math.random() * 11) + 1 <= 1) {
				raise(game.getBigBlind() * ((int) (Math.random() * 20) + 1));
				System.out.println("Comp. Raises");
				return;
			} else if (currentHandStrength >= 7 || (int) (Math.random() * 11) + 1 <= 1) {
				raise(game.getBigBlind() * ((int) (Math.random() * 15) + 1));
				System.out.println("Comp. Raises");
				return;
			} else if (currentHandStrength == 6 || (int) (Math.random() * 11) + 1 <= 1) {
				raise(game.getBigBlind() * ((int) (Math.random() * 14) + 1));
				System.out.println("Comp. Raises");
				return;
			} else if (currentHandStrength == 5 || (int) (Math.random() * 11) + 1 <= 1) {
				raise(game.getBigBlind() * ((int) (Math.random() * 13) + 1));
				System.out.println("Comp. Raises");
				return;
			} else if (currentHandStrength == 4 || (int) (Math.random() * 11) + 1 <= 1) {
				raise(game.getBigBlind() * ((int) (Math.random() * 12) + 1));
				System.out.println("Comp. Raises");
				return;
			} else if (currentHandStrength == 3 || (int) (Math.random() * 11) + 1 <= 2) {
				raise(game.getBigBlind() * ((int) (Math.random() * 11) + 1));
				System.out.println("Comp. Raises");
				return;
			} else {
				check();
				System.out.println("Comp. Checks");
				return;
			}
		}
		// If Player Before Raised
		else if (game.getPlayers().get(index).getRaiseBoolean()) {
			if (currentHandStrength == 10) {
				call();
				System.out.println("Comp. Calls");
				return;
			}
			if (game.getMaxBetAmount() > 0.5 * getPoints()) {
				if (currentHandStrength >= 7) {
					call();
					System.out.println("Comp. Calls");
					return;
				} else {
					fold();
					System.out.println("Comp. Folded");
					return;
				}
			}
			if (game.getMaxBetAmount() > 0.25 * getPoints()) {
				if (currentHandStrength >= 6 || (int) (Math.random() * 11) + 1 <= 1) {
					call();
					System.out.println("Comp. Calls");
					return;
				} else {
					fold();
					System.out.println("Comp. Folded");
					return;
				}
			}
			if (game.getMaxBetAmount() > 0.1 * getPoints()) {
				if (currentHandStrength >= 5 || (int) (Math.random() * 11) + 1 <= 1) {
					call();
					System.out.println("Comp. Calls");
					return;
				} else {
					fold();
					System.out.println("Comp. Folded");
					return;
				}
			} else {
				if (currentHandStrength >= 4 || (int) (Math.random() * 11) + 1 <= 1) {
					raise(game.getMaxBetAmount() * ((int) (Math.random() * 3) + 2));
					System.out.println("Comp. Raises");
					return;
				} else if (currentHandStrength >= 2 || (int) (Math.random() * 11) + 1 <= 1) {
					call();
					System.out.println("Comp. Calls");
					return;
				} else {
					fold();
					System.out.println("Comp. Folded");
					return;
				}
			}
		}
		// If Player Before Called
		else if (game.getPlayers().get(index).getCallBoolean()) {
			call();
			System.out.println("Comp. Calls");
		}
		// If Reaches Here, The Game Broke
		else {
			System.out.println("BROKEN.");
		}
	}
}
