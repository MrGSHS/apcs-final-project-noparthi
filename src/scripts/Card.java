package scripts;

public class Card {
	
	private int number;
	private int suite;
	private String encodedSuite;
	
	public static int id;
	
	public Card(){		
		number = id % 13 + 2; //Ace = 14
		suite = id % 4;
		if(suite==3) encodedSuite = "\u2660";
		else if(suite==2) encodedSuite = "\u2665";
		else if(suite==1) encodedSuite = "\u2666";
		else if(suite==0) encodedSuite = "\u2663";
		id++;
	}
	
	public int getNumber(){ return number;}	
	public int getSuite(){ return suite;}
	public String getEncodedSuite(){ return encodedSuite;}
	
}