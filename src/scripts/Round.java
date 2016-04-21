package scripts;

import java.util.Scanner;

public class Round extends Game{
	
	Scanner sc;
	int maxBet = 0;	
	int currentBet = 0;	
	int pot = 0;
	static int roundNumber = 0;
	Table table = new Table();
	public Round(){
		roundNumber++;
		System.out.println("\nRound Numer: " + roundNumber + "\n");
		sc = new Scanner(System.in);
		getPlayer().newHand();
		getComputer().newHand();
		preFlop();
	}
	
	public int getPot(){return pot;}
	public void setPot(int amt){ pot = amt; }
	
	public void raise(){ pot += 100; }
	public void preFlop(){ 
		printHand();
	    requestAction();
	}
	
	public Table getTable(){ return table; }
	
	private void printHand(){
		System.out.println("Your hand is: ");
	    System.out.println("Number: " + getPlayer().getCurrentHand()[0].getNumber() + "\t Type: " + getPlayer().getCurrentHand()[0].getSuite()); 
	    System.out.println("Number: " + getPlayer().getCurrentHand()[1].getNumber() + "\t Type: " + getPlayer().getCurrentHand()[1].getSuite());
	}
	

	public void requestAction(){
		System.out.println("What would you like to do?");
		String action = sc.nextLine();
		action = action.toUpperCase();
		if(action.equals(Actions.CHECK.toString())) getPlayer().check();
		if(action.equals(Actions.FOLD.toString())) getPlayer().fold();
		if(action.equals(Actions.RAISE.toString())) {
			//System.out.println("How much would you like to raise?");
			int raiseAmt = sc.nextInt();
			getPlayer().raise(raiseAmt);
		}

	}
	
}
