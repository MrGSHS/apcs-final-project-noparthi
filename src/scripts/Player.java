package scripts;

public class Player {

	private Game game;
	private Hand hand;

	private int points;
	private int betAmount;
	
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
	public Hand getHand(){ return hand; }
	public Card[] getCurrentHand() { return hand.getHand(); }
	
	public boolean isBigBlind(){ return bigBlind; }
	public boolean isSmallBlind() {return smallBlind; }
	public boolean isFolded(){ return folded; }	

	public void setPoints(int num) { points = num; }
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
		folded = true;
		game.isRoundActive();
	}

	public void check() {
		resetActionBoolean();
		checkBoolean = true;
	}

	public void call() {
		int maxBet = 0;
		int bet = 0;
		for(Player p : game.getPlayers()){
			bet = p.getBetAmount();
			if(bet > maxBet)maxBet = bet;
		}
		maxBet -= betAmount;
		game.getRound().setPot(game.getRound().getPot() + maxBet);
		points -= maxBet;
		
		resetActionBoolean();
		callBoolean = true;
	}

	public boolean raise(int amt) {
		amt -= betAmount;
		if (amt >= game.getRound().getMinBet()) {
			if (amt > points) {
				betAmount = points;
				game.getRound().setPot(game.getRound().getPot()+points);
				points = 0;
				
				resetActionBoolean();
				raiseBoolean = true;
				return true;
			}
			betAmount = amt;
			game.getRound().setPot(game.getRound().getPot() + betAmount);
			game.getRound().setMinBet(amt);
			points -= amt;
			
			resetActionBoolean();
			raiseBoolean = true;
			return true;
		}
		else{
			System.out.println("Must raise at least something greater than or equal to " + game.getRound().getMinBet());		
			return false;		
		}
	}
	
	public void setBetAmount(int amt){
		betAmount = amt;
	}

	public int getBetAmount(){
		return betAmount;
	}
}
