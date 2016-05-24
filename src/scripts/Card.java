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
	public String toString(){
		String cards="";
		if(getNumber()==11)
			cards+="Jack";
		else if(getNumber()==12)
			cards+="Queen";
		else if(getNumber()==13)
			cards+="King";
		else if(getNumber()==14)
			cards+="Ace";
		else
			cards+=getNumber();
		cards+=" of ";
		cards+=getSuiteValue();
	
	return cards;
	}
	
}