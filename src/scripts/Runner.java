package scripts;

public class Runner {
	public static void main(String[] args) {
		LogoDrop dropLogo = new LogoDrop();
		while(!dropLogo.getLogoDone()) {
			System.out.print("Loading...");
		}
		dropLogo.dispose();
		GameName nameGame = new GameName();
		while(!nameGame.getGameNameDone()){
			System.out.print("Loading...");
		}
		nameGame.dispose();
		new ChooserDisplay();
	}

}