package holdem;

public class Round {

	private static int roundNumber = 0;
	private Game game;

	private int pot;
	private int minBet;
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
		for(Player p : game.getPlayers()){
			p.newHand();
		}
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

	//Before Flop Actions
	public void preFlop() {
		// Prints Out Hand Before Flop, Then Deals Flop
		setMinBet(game.getBigBlind());

	}

	//Before Turn Actions 
	public void preTurn() {
		minBet = 500;

	}

	//Before River Actions
	public void preRiver() {
		minBet = 500;
	}

	//After River Actions (Final Actions)
	public void postRiver() {
		minBet = 500;
	}

	//Check To See If Should Move To Next Round
	public boolean moveOn() {
		//Skips If Someone Had Raised, And Resets All Action Booleans Back To Null
		int check = 0;
		int call = 0;
		int raise = 0;
		for(Player p : game.getActivePlayers()){
			if(p.getCheckBoolean())
				check++;
			if(p.getCallBoolean())
				call++;
			if(p.getRaiseBoolean())
				raise++;
		}	
		
		if(check != game.getActivePlayers().size() && ((call+raise)!= game.getActivePlayers().size() || raise > 1)){  
			System.out.println("Failed Move-On");
			return false;
		}
		
		//Else Reset Stuff
		game.resetPlayerBetAmount();
		for(Player p : game.getPlayers()){
			p.resetActionBoolean();
		}

		//And Proceeds To Next Round If None Of The Above Are True
		if (stageOfRound == 0) {
			stageOfRound++;
			System.out.println("Stage: " + stageOfRound);
			game.getTable().dealFlop();
			preTurn();
		} else if (stageOfRound == 1) {
			stageOfRound++;
			System.out.println("Stage: " + stageOfRound);
			game.getTable().dealTurn();
			preRiver();
		} else if (stageOfRound == 2) {
			stageOfRound++;
			System.out.println("Stage: " + stageOfRound);
			game.getTable().dealRiver();
			postRiver();
		} else 
			game.payout();
		return true;
	}
}
