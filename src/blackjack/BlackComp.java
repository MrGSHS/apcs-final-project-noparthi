package blackjack;



public class BlackComp extends BlackPlayer {

	
	private BlackPlayer dude;
	
	public BlackComp(BlackPlayer play){
		super(Integer.MAX_VALUE);
		dude=play;
	}
//"hits" and returns whether or not added card
	public boolean hit(){
		if (dude.isIn() && dude.calc()==super.calc()&&dude.calc()<=14){
			super.hit();
			return true;
			
		}
		return false;
	}

}