package scripts;

public class Card {
	
	private int number;
	private int suite;
	public static int id;
	
	public Card(){		
		number = id % 13 + 1;
		suite = id % 4;
		id++;		
	}
	
	public int getNumber(){ return number;}	
	public int getSuite(){ return suite;}
	
}