package scripts;

public class Game {
	static Player user;
	static Player computer;
	
	static Deck deck;
	static Round round;
	public Game(){}
	
	public static void main(String[] args){
		deck = new Deck();
		user = new Player();
		computer = new Computer();
		round = new Round();
	}
	
	public void newRound(){ round = new Round(); }
	public Round getRound(){ return round; }
	public static Deck getDeck(){ return deck; } 
	public Player getPlayer(){ return user; }	
	public Player getComputer(){ return computer; }
}
