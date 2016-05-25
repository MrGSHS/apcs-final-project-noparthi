package blackjack;
import java.util.*;
import scripts.Card;
public class BlackGame {
	private BlackRound round;
	private String cont = "y";
	
	public BlackRound getRound(){
		return round;
	}
	
	public BlackGame(){

		
		Scanner sc = new Scanner (System.in);
		
		System.out.println("How much would you like to bet?");
		round=new BlackRound(sc.nextInt());
		
		new BlackDisplay(this);
		
		while (cont.equals("y")){
			
			System.out.println("Your cards: "+round.getPlayer().getHand());
			
			System.out.println("The computer's cards: "+round.getComputer().getHand().getCards().get(0));
			System.out.println("Would you like to hit? y/n");
			sc.nextLine();
			String hit=sc.nextLine();
			while (hit.equals("y")){
				round.getPlayer().hit();
				System.out.println("Your cards: "+round.getPlayer().getHand());
				System.out.println("Your hand value "+round.getPlayer().calc());
				
				if (round.getPlayer().calc()>21) {
					System.out.println("Your hand value is > 21. You lose.");
					hit="no";
				}
				else if (round.getPlayer().calc()==21){
					round.getPlayer().demGains();
					break;
				}
				else{
					System.out.println("Would you like to hit? y/n");
					hit=sc.nextLine();
					
				}
				
			}
			System.out.println("Computer's cards: "+round.getComputer().getHand());
			while (round.getComputer().hit()){
				
				System.out.println("Computer's cards: "+round.getComputer().getHand());
				
			}
			if (hit.equals("no")) continue;
			else if (round.getComputer().calc()<round.getPlayer().calc() || round.getComputer().calc()>21){
				round.getPlayer().demGains();
				
				System.out.println("You win! Your new balance: $"+round.getPlayer().getBalance());
				
			}
			else if (round.getComputer().calc()==round.getPlayer().calc() && round.getComputer().calc()<=21){
				System.out.println("Push");
			}
			else{
				System.out.println("You lose!");
			}
			
			System.out.println("Would you like to play another round of Black Jack? y/n");
			cont=sc.nextLine();
			round.getPlayer().getHand().restart();
			round.getPlayer().getHand().reset();
			round.getComputer().getHand().reset();
		}
	}
}
