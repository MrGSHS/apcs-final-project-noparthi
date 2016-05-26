package blackjack;

public class BlackGame {
	private BlackDisplay display;
	private BlackPlayer user = new BlackPlayer();
	private BlackPlayer computer = new BlackComp();
	private boolean payout = false;
	private int pot;

	public void setPayout(boolean b) {
		payout = b;
	}

	public boolean getPayout() {
		return payout;
	}

	public BlackPlayer getUser() {
		return user;
	}

	public BlackPlayer getComputer() {
		return computer;
	}

	public BlackGame() {
		display = new BlackDisplay(this);
		start();
	}

	public int getPot() {
		return 2 * pot;
	}

	public void setPot(int pot) {
		this.pot = user.makeBet(pot);
	}

	public void start() {
		new Thread(new Runnable() {
			public void run() {
				payout = false;
				display.update();
				while (!user.getStand() && user.calc() < 21) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					display.update();
				}
				if (user.calc() > 21){
					payout = true;
					display.restart(false);
				}
				else {
					while (!computer.takeAction()) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.println("Computer total: " + computer.calc());
					System.out.println("Player total: " + user.calc());
					payout = true;
					if ((user.calc() - computer.calc() > 0 && user.calc() <= 21) || computer.calc() > 21) {
						display.restart(true);
					} else {
						display.restart(false);
					}
				}
			}
		}).start();
	}
}
