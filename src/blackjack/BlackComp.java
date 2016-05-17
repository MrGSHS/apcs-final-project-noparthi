package blackjack;

import java.util.*;

import holdem.Player;
import scripts.Card;
public class BlackComp extends BlackPlayer {

	
	private BlackPlayer dude;
	
	public BlackComp(BlackPlayer play){
		super(Integer.MAX_VALUE);
		dude=play;
	}
	
	public void hit(){
		super.hit();
		if (player.getIn() && player.getPoints()==points && points<=14){
			hand.add(new Card());
		}
		points=this.calc();
		if (points>21) in=false;
	}

}