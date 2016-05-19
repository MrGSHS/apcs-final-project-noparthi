package blackjack;
import java.util.*;
public class BGame {
	public static void main(String [] args){
		Scanner sc = new Scanner (System.in);
		String wants="y";
		while (wants.equals("y")){
			System.out.println("How much would you like to bet?");
			
			BlackRound thisOne=new BlackRound(sc.nextInt());
			System.out.println("Your cards: "+thisOne.getPlayer().getHand().getCards());
			System.out.println("The computer's cards"+thisOne().getComputer().getHand().getCards().get(0));
			System.out.println("Would you like to hit? y/n");
			String hit=sc.nextLine();
			while (hit.equals("y")){
				thisOne.getPlayer().hit();
				System.out.println("Your cards: "+thisOne.getPlayer().getHand().getCards());
				System.out.println("Your hand value"+thisOne.getPlayer().calc());
				
				
				
				if (thisOne.getPlayer().calc()>21) {
					System.out.println("Your hand value is > 21. You lose.")
					hit="no";
				}
				else{
					System.out.println("Would you like to hit? y/n");
					hit=sc.nextLine();
					
				}
				
			}
			
			
			sc.nextLine();
			String hit
			
			System.out.println("Would you like to play a round of Black Jack? y/n");
			wants=sc.nextLine();
		}
	}
}
