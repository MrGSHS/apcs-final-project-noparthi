package scripts;

public class Computer extends Player {
	private Game game;
	private int currentHandStrength;

	public Computer(Game game, int pos) {
		super(game, pos);
		this.game = game;
	}

	public void takeAction(){
		if (game.getTable().getCardsOnTable().size()==0)
			preFlopLogic();
		else
			logic();
	}

	public void preFlopLogic() {
		currentHandStrength = getHand().initialHandStrength();
		int index = getPosition()-1;
		for(Player p : game.getActivePlayers()){
			if(p.getPosition() != getPosition()){
				index = p.getPosition();
				break;
			}
		}
		//System.out.println("Check Boolean: "+game.getPlayers().get(index).getCheckBoolean());
		//System.out.println("Call Boolean: "+game.getPlayers().get(index).getCallBoolean());
		//System.out.println("Raise Boolean: "+game.getPlayers().get(index).getRaiseBoolean());
		if (game.getPlayers().get(index).getCheckBoolean()) {
			if (currentHandStrength > 6 || (int) (Math.random() * 11) + 1 <= 2) {
				raise(game.getBigBlind() * 2);
				return;
			} else {
				check();
				System.out.println("Comp. Checks");
				return;
			}
		} else if (game.getPlayers().get(index).getRaiseBoolean() || game.getPlayers().get(index).getCallBoolean()) {
			if (currentHandStrength == 10) {
				call();
				System.out.println("Comp. Calls");
				return;
			}
			if (game.getMaxBetAmount() > 0.50 * getPoints()) {
				if (currentHandStrength >= 8) {
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
				if (currentHandStrength >= 7 || (int) (Math.random() * 11) + 1 == 1) {
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
				if (currentHandStrength >= 6 || (int) (Math.random() * 11) + 1 <= 2) {
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
		else{
			System.out.println("BROKEN.");
			System.exit(1);
		}
	}

	public void logic() {
		currentHandStrength = getHand().updateHandStrength();
		int index = getPosition()-1;
		for(Player p : game.getActivePlayers()){
			if(p.getPosition() != getPosition()){
				index = p.getPosition();
				break;
			}
		}
		if (game.getPlayers().get(index).getCheckBoolean()) {
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
				System.out.println("Comp. Checks");
				return;
			}
		} else if (game.getPlayers().get(index).getRaiseBoolean()) {
			if (currentHandStrength == 10) {
				call();
				System.out.println("Comp. Calls");
				return;
			}
			if (game.getMaxBetAmount() > 0.75 * getPoints()) {
				if (currentHandStrength >= 8) {
					call();
					System.out.println("Comp. Calls");
					return;
				} else {
					fold();
					System.out.println("Comp. Folded");
					return;
				}
			}
			if (game.getMaxBetAmount() > 0.5 * getPoints()) {
				if (currentHandStrength >= 7 || (int) (Math.random() * 11) + 1 <= 1) {
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
			} else {
				if (currentHandStrength >= 7 || (int) (Math.random() * 11) + 1 <= 1) {
					raise(game.getBigBlind() * ((int) (Math.random() * 6) + 7));
					return;
				} else if (currentHandStrength >= 5 || (int) (Math.random() * 11) + 1 <= 1) {
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
		else if(game.getPlayers().get(index).getCallBoolean()){
			call();
		}
		else{
			System.out.println("BROKEN.");
		}
	}
}
