package scripts;

public class Game {
	static Player user;
	static Player computer;
	
	static Deck deck;
	static Round round;
	
	public static void main(String[] args){
		System.out.println("Game init");
		deck = new Deck();
		user = new Player();
		computer = new Computer();
		round = new Round();
	}
	
	public static Round getRound(){ return round; }
	public static Deck getDeck(){ return deck; } 
	public Player getPlayer(){ return user; }	
	public Player getComputer(){ return computer; }
}
