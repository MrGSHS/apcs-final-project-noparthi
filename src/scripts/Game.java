package scripts;

public class Game {
	static Player user;
	static Player computer;
	Round round;
	
	public Game(){
		System.out.println("Game");
		user = new Player();
		computer = new Computer();
	}
	
	public void newRound(){ round = new Round(); }
	public Round getRound(){ return round; } 
	public Player getPlayer(){ return user; }	
	public Player getComputer(){ return computer; }
}
