package blackjack;

import holdem.Player;

public class BlackRound {
	private static int roundNumber = 0;
	private static BlackPlayer play=new BlackPlayer();
	private static BlackComp deal=new BlackComp();
	private int bet;
	public BlackRound(int playerBet){
		play.makeBet(playerBet);
		bet=playerBet;
		
	}
	public void updateHandAfterPlayerHits (){
		play.hit();
		if (play.calc()>21){
			deal.demGains(2*bet);
		}
			
			
	}
	
		
	}
		
	}



