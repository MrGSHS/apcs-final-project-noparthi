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
	
	
	public Table getTable(){ return table; }
	public Round getRound(){ return round; }
	public Player getPlayer(){ return user; }	
	public Player getComputer(){ return computer1; }
	public int getBigBlind(){return BIGBLIND;}
	public int getSmallBlind(){return SMALLBLIND;}
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
	
	public void takeBigBlind(){
		for(Player p : players)
			p.setPoints(p.getPoints() - BIGBLIND);
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
