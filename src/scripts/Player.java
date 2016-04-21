package scripts;

public class Player extends Deck {

	Card card1;
	Card card2;
	int points;
	int meter = 0; // Meter Goes From 1 - 10

	public Player() {
		card1 = deal();
		card2 = deal();
		points = 10000;
	}
	public int getPoints(){ return points;}
	public int initalHandMeter(){	
		//Check High Card
		if(card1.getNumber()<= 5 || card2.getNumber()<= 5) meter+=1;
		else if(card1.getNumber()<= 10 || card2.getNumber()<= 10) meter +=3;
		else meter+=5;
		//Check Straight Draws	
		if(Math.abs(card1.getNumber()-card2.getNumber())<=4){
			if(Math.abs(card1.getNumber()-card2.getNumber())==1) meter+=2;
			else meter++;
		}	
		//Check Flush Draws
		if(card1.getSuite()==card2.getSuite()) meter+=2;		
		//Check Pocket Pairs
		if (card1.getNumber() == card2.getNumber()) {
			if (card1.getNumber() <= 5) meter = 6;
			else if (card1.getNumber() <= 11) meter = 8;
			else meter = 10;
		}			
		return meter;
	}
	//Update Hand Meter
	public int updateHandMeter(){
		return meter;
	}	
	//Checks Probability For Such Hands (1-100)
	public int fourOfAKind(){
		return -1;
	}
	public int flush(){
		return -1;
	}
	public int straight(){
		return -1;
	}
	public int fullHouse(){
		return -1;
	}
	public int trips(){
		return -1;
	}
	public int twoPair(){
		return -1;
	}
}


