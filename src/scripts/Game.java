package scripts;

public class Game {
	Player user;
	Player computer;
	
	public Game(){
		user = new User();
		computer = new Computer();
	}
	
	public Player getPlayer(){ return user; }	
	public Player getComputer(){ return computer; }
}
