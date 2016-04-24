package scripts;

import java.util.Scanner;

public class Round {
	
	private Scanner sc;
	private static int roundNumber = 0;
	private Game game;
	
	private int pot;
	private int bet;
	
	public Round(Game game){
		pot = 0;
		bet = 0;
		this.game = game;
		game.resetPointsInvested();
		roundNumber++;
		setPot(game.getBigBlind() + game.getSmallBlind());
		setBet(game.getBigBlind());
		sc = new Scanner(System.in);
		game.getPlayer().newHand();
		game.getComputer().newHand();
		game.takeBlinds();
		
	}	
	public int getPot(){ return pot; }
	public int getBet(){ return bet; }
	
	public void setPot(int amt){ pot = amt; }
	public void setBet(int amt){ bet = amt; }
	
	public void preFlop(){
		game.takeAnte();
		//Prints  Out Hand Before Flop, Then Deals Flop
		setBet(game.getBigBlind());
		printTable();
		requestAction();
		game.getComputer().takeAction();
		game.getTable().dealFlop();
		preTurn();

	}	
	public void preTurn(){
		game.getDisplay().update();
		bet = 500;
		//Prints Out Hand Before Turn, Then Deals Turn
	    printTable();
		requestAction();
		game.getComputer().takeAction();
		game.getTable().dealTurn();
		preRiver();
	}	
	public void preRiver(){
		game.getDisplay().update();
		bet = 500;;
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
		System.out.println("The minimum bet is: " + bet);
		
		System.out.println("\n" + "Current cards on the table: ");
		for(int i = 0; i < game.getTable().getCardsOnTable().size(); i++){
			System.out.print("[" + game.getTable().getCardsOnTable().get(i).getNumber() + game.getTable().getCardsOnTable().get(i).getEncodedSuite() + "]" + " ");
		}
		System.out.println("\n");
		
		System.out.println("Your hand is:");
	    System.out.print("[" + game.getPlayer().getCurrentHand()[0].getNumber() + game.getPlayer().getCurrentHand()[0].getEncodedSuite() + "] "); 
	    System.out.println("[" + game.getPlayer().getCurrentHand()[1].getNumber() + game.getPlayer().getCurrentHand()[1].getEncodedSuite() + "]");
	    System.out.println("Computer's hand is: ");
	    System.out.print("[" + game.getComputer().getCurrentHand()[0].getNumber() + game.getComputer().getCurrentHand()[0].getEncodedSuite() + "] "); 
	    System.out.println("[" + game.getComputer().getCurrentHand()[1].getNumber() + game.getComputer().getCurrentHand()[1].getEncodedSuite() + "]");
	    String meter = "[";
	    
	    if(game.getTable().getCardsOnTable().size()==0) strength = game.getPlayer().getHand().initialHandStrength();
	    else strength = game.getPlayer().getHand().updateHandStrength();
	    
	    
	    //Print Meter
	    for(int i = 0; i < 10; i++) {
	    	if(strength > 0) meter += "\u25A0";
	    	else meter += " ";
	    	strength--;
	    }
	    meter += "]";
	    System.out.println("\n" + "Hand Strength: " + meter + "\n");
	    System.out.println("You have: " + game.getPlayer().getPoints() + " points");
		System.out.println("*********************************");
	}
	

	public void requestAction(){
		System.out.println("What would you like to do?");
		String action = sc.nextLine();
		action= action.replaceAll("\\s+","");
		action = action.toUpperCase();
		
		if(action.equals("")) System.exit(0);
		if(action.equals(Actions.CHECK.toString())) game.getPlayer().check();
		if(action.equals(Actions.FOLD.toString())) game.getPlayer().fold();
		if(action.equals(Actions.RAISE.toString())) {
			System.out.println("How much would you like to raise?");
			int raiseAmt = sc.nextInt();
			sc.nextLine();
			while(!game.getPlayer().raise(raiseAmt)){
				System.out.println("How much would you like to raise?");
				raiseAmt = sc.nextInt();
				sc.nextLine();
			}
		}
		if(action.equals(Actions.CALL.toString())) game.getPlayer().call();
		if(action.equals(Actions.END_GAME.toString())) System.exit(0);
	}
	
}
