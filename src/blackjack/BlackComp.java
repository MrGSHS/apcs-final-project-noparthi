package blackjack;

package blackjack;
import java.util.*;

import scripts.Card;
public class BlackComp extends BlackPlayer {
	Card initShown;
	Card initHid;
	ArrayList<Card> hand;
	int points=0;
	
	public BlackComp(){
		initShown=new Card();
		initHid=new Card();
		hand=new ArrayList<>();
		hand.add(initShown);
		hand.add(initHid);
		points=this.calc();
	}
	
	public void hit(){
		if (player.in() && player.getPoints()==points && points<=14){
			hand.add(new Card());
		}
		points=this.calc();
	
	}	
		
	}

}