package scripts;

public class Computer extends Player {
	private Game game;
	private int currentHandStrength;
	//private Hand hand;
	public Computer(Game game) {
		super(game);
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
				return;
			} else {
				check();
				return;
			}
		} else if(game.getPlayer().getRaiseBoolean()) {
			if (currentHandStrength > 7) {
				call();
				return;
			} else {
				fold();
				System.out.println("The Computer Has Folded. New Round Will Start");
				return;
			}
		}
	}

	public String logic() {
		currentHandStrength = game.getComputer().getHand().updateHandStrength();
		return "";
	}
}
