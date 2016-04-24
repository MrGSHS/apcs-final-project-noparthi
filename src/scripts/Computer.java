package scripts;

public class Computer extends Player {
	private Game game;
	private int currentHandStrength;
	//private Hand hand;
	public Computer(Game game) {
		super(game);
		newHand();
		this.game = game;
	}

	public void takeAction() {
	    
		if (game.getTable().getCardsOnTable().size() == 0) preFlopLogic();
		else logic();
	}

	public void preFlopLogic() {
		currentHandStrength = getHand().initialHandStrength();
		if (game.getPlayer().getCheckBoolean()) {
			if (currentHandStrength > 6) {
				raise(game.getBigBlind() * 2);
				System.out.print(getPoints());
				return;
			} else {
				check();
				return;
			}
		} else if (game.getPlayer().getRaiseBoolean()) {
			if (currentHandStrength > 7) {
				call();
				System.out.print(getPoints());
				return;
			} else {
				fold();
				return;
			}
		}

	}

	public String logic() {
		currentHandStrength = game.getComputer().getHand().updateHandStrength();
		return "";
	}
}
