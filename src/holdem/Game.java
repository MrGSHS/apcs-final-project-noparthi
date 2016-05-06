package holdem;

import java.util.ArrayList;

public class Game {

	private Display display;
	private final int BIGBLIND = 500;
	private final int SMALLBLIND = 250;
	private final int ANTE = 100;
	private int bigBlindIndex;
	private int smallBlindIndex;
	private ArrayList<Player> players = new ArrayList<>();

	private Player user;
	private Round round;
	private Table table;
	private int dealerIndex = 0;

	public ArrayList<int[]> playerPositions = new ArrayList<>();

	public int getBigBlindIndex(){
		return bigBlindIndex;
	}
	
	public int getSmallBlindIndex(){
		return smallBlindIndex;
	}
	
	public Display getDisplay() {
		return display;
	}

	public Table getTable() {
		return table;
	}

	public Round getRound() {
		return round;
	}

	public Player getUser() {
		return user;
	}

	public int getBigBlind() {
		return BIGBLIND;
	}

	public int getSmallBlind() {
		return SMALLBLIND;
	}

	public int getDealerIndex() {
		return dealerIndex;
	}

	public int getMaxBetAmount() {
		int max = 0;
		for (Player p : players) {
			if (p.getBetAmount() > max) {
				max = p.getBetAmount();
			}
		}
		return max;
	}

	public void setDealerIndex(int index) {
		if (index >= players.size())
			dealerIndex = 0;
		else
			dealerIndex = index;
	}

	// Index 0 Is A Player, The Rest Are Computers
	public ArrayList<Player> getPlayers() {
		return players;
	}

	public ArrayList<Player> getActivePlayers() {
		ArrayList<Player> active = new ArrayList<>();
		for (Player p : players) {
			if (!p.isFolded())
				active.add(p);
		}
		return active;
	}

	public ArrayList<Player> getActiveComputers() {
		ArrayList<Player> active = new ArrayList<>();
		for (Player p : players) {
			if (!p.isFolded()) //p.getPosition() != 0 && 
				active.add(p);
		}
		return active;
	}

	public void allComputersTakeAction() {
		ArrayList<Player> computers = getActiveComputers();
		for (Player computer : computers) {
			computer.takeAction();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(getRound().moveOn())
				break;
		}
	}

	public Game() {
		int pos = 0;
		table = new Table();
		user = new Player(this, pos++);
		players.add(user);
		Player computer1 = new Computer(this, pos++);
		players.add(computer1);
		Player computer2 = new Computer(this, pos++);
		players.add(computer2);
		Player computer3 = new Computer(this, pos++);
		players.add(computer3);
		Player computer4 = new Computer(this, pos++);
		players.add(computer4);
		round = new Round(this);
		display = new Display(this);
		takeBlinds();
		takeAnte();
		display.setRoundTitle();
		round.preFlop();
	}

	// Takes The Blinds
	public void takeBlinds() {
		// Set Index Of Big Blind Player
		bigBlindIndex = dealerIndex + 1;
		if (bigBlindIndex >= players.size())
			bigBlindIndex = 0;
		players.get(bigBlindIndex).setBigBlind(true);
		players.get(bigBlindIndex).raise(BIGBLIND);

		// Set Index Of Small Blind Player
		smallBlindIndex = bigBlindIndex + 1;
		if (smallBlindIndex >= players.size())
			smallBlindIndex = 0;
		players.get(smallBlindIndex).setSmallBlind(true);

		// Takes The Big And Small Blind
		//players.get(bigBlindIndex).raise(BIGBLIND);
		System.out.println(bigBlindIndex);
		players.get(smallBlindIndex).setPoints(players.get(smallBlindIndex).getPoints() - SMALLBLIND);
		players.get(smallBlindIndex).setPointsInvested(SMALLBLIND);
		players.get(smallBlindIndex).setBetAmount(SMALLBLIND);
		round.setPot(round.getPot() + SMALLBLIND);
	}

	// Takes Ante
	public void takeAnte() {
		for (Player p : players) {
			if (!p.isBigBlind() && !p.isSmallBlind()) {
				p.setPoints(p.getPoints() - ANTE);
				p.setPointsInvested(ANTE);
				p.setBetAmount(ANTE);
				round.setPot(round.getPot() + ANTE);
			}
		}
	}

	// Pays Out Money In Pot To Winner
	public void payout() {
		ArrayList<Integer> strongestPlayersIndex = new ArrayList<>();
		strongestPlayersIndex.add(0);
		//TODO: Add In Kickers And High Card 
		for (int i = 1; i < getActivePlayers().size(); i++) {
			//If One User's Hand Strength Is Better Than The Best Currently
			if (getActivePlayers().get(i).getHand().updateHandStrength() > getActivePlayers().get(strongestPlayersIndex.get(0)).getHand().updateHandStrength()) {
				strongestPlayersIndex = new ArrayList<Integer>();
				strongestPlayersIndex.add(i);
			} 
			//If Final Hand Strengths Are Equal
			else if (getActivePlayers().get(i).getHand().updateHandStrength() == getActivePlayers().get(strongestPlayersIndex.get(0)).getHand().updateHandStrength()) {
				//Four Of A Kind
				if(getActivePlayers().get(i).getHand().getCurrentHandStrengthString().equals("Four Of A Kind")){
					if(getActivePlayers().get(i).getHand().getQuads()==getActivePlayers().get(strongestPlayersIndex.get(0)).getHand().getQuads())
						strongestPlayersIndex.add(i);
					else if(getActivePlayers().get(i).getHand().getQuads() > getActivePlayers().get(strongestPlayersIndex.get(0)).getHand().getQuads()){
						strongestPlayersIndex = new ArrayList<Integer>();
						strongestPlayersIndex.add(i);
					}
				}
				
				//Full House
				if(getActivePlayers().get(i).getHand().getCurrentHandStrengthString().equals("Full House")){
					if(getActivePlayers().get(i).getHand().getFullHouse().get(0)==getActivePlayers().get(strongestPlayersIndex.get(0)).getHand().getFullHouse().get(0))
						//If The Triples Are Equivalent
						if(getActivePlayers().get(i).getHand().getFullHouse().get(1)==getActivePlayers().get(strongestPlayersIndex.get(0)).getHand().getFullHouse().get(1))
							strongestPlayersIndex.add(i);
						else if(getActivePlayers().get(i).getHand().getFullHouse().get(1) > getActivePlayers().get(strongestPlayersIndex.get(0)).getHand().getFullHouse().get(1)){
							strongestPlayersIndex = new ArrayList<Integer>();
							strongestPlayersIndex.add(i);
						}
					else if(getActivePlayers().get(i).getHand().getFullHouse().get(0) > getActivePlayers().get(strongestPlayersIndex.get(0)).getHand().getFullHouse().get(0)){
						strongestPlayersIndex = new ArrayList<Integer>();
						strongestPlayersIndex.add(i);
					}
				}
				
				//Flush
				if(getActivePlayers().get(i).getHand().getCurrentHandStrengthString().equals("Flush")){
					strongestPlayersIndex.add(i);
				}
				
				//Straight
				if(getActivePlayers().get(i).getHand().getCurrentHandStrengthString().equals("Straight")){
					if(getActivePlayers().get(i).getHand().getStraight()==getActivePlayers().get(strongestPlayersIndex.get(0)).getHand().getStraight())
						strongestPlayersIndex.add(i);
					else if(getActivePlayers().get(i).getHand().getStraight() > getActivePlayers().get(strongestPlayersIndex.get(0)).getHand().getStraight()){
						strongestPlayersIndex = new ArrayList<Integer>();
						strongestPlayersIndex.add(i);
					}
				}
				
				//Three Of A Kind
				if(getActivePlayers().get(i).getHand().getCurrentHandStrengthString().equals("Three Of A Kind")){
					if(getActivePlayers().get(i).getHand().getTrips()==getActivePlayers().get(strongestPlayersIndex.get(0)).getHand().getTrips())
						strongestPlayersIndex.add(i);
					else if(getActivePlayers().get(i).getHand().getTrips() > getActivePlayers().get(strongestPlayersIndex.get(0)).getHand().getTrips()){
						strongestPlayersIndex = new ArrayList<Integer>();
						strongestPlayersIndex.add(i);
					}
				}
				
				//Two Pair
				if(getActivePlayers().get(i).getHand().getCurrentHandStrengthString().equals("Two-Pair")){
					if(getActivePlayers().get(i).getHand().getTwoPair().get(0)==getActivePlayers().get(strongestPlayersIndex.get(0)).getHand().getTwoPair().get(0))
						//If The High Pair Are Equivalent
						if(getActivePlayers().get(i).getHand().getTwoPair().get(1)==getActivePlayers().get(strongestPlayersIndex.get(0)).getHand().getTwoPair().get(1))
							strongestPlayersIndex.add(i);
						else if(getActivePlayers().get(i).getHand().getTwoPair().get(1) > getActivePlayers().get(strongestPlayersIndex.get(0)).getHand().getTwoPair().get(1)){
							strongestPlayersIndex = new ArrayList<Integer>();
							strongestPlayersIndex.add(i);
						}
					else if(getActivePlayers().get(i).getHand().getTwoPair().get(0) > getActivePlayers().get(strongestPlayersIndex.get(0)).getHand().getTwoPair().get(0)){
						strongestPlayersIndex = new ArrayList<Integer>();
						strongestPlayersIndex.add(i);
					}
				}
				
				//Pair
				if(getActivePlayers().get(i).getHand().getCurrentHandStrengthString().equals("Pair")){
					if(getActivePlayers().get(i).getHand().getPair()==getActivePlayers().get(strongestPlayersIndex.get(0)).getHand().getPair())
						strongestPlayersIndex.add(i);
					else if(getActivePlayers().get(i).getHand().getPair() > getActivePlayers().get(strongestPlayersIndex.get(0)).getHand().getPair()){
						strongestPlayersIndex = new ArrayList<Integer>();
						strongestPlayersIndex.add(i);
					}
				}
				
			}
		}
		int numberShared = strongestPlayersIndex.size();
		for(int i : strongestPlayersIndex){
			getActivePlayers().get(i).setPoints((int)(getActivePlayers().get(i).getPoints() + round.getPot()/numberShared));
		}
		newRound();
	}

	// Reset Each Players Bet Amount At End Of Each Turn
	public void resetPlayerBetAmount() {
		for (Player p : players)
			p.setBetAmount(0);
	}

	// Checks To See If 2 Or More Players Are Still Active
	public boolean isRoundActive() {
		if (getActivePlayers().size() > 1)
			return true;

		for (Player p : players) {
			if (p.isFolded()) {
				p.resetActionBoolean();
			}
		}
		payout();
		return false;
	}

	// Creates New Round
	public void newRound() {
		for (Player p : players) {
			p.unFold();
			p.resetPointsInvested();
		}
		resetPlayerBetAmount();
		setDealerIndex(dealerIndex += 1);
		table = new Table();
		round = new Round(this);
		takeBlinds();
		takeAnte();
		display.setRoundTitle();
		round.preFlop();
	}
}
