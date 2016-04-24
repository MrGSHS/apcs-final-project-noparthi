package scripts;

public class Player {

	private Game game;
	private Hand hand;

	private int points;
	private int pointsInvested;
	
	private boolean folded;
	private boolean bigBlind;
	private boolean smallBlind; 
	public boolean checkBoolean = false;
	public boolean raiseBoolean = false;
	public boolean callBoolean = false;
	
	public Player(Game game) {
		this.game = game;
		points = 100000;
	}
	public void takeAction(){}
	
	public boolean getCheckBoolean(){return checkBoolean;}
	public boolean getRaiseBoolean(){return raiseBoolean;}
	public boolean getCallBoolean(){return callBoolean;}

	public int getPoints() { return points; }
	public int getPointsInvested(){ return pointsInvested; }
	public Hand getHand(){ return hand; }
	public Card[] getCurrentHand() { return hand.getHand(); }
	
	public boolean isBigBlind(){ return bigBlind; }
	public boolean isSmallBlind() {return smallBlind; }
	public boolean isFolded(){ return folded; }	

	public void setPoints(int num) { points = num; }
	public void setPointsInvested(int amt){ pointsInvested = amt; }
	public void setBigBlind(boolean bb){ bigBlind = bb; }
    public void setSmallBlind(boolean sb) {smallBlind = sb;}
    
	public void newHand() {
		hand = new Hand(game, game.getTable().getDeck().deal(), game.getTable().getDeck().deal());
	}
	
	public void resetActionBoolean(){
		checkBoolean = false;
		raiseBoolean = false;
		callBoolean = false;
	}
	
	public void fold() {
		hand = null;
		folded = true;
		game.isRoundActive();
	}

	public boolean check() {
		checkBoolean = true;
		return true;
	}

	public boolean call() {
		if(pointsInvested >= game.getRound().getBet()) return false; 
		if (game.getRound().getBet() > points) {
			game.getRound().setPot(game.getRound().getPot() + points);
			points = 0;
			return false;
		}
		game.getRound().setPot(game.getRound().getPot() + game.getRound().getBet());
		points -= game.getRound().getBet();
		callBoolean = true;
		return true;
	}

	public boolean raise(int amt) {
		if (amt >= game.getRound().getBet()) {
			if (amt > points) {
				game.getRound().setPot(game.getRound().getPot()+points);
				points = 0;
				raiseBoolean = true;
				return true;
			}
			game.getRound().setPot(game.getRound().getPot() + amt);
			game.getRound().setBet(amt);
			points -= amt;
			raiseBoolean = true;
			return true;
		}
		System.out.println("Must raise at least something greater than or equal to " + game.getRound().getBet());		
		return false;		
	}
	
}
