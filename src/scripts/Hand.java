package scripts;

import java.util.ArrayList;
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

	public int initialHandStrength() {
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
	public int updateHandStrength() {
		//Adds Cards On Table To TotalCardsAvailableToPlayer Pool
		cardsOnTable = game.getTable().getCardsOnTable();
		if(cardsOnTable.size()==3){
			totalCards.add(cardsOnTable.get(0));
			totalCards.add(cardsOnTable.get(1));
			totalCards.add(cardsOnTable.get(2));
		}
		else if(cardsOnTable.size()==4) totalCards.add(cardsOnTable.get(3));
		else totalCards.add(cardsOnTable.get(4));
		//Updates Hand Strength
		if(pair() && (card1.getNumber()<7 || card2.getNumber()<7)) return 2;
		else if(pair() && (card1.getNumber()>=7 || card2.getNumber()>=7)) return 3;
		else if(twoPair() && (card1.getNumber()<8 || card2.getNumber()<8)) return 4;
		else if(twoPair() && (card1.getNumber()>=8 || card2.getNumber()>=8)) return 5;
		else if(trips() && (card1.getNumber()<9 || card2.getNumber()<9)) return 6;
		else if(trips() && (card1.getNumber()>=9 || card2.getNumber()>=9)) return 7;		
		else if(straight() || flush()) return 8;
		else if(fourOfAKind() || fullHouse()||straightFlush()) return 9;
		else if(royalFlush()) return 10;	
		else return 1;
	}

	private ArrayList<Integer> dupeNumberLogic() {
		ArrayList<Integer> dupes = new ArrayList<Integer>();		
		Set<Integer> set = new HashSet<>();
		for (Card card : totalCards) {
			if (set.add(card.getNumber()) == false) dupes.add(card.getNumber());
		}
		Collections.sort(dupes);
		return dupes;
	}

	private ArrayList<Integer> dupeSuitLogic() {
		ArrayList<Integer> dupes = new ArrayList<>();
		Set<Integer> set = new HashSet<>();
		for (Card card : totalCards) {
			if (set.add(card.getSuite()) == false) dupes.add(card.getSuite());
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
		return (maxInARow==3);
	}
	public boolean flush() {
		return (dupeSuitLogic().size() >= 4);
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
		if(temp.size()==0) return false;
		int tripsValue = 0;
		int pairValue = 0;
		for(int i = 0; i < temp.size()-1; i++){
			if(!fourOfAKind() && temp.get(i)==temp.get(i+1))tripsValue = temp.get(i);
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
		return (maxInARow==2);
	}
	public boolean twoPair() {
		ArrayList<Integer> temp = dupeNumberLogic();
		if(temp.size()==0) return false;
		int pairOneValue = temp.get(0);
		int pairTwoValue = temp.get(temp.size()-1);
		return (pairOneValue!=pairTwoValue && !trips() && !fullHouse());
	}
	public boolean pair() {
		ArrayList<Integer> temp = dupeNumberLogic();
		return (temp.size()==1);
	}
	public boolean highCard() {
		ArrayList<Integer> temp = dupeNumberLogic();
		return (temp.size()==0);
	}
}
