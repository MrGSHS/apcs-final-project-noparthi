package blackjack;

package blackjack;
import java.util.*;

import scripts.Card;
public class BlackComp extends BlackPlayer {
	private Card initShown;
	private Card initHid;
	private ArrayList<Card> hand;
	private int points=0;
	private boolean in=true;
	
	public BlackComp(){
		initShown=new Card();
		initHid=new Card();
		hand=new ArrayList<>();
		hand.add(initShown);
		hand.add(initHid);
		points=this.calc();
	}
	
	public void hit(){
		if (player.getIn() && player.getPoints()==points && points<=14){
			hand.add(new Card());
		}
		points=this.calc();
		if (points>21) in=false;
	
<<<<<<< Updated upstream
	}	
=======
	}
	
		
>>>>>>> Stashed changes
		
	}

}