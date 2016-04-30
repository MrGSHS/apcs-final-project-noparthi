package scripts;

import java.util.ArrayList;

public class Game{
	
	private Display display;
	private final int BIGBLIND = 500;
	private final int SMALLBLIND = 250;
	private final int ANTE = 50;
	private ArrayList<Player> players = new ArrayList<>();

	private Player user;
	private Player computer1;
	private Player computer2;
	private Round round;
	private Table table;
	private int dealerIndex = 0;

	public Display getDisplay(){ return display; }
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

	public void setDealerIndex(int index) {
		if (index >= players.size()) dealerIndex = 0;
		else dealerIndex = index;
	}

	//Index 0 Is A Player, The Rest Are Computers
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	private ArrayList<Player> getActivePlayers(){
		ArrayList<Player> active = new ArrayList<>();
		for(Player p : players){
			if(!p.isFolded())
				active.add(p);
		}
		return active;
	}
	
	public void allComputersTakeAction(){
		for(int i = 1; i < players.size(); i++)
			players.get(i).takeAction();
	}
	public Game() {
		table = new Table();
		user = new Player(this);
		players.add(user);
		computer1 = new Computer(this);
		players.add(computer1);
		computer2 = new Computer(this);
		players.add(computer2);
		
		round = new Round(this);
		display = new Display(this);
		takeBlinds();
		display.setRoundTitle();
		round.preFlop();
	}

	//Takes The Blinds
	public void takeBlinds() {
		// Set Index Of Big Blind Player
		int bigBlindIndex = dealerIndex + 1;
		if (bigBlindIndex >= players.size())
			bigBlindIndex = 0;
		players.get(bigBlindIndex).setBigBlind(true);
		
		// Set Index Of Small Blind Player
		int smallBlindIndex = bigBlindIndex + 1;
		if (smallBlindIndex >= players.size())
			smallBlindIndex = 0;
		players.get(smallBlindIndex).setSmallBlind(true);
		
		// Takes The Big And Small Blind
		players.get(bigBlindIndex).raise(BIGBLIND);
		players.get(smallBlindIndex).setPoints(players.get(smallBlindIndex).getPoints() - SMALLBLIND);
		players.get(smallBlindIndex).setBetAmount(SMALLBLIND);
		round.setPot(round.getPot() + SMALLBLIND);
	}

	//Takes Ante
	public void takeAnte() {
		for (Player p : players) {
			if (!p.isBigBlind() && !p.isSmallBlind()) {
				p.setPoints(p.getPoints() - ANTE);
				p.setBetAmount(ANTE);		
				round.setPot(round.getPot() + ANTE);
			}
		}
	}

	//Pays Out Money In Pot To Winner
	public void payout() {
		int strongestPlayerIndex = 0;
		if(getActivePlayers().size()==1)
			strongestPlayerIndex = 0;
		else{
			for(int i = 1; i < getActivePlayers().size(); i++){
				if(getActivePlayers().get(i).getHand().updateHandStrength() > getActivePlayers().get(strongestPlayerIndex).getHand().updateHandStrength()){
					strongestPlayerIndex = i;
				}
				else if(getActivePlayers().get(i).getHand().updateHandStrength() == getActivePlayers().get(strongestPlayerIndex).getHand().updateHandStrength()){
					// TODO: Need To Finish
				}
			}	
		}
		getActivePlayers().get(strongestPlayerIndex).setPoints(user.getPoints() + round.getPot());
		newRound();
	}

	//Reset Each Players Bet Amount At End Of Each Turn
	public void resetPlayerBetAmount() {
		for (Player p : players)
			p.setBetAmount(0);
	}

	//Checks To See If 2 Or More Players Are Still Active
	public boolean isRoundActive() {
		int count = 0;
		for (Player p : players)
			if (p.isFolded())
				count++;

		if (count != 1) {
			return true;
		}
		for (Player p : players) {
			p.resetActionBoolean();
		}
		payout();
		return false;
	}

	//Creates New Round
	public void newRound() {
		resetPlayerBetAmount();
		setDealerIndex(dealerIndex+=1);
		table = new Table();
		round = new Round(this);
		takeBlinds();
		display.setRoundTitle();
		round.preFlop();
	}
}
