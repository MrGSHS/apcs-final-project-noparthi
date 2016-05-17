package holdem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;

import javax.swing.SwingUtilities;

public class HoldemGame {

	private Display display;
	private final int BIGBLIND = 500;
	private final int SMALLBLIND = 250;
	private final int ANTE = 125;

	private Player user;
	private Round round;
	private Table table;
	private int dealerIndex = 0;
	private boolean payout;

	public ArrayList<int[]> playerPositions = new ArrayList<>();
	private ArrayList<Player> actionsOrder = new ArrayList<>();
	private ArrayList<Player> players = new ArrayList<>();

	public Display getDisplay() {
		return display;
	}

	public Table getTable() {
		return table;
	}

	public Round getRound() {
		return round;
	}

	public Player getUser() {
		return user;
	}
	
	public boolean isPayout(){
		return payout;
	}

	public int getBigBlind() {
		return BIGBLIND;
	}

	public int getSmallBlind() {
		return SMALLBLIND;
	}

	public int getDealerIndex() {
		return dealerIndex;
	}

	public ArrayList<Player> getActionsOrder() {
		return actionsOrder;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPayout(boolean sp){
		payout = sp;
	}
	
	public HoldemGame() {
		int pos = 0;
		table = new Table();
		user = new Player(this, pos++);
		players.add(user);
		actionsOrder.add(user);
		Player computer1 = new Computer(this, pos++);
		players.add(computer1);
		actionsOrder.add(computer1);
		Player computer2 = new Computer(this, pos++);
		players.add(computer2);
		actionsOrder.add(computer2);
		Player computer3 = new Computer(this, pos++);
		players.add(computer3);
		actionsOrder.add(computer3);
		Player computer4 = new Computer(this, pos++);
		players.add(computer4);
		actionsOrder.add(computer4);
		round = new Round(this);
		new Timer().schedule(display = new Display(this), 0, 250);
		takeBlinds();
		takeAnte();
		display.setRoundTitle();
		round.preFlop();
	}

	public int getMaxBetAmount() {
		int max = 0;
		for (Player p : players) {
			if (p.getBetAmount() > max)
				max = p.getBetAmount();
		}
		return max;
	}

	public void incrementDealer() {
		Collections.rotate(actionsOrder, -1);
	}

	public void allComputersTakeAction() {
		new Thread(new Runnable() {
			@SuppressWarnings("static-access")
			public void run() {
				for (Player computer : actionsOrder) {
					if (!computer.isFolded()) {
						if (computer != getUser()) {
							getUser().setUserTurn(false);
							try {
								Thread.currentThread().sleep(((int) (Math.random() * 3) + 3) * 1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							computer.takeAction();
							if (getRound().moveOn())
								break;
						} else {
							getUser().setUserTurn(true);
							while (getUser().isUserTurn()) {
								try {
									Thread.currentThread().sleep(100);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		}).start();
	}

	// Takes The Blinds
	public void takeBlinds() {
		Collections.rotate(actionsOrder, -1);
		actionsOrder.get(0).setSmallBlind(true);
		actionsOrder.get(0).setBetAmount(SMALLBLIND);
		actionsOrder.get(0).setPoints(actionsOrder.get(0).getPoints() - SMALLBLIND);
		actionsOrder.get(0).setPointsInvested(SMALLBLIND);
		round.setPot(round.getPot() + SMALLBLIND);

		Collections.rotate(actionsOrder, -1);
		actionsOrder.get(0).setBigBlind(true);
		actionsOrder.get(0).setBetAmount(BIGBLIND);
		actionsOrder.get(0).setPoints(actionsOrder.get(0).getPoints() - BIGBLIND);
		actionsOrder.get(0).setPointsInvested(BIGBLIND);
		round.setPot(round.getPot() + BIGBLIND);
	}

	// Takes Ante
	public void takeAnte() {
		for (Player p : players) {
			if (!p.isBigBlind() && !p.isSmallBlind()) {
				p.setPoints(p.getPoints() - ANTE);
				p.setPointsInvested(ANTE);
				p.setBetAmount(ANTE);
				round.setPot(round.getPot() + ANTE);
			}
		}
	}

	// Pays Out Money In Pot To Winner
	public void payout() {
		ArrayList<Player> strongestPlayersIndex = new ArrayList<>();
		// TODO: Add In Kickers And High Card
		// Add First Active Player As Strongest Player
		for (Player p : players)
			if (!p.isFolded()) {
				strongestPlayersIndex.add(p);
				break;
			}
		for (Player p : players) {
			// If Not Folded
			if (!p.isFolded()) {
				// If One User's Hand Strength Is Better Than The Best Currently
				if (p.getHand().updateHandStrength() > strongestPlayersIndex.get(0).getHand().updateHandStrength()) {
					strongestPlayersIndex = new ArrayList<Player>();
					strongestPlayersIndex.add(p);
				}
				// If Final Hand Strengths Are Equal
				else if (p.getHand().updateHandStrength() == strongestPlayersIndex.get(0).getHand().updateHandStrength()
						&& p != strongestPlayersIndex.get(0)) {
					// Four Of A Kind
					if (p.getHand().getCurrentHandStrengthString().equals("Four Of A Kind")) {
						if (p.getHand().getQuads() == strongestPlayersIndex.get(0).getHand().getQuads())
							strongestPlayersIndex.add(p);
						else if (p.getHand().getQuads() > strongestPlayersIndex.get(0).getHand().getQuads()) {
							strongestPlayersIndex = new ArrayList<Player>();
							strongestPlayersIndex.add(p);
						}
					}

					// Full House
					if (p.getHand().getCurrentHandStrengthString().equals("Full House")) {
						if (p.getHand().getFullHouse().get(0) == strongestPlayersIndex.get(0).getHand().getFullHouse()
								.get(0))
							// If The Triples Are Equivalent
							if (p.getHand().getFullHouse().get(1) == strongestPlayersIndex.get(0).getHand()
									.getFullHouse().get(1))
							strongestPlayersIndex.add(p);
							else if (p.getHand().getFullHouse().get(1) > strongestPlayersIndex.get(0).getHand().getFullHouse().get(1)) {
							strongestPlayersIndex = new ArrayList<Player>();
							strongestPlayersIndex.add(p);
							}
					}

					// Flush
					if (p.getHand().getCurrentHandStrengthString().equals("Flush")) {
						strongestPlayersIndex.add(p);
					}

					// Straight
					if (p.getHand().getCurrentHandStrengthString().equals("Straight")) {
						if (p.getHand().getStraight() == strongestPlayersIndex.get(0).getHand().getStraight())
							strongestPlayersIndex.add(p);
						else if (p.getHand().getStraight() > strongestPlayersIndex.get(0).getHand().getStraight()) {
							strongestPlayersIndex = new ArrayList<Player>();
							strongestPlayersIndex.add(p);
						}
					}

					// Three Of A Kind
					if (p.getHand().getCurrentHandStrengthString().equals("Three Of A Kind")) {
						if (p.getHand().getTrips() == strongestPlayersIndex.get(0).getHand().getTrips())
							strongestPlayersIndex.add(p);
						else if (p.getHand().getTrips() > strongestPlayersIndex.get(0).getHand().getTrips()) {
							strongestPlayersIndex = new ArrayList<Player>();
							strongestPlayersIndex.add(p);
						}
					}

					// Two Pair
					if (p.getHand().getCurrentHandStrengthString().equals("Two-Pair")) {
						if (p.getHand().getTwoPair().get(0) == strongestPlayersIndex.get(0).getHand().getTwoPair()
								.get(0))
							// If The High Pair Are Equivalent
							if (p.getHand().getTwoPair().get(1) == strongestPlayersIndex.get(0).getHand().getTwoPair()
									.get(1))
							strongestPlayersIndex.add(p);
							else if (p.getHand().getTwoPair().get(1) > strongestPlayersIndex.get(0).getHand().getTwoPair().get(1)) {
							strongestPlayersIndex = new ArrayList<Player>();
							strongestPlayersIndex.add(p);
							} else if (p.getHand().getTwoPair().get(0) > strongestPlayersIndex.get(0).getHand().getTwoPair().get(0)) {
							strongestPlayersIndex = new ArrayList<Player>();
							strongestPlayersIndex.add(p);
							}
					}

					// Pair
					if (p.getHand().getCurrentHandStrengthString().equals("Pair")) {
						if (p.getHand().getPair() == strongestPlayersIndex.get(0).getHand().getPair())
							strongestPlayersIndex.add(p);
						else if (p.getHand().getPair() > strongestPlayersIndex.get(0).getHand().getPair()) {
							strongestPlayersIndex = new ArrayList<Player>();
							strongestPlayersIndex.add(p);
						}
					}
				}
			}
		}
		int numberShared = strongestPlayersIndex.size();
		for (Player p : strongestPlayersIndex) {
			p.setPoints((int) (p.getPoints() + round.getPot() / numberShared));
		}
		newRound();
	}

	// Reset Each Players Bet Amount At End Of Each Turn
	public void resetPlayerBetAmount() {
		for (Player p : players)
			p.setBetAmount(0);
	}

	// Checks To See If 2 Or More Players Are Still Active
	public boolean isRoundActive() {
		int total = 0;
		for (Player p : players) {
			if (!p.isFolded())
				total++;
		}
		if (total > 1)
			return true;
		payout();
		return false;
	}

	public boolean onMainThread() {
		return SwingUtilities.isEventDispatchThread();
	}

	// Creates New Round
	public void newRound() {
		for (Player p : players) {
			p.unFold();
			p.resetPointsInvested();
			p.resetActionBoolean();
			p.setFirstAction(false);
			p.setBigBlind(false);
			p.setSmallBlind(false);
		}
		resetPlayerBetAmount();
		table = new Table();
		round = new Round(this);
		takeBlinds();
		takeAnte();
		display.setRoundTitle();
		round.preFlop();
	}
}
