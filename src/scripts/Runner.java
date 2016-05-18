package scripts;

public class Runner {
	public static void main(String[] args) {
		LogoDrop dropLogo = new LogoDrop();
		while(!dropLogo.getLogoDone()) {
			System.out.print("");
		}
		dropLogo.dispose();
		new ChooserDisplay();
	}

}