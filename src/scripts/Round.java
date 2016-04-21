package scripts;

import java.util.Scanner;

public class Round {
	
	Scanner sc;
	static int roundNumber = 0;
	Game game;
	public Round(Game game){
		this.game = game;
		roundNumber++;
		System.out.println("\nRound Numer: " + roundNumber + "\n");
		sc = new Scanner(System.in);
		game.getPlayer().newHand();
		//game.getComputer().newHand();
		preFlop();
	}
	
	public void preFlop(){ 
		printHand();
	    requestAction();
	}
	
	private void printHand(){
		System.out.println("Your hand is: ");
	    System.out.println("Number: " + game.getPlayer().getCurrentHand()[0].getNumber() + "\t Type: " + game.getPlayer().getCurrentHand()[0].getSuite()); 
	    System.out.println("Number: " + game.getPlayer().getCurrentHand()[1].getNumber() + "\t Type: " + game.getPlayer().getCurrentHand()[1].getSuite());
	}
	

	public void requestAction(){
		System.out.println("What would you like to do?");
		String action = sc.nextLine();
		action = action.toUpperCase();
		if(action.equals(Actions.CHECK.toString())) game.getPlayer().check();
		if(action.equals(Actions.FOLD.toString())) game.getPlayer().fold();
		if(action.equals(Actions.RAISE.toString())) {
			//System.out.println("How much would you like to raise?");
			int raiseAmt = sc.nextInt();
			game.getPlayer().raise(raiseAmt);
		}

	}
	
}
