package scripts;

import java.util.ArrayList;

public class Game {
	
	private final int ANTE = 500;
	
	ArrayList<Player> players = new ArrayList<>();
	
	Player user;
	Player computer;
	Round round;
	Table table;
	public Table getTable(){ return table; }
	public Round getRound(){ return round; }
	public Player getPlayer(){ return user; }	
	public Player getComputer(){ return computer; }
	
	public Game(){
		table = new Table();
		System.out.println("Game");
		user = new Player(this);
		//computer = new Computer(this);
		round = new Round(this);
		players.add(user);
		round.preFlop();
	}
	
	public void takeAnte(){
		for(Player p : players)
			p.setPoints(p.getPoints() - ANTE);
	}
	
	
}
