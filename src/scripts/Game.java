package scripts;

import java.util.ArrayList;

public class Game {
	
	private final int BIGBLIND = 500;
	private final int SMALLBLIND = 250; 
	
	private ArrayList<Player> players = new ArrayList<>();

	private Player user; 
	private Player computer1;
	private Round round;
	private Table table;
	private boolean roundActive;
	private int dealerIndex = 0;
	
	
	public Table getTable(){ return table; }
	public Round getRound(){ return round; }
	public Player getPlayer(){ return user; }	
	public Player getComputer(){ return computer1; }
	public int getBigBlind(){return BIGBLIND;}
	public int getSmallBlind(){return SMALLBLIND;}
	public int getDealerIndex(){return dealerIndex;}
	public void setDealerIndex(int index) {
		if(index>=players.size()) dealerIndex = 0;
		else dealerIndex = index;
	}
	public ArrayList<Player> getPlayers(){ return players; }
	
	public Game(){
		table = new Table();
		user = new Player(this);
		players.add(user);
		//computer1 = new Computer(this);
		//players.add(computer1);
		roundActive = true;
		round = new Round(this);
		round.preFlop();
		round.preTurn();
		round.preRiver();
		round.postRiver();
	}
	
	public void takeBlinds(){
		//Set Index Of Big Blind Player
		int bigBlindPlayerIndex = dealerIndex + 1;
		if(bigBlindPlayerIndex>=players.size()) bigBlindPlayerIndex = 0;
		//Set Index Of Small Blind Player
		int smallBlindPlayerIndex = bigBlindPlayerIndex + 1;
		if(smallBlindPlayerIndex>=players.size()) smallBlindPlayerIndex = 0; 
		//Takes The Big And Small Blind
		players.get(bigBlindPlayerIndex).setPoints(players.get(bigBlindPlayerIndex).getPoints()-BIGBLIND);
		players.get(smallBlindPlayerIndex).setPoints(players.get(smallBlindPlayerIndex).getPoints()-SMALLBLIND);
	}
	
	public boolean isRoundActive(){
		int numberOfPlayers = players.size();
		int count = 0;
		for(Player p : players)
			if(p.isFolded()) count++;
		if(count > 0 && count < numberOfPlayers){ return true; }
		table = new Table();
		round = new Round(this);
		round.preFlop();
		return false;
	}	
}
