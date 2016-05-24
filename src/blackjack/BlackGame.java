package blackjack;
import java.util.*;
import scripts.Card;
public class BlackGame {
	public static void main(String [] args){
		Scanner sc = new Scanner (System.in);
		
		String wants="y";
		
		
		while (wants.equals("y")){
			
			System.out.println("How much would you like to bet?");
			BlackRound thisOne=new BlackRound(sc.nextInt());
			System.out.println("Your cards: "+thisOne.getPlayer().getHand());
			
			System.out.println("The computer's cards"+thisOne.getComputer().getHand().getCards().get(0));
			System.out.println("Would you like to hit? y/n");
			sc.nextLine();
			String hit=sc.nextLine();
			while (hit.equals("y")){
				thisOne.getPlayer().hit();
				System.out.println("Your cards: "+thisOne.getPlayer().getHand());
				System.out.println("Your hand value "+thisOne.getPlayer().calc());
				
				
				
				if (thisOne.getPlayer().calc()>21) {
					System.out.println("Your hand value is > 21. You lose.");
					hit="no";
				}
				else if (thisOne.getPlayer().calc()==21){
					thisOne.getPlayer().demGains();
					break;
				}
				
				else{
					System.out.println("Would you like to hit? y/n");
					hit=sc.nextLine();
					
				}
				
			}
			System.out.println("Computer's cards: "+thisOne.getComputer().getHand());
			
			while (thisOne.getComputer().hit()){
				
				System.out.println("Computer's cards: "+thisOne.getComputer().getHand());
				
			}
			if (hit.equals("no")) continue;
			else if (thisOne.getComputer().calc()<thisOne.getPlayer().calc() || thisOne.getComputer().calc()>21){
				thisOne.getPlayer().demGains();
				
				System.out.println("You win! Your new balance: $"+thisOne.getPlayer().getBalance());
				
			}
			else if (thisOne.getComputer().calc()==thisOne.getPlayer().calc() && thisOne.getComputer().calc()<=21){
				System.out.println("Push");
			}
			else{
				System.out.println("You lose!");
			}
			
			System.out.println("Would you like to play another round of Black Jack? y/n");
			wants=sc.nextLine();
			thisOne.getPlayer().getHand().restart();
			thisOne.getPlayer().getHand().reset();
			thisOne.getComputer().getHand().reset();
		}
	}
}
