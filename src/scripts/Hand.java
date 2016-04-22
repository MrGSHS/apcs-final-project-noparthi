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
	public boolean royalFlush(){
		return false; //Never Ever Will Happen
	}
	public boolean straightFlush(){
		return (flush() && straight());
	}
	public boolean fourOfAKind() {
		ArrayList<Integer> temp = dupeNumberLogic();
		int inARow = 1;
		int maxInARow = 1;
		for(int i = 0; i < temp.size()-1; i++){
			if(temp.get(i+1)==temp.get(i)){
				inARow++;
				if(inARow > maxInARow) maxInARow=inARow;
			}
			else inARow=1;
		}
		return (maxInARow==4);
	}
	public boolean flush() {
		return (dupeSuitLogic().size() >= 5);
	}
	public boolean straight() {
		int straightCheck = 1;
		int maxStraightCheck = 1;
		for(int i = 0; i < totalCards.size()-1; i++){
			if(totalCards.get(i+1).getNumber()-totalCards.get(i).getNumber()<=1){
				if(totalCards.get(i+1).getNumber()-totalCards.get(i).getNumber()!=0){
					straightCheck++;
					if(straightCheck>maxStraightCheck) maxStraightCheck=straightCheck;
				}
			}
			else straightCheck = 1;
		}
		return (maxStraightCheck>=5);
	}
	public boolean fullHouse() {
		ArrayList<Integer> temp = dupeNumberLogic();
		int tripsValue = 0;
		int pairValue = 0;
		for(int i = 0; i < temp.size()-2; i++){
			if(!fourOfAKind() && temp.get(i)==temp.get(i+1) && temp.get(i)==temp.get(i+2))tripsValue = temp.get(i);
			else pairValue = temp.get(i);
		}
		return (tripsValue!=0 && pairValue!=0);
	}
	public boolean trips() {
		ArrayList<Integer> temp = dupeNumberLogic();
		int inARow = 1;
		int maxInARow = 1;
		for(int i = 0; i < temp.size()-1; i++){
			if(temp.get(i+1)==temp.get(i)){
				inARow++;
				if(inARow > maxInARow) maxInARow=inARow;
			}
			else inARow=1;
		}
		return (maxInARow==3);
	}
	public boolean twoPair() {
		ArrayList<Integer> temp = dupeNumberLogic();
		int pairOneValue = temp.get(0);
		int pairTwoValue = temp.get(temp.size()-1);
		return (pairOneValue!=pairTwoValue && !trips() && !fullHouse());
	}
	public boolean pair() {
		ArrayList<Integer> temp = dupeNumberLogic();
		return (temp.size()==2);
	}
	public boolean highCard() {
		ArrayList<Integer> temp = dupeNumberLogic();
		return (temp.size()==0);
	}
}
