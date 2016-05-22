package scripts;

public class Card {

	private Integer number;
	private int suite;
	private String suiteValue;

	public Card(int num, int sui) {
		number = num;
		suite = sui; // Ace = 14
		if (suite == 3) {
			suiteValue = "spades";
		} else if (suite == 2) {
			suiteValue = "hearts";
		} else if (suite == 1) {
			suiteValue = "clubs";
		} else if (suite == 0) {
			suiteValue = "diamonds";
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