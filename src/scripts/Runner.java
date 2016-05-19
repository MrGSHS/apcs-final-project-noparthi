package scripts;

public class Runner {
	public static void main(String[] args) {
		Initializer dropLogo = new Initializer();
		while(!dropLogo.getGameNameDone()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		dropLogo.dispose();
		new ChooserDisplay();
	}

}