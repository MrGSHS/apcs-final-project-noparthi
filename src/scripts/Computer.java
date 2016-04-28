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
			System.out.println("Using Pre-Flop Logic");
			preFlopLogic();
		}
		else{
			System.out.println("Using Logic");
			logic();
		}
	}
	public void preFlopLogic() {
		currentHandStrength = getHand().initialHandStrength();
		if (game.getUser().getCheckBoolean()) {
			if (currentHandStrength > 6 || (int) (Math.random() * 11) + 1 <= 2) {
				raise(game.getBigBlind() * 2);
				System.out.println("The Computer Has Raised");
				return;
			} else {
				check();
				return;
			}
		}
		if (game.getUser().getRaiseBoolean()) {
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
					System.out.println("The Computer Has Folded. New Round Will Start");
					return;
				}
			}
			if (game.getUser().getBetAmount() > 0.5 * getPoints()) {
				if (currentHandStrength >= 7 || (int) (Math.random() * 11) + 1 == 1) {
					call();
					return;
				} else {
					fold();
					System.out.println("The Computer Has Folded. New Round Will Start");
					return;
				}
			}
			if (game.getUser().getBetAmount() > 0.25 * getPoints()) {
				if (currentHandStrength >= 6 || (int) (Math.random() * 11) + 1 <= 2) {
					call();
					return;
				} else {
					fold();
					System.out.println("The Computer Has Folded. New Round Will Start");
					return;
				}
			} else {
				if (currentHandStrength >= 5 || (int) (Math.random() * 11) + 1 <= 3) {
					call();
					return;
				} else {
					fold();
					System.out.println("The Computer Has Folded. New Round Will Start");
					return;
				}
			}
		}
	}

	public void logic() {
		currentHandStrength = getHand().updateHandStrength();
		System.out.println("Computer Hand Strength: " + currentHandStrength);
		if (game.getUser().getCheckBoolean()) {
			System.out.println("User Has Checked");
			if ((int) (Math.random() * 11) + 1 <= 2) {
				raise(game.getBigBlind() * 32);
				System.out.println("The Computer Has Raised");
				return;
			} else if (currentHandStrength >= 8 || (int) (Math.random() * 11) + 1 <= 2) {
				raise(game.getBigBlind() * 32);
				System.out.println("The Computer Has Raised");
				return;
			} else if (currentHandStrength == 7 || (int) (Math.random() * 11) + 1 <= 2) {
				raise(game.getBigBlind() * 16);
				System.out.println("The Computer Has Raised");
				return;
			} else if (currentHandStrength == 6 || (int) (Math.random() * 11) + 1 <= 2) {
				raise(game.getBigBlind() * 8);
				System.out.println("The Computer Has Raised");
				return;
			} else if (currentHandStrength == 5 || (int) (Math.random() * 11) + 1 <= 2) {
				raise(game.getBigBlind() * 4);
				System.out.println("The Computer Has Raised");
				return;
			} else if (currentHandStrength == 4 || currentHandStrength == 3 || (int) (Math.random() * 11) + 1 <= 3) {
				raise(game.getBigBlind() * 2);
				System.out.println("The Computer Has Raised");
				return;
			} else {
				check();
				System.out.println("The Computer Has Checked");
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
					System.out.println("The Computer Has Folded. New Round Will Start");
					return;
				}
			}
			if (game.getUser().getBetAmount() > 0.5 * getPoints()) {
				if (currentHandStrength >= 7 || (int) (Math.random() * 11) + 1 <= 1) {
					call();
					return;
				} else {
					fold();
					System.out.println("The Computer Has Folded. New Round Will Start");
					return;
				}
			}
			if (game.getUser().getBetAmount() > 0.25 * getPoints()) {
				if (currentHandStrength >= 6 || (int) (Math.random() * 11) + 1 <= 1) {
					call();
					return;
				} else {
					fold();
					System.out.println("The Computer Has Folded. New Round Will Start");
					return;
				}
			} else {
				if (currentHandStrength >= 7 || (int) (Math.random() * 11) + 1 <= 2) {
					raise(game.getBigBlind() * ((int) (Math.random() * 6) + 7));
					return;
				} else if (currentHandStrength >= 5 || (int) (Math.random() * 11) + 1 <= 2) {
					call();
					return;
				} else {
					fold();
					System.out.println("The Computer Has Folded. New Round Will Start");
					return;
				}
			}
		}
	}
}
