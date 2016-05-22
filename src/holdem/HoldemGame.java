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
	private ArrayList<Player> strongestPlayers = new ArrayList<>();

	@SuppressWarnings("unused")
	private HoldemGame getGame() {
		return this;
	}

	public Display getDisplay() {
		return display;
	}

	public Table getTable() {
		return table;
	}

	public Round getRound() {
		return round;
	}

	public ArrayList<Player> getStrongestPlayers() {
		return strongestPlayers;
	}

	public Player getUser() {
		return user;
	}

	public boolean isPayout() {
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
		display = new Display(this);
		new Timer().schedule(display, 0, 250);
		takeBlinds();
		takeAnte();
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

	public void allPlayersTakeAction() {
		new Thread(new Runnable() {
			@SuppressWarnings("static-access")
			public void run() {
				int numberOfRaises = 0;
				boolean check = false;
				boolean checkRaise = false;
				for (Player player : actionsOrder) {
					if (!player.isFolded()) {
						if (!player.equals(getUser())) {
							getUser().setIsTurn(false);
							player.setIsTurn(true);
							try {
								Thread.currentThread().sleep(((int) (Math.random() * 3) + 3) * 1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							player.takeAction();
							player.setIsTurn(false);
							if (getRound().moveOn()) {
								break;
							}
						} else {
							getUser().setIsTurn(true);
							while (getUser().isTurn()) {
								try {
									Thread.currentThread().sleep(100);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
				// If More Than One Person Has Raised, And Everyone Has Acted,
				// Then Run Another Rotation
				for (Player player : actionsOrder) {
					if (player.getRaiseBoolean())
						numberOfRaises++;
				}
				if (numberOfRaises > 1) {
					run();
				}
				// If Previous Person Checks, Then A Following One Raises, Then
				// Run Another Rotation
				for (Player p : actionsOrder) {
					if (check) {
						if (p.getRaiseBoolean()) {
							checkRaise = true;
						}
					}
					if (p.getCheckBoolean()) {
						check = true;
					}
				}
				if (checkRaise) {
					run();
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
		payout = true;
		strongestPlayers = new ArrayList<Player>();

		// Add First Player As Strongest Player
		for (Player p : players) {
			if (!p.isFolded()) {
				strongestPlayers.add(p);
				break;
			}
		}

		// Adds In Strongest Players To strongestPlayers
		for (Player p : players) {
			// If Not Folded
			if (!p.isFolded()) {
				String playerString = p.getHand().getCurrentHandStrengthString();
				String strongestPlayerString = strongestPlayers.get(0).getHand().getCurrentHandStrengthString();
				// If One User's Hand Strength Is Better Than The Best Currently
				if (p.getHand().updateHandStrength() > strongestPlayers.get(0).getHand().updateHandStrength()) {
					strongestPlayers = new ArrayList<Player>();
					strongestPlayers.add(p);
				}
				// If Final Hand Strengths Are Equal
				else if (p.getHand().updateHandStrength() == strongestPlayers.get(0).getHand().updateHandStrength()
						&& p != strongestPlayers.get(0)) {
					// Straight Flush, Four Of A Kind, Full House: Flush,
					// Straight All Have Same Hand Strength Number
					if (playerString.equals("Royal Flush")) {
						strongestPlayers.add(p);
					}

					// Straight Flush
					else if (playerString.equals("Straight Flush")) {
						if (p.getHand().getStraight() > strongestPlayers.get(0).getHand().getStraight()) {
							strongestPlayers = new ArrayList<Player>();
							strongestPlayers.add(p);
						} else if (p.getHand().getStraight() == strongestPlayers.get(0).getHand().getStraight()) {
							strongestPlayers.add(p);
						}
					}

					// Four Of A Kind
					else if (playerString.equals("Four Of A Kind") && !strongestPlayerString.equals("Straight Flush")) {
						if (p.getHand().getQuads() > strongestPlayers.get(0).getHand().getQuads()) {
							strongestPlayers = new ArrayList<Player>();
							strongestPlayers.add(p);
						} else if (p.getHand().getQuads() == strongestPlayers.get(0).getHand().getQuads()) {
							strongestPlayers.add(p);
						}
					}

					// Full House
					else if (playerString.equals("Full House") && !strongestPlayerString.equals("Straight Flush")
							&& !strongestPlayerString.equals("Four Of A Kind")) {
						// If Players Triple Is Greater
						if (p.getHand().getFullHouse().get(0) > strongestPlayers.get(0).getHand().getFullHouse()
								.get(0)) {
							strongestPlayers = new ArrayList<Player>();
							strongestPlayers.add(p);
						}
						// If The Triples Are Equivalent
						else if (p.getHand().getFullHouse().get(0) == strongestPlayers.get(0).getHand().getFullHouse()
								.get(0)) {
							// Check The Doubles
							if (p.getHand().getFullHouse().get(1) == strongestPlayers.get(0).getHand().getFullHouse()
									.get(1)) {
								strongestPlayers.add(p);
							} else if (p.getHand().getFullHouse().get(1) > strongestPlayers.get(0).getHand()
									.getFullHouse().get(1)) {
								strongestPlayers = new ArrayList<Player>();
								strongestPlayers.add(p);
							}
						}
					}

					// Flush
					else if (playerString.equals("Flush")) {
						int highestCardInHandWithFlushP1 = -1;
						int highestCardInHandWithFlushSP1 = -1;
						// Grabs Highest Player Card On Suite
						if (p.getHand().getFlush() == p.getHand().getHighestCard().getSuite()) {
							highestCardInHandWithFlushP1 = p.getHand().getHighestCard().getNumber();
						} else if (strongestPlayers.get(0).getHand().getFlush() == p.getHand().getLowestCard()
								.getSuite()) {
							highestCardInHandWithFlushP1 = p.getHand().getLowestCard().getNumber();
						}
						// Grabs Highest StrongestPlayer Card On Suite
						if (strongestPlayers.get(0).getHand().getFlush() == strongestPlayers.get(0).getHand()
								.getHighestCard().getSuite()) {
							highestCardInHandWithFlushSP1 = strongestPlayers.get(0).getHand().getHighestCard()
									.getNumber();
						} else if (strongestPlayers.get(0).getHand().getFlush() == strongestPlayers.get(0).getHand()
								.getLowestCard().getSuite()) {
							highestCardInHandWithFlushSP1 = strongestPlayers.get(0).getHand().getLowestCard()
									.getNumber();
						}
						// Calculates Who Is Strongest Player
						if (highestCardInHandWithFlushP1 > highestCardInHandWithFlushSP1) {
							strongestPlayers = new ArrayList<Player>();
							strongestPlayers.add(p);
						} else if (highestCardInHandWithFlushP1 == highestCardInHandWithFlushSP1) {
							strongestPlayers.add(p);
						}
					}

					// Straight
					else if (playerString.equals("Straight") && !strongestPlayerString.equals("Flush")) {
						if (p.getHand().getStraight() > strongestPlayers.get(0).getHand().getStraight()) {
							strongestPlayers = new ArrayList<Player>();
							strongestPlayers.add(p);
						} else if (p.getHand().getStraight() == strongestPlayers.get(0).getHand().getStraight()) {
							strongestPlayers.add(p);
						}
					}

					// Three Of A Kind
					else if (playerString.equals("Three Of A Kind")) {
						if (p.getHand().getTrips() > strongestPlayers.get(0).getHand().getTrips()) {
							strongestPlayers = new ArrayList<Player>();
							strongestPlayers.add(p);
						}
						if (p.getHand().getTrips() == strongestPlayers.get(0).getHand().getTrips()) {
							if (p.getHand().getHighestCard().getNumber() > strongestPlayers.get(0).getHand()
									.getHighestCard().getNumber()) {
								strongestPlayers = new ArrayList<Player>();
								strongestPlayers.add(p);
							} else if (p.getHand().getHighestCard().getNumber() == strongestPlayers.get(0).getHand()
									.getHighestCard().getNumber()) {
								if (p.getHand().getLowestCard().getNumber() == strongestPlayers.get(0).getHand()
										.getLowestCard().getNumber()) {
									strongestPlayers.add(p);
								} else if (p.getHand().getLowestCard().getNumber() > strongestPlayers.get(0).getHand()
										.getLowestCard().getNumber()) {
									strongestPlayers = new ArrayList<Player>();
									strongestPlayers.add(p);
								}
							}
						}
					}

					// Two Pair
					else if (playerString.equals("Two-Pair")) {
						if (p.getHand().getTwoPair().get(0) > strongestPlayers.get(0).getHand().getTwoPair().get(0)) {
							strongestPlayers = new ArrayList<Player>();
							strongestPlayers.add(p);
						}
						// If The High Pair Are Equivalent
						else if (p.getHand().getTwoPair().get(0) == strongestPlayers.get(0).getHand().getTwoPair()
								.get(0)) {
							if (p.getHand().getTwoPair().get(1) == strongestPlayers.get(0).getHand().getTwoPair()
									.get(1)) {
								// Check Kicker
								if (p.getHand().getHighestCard().getNumber() == strongestPlayers.get(0).getHand()
										.getHighestCard().getNumber()) {
									strongestPlayers.add(p);
								} else if (p.getHand().getHighestCard().getNumber() > strongestPlayers.get(0).getHand()
										.getHighestCard().getNumber()) {
									strongestPlayers = new ArrayList<Player>();
									strongestPlayers.add(p);
								}
							} else if (p.getHand().getTwoPair().get(1) > strongestPlayers.get(0).getHand().getTwoPair()
									.get(1)) {
								strongestPlayers = new ArrayList<Player>();
								strongestPlayers.add(p);
							}
						}
					}

					// Pair
					else if (playerString.equals("Pair")) {
						if (p.getHand().getPair() > strongestPlayers.get(0).getHand().getPair()) {
							strongestPlayers = new ArrayList<Player>();
							strongestPlayers.add(p);
						} else if (p.getHand().getPair() == strongestPlayers.get(0).getHand().getPair()) {
							// Check Kicker
							if (p.getHand().getHighestCard().getNumber() == strongestPlayers.get(0).getHand()
									.getHighestCard().getNumber()) {
								if (p.getHand().getLowestCard().getNumber() == strongestPlayers.get(0).getHand()
										.getLowestCard().getNumber()) {
									strongestPlayers.add(p);
								} else if (p.getHand().getLowestCard().getNumber() > strongestPlayers.get(0).getHand()
										.getLowestCard().getNumber()) {
									strongestPlayers = new ArrayList<Player>();
									strongestPlayers.add(p);
								}
							} else if (p.getHand().getHighestCard().getNumber() > strongestPlayers.get(0).getHand()
									.getHighestCard().getNumber()) {
								strongestPlayers = new ArrayList<Player>();
								strongestPlayers.add(p);
							}
						}
					}

					// High Card
					else {
						if (p.getHand().getHighestCard().getNumber() > strongestPlayers.get(0).getHand()
								.getHighestCard().getNumber()) {
							strongestPlayers = new ArrayList<Player>();
							strongestPlayers.add(p);
						} else if (p.getHand().getHighestCard().getNumber() == strongestPlayers.get(0).getHand()
								.getHighestCard().getNumber()) {
							if (p.getHand().getLowestCard().getNumber() == strongestPlayers.get(0).getHand()
									.getLowestCard().getNumber()) {
								strongestPlayers.add(p);
							} else if (p.getHand().getLowestCard().getNumber() > strongestPlayers.get(0).getHand()
									.getLowestCard().getNumber()) {
								strongestPlayers = new ArrayList<Player>();
								strongestPlayers.add(p);
							}
						}
					}
				}
			}
		}
		for (Player p : strongestPlayers) {
			p.setPoints((int) (p.getPoints() + round.getPot() / strongestPlayers.size()));
		}
		// Slows Down Before Payout
		new Thread(new Runnable() {
			@SuppressWarnings("static-access")
			public void run() {
				try {
					Thread.currentThread().sleep(10000);
					newRound();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// int numberShared = strongestPlayers.size();
				// for (Player p : strongestPlayers) {
				// p.setPoints((int) (p.getPoints() + round.getPot() /
				// numberShared));
				// }
			}
		}).start();
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
		payout = false;
		takeBlinds();
		takeAnte();
		display.setRoundTitle();
		round.preFlop();
	}
}
