package holdem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import scripts.Card;

public class Hand {

	private HoldemGame game;
	private Card card1;
	private Card card2;
	private int strength;
	private ArrayList<Card> cardsOnTable;
	private ArrayList<Card> totalCards = new ArrayList<>();
	private ArrayList<Integer> straightList = new ArrayList<>();
	private String currentHandString = "Hand Strength";

	private int pair = 0;
	private ArrayList<Integer> twoPair = new ArrayList<>();
	private int trips = 0;
	private ArrayList<Integer> fullHouse = new ArrayList<>();
	private int straight = 0;
	private int flush = 0;
	private int quads = 0;

	public Hand(HoldemGame game, Card fcard, Card scard) {
		this.game = game;
		card1 = fcard;
		card2 = scard;
		totalCards.add(card1);
		totalCards.add(card2);
	}

	public Card[] getHand() {
		return new Card[] { card1, card2 };
	}

	public String getCurrentHandStrengthString() {
		return currentHandString;
	}

	public Card getHighestCard() {
		if (card1.getNumber() > card2.getNumber()) {
			return card1;
		}
		return card2;
	}

	public Card getLowestCard() {
		if (card1.getNumber() < card2.getNumber()) {
			return card1;
		}
		return card2;
	}

	public int getPair() {
		return pair;
	}

	public ArrayList<Integer> getTwoPair() {
		return twoPair;
	}

	public int getTrips() {
		return trips;
	}

	public ArrayList<Integer> getFullHouse() {
		return fullHouse;
	}

	public int getStraight() {
		return straight;
	}

	// Returns Suit Number
	public int getFlush() {
		return flush;
	}

	public int getQuads() {
		return quads;
	}

	// Pre-Flop Hand Strength
	public int initialHandStrength() {
		strength = 0;
		// Check High Card
		if ((card1.getNumber() <= 5 || card2.getNumber() <= 5) && (card1.getNumber() == 14 || card2.getNumber() == 14))
			strength += 3;
		else if (card1.getNumber() <= 6 && card2.getNumber() <= 6)
			strength += 1;
		else if (card1.getNumber() <= 10 || card2.getNumber() <= 10)
			strength += 4;
		else
			strength += 6;
		// Check Straight Draws
		if (Math.abs(card1.getNumber() - card2.getNumber()) == 1) {
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

	// Post-Flop Hand Strengths
	public int updateHandStrength() {
		// Adds Cards On Table To TotalCardsAvailableToPlayer Pool
		cardsOnTable = game.getTable().getCardsOnTable();
		if (cardsOnTable.size() >= 3) {
			totalCards = new ArrayList<Card>();
			totalCards.add(card1);
			totalCards.add(card2);
			totalCards.add(cardsOnTable.get(0));
			totalCards.add(cardsOnTable.get(1));
			totalCards.add(cardsOnTable.get(2));
			if (cardsOnTable.size() >= 4)
				totalCards.add(cardsOnTable.get(3));
			if (cardsOnTable.size() == 5)
				totalCards.add(cardsOnTable.get(4));
		}

		// Re-Orders Cards On Table To Straight Check List
		for (Card c1 : totalCards) {
			straightList.add(c1.getNumber());
		}
		Collections.sort(straightList);

		// Updates Hand Strength String & Returns Hand Strength
		if (royalFlush()) {
			currentHandString = "Royal Flush";
			return 10;
		} else if (fourOfAKind() || fullHouse() || straightFlush()) {
			if (fullHouse())
				currentHandString = "Full House";
			else if (fourOfAKind())
				currentHandString = "Four Of A Kind";
			else
				currentHandString = "Straight Flush";
			return 9;
		} else if (straight() || flush()) {
			if (straight())
				currentHandString = "Straight";
			else
				currentHandString = "Flush";
			return 8;
		} else if (trips() && (card1.getNumber() >= 9 || card2.getNumber() >= 9)) {
			currentHandString = "Three Of A Kind";
			return 7;
		} else if (trips() && (card1.getNumber() < 9 || card2.getNumber() < 9)) {
			currentHandString = "Three Of A Kind";
			return 6;
		} else if (twoPair() && (card1.getNumber() >= 8 || card2.getNumber() >= 8)) {
			currentHandString = "Two-Pair";
			return 5;
		} else if (twoPair() && (card1.getNumber() < 8 || card2.getNumber() < 8)) {
			currentHandString = "Two-Pair";
			return 4;
		} else if (pair() && (card1.getNumber() >= 7 || card2.getNumber() >= 7)) {
			currentHandString = "Pair";
			return 3;
		} else if (pair() && (card1.getNumber() < 7 || card2.getNumber() < 7)) {
			currentHandString = "Pair";
			return 2;
		} else {
			currentHandString = "High Card";
			return 1;
		}
	}

	// Adds Dupe Numbers Into Separate Array (Adds One Less Than The Number
	// Available)
	private ArrayList<Integer> dupeNumberLogic() {
		ArrayList<Integer> dupes = new ArrayList<Integer>();
		Set<Integer> set = new HashSet<>();
		for (Card card : totalCards) {
			if (set.add(card.getNumber()) == false) {
				dupes.add(card.getNumber());
			}
		}
		Collections.sort(dupes);
		return dupes;
	}

	// Adds Dupe Suits Into Separate Array (Adds One Less Than The Number
	// Available)
	private ArrayList<Integer> dupeSuitLogic() {
		ArrayList<Integer> dupes = new ArrayList<>();
		Set<Integer> set = new HashSet<>();
		for (Card card : totalCards) {
			if (set.add(card.getSuite()) == false) {
				dupes.add(card.getSuite());
			}
		}
		Collections.sort(dupes);
		return dupes;
	}

	// Checks For Royal Flush
	public boolean royalFlush() {
		int royalFlushCounter = 0;
		for (Card card : totalCards) {
			if (card.getSuite() == getFlush() && card.getNumber() == 10) {
				royalFlushCounter++;
			} else if (card.getSuite() == getFlush() && card.getNumber() == 11) {
				royalFlushCounter++;
			} else if (card.getSuite() == getFlush() && card.getNumber() == 12) {
				royalFlushCounter++;
			} else if (card.getSuite() == getFlush() && card.getNumber() == 13) {
				royalFlushCounter++;
			} else if (card.getSuite() == getFlush() && card.getNumber() == 14) {
				royalFlushCounter++;
			}
		}
		return (royalFlushCounter == 5);
	}

	// Checks For Straight Flush
	public boolean straightFlush() {
		int straightFlushCounter = 0;
		for (Card card : totalCards) {
			if (card.getSuite() == getFlush() && card.getNumber() == getStraight()) {
				straightFlushCounter++;
			} else if (card.getSuite() == getFlush() && card.getNumber() == getStraight()+1) {
				straightFlushCounter++;
			} else if (card.getSuite() == getFlush() && card.getNumber() == getStraight()+2) {
				straightFlushCounter++;
			} else if (card.getSuite() == getFlush() && card.getNumber() == getStraight()+3) {
				straightFlushCounter++;
			} else if (card.getSuite() == getFlush() && card.getNumber() == getStraight()+4) {
				straightFlushCounter++;
			}
		}
		return (straightFlushCounter >= 5);
	}

	// Checks For Quads
	public boolean fourOfAKind() {
		ArrayList<Integer> temp = dupeNumberLogic();
		quads = 0;
		int inARow = 1;
		int maxInARow = 1;

		for (int i = 0; i < temp.size() - 1; i++) {
			if (temp.get(i + 1) == temp.get(i)) {
				inARow++;
				if (inARow > maxInARow) {
					maxInARow = inARow;
					quads = temp.get(i);
				}
			} else
				inARow = 1;
		}
		return (maxInARow == 3);
	}

	// Checks For Flush
	public boolean flush() {
		ArrayList<Integer> temp = dupeSuitLogic();
		int inARow = 1;
		int maxInARow = 1;

		for (int i = 0; i < temp.size() - 1; i++) {
			if (temp.get(i + 1) == temp.get(i)) {
				inARow++;
				if (inARow > maxInARow) {
					maxInARow = inARow;
					flush = temp.get(i);
				}
			} else
				inARow = 1;
		}
		return (maxInARow >= 4);
	}

	// Checks For Straight
	public boolean straight() {
		straight = 0;
		int straightCheck = 1;
		int maxStraightCheck = 1;

		for (int i = 0; i < straightList.size() - 1; i++) {
			if (straightList.get(i + 1) - straightList.get(i) <= 1) {
				if (straightList.get(i + 1) - straightList.get(i) == 1) {
					straightCheck++;
					if (straightCheck > maxStraightCheck) {
						maxStraightCheck = straightCheck;
						straight = straightList.get(i + 1);
					}
				}
			} else
				straightCheck = 1;
		}
		// Check A2345
		boolean two = false;
		boolean three = false;
		boolean four = false;
		boolean five = false;
		boolean ace = false;
		for (int j = 0; j < straightList.size(); j++) {
			if (straightList.get(j) == 2)
				two = true;
			if (straightList.get(j) == 3)
				three = true;
			if (straightList.get(j) == 4)
				four = true;
			if (straightList.get(j) == 5)
				five = true;
			if (straightList.get(j) == 14)
				ace = true;
			if (ace && two && three && four && five) {
				maxStraightCheck = 5;
				straight = 5;
			}
		}
		return (maxStraightCheck >= 5);
	}

	// Checks For Full House
	public boolean fullHouse() {
		ArrayList<Integer> temp = dupeNumberLogic();
		fullHouse = new ArrayList<Integer>();
		int tripsValue = 0;
		int pairValue = 0;

		for (int i = 0; i < temp.size() - 1; i++) {
			if (!fourOfAKind() && temp.get(i) == temp.get(i + 1))
				tripsValue = temp.get(i);
			else
				pairValue = temp.get(i);
		}
		if (tripsValue != 0 && pairValue != 0) {
			fullHouse.add(tripsValue);
			fullHouse.add(pairValue);
			return true;
		}
		return false;
	}

	// Checks For Trips
	public boolean trips() {
		ArrayList<Integer> temp = dupeNumberLogic();
		trips = 0;
		int inARow = 1;
		int maxInARow = 1;

		for (int i = 0; i < temp.size() - 1; i++) {
			if (temp.get(i + 1) == temp.get(i)) {
				inARow++;
				if (inARow > maxInARow) {
					maxInARow = inARow;
					trips = temp.get(i);
				}
			} else
				inARow = 1;
		}
		return (maxInARow == 2);
	}

	// Checks For Two Pair
	public boolean twoPair() {
		ArrayList<Integer> temp = dupeNumberLogic();
		twoPair = new ArrayList<Integer>();

		if (temp.size() == 0)
			return false;
		int pairOneValue = temp.get(0);
		int pairTwoValue = temp.get(temp.size() - 1);
		if (pairOneValue != pairTwoValue) {
			twoPair.add(pairTwoValue);
			twoPair.add(pairOneValue);
			return true;
		}
		return false;
	}// Checks For Pair

	public boolean pair() {
		ArrayList<Integer> temp = dupeNumberLogic();
		pair = 0;

		if (temp.size() == 1) {
			pair = temp.get(0);
			return true;
		}
		return false;
	}

	// Checks For High Card
	public boolean highCard() {
		ArrayList<Integer> temp = dupeNumberLogic();
		return (temp.size() == 0);
	}
}
