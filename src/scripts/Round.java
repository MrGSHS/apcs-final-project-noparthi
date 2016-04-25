package scripts;

import java.util.Scanner;

public class Round {
	
	private Scanner sc;
	private static int roundNumber = 0;
	private Game game;
	
	private int pot;
	private int minBet;
	
	public Round(Game game){
		pot = 0;
		minBet = 0;
		this.game = game;
		roundNumber++;
		sc = new Scanner(System.in);
		game.getUser().newHand();
		game.getComputer().newHand();		
	}	
	public int getRoundNumber(){ return roundNumber; }
	public int getPot(){ return pot; }
	public int getMinBet(){ return minBet; }
	
	public void setPot(int amt){ pot = amt; }
	public void setMinBet(int amt){ minBet = amt; }
	
	public void preFlop(){
		game.getDisplay().update();
		game.takeAnte();
		//Prints  Out Hand Before Flop, Then Deals Flop
		setMinBet(game.getBigBlind());
		printTable();
		for(int i = 0; i < game.getPlayers().size(); i++){
			if(game.getPlayers().get(i).getCallBoolean()==true || game.getPlayers().get(i).getCheckBoolean()==true){}
			else{
				if(i == 0) requestAction();		
				else game.getComputer().takeAction();
			}
		}
		game.getTable().dealFlop();
		preTurn();

	}	
	public void preTurn(){
		game.getDisplay().update();
		minBet = 500;
		//Prints Out Hand Before Turn, Then Deals Turn
	    printTable();
		requestAction();
		game.getComputer().takeAction();
		game.getTable().dealTurn();
		preRiver();
	}	
	public void preRiver(){
		game.getDisplay().update();
		minBet = 500;;
		//Prints Out Hand Before River, Then Deals River
	    printTable();
		requestAction();
		game.getComputer().takeAction();
		game.getTable().dealRiver();
		postRiver();
	}	
	public void postRiver(){
		game.getDisplay().update();
		//Prints Hand Before Show-down, And Asks For Final Raise/Check/Fold
	    printTable();
		requestAction();
		game.getComputer().takeAction();
		game.payout();
	}
	
	
	
	private void printTable(){
		int strength;
		System.out.println("*********************************");
		System.out.println("Round: " + roundNumber + "\n");
		System.out.println("The current pot is: " + pot);
		System.out.println("The minimum bet is: " + minBet);
		
		System.out.println("\n" + "Current cards on the table: ");
		for(int i = 0; i < game.getTable().getCardsOnTable().size(); i++){
			System.out.print("[" + game.getTable().getCardsOnTable().get(i).getNumber() + game.getTable().getCardsOnTable().get(i).getEncodedSuite() + "]" + " ");
		}
		System.out.println("\n");
		
		System.out.println("Your hand is:");
	    System.out.print("[" + game.getUser().getCurrentHand()[0].getNumber() + game.getUser().getCurrentHand()[0].getEncodedSuite() + "] "); 
	    System.out.println("[" + game.getUser().getCurrentHand()[1].getNumber() + game.getUser().getCurrentHand()[1].getEncodedSuite() + "]");
	    System.out.println("Computer's hand is: ");
	    System.out.print("[" + game.getComputer().getCurrentHand()[0].getNumber() + game.getComputer().getCurrentHand()[0].getEncodedSuite() + "] "); 
	    System.out.println("[" + game.getComputer().getCurrentHand()[1].getNumber() + game.getComputer().getCurrentHand()[1].getEncodedSuite() + "]");
	    String meter = "[";
	    
	    if(game.getTable().getCardsOnTable().size()==0) strength = game.getUser().getHand().initialHandStrength();
	    else strength = game.getUser().getHand().updateHandStrength();
	    
	    
	    //Print Meter
	    for(int i = 0; i < 10; i++) {
	    	if(strength > 0) meter += "\u25A0";
	    	else meter += " ";
	    	strength--;
	    }
	    meter += "]";
	    System.out.println("\n" + "Hand Strength: " + meter + "\n");
	    System.out.println("You have: " + game.getUser().getPoints() + " points");
		System.out.println("*********************************");
	}
	

	public void requestAction(){
		System.out.println("What would you like to do?");
		String action = sc.nextLine();
		action= action.replaceAll("\\s+","");
		action = action.toUpperCase();
		
		if(action.equals("")) System.exit(0);
		if(action.equals(Actions.CHECK.toString())) game.getUser().check();
		if(action.equals(Actions.FOLD.toString())) game.getUser().fold();
		if(action.equals(Actions.RAISE.toString())) {
			System.out.println("How much would you like to raise?");
			int raiseAmt = sc.nextInt();
			sc.nextLine();
			while(!game.getUser().raise(raiseAmt)){
				System.out.println("How much would you like to raise?");
				raiseAmt = sc.nextInt();
				sc.nextLine();
			}
		}
		if(action.equals(Actions.CALL.toString())) game.getUser().call();
		if(action.equals(Actions.END_GAME.toString())) System.exit(0);
	}
	
}
