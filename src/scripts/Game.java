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

	public Player getComputer() {
		return computer1;
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

	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public Game() {
		table = new Table();
		user = new Player(this);
		players.add(user);
		computer1 = new Computer(this);
		players.add(computer1);
		round = new Round(this);
		display = new Display(this);
		takeBlinds();
		display.setRoundTitle();
		round.preFlop();
	}

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

	public void takeAnte() {
		for (Player p : players) {
			if (!p.isBigBlind() && !p.isSmallBlind()) {
				p.setPoints(p.getPoints() - ANTE);
				p.setBetAmount(ANTE);		
				round.setPot(round.getPot() + ANTE);
			}
		}
	}

	public void payout() {
		user.setPoints(user.getPoints() + round.getPot());
		newRound();
	}

	public void resetPlayerBetAmount() {
		for (Player p : players)
			p.setBetAmount(0);
	}

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
