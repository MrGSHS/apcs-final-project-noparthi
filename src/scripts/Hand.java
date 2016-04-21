package scripts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Hand {
	
	private Game game;
	private Card card1;
	private Card card2;
	private int strength;
	private ArrayList<Card> totalCards = new ArrayList<>();
	private ArrayList<Card> cardsOnTable;

	public Hand(Game game, Card fcard, Card scard) {
		this.game = game;
		card1 = fcard;
		card2 = scard;
		totalCards.add(card1);
		totalCards.add(card2);
	}

	public Card[] getHand() {
		return new Card[] { card1, card2 };
	}

	// Adds Flop To Entire Hand Combination
	public void addFlopToHandList() {
		cardsOnTable = game.getTable().getCardsOnTable();
		totalCards.add(cardsOnTable.get(0));
		totalCards.add(cardsOnTable.get(1));
		totalCards.add(cardsOnTable.get(2));
	}

	// Adds Turn To Entire Hand Combination
	public void addTurnToHandList() {
		cardsOnTable = game.getTable().getCardsOnTable();
		totalCards.add(cardsOnTable.get(3));
	}

	// Adds River To Entire Hand Combination
	public void addRiverToHandList() {
		cardsOnTable = game.getTable().getCardsOnTable();
		totalCards.add(cardsOnTable.get(4));
	}

	public int initalHandStrength() {
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

	private ArrayList<Integer> dupeNumberLogic() {
		ArrayList<Integer> dupes = new ArrayList<Integer>();		
		Set<Integer> set = new HashSet<>();
		int count = 0;
		for (Card card : totalCards) {
			if (set.add(card.getNumber()) == false) {
				if (dupes.size() == 0)
					dupes.add(card.getNumber());
				else if (card.getNumber() != dupes.get(0)) {
					for (int check : dupes) {
						if (card.getNumber() != check)
							count++;
					}
					if (count == dupes.size())
						dupes.add(card.getNumber());
				}
				dupes.add(card.getNumber());
			}
		}
		Collections.sort(dupes);
		return dupes;
	}

	private ArrayList<Integer> dupeSuitLogic() {
		ArrayList<Integer> dupes = new ArrayList<>();
		Set<Integer> set = new HashSet<>();
		for (Card card : totalCards) {
			if (set.add(card.getSuite()) == false) {
				if (dupes.size() == 0)
					dupes.add(card.getSuite());
				dupes.add(card.getSuite());
			}
		}
		return dupes;
	}

	// Checks For Such Hands
	public boolean fourOfAKind() {
		return false;
	}

	public boolean flush() {
		return (dupeSuitLogic().size() >= 5);
	}

	public boolean straight() {
		ArrayList<Integer> temp = dupeNumberLogic();
		int straightCheck = 0;
		int maxStraightCheck = 0;
		for(int i = 0; i < temp.size()-1; i++){
			if(temp.get(i+1)-temp.get(i)==1){
				straightCheck++;
				if(straightCheck>maxStraightCheck) maxStraightCheck=straightCheck;
			}
			else straightCheck = 0;
		}
		return (maxStraightCheck>=5);
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

	public boolean highCard() {
		return (!twoPair() && !trips() && !fullHouse() && !straight() && !flush() && !fourOfAKind());
	}
}
