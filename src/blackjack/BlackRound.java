package blackjack;


public class BlackRound {
	private static int roundNumber = 0;
	private static BlackPlayer play=new BlackPlayer();
	private static BlackComp deal=new BlackComp(play);
	private int bet;
	private Deck use;
	private boolean whoWins;
	public BlackRound(int playerBet){
		play.makeBet(playerBet);
		bet=playerBet;
		use=new Deck();
		roundNumber++;
		
	}
	public boolean tryHit(){
		boolean isIn=play.hit();
		if (!isIn) {
			whoWins=false;
		}
		
	}
	
	public int getRound(){
		return roundNumber;
	}
	
	public void setPlayer(Player a){
		play=a;
	}
	public BlackPlayer getPlayer(){
		return play;
		
	}
	public BlackComp getComputer(){
		return deal;
	}
		
	}
		
	}



