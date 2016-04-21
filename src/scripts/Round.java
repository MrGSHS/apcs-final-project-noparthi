package scripts;

import java.util.Scanner;

public class Round extends Game{
	
	Scanner sc;
	Actions actions;
	int currentBet = 0;
	int maxBet = 0;
	
	int pot;
	public int getPot(){return pot;}
	public void setPot(int amt){ pot = amt; }
	
	static Table table = new Table();
	static int roundNumber = 0;
	public Round(){
		roundNumber++;
		pot = 0;
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
	    System.out.println("Number: " + getPlayer().getCurrentHand()[0].getNumber() + "\t Type: " + getPlayer().getCurrentHand()[0].getSuite()); 
	    System.out.println("Number: " + getPlayer().getCurrentHand()[1].getNumber() + "\t Type: " + getPlayer().getCurrentHand()[1].getSuite());
	}
	

	public void requestAction(){
		System.out.println("What would you like to do?");
		String action = sc.nextLine();
		action = action.toUpperCase();
		if(action.equals(actions.CHECK)) getPlayer().check();
		if(action.equals(actions.FOLD)) getPlayer().fold();
		if(action.equals(actions.RAISE)) {
			System.out.println("How much would you like to raise?");
			int raiseAmt = sc.nextInt();
			sc.nextLine();
			getPlayer().raise(raiseAmt);
		}

	}
	
}
