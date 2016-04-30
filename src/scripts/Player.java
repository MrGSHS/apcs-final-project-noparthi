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
	
	//Reset The Action Booleans
	public void resetActionBoolean(){
		checkBoolean = false;
		raiseBoolean = false;
		callBoolean = false;
	}
	
	//Fold Method
	public void fold() {
		folded = true;
		betAmount = 0;
		game.isRoundActive();
	}

	//Check Method
	public void check() {
		resetActionBoolean();
		betAmount = 0;
		checkBoolean = true;
	}

	//Call Method
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
		betAmount = 0;
		callBoolean = true;
	}

	//Raise Method
	public boolean raise(int amt) {
		//Subtracts Bet Amount If Made Earlier From This Time's Bet
		amt -= betAmount;
		//Guarantees That Raise Is Legal
		if (amt >= game.getRound().getMinBet()) {
			//All-In
			if (amt > points) {
				betAmount = points;
				game.getRound().setPot(game.getRound().getPot()+points);
				points = 0;
				
				resetActionBoolean();
				raiseBoolean = true;
				return true;
			}
			//Not All-In
			betAmount = amt;
			game.getRound().setPot(game.getRound().getPot() + betAmount);
			game.getRound().setMinBet(amt);
			points -= amt;
			
			resetActionBoolean();
			raiseBoolean = true;
			return true;
		}
		//Not Legal Message
		else{
			System.out.println("Must raise at least something greater than or equal to " + game.getRound().getMinBet());		
			return false;		
		}
	}
	
	public void unFold(){
		folded = false;
	}
	public void setBetAmount(int amt){
		betAmount = amt;
	}

	public int getBetAmount(){
		return betAmount;
	}
}
