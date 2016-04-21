package scripts;

public class Game {
	static Player user;
	static Player computer;
	
	static Deck deck;
	
	
	public static void main(String[] args){
		deck = new Deck();
		user = new Player();
		computer = new Computer();
		new Round();
	}
	
	public static Deck getDeck(){ return deck; } 
	public Player getPlayer(){ return user; }	
	public Player getComputer(){ return computer; }
}
