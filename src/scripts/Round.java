package scripts;

import java.util.Scanner;

public class Round extends Game{
	
	Scanner sc;
	
	public Round(){
		System.out.println("There are this many cards " + Card.id);
		sc = new Scanner(System.in);
		getPlayer().newHand();
		getComputer().newHand();
		preFlop();
	}
	
	
	public void preFlop(){
		System.out.println("Your hand is: ");
	    System.out.println("Number: " + getPlayer().getHand()[0].getNumber() + "\t Type: " + getPlayer().getHand()[0].getSuite()); 
	    System.out.println("Number: " + getPlayer().getHand()[1].getNumber() + "\t Type: " + getPlayer().getHand()[1].getSuite()); 
	}

}
