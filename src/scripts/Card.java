package scripts;

public class Card {

	private Integer number;
	private int suite;
	private String suiteValue;
	private static int tempSuite = 0;
	public static int id = 0;

	public Card() {
		number = id % 13 + 2; // Ace = 14
		suite = tempSuite;
		if (suite == 3) {
			suiteValue = "spades";
		} else if (suite == 2) {
			suiteValue = "hearts";
		} else if (suite == 1) {
			suiteValue = "clubs";
		} else if (suite == 0) {
			suiteValue = "diamonds";
		}
		id++;
		if(id == 13){
			id = 0;
			tempSuite ++;
		}
	}

	public Integer getNumber() {
		return number;
	}

	public int getSuite() {
		return suite;
	}
	
	public String getSuiteValue() {
		return suiteValue;
	}

}