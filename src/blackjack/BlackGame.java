package blackjack;

import java.util.*;
import scripts.Card;

public class BlackGame {
	private BlackRound round;
	private String cont = "y";

	private 
	public BlackRound getRound() {
		return round;
	}

	public BlackGame() {
		start();
	}
	
	public void start(){
		new Thread(new Runnable(){
			public void run(){
				while(!userStand){
					Thread.sleep(100);
				}
				
			}
		}).start();
	} 
}
