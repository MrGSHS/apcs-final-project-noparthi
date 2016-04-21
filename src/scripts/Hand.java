package scripts;

public class Hand extends Deck{
	
	Card card1;
	Card card2;
	
	public Hand(){
		card1 = deal();
		card2 = deal();
	}
	
}
