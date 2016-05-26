package blackjack;

import java.util.*;
import scripts.Card;

public class BlackGame {
	private BlackDisplay display;
	private BlackRound round;
	private String cont = "y";
	private BlackPlayer user = new BlackPlayer();
	private BlackPlayer computer = new BlackComp();
	private int pot;
	public BlackRound getRound() {
		return round;
		
	}

	public BlackPlayer getUser() {
		return user;
	}

	public BlackPlayer getComputer() {
		return computer;
	}

	public BlackGame() {
		display = new BlackDisplay(this);
		start();
	}
	
	public int getPot(){
		return 2*pot;
	}
	public void setPot(int pot){
		this.pot=user.makeBet(pot);
	}

	public void start() {
		new Thread(new Runnable() {
			public void run() {
				display.update();
				while (!user.getStand() && user.calc() < 21) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					display.update();
				}
				if(user.calc() > 21) display.restart(false);
				else {
					while (!computer.takeAction()) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						display.update();
					}
					System.out.println("Computer total: " + computer.calc());
					System.out.println("Player total: " + user.calc());
					display.restart(false);
				}
			}
		}).start();
	}
}
