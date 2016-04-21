package scripts;

import java.util.Scanner;

public class Round extends Game{
	
	Scanner sc;
	Actions actions;
	int maxBet = 0;
	static Table table = new Table();
	static int roundNumber = 0;
	public Round(){
		roundNumber++;
		System.out.println("\nRound Numer: " + roundNumber + "\n");
		sc = new Scanner(System.in);
		getPlayer().newHand();
		getComputer().newHand();
		preFlop();
	}
	
	public void preFlop(){ 
		printHand();
	    requestAction();
	}
	
	public static Table getTable(){ return table; }
	
	private void printHand(){
		System.out.println("Your hand is: ");
	    System.out.println("Number: " + getPlayer().getHand()[0].getNumber() + "\t Type: " + getPlayer().getHand()[0].getSuite()); 
	    System.out.println("Number: " + getPlayer().getHand()[1].getNumber() + "\t Type: " + getPlayer().getHand()[1].getSuite());
	}
	

	public void requestAction(){
		System.out.println("What would you like to do?");
		String action = sc.nextLine();
		action = action.toUpperCase();
		if(action.equals(actions.CHECK)) getPlayer().check();
	}
}
