package scripts;

import java.util.ArrayList;

public class Game {
	
	private final int ANTE = 500;
	
	private ArrayList<Player> players = new ArrayList<>();

	private Player user; 
	private Player computer;
	private Round round;
	private Table table;
	private boolean roundActive;
	public Table getTable(){ return table; }
	public Round getRound(){ return round; }
	public Player getPlayer(){ return user; }	
	public Player getComputer(){ return computer; }
	public int getAnte(){return ANTE;}
	public ArrayList<Player> getPlayers(){ return players; }
	
	public Game(){
		table = new Table();
		user = new Player(this);
		players.add(user);
		//computer = new Computer(this);
		roundActive = true;
		round = new Round(this);
		round.preFlop();
		round.preTurn();
		round.preRiver();
		round.postRiver();
	}
	
	public void takeAnte(){
		for(Player p : players)
			p.setPoints(p.getPoints() - ANTE);
	}
	
	public boolean isRoundActive(){
		int numberOfPlayers = players.size();
		int count = 0;
		for(Player p : players)
			if(p.isFolded()) count++;
		if(count > 0 && count < numberOfPlayers){ return true; }
		round = new Round(this);
		round.preFlop();
		return false;
	}	
}
