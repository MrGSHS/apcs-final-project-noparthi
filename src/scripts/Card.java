package scripts;

public class Card {

	private Integer number;
	private int suite;
	private String encodedSuite;
	private String suiteValue;
	public static int id;

	public Card() {
		number = id % 13 + 2; // Ace = 14
		suite = id % 4;
		if (suite == 3) {
			suiteValue = "spades";
			encodedSuite = "\u2660";
		} else if (suite == 2) {
			suiteValue = "hearts";
			encodedSuite = "\u2665";
		} else if (suite == 1) {
			suiteValue = "clubs";
			encodedSuite = "\u2663";
		} else if (suite == 0) {
			suiteValue = "diamonds";
			encodedSuite = "\u2666";
		}
		id++;
	}

	public Integer getNumber() {
		return number;
	}

	public int getSuite() {
		return suite;
	}

	public String getEncodedSuite() {
		return encodedSuite;
	}

	public String getSuiteValue() {
		return suiteValue;
	}

}