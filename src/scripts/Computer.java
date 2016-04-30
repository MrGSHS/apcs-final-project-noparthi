package scripts;

public class Computer extends Player {
	private Game game;
	private int currentHandStrength;

	public Computer(Game game) {
		super(game);
		this.game = game;
	}

	public void takeAction(){
		if (game.getTable().getCardsOnTable().size()==0){
			preFlopLogic();
		}
		else{
			logic();
		}
	}

	public void preFlopLogic() {
		currentHandStrength = getHand().initialHandStrength();
		if (game.getUser().getCheckBoolean()) {
			if (currentHandStrength > 6 || (int) (Math.random() * 11) + 1 <= 2) {
				raise(game.getBigBlind() * 2);

				return;
			} else {
				check();
				return;
			}
		} else if (game.getUser().getRaiseBoolean()) {
			if (currentHandStrength == 10) {
				call();
				return;
			}
			if (game.getUser().getBetAmount() > 0.75 * getPoints()) {
				if (currentHandStrength >= 8) {
					call();
					return;
				} else {
					fold();

					return;
				}
			}
			if (game.getUser().getBetAmount() > 0.5 * getPoints()) {
				if (currentHandStrength >= 7 || (int) (Math.random() * 11) + 1 == 1) {
					call();
					return;
				} else {
					fold();

					return;
				}
			}
			if (game.getUser().getBetAmount() > 0.25 * getPoints()) {
				if (currentHandStrength >= 6 || (int) (Math.random() * 11) + 1 <= 2) {
					call();
					return;
				} else {
					fold();

					return;
				}
			} else {
				if (currentHandStrength >= 5 || (int) (Math.random() * 11) + 1 <= 3) {
					call();
					return;
				} else {
					fold();

					return;
				}
			}
		} else {
			check();
		}
	}

	public void logic() {
		currentHandStrength = getHand().updateHandStrength();
		if (game.getUser().getCheckBoolean()) {

			if ((int) (Math.random() * 11) + 1 <= 1) {
				raise(game.getBigBlind() * ((int) (Math.random() * 12) + 20));

				return;
			} else if (currentHandStrength >= 8 || (int) (Math.random() * 11) + 1 <= 1) {
				raise(game.getBigBlind() * ((int) (Math.random() * 12) + 20));

				return;
			} else if (currentHandStrength == 7 || (int) (Math.random() * 11) + 1 <= 1) {
				raise(game.getBigBlind() * ((int) (Math.random() * 6) + 12));

				return;
			} else if (currentHandStrength == 6 || (int) (Math.random() * 11) + 1 <= 1) {
				raise(game.getBigBlind() * ((int) (Math.random() * 8) + 8));

				return;
			} else if (currentHandStrength == 5 || (int) (Math.random() * 11) + 1 <= 1) {
				raise(game.getBigBlind() * ((int) (Math.random() * 6) + 4));

				return;
			} else if (currentHandStrength == 4 || currentHandStrength == 3 || (int) (Math.random() * 11) + 1 <= 2) {
				raise(game.getBigBlind() * ((int) (Math.random() * 5) + 1));

				return;
			} else {
				check();
				return;
			}
		} else if (game.getUser().getRaiseBoolean()) {
			if (currentHandStrength == 10) {
				call();
				return;
			}
			if (game.getUser().getBetAmount() > 0.75 * getPoints()) {
				if (currentHandStrength >= 8) {
					call();
					return;
				} else {
					fold();

					return;
				}
			}
			if (game.getUser().getBetAmount() > 0.5 * getPoints()) {
				if (currentHandStrength >= 7 || (int) (Math.random() * 11) + 1 <= 1) {
					call();
					return;
				} else {
					fold();

					return;
				}
			}
			if (game.getUser().getBetAmount() > 0.25 * getPoints()) {
				if (currentHandStrength >= 6 || (int) (Math.random() * 11) + 1 <= 1) {
					call();
					return;
				} else {
					fold();

					return;
				}
			} else {
				if (currentHandStrength >= 7 || (int) (Math.random() * 11) + 1 <= 1) {
					raise(game.getBigBlind() * ((int) (Math.random() * 6) + 7));
					return;
				} else if (currentHandStrength >= 5 || (int) (Math.random() * 11) + 1 <= 1) {
					call();
					return;
				} else {
					fold();

					return;
				}
			}
		} else {
			check();
		}
	}
}
