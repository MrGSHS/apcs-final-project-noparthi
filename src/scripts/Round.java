package scripts;

public class Round {

	private static int roundNumber = 0;
	private Game game;

	private int pot;
	private int minBet;
	private int pastBet;
	/*
	 * 0 = pre flop 1 = pre turn 2 = pre river 3 = post river
	 */
	public int stageOfRound;

	public Round(Game game) {
		pot = 0;
		minBet = 0;
		stageOfRound = 0;
		this.game = game;
		roundNumber++;
		game.getUser().newHand();
		game.getComputer().newHand();
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
		game.getDisplay().update();
		game.takeAnte();
		// Prints Out Hand Before Flop, Then Deals Flop
		setMinBet(game.getBigBlind());

	}

	public void preTurn() {
		game.getDisplay().update();
		minBet = 500;
		// Prints Out Hand Before Turn, Then Deals Turn

	}

	public void preRiver() {
		game.getDisplay().update();
		minBet = 500;
		;
		// Prints Out Hand Before River, Then Deals River
		// printTable();
	}

	public void postRiver() {
		game.getDisplay().update();
		// Prints Hand Before Show-down, And Asks For Final Raise/Check/Fold
		// printTable();
	}
/*
	private void printTable() {
		int strength;
		System.out.println("*********************************");
		System.out.println("Round: " + roundNumber + "\n");
		System.out.println("The current pot is: " + pot);
		System.out.println("The minimum bet is: " + minBet);

		System.out.println("\n" + "Current cards on the table: ");
		for (int i = 0; i < game.getTable().getCardsOnTable().size(); i++) {
			System.out.print("[" + game.getTable().getCardsOnTable().get(i).getNumber()
					+ game.getTable().getCardsOnTable().get(i).getEncodedSuite() + "]" + " ");
		}
		System.out.println("\n");

		System.out.println("Your hand is:");
		System.out.print("[" + game.getUser().getCurrentHand()[0].getNumber()
				+ game.getUser().getCurrentHand()[0].getEncodedSuite() + "] ");
		System.out.println("[" + game.getUser().getCurrentHand()[1].getNumber()
				+ game.getUser().getCurrentHand()[1].getEncodedSuite() + "]");
		System.out.println("Computer's hand is: ");
		System.out.print("[" + game.getComputer().getCurrentHand()[0].getNumber()
				+ game.getComputer().getCurrentHand()[0].getEncodedSuite() + "] ");
		System.out.println("[" + game.getComputer().getCurrentHand()[1].getNumber()
				+ game.getComputer().getCurrentHand()[1].getEncodedSuite() + "]");
		String meter = "[";

		if (game.getTable().getCardsOnTable().size() == 0)
			strength = game.getUser().getHand().initialHandStrength();
		else
			strength = game.getUser().getHand().updateHandStrength();

		// Print Meter
		for (int i = 0; i < 10; i++) {
			if (strength > 0)
				meter += "\u25A0";
			else
				meter += " ";
			strength--;
		}
		meter += "]";
		System.out.println("\n" + "Hand Strength: " + meter + "\n");
		System.out.println("You have: " + game.getUser().getPoints() + " points");
		System.out.println("*********************************");
	}
*/
	
	public void moveOn() {
		game.getComputer().takeAction();
		/*if (game.getComputer().getTurn()) {
			game.getComputer().takeAction();
		} else {
			if (game.getComputer().getRaiseBoolean()) {
				game.getComputer().setTurn(false);
				return;
			}
		}
		*/
		if (stageOfRound == 0) {
			stageOfRound++;
			System.out.println(stageOfRound);
			game.getTable().dealFlop();
			preTurn();
		} else if (stageOfRound == 1) {
			stageOfRound++;
			System.out.println(stageOfRound);
			game.getTable().dealTurn();
			preRiver();
		} else if (stageOfRound == 2) {
			stageOfRound++;
			System.out.println(stageOfRound);
			game.getTable().dealRiver();
			postRiver();
		} else {
			game.payout();
		}
	}
}
