package blackjack;
import scripts.Deck;

public class BlackRound {
	private static int roundNumber = 0;
	private static BlackPlayer player=new BlackPlayer();
	private static BlackComp deal=new BlackComp(player);
	private int bet;
	private Deck use;
	private boolean whoWins;
	
	public BlackRound(int playerBet){
		bet=playerBet;
		use=new Deck();
		roundNumber++;
	}

	
	public int getRound(){
		return roundNumber;
	}
	
	public void setPlayer(BlackPlayer a){
		player = a;
	}
	public BlackPlayer getPlayer(){
		return player;
		
	}
	public BlackComp getComputer(){
		return deal;
	}
		
	}
		
	



