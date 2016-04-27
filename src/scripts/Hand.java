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
	private ArrayList <Integer>  straightList = new ArrayList<>();
    private String currentHandString = "High Card";
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

	public String getCurrentHandStrengthString(){
		return currentHandString;
	}
	
	public int initialHandStrength() {
		strength = 0;
		// Check High Card
		if ((card1.getNumber() <= 4 || card2.getNumber() <= 4)&&(card1.getNumber() >= 12 || card2.getNumber() <= 12))
			strength += 2;
		else if (card1.getNumber() <= 10 || card2.getNumber() <= 10)
			strength += 4;
		else
			strength += 6;
		// Check Straight Draws
		if (Math.abs(card1.getNumber() - card2.getNumber()) <= 4) {
			if (Math.abs(card1.getNumber() - card2.getNumber()) == 1)
				strength += 2;
			else
				strength += 1;
		}
		// Check Flush Draws
		if (card1.getSuite() == card2.getSuite())
			strength += 2;
		// Check Pocket Pairs
		if (card1.getNumber() == card2.getNumber()) {
			if (card1.getNumber() <= 6)
				strength = 6;
			else if (card1.getNumber() <= 11)
				strength = 8;
			else
				strength = 10;
			currentHandString = "Pair";
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
		
		//Re-Orders Cards On Table To Straight Check List
		for(Card c1 : totalCards){
			straightList.add(c1.getNumber());
		}
		Collections.sort(straightList);
		
		//Updates Hand Strength
		if(royalFlush()){
			currentHandString = "Royal Flush";
			return 10;	
		}
		else if(fourOfAKind() || fullHouse()||straightFlush()){
			if(fullHouse())currentHandString = "Full House";
			else if (fourOfAKind()) currentHandString = "Four Of A Kind";
			else currentHandString = "Straight Flush";
			return 9;
		}
		else if(straight() || flush()){
			if(straight())currentHandString = "Straight";
			else currentHandString = "Flush";
			return 8;
		}
		else if(trips() && (card1.getNumber()>=9 || card2.getNumber()>=9)){
			currentHandString = "Three Of A Kind";
			return 7;
		}
		else if(trips() && (card1.getNumber()<9 || card2.getNumber()<9)){
			currentHandString = "Three Of A Kind";
			return 6;
		}
		else if(twoPair() && (card1.getNumber()>=8 || card2.getNumber()>=8)){
			currentHandString = "Two-Pair";
			return 5;
		}
		else if(twoPair() && (card1.getNumber()<8 || card2.getNumber()<8)){
			currentHandString = "Two-Pair";
			return 4;
		}
		else if(pair() && (card1.getNumber()>=7 || card2.getNumber()>=7)){
			currentHandString = "Pair";
			return 3;
		}
		else if(pair() && (card1.getNumber()<7 || card2.getNumber()<7)){
			currentHandString = "Pair";
			return 2;
		}
		else{
			currentHandString = "High Card";
			return 1;
		}
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
		Collections.sort(dupes);
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
		ArrayList<Integer> temp = dupeSuitLogic();
		int inARow = 1;
		int maxInARow = 1;
		for(int i = 0; i < temp.size()-1; i++){
			if(temp.get(i+1)==temp.get(i)){
				inARow++;
				if(inARow > maxInARow) maxInARow=inARow;
			}
			else inARow=1;
		}
		return (maxInARow>=4);
	}
	public boolean straight() {
		int straightCheck = 1;
		int maxStraightCheck = 1;
		for(int i = 0; i < straightList.size()-1; i++){
			if(straightList.get(i+1)-straightList.get(i)<=1){
				if(straightList.get(i+1)-straightList.get(i)==1){
					straightCheck++;
					if(straightCheck>maxStraightCheck) maxStraightCheck=straightCheck;
				}
			}
			else straightCheck = 1;
		}
		//Check A2345
		int two = 0;
		int three = 0;
		int four = 0;
		int five = 0;
		for(int j = 0; j < straightList.size(); j++){
			if(straightList.get(j)==2) two+=1;
			if(straightList.get(j)==3) three +=1;
			if(straightList.get(j)==4) four +=1;
			if(straightList.get(j)==5) five +=1;
			if(two >= 1 && three >= 1 && four >= 1 && five >= 1 && straightList.get(straightList.size())==14) maxStraightCheck = 5;
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
