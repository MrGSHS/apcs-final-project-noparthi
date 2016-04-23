package scripts;

import java.util.ArrayList;

public class Game {
	
	private final int BIGBLIND = 500;
	private final int SMALLBLIND = 250; 
	private final int ANTE = 50;
	
	private ArrayList<Player> players = new ArrayList<>();

	private Player user; 
	private Player computer1;
	private Round round;
	private Table table;
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
		round = new Round(this);
		round.preFlop();
		round.preTurn();
		round.preRiver();
		round.postRiver();
	}
	
	public void takeBlinds(){
		//Set Index Of Big Blind Player
		int bigBlindIndex = dealerIndex + 1;
		if(bigBlindIndex>=players.size()) bigBlindIndex = 0;
		//Set Index Of Small Blind Player
		int smallBlindIndex = bigBlindIndex + 1;
		if(smallBlindIndex>=players.size()) smallBlindIndex = 0; 
		//Takes The Big And Small Blind
		players.get(bigBlindIndex).setPoints(players.get(bigBlindIndex).getPoints()-BIGBLIND);
		players.get(smallBlindIndex).setPoints(players.get(smallBlindIndex).getPoints()-SMALLBLIND);
	}
	
	public void takeAnte(){
		for(Player p : players) p.setPoints(p.getPoints()-ANTE);		
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
