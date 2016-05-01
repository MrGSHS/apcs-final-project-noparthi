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
		else{
			System.out.println("BROKEN.");
			System.exit(1);
		}
	}

	public void logic() {
		currentHandStrength = getHand().updateHandStrength();
		int index = getPosition()-1;
		//for(Player p : game.getActivePlayers()){
			//if(p.getPosition() != getPosition()){
				//index = p.getPosition();
				//break;
			//}
		//}
		System.out.println("Index: " + index);
		
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
		} else if (game.getPlayers().get(index).getRaiseBoolean()) {
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
					raise((int)(game.getMaxBetAmount() * (Math.random() * 3) + 1.25 ));
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
		else if(game.getPlayers().get(index).getCallBoolean()){
			call();
			System.out.println("Comp. Calls");
		}
		else{
			System.out.println("BROKEN.");
		}
	}
}
