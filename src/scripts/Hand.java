package scripts;
import java.util.*;

public class Hand {
	Card card1;
	Card card2;
	int strength;
    ArrayList<Card> totalCards = new ArrayList<>();
	ArrayList<Card> cardsOnTable = Table.getCardsOnTable();

	public Hand() {
		card1 = Game.getDeck().deal();
		card2 = Game.getDeck().deal();
		totalCards.add(card1);
		totalCards.add(card2);
	}

	public Card[] getHand() {
		return new Card[] { card1, card2 };
	}
	//Adds Flop To Entire Hand Combination
	public void addFlopToHandList(){
		cardsOnTable = Table.getCardsOnTable();
		totalCards.add(cardsOnTable.get(0));
		totalCards.add(cardsOnTable.get(1));
		totalCards.add(cardsOnTable.get(2));
	}
	//Adds Turn To Entire Hand Combination
	public void addTurnToHandList(){
		cardsOnTable = Table.getCardsOnTable();
		totalCards.add(cardsOnTable.get(3));
	}
	//Adds River To Entire Hand Combination
	public void addRiverToHandList(){
		cardsOnTable = Table.getCardsOnTable();
		totalCards.add(cardsOnTable.get(4));
	}

	public int initalHandstrength() {
		// Check High Card
		if (card1.getNumber() <= 5 || card2.getNumber() <= 5)
			strength += 1;
		else if (card1.getNumber() <= 10 || card2.getNumber() <= 10)
			strength += 3;
		else
			strength += 5;
		// Check Straight Draws
		if (Math.abs(card1.getNumber() - card2.getNumber()) <= 4) {
			if (Math.abs(card1.getNumber() - card2.getNumber()) == 1)
				strength += 2;
			else
				strength++;
		}
		// Check Flush Draws
		if (card1.getSuite() == card2.getSuite())
			strength += 2;
		// Check Pocket Pairs
		if (card1.getNumber() == card2.getNumber()) {
			if (card1.getNumber() <= 5)
				strength = 6;
			else if (card1.getNumber() <= 11)
				strength = 8;
			else
				strength = 10;
		}
		return strength;
	}

	// Update Hand strength
	public int updateHandstrength() {
		return strength;
	}

	// Checks For Such Hands
	public boolean fourOfAKind() {
		int check = 0;
		for(int i = 0; i < totalCards.size()-1; i++){
			if(totalCards.get(i).getNumber() == totalCards.get(i+1).getNumber())check++;
		}
		if(check ==4){
		}
		return false;
	}

	public boolean flush() {
		return false;
	}

	public boolean straight() {
		return false;
	}

	public boolean fullHouse() {
		return false;
	}

	public boolean trips() {
		return false;
	}

	public boolean twoPair() {
		return false;
	}
	
	public boolean pair() {
		return false;
	}
}
