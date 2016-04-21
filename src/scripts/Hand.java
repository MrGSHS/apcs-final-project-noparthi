package scripts;
import java.util.*;

public class Hand {
	Card card1;
	Card card2;
	int strength;
    ArrayList<Card> totalCards = new ArrayList<>();
	ArrayList<Card> cardsOnTable;

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
		cardsOnTable = Round.getTable().getCardsOnTable();
		totalCards.add(cardsOnTable.get(0));
		totalCards.add(cardsOnTable.get(1));
		totalCards.add(cardsOnTable.get(2));
	}
	//Adds Turn To Entire Hand Combination
	public void addTurnToHandList(){
		cardsOnTable = Round.getTable().getCardsOnTable();
		totalCards.add(cardsOnTable.get(3));
	}
	//Adds River To Entire Hand Combination
	public void addRiverToHandList(){
		cardsOnTable = Round.getTable().getCardsOnTable();
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

    private ArrayList<Integer> dupeNumberLogic(){
    	ArrayList<Integer> dupes = new ArrayList<>();
    	Set<Integer> set = new HashSet<>();
    	int count = 0;
    	for(Card card : totalCards){
    		if(set.add(card.getNumber())==false){
    			if(dupes.size()==0) dupes.add(card.getNumber());
    			else if(card.getNumber()!=dupes.get(0)){
    				for(int check: dupes){
    					if(card.getNumber()!=check) count++;   				    			
    			    }    		
    				if(count == dupes.size()) dupes.add(card.getNumber());
    		    }
    			dupes.add(card.getNumber());
    	    }
    	}
    	return dupes;
    }
    
    private ArrayList<Integer> dupeSuitLogic(){
    	ArrayList<Integer> dupes = new ArrayList<>();
    	Set<Integer> set = new HashSet<>();
    	for(Card card : totalCards){
    		if(set.add(card.getSuite())==false){
    			if(dupes.size()==0) dupes.add(card.getSuite());
    			dupes.add(card.getSuite());
    		}
    	}
    	return dupes;
    }
    
	// Checks For Such Hands
	public boolean fourOfAKind() { return false;}
	public boolean flush() {return (dupeSuitLogic().size() == 5);}
	public boolean straight() {return false;}
	public boolean fullHouse() {return (dupeNumberLogic().size()>=5);}
	public boolean trips() {return(dupeNumberLogic().size()==3);}
	public boolean twoPair() {
		int count = 0;
		ArrayList<Integer> pairs = dupeNumberLogic();
		Set<Integer> set = new HashSet<>();
    	for(Integer cardNumber : pairs){
    		if(set.add(cardNumber)==false) count++;
    	}
		return ((count == 2 && !trips())||((count==3) && !fullHouse() && !fourOfAKind()));
    }
	public boolean pair() {	return(dupeNumberLogic().size()==2);}
	public boolean highCard(){ return (dupeNumberLogic().size() == 0);}
}
