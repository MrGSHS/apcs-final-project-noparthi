package holdem;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import scripts.Card;

public class Display extends TimerTask {

	private ArrayList<String> NAMES = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("Andrew");
			add("Amy");
			add("Caitlyn");
			add("David");
			add("Ethan");
			add("Jerry");
			add("Jessica");
			add("Jonathan");
			add("Julia");
			add("Lily");
			add("Michael");
			add("Sophia");
			add("Srikar");
			add("Taylor");
			add("Zoe");
		}
	};
	private String USERNAME;
	private String COMP1NAME;
	private String COMP2NAME;
	private String COMP3NAME;
	private String COMP4NAME;

	private ArrayList<BufferedImage> LABELS = new ArrayList<BufferedImage>() {
		private static final long serialVersionUID = 1L;

		{
			try {
				add(ImageIO.read(getClass().getResourceAsStream("/labels/penguin-label.png")));
				add(ImageIO.read(getClass().getResourceAsStream("/labels/bird-label.png")));
				add(ImageIO.read(getClass().getResourceAsStream("/labels/monkey-label.png")));
				add(ImageIO.read(getClass().getResourceAsStream("/labels/frog-label.png")));
				add(ImageIO.read(getClass().getResourceAsStream("/labels/goat-label.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};
	private BufferedImage userLabel = LABELS.remove((int) (Math.random() * LABELS.size()));
	private BufferedImage computer1Label = LABELS.remove((int) (Math.random() * LABELS.size()));
	private BufferedImage computer2Label = LABELS.remove((int) (Math.random() * LABELS.size()));
	private BufferedImage computer3Label = LABELS.remove((int) (Math.random() * LABELS.size()));
	private BufferedImage computer4Label = LABELS.remove((int) (Math.random() * LABELS.size()));

	private final int CARD_WIDTH = 55;
	private final int CARD_HEIGHT = 80;
	private final int TABLE_WIDTH = 660;
	private final int TABLE_HEIGHT = 286;
	private final int FRAME_WIDTH = 900;
	private final int FRAME_HEIGHT = 600;
	private final int DEALER_WIDTH = 140;
	private final int DEALER_HEIGHT = 130;

	private Font buttonFont = new Font("SansSerif", Font.PLAIN, 12);
	private Color modifiedGrey = new Color(55, 53, 53);
	private JFrame frame;
	private TableDisplayPanel tablePanel;
	private ActionsDisplayPanel actionsPanel;

	private BufferedImage theme;
	private BufferedImage table;
	private BufferedImage dealer;
	private BufferedImage iconImage;

	private BufferedImage cardBack;
	private BufferedImage card1;
	private BufferedImage card2;
	private BufferedImage chips1k;
	private BufferedImage chips5k;
	private BufferedImage chips10k;
	private BufferedImage chips25k;
	private BufferedImage chips50k;
	private ArrayList<BufferedImage> tableCards;
	private ArrayList<BufferedImage> faceUpComputerCards;

	private HoldemGame game;

	private JButton fold = new JButton("Fold");
	private JButton check = new JButton("Check");
	private JButton call = new JButton("Call");
	private JButton raise = new JButton("Raise");
	private JButton tip = new JButton("Tip Mr. G");

	public boolean userTip = false;
	private int extraCreditPoints = 1;
	private int counter = 0;
	private ArrayList<Card> cardsOnTable;

	public Display(HoldemGame game) {
		this.game = game;
		tableCards = new ArrayList<BufferedImage>();

		// Prompt For User Name
		String[] options = { "OK" };
		JPanel panel = new JPanel();
		JLabel lbl = new JLabel("Enter Your Name: ");
		JTextField txt = new JTextField(10);
		panel.add(lbl);
		panel.add(txt);
		int selectedOption = JOptionPane.showOptionDialog(null, panel, "Welcome to Texas Hold'em",
				JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if (selectedOption == 0) {
			if (!txt.getText().trim().equals(""))
				USERNAME = txt.getText();
			else
				USERNAME = NAMES.remove((int) (Math.random() * NAMES.size()));
		} else {
			System.exit(0);
		}
		game.getPlayers().get(0).setName(USERNAME);

		// Removes User Name From Naming List If Exists
		for (String name : NAMES)
			if (USERNAME.equals(name))
				NAMES.remove(name);

		// Set Computer Names
		COMP1NAME = NAMES.remove((int) (Math.random() * NAMES.size()));
		game.getPlayers().get(1).setName(COMP1NAME);
		COMP2NAME = NAMES.remove((int) (Math.random() * NAMES.size()));
		game.getPlayers().get(2).setName(COMP2NAME);
		COMP3NAME = NAMES.remove((int) (Math.random() * NAMES.size()));
		game.getPlayers().get(3).setName(COMP3NAME);
		COMP4NAME = NAMES.remove((int) (Math.random() * NAMES.size()));
		game.getPlayers().get(4).setName(COMP4NAME);

		// Remind User that ESC opens hand chart
		JOptionPane.showConfirmDialog(null, "Press ESC Anytime To Bring Up The Hand Chart", "Reminder",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);

		try {
			dealer = ImageIO.read(getClass().getResourceAsStream("/other/dealer-face.png"));
			table = ImageIO.read(getClass().getResourceAsStream("/other/poker-table.png"));
			theme = ImageIO.read(getClass().getResourceAsStream("/themes/red-gradient.jpg"));
			cardBack = ImageIO.read(getClass().getResourceAsStream("/other/card-back.png"));

			chips1k = ImageIO.read(getClass().getResourceAsStream("/chips/1k.png"));
			chips5k = ImageIO.read(getClass().getResourceAsStream("/chips/5k.png"));
			chips10k = ImageIO.read(getClass().getResourceAsStream("/chips/10k.png"));
			chips25k = ImageIO.read(getClass().getResourceAsStream("/chips/25k.png"));
			chips50k = ImageIO.read(getClass().getResourceAsStream("/chips/50k.png"));
			iconImage = ImageIO.read(this.getClass().getResource("/menu/pokerIcon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Main Frame
		frame = new JFrame();
		frame.setTitle("Texas Holdem: Round ");
		frame.setIconImage(iconImage);
		tablePanel = new TableDisplayPanel();
		actionsPanel = new ActionsDisplayPanel();
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		// frame.setLayout(null);
		tablePanel.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ESCAPE)
					new HelpDisplay();
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyTyped(KeyEvent key) {
			}

		});
		tablePanel.setFocusable(true);
		frame.add(actionsPanel);
		frame.add(tablePanel);
		frame.setVisible(true);
	}

	public JFrame getFrame() {
		return frame;
	}

	public JPanel getTablePanel() {
		return tablePanel;
	}

	public JPanel getActionsPanel() {
		return actionsPanel;
	}

	private class TableDisplayPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		// Dealer & Background & Theme
		public void drawBackground(Graphics g) {
			g.drawImage(theme, 0, 0, null);
			g.drawImage(dealer, FRAME_WIDTH / 2 - DEALER_WIDTH / 2,
					(FRAME_HEIGHT / 2 - TABLE_HEIGHT / 2 - 15) - DEALER_HEIGHT, null);
			g.drawImage(table, FRAME_WIDTH / 2 - TABLE_WIDTH / 2, FRAME_HEIGHT / 2 - TABLE_HEIGHT / 2 - 25, null);
		}

		// Button Background
		public void drawButtonBackground(Graphics g) {
			// Box
			g.setColor(new Color(32, 32, 32));
			g.fillRect(0, 525, FRAME_WIDTH, 50);
			// Lines Separating Each Action
			g.setColor(Color.BLACK);
			g.drawLine(FRAME_WIDTH / 4, 525, FRAME_WIDTH / 4, 575);
			g.drawLine(FRAME_WIDTH / 2, 525, FRAME_WIDTH / 2, 575);
			g.drawLine(3 * FRAME_WIDTH / 4, 525, 3 * FRAME_WIDTH / 4, 575);
			// String
			g.setColor(Color.WHITE);
			g.setFont(buttonFont);
			g.drawString("Fold", FRAME_WIDTH / 10, 555);
			g.drawString("Check", FRAME_WIDTH / 4 + FRAME_WIDTH / 10, 555);
			g.drawString("Call", FRAME_WIDTH / 2 + FRAME_WIDTH / 10, 555);
			g.drawString("Raise", 3 * FRAME_WIDTH / 4 + FRAME_WIDTH / 10, 555);
		}

		// Add Player Positions
		public void addPlayerPositions() {
			game.playerPositions.add(
					new int[] { FRAME_WIDTH / 2 - userLabel.getWidth() / 2, FRAME_HEIGHT / 2 + TABLE_HEIGHT / 2 + 5 });
			game.playerPositions
					.add(new int[] { FRAME_WIDTH / 2 - computer1Label.getWidth() / 2 - 340, FRAME_HEIGHT / 2 + 55 });
			game.playerPositions
					.add(new int[] { FRAME_WIDTH / 2 - computer2Label.getWidth() / 2 - 310, FRAME_HEIGHT / 2 - 190 });
			game.playerPositions
					.add(new int[] { FRAME_WIDTH - computer3Label.getWidth() / 2 - 135, FRAME_HEIGHT / 2 - 190 });
			game.playerPositions
					.add(new int[] { FRAME_WIDTH / 2 - computer4Label.getWidth() / 2 + 340, FRAME_HEIGHT / 2 + 55 });

		}

		// Draw User Cards
		public void drawUserCards(Graphics g) {
			if (!game.getUser().isFolded()) {
				g.drawImage(card1, game.playerPositions.get(0)[0] + CARD_WIDTH + 25,
						game.playerPositions.get(0)[1] - 50, CARD_WIDTH + 10, CARD_HEIGHT + 20, null);
				g.drawImage(card2, game.playerPositions.get(0)[0] + 15, game.playerPositions.get(0)[1] - 50,
						CARD_WIDTH + 10, CARD_HEIGHT + 20, null);
			}
		}

		// Draw Computer Cards
		public void drawComputerCards(Graphics g) {
			// If Is Pay-out...Otherwise...
			if (game.isPayout()) {
				faceUpComputerCards = new ArrayList<BufferedImage>();
				// Adds Computer Cards To Array
				for (int i = 1; i < game.getPlayers().size(); i++) {
					try {
						String card1Path = "/cards/"
								+ game.getPlayers().get(i).getCurrentHand()[0].getNumber().toString() + "_of_"
								+ game.getUser().getCurrentHand()[0].getSuiteValue() + ".jpg";
						String card2Path = "/cards/"
								+ game.getPlayers().get(i).getCurrentHand()[1].getNumber().toString() + "_of_"
								+ game.getUser().getCurrentHand()[1].getSuiteValue() + ".jpg";
						faceUpComputerCards.add(ImageIO.read(getClass().getResourceAsStream(card1Path)));
						faceUpComputerCards.add(ImageIO.read(getClass().getResourceAsStream(card2Path)));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				// Draws In Cards For Non-Folded Players
				for (int i = 1; i < game.getPlayers().size(); i++) {
					if (!game.getPlayers().get(i).isFolded()) {
						g.drawImage(faceUpComputerCards.get(i * 2 - 2),
								game.playerPositions.get(i)[0] + CARD_WIDTH + 25, game.playerPositions.get(i)[1] - 50,
								CARD_WIDTH + 10, CARD_HEIGHT + 20, null);
						g.drawImage(faceUpComputerCards.get(i * 2 - 1), game.playerPositions.get(i)[0] + 15,
								game.playerPositions.get(i)[1] - 50, CARD_WIDTH + 10, CARD_HEIGHT + 20, null);
					}
				}
				// Waits For A While
			} else {
				// Draws In CardBack For Non-Folded Players
				for (int i = 1; i < game.getPlayers().size(); i++) {
					if (!game.getPlayers().get(i).isFolded()) {
						g.drawImage(cardBack, game.playerPositions.get(i)[0] + CARD_WIDTH + 25,
								game.playerPositions.get(i)[1] - 50, CARD_WIDTH + 10, CARD_HEIGHT + 20, null);
						g.drawImage(cardBack, game.playerPositions.get(i)[0] + 15, game.playerPositions.get(i)[1] - 50,
								CARD_WIDTH + 10, CARD_HEIGHT + 20, null);
					}
				}
			}
		}

		// Draw Blinds
		public void drawBlinds(Graphics g) {
			for (Player p : game.getPlayers()) {
				int isTurnBorder = 0;
				g.setFont(new Font("Calibri", Font.BOLD, 14));
				g.setColor(modifiedGrey);
				if (p.isTurn()) {
					isTurnBorder = 3;
				}
				if (p.isSmallBlind()) {
					g.fillRoundRect(game.playerPositions.get(p.getPosition())[0],
							game.playerPositions.get(p.getPosition())[1] + userLabel.getHeight() + isTurnBorder, 80, 18,
							10, 10);
					g.setColor(Color.WHITE);
					g.drawString("SM. Blind", game.playerPositions.get(p.getPosition())[0] + 13,
							game.playerPositions.get(p.getPosition())[1] + userLabel.getHeight() + isTurnBorder + 13);
				} else if (p.isBigBlind()) {
					g.fillRoundRect(game.playerPositions.get(p.getPosition())[0],
							game.playerPositions.get(p.getPosition())[1] + userLabel.getHeight() + isTurnBorder, 80, 18,
							10, 10);
					g.setColor(Color.WHITE);
					g.drawString("Big Blind", game.playerPositions.get(p.getPosition())[0] + 13,
							game.playerPositions.get(p.getPosition())[1] + userLabel.getHeight() + isTurnBorder + 13);
				}
			}
		}

		// Draw Whose Turn It Is Turn Around Label
		public void drawTurnBorder(Graphics g) {
			g.setColor(new Color(30, 30, 180));
			for (Player p : game.getPlayers()) {
				if (p.isTurn()) {
					g.fillRoundRect(game.playerPositions.get(p.getPosition())[0] - 3,
							game.playerPositions.get(p.getPosition())[1] - 3, userLabel.getWidth() + 6,
							userLabel.getHeight() + 6, 30, 30);
				}
			}
		}

		// Draw User Label
		public void drawUserLabel(Graphics g) {
			g.drawImage(userLabel, FRAME_WIDTH / 2 - userLabel.getWidth() / 2, FRAME_HEIGHT / 2 + TABLE_HEIGHT / 2 + 5,
					null);
		}

		// Draw Computer Labels
		public void drawComputerLabels(Graphics g) {
			g.drawImage(computer1Label, game.playerPositions.get(1)[0], game.playerPositions.get(1)[1], null);
			g.drawImage(computer2Label, game.playerPositions.get(2)[0], game.playerPositions.get(2)[1], null);
			g.drawImage(computer3Label, game.playerPositions.get(3)[0], game.playerPositions.get(3)[1], null);
			g.drawImage(computer4Label, game.playerPositions.get(4)[0], game.playerPositions.get(4)[1], null);
		}

		// Draw Check/Call/Raise/Fold
		public void drawAction(Graphics g) {
			g.setFont(new Font("Calibri", Font.BOLD, 14));
			g.setColor(modifiedGrey);

			for (Player p : game.getPlayers()) {
				int isTurnBorder = 0;
				int srcX = game.playerPositions.get(p.getPosition())[0] + 80;
				int rectWidth = 80;
				int stringOffset = 0;
				if (p.isTurn()) {
					isTurnBorder = 3;
				}
				if (!p.isBigBlind() && !p.isSmallBlind()) {
					srcX = game.playerPositions.get(p.getPosition())[0];
					rectWidth = userLabel.getWidth();
					stringOffset = 40;
				}
				if (p.isFolded()) {
					g.setColor(Color.RED);
					g.fillRoundRect(srcX, game.playerPositions.get(p.getPosition())[1] + userLabel.getHeight(),
							rectWidth, 18, 10, 10);
					g.setColor(Color.BLACK);
					g.drawString("Fold", game.playerPositions.get(p.getPosition())[0] + 110 - stringOffset,
							game.playerPositions.get(p.getPosition())[1] + userLabel.getHeight() + 13);
				}
				if (p.isBigBlind() && game.getTable().getCardsOnTable().size() == 0 && p.getRaiseBoolean()
						&& p.getPointsInvested() == game.getBigBlind()) {
				} else if (!p.getCheckBoolean() && !p.getCallBoolean() && !p.getRaiseBoolean()) {
				} else if (p.getCheckBoolean() || p.getCallBoolean()) {
					g.setColor(new Color(53, 192, 18));
					g.fillRoundRect(srcX,
							game.playerPositions.get(p.getPosition())[1] + userLabel.getHeight() + isTurnBorder,
							rectWidth, 18, 10, 10);

					g.setColor(Color.BLACK);
					if (p.getCheckBoolean())
						g.drawString("Check", game.playerPositions.get(p.getPosition())[0] + 100 - stringOffset,
								game.playerPositions.get(p.getPosition())[1] + userLabel.getHeight() + isTurnBorder
										+ 13);
					else
						g.drawString("Call", game.playerPositions.get(p.getPosition())[0] + 110 - stringOffset,
								game.playerPositions.get(p.getPosition())[1] + userLabel.getHeight() + isTurnBorder
										+ 13);
				} else if (p.getRaiseBoolean()) {
					g.setColor(Color.YELLOW);
					g.fillRoundRect(srcX,
							game.playerPositions.get(p.getPosition())[1] + userLabel.getHeight() + isTurnBorder,
							rectWidth, 18, 10, 10);
					g.setColor(Color.BLACK);
					g.drawString("Raise", game.playerPositions.get(p.getPosition())[0] + 105 - stringOffset,
							game.playerPositions.get(p.getPosition())[1] + userLabel.getHeight() + isTurnBorder + 13);
				}
			}
		}

		// Add Player Names
		public void addPlayerName(Graphics g) {
			g.setFont(new Font("Calibri", Font.BOLD, 16));
			g.setColor(Color.BLACK);
			g.drawString(USERNAME,
					game.playerPositions.get(0)[0]
							+ (userLabel.getWidth() - g.getFontMetrics().stringWidth(USERNAME)) / 2 + 15,
					game.playerPositions.get(0)[1] + 20);
		}

		// Add Computer Names
		public void addComputerNames(Graphics g) {
			g.setFont(new Font("Calibri", Font.PLAIN, 16));
			g.drawString(COMP1NAME,
					game.playerPositions.get(1)[0] + 48 + (128 - g.getFontMetrics().stringWidth(COMP1NAME)) / 3,
					game.playerPositions.get(1)[1] + 20);
			g.drawString(COMP2NAME,
					game.playerPositions.get(2)[0] + 48 + (128 - g.getFontMetrics().stringWidth(COMP2NAME)) / 3,
					game.playerPositions.get(2)[1] + 20);
			g.drawString(COMP3NAME,
					game.playerPositions.get(3)[0] + 48 + (128 - g.getFontMetrics().stringWidth(COMP3NAME)) / 3,
					game.playerPositions.get(3)[1] + 20);
			g.drawString(COMP4NAME,
					game.playerPositions.get(4)[0] + 48 + (128 - g.getFontMetrics().stringWidth(COMP4NAME)) / 3,
					game.playerPositions.get(4)[1] + 20);
		}

		// Add Player Points
		public void addPlayerPoints(Graphics g) {
			g.setFont(new Font("Calibri", Font.PLAIN, 20));
			g.setColor(new Color(5, 145, 60));
			g.drawString(game.getUser().getPoints() + " Pts", game.playerPositions.get(0)[0] + 60,
					game.playerPositions.get(0)[1] + 45);
		}

		// Add Computer Points
		public void addComputerPoints(Graphics g) {
			g.drawString(game.getPlayers().get(4).getPoints() + " Pts", game.playerPositions.get(4)[0] + 63,
					game.playerPositions.get(4)[1] + computer1Label.getHeight() - 11);
			g.drawString(game.getPlayers().get(2).getPoints() + " Pts", game.playerPositions.get(2)[0] + 63,
					game.playerPositions.get(2)[1] + computer2Label.getHeight() - 11);
			g.drawString(game.getPlayers().get(3).getPoints() + " Pts", game.playerPositions.get(3)[0] + 63,
					game.playerPositions.get(3)[1] + computer3Label.getHeight() - 11);
			g.drawString(game.getPlayers().get(1).getPoints() + " Pts", game.playerPositions.get(1)[0] + 63,
					game.playerPositions.get(1)[1] + computer4Label.getHeight() - 11);
		}

		// Add Pot
		public void addPot(Graphics g) {
			g.setColor(modifiedGrey);
			g.fillRoundRect(FRAME_WIDTH / 2 - 70, FRAME_HEIGHT / 2 + CARD_WIDTH - 55, 140, 20, 15, 15);
			g.setColor(new Color(246, 246, 246));
			g.setFont(new Font("Calibri", Font.BOLD, 16));
			String potSize = "POT: " + game.getRound().getPot() + " Pts";
			int potSizeWidth = g.getFontMetrics().stringWidth(potSize);
			g.drawString(potSize, FRAME_WIDTH / 2 - (int) (potSizeWidth / 2), FRAME_HEIGHT / 2 + CARD_WIDTH - 40);
		}

		// Add Dealt Cards
		public void addDealtCards(Graphics g) {
			if (tableCards.size() > 0) {
				for (int i = 0; i < tableCards.size(); i++) {
					g.drawImage(tableCards.get(i), 277 + (CARD_WIDTH + 15) * i, FRAME_HEIGHT / 2 - CARD_HEIGHT / 2 - 45,
							CARD_WIDTH, CARD_HEIGHT, null);
				}
			}
		}

		// Add Hand Strength Meter
		public void addHandStrengthMeter(Graphics g) {
			final int BORDER = 5;
			int handStrength = 0;
			g.setColor(Color.DARK_GRAY);

			// Sets Hand Strength
			if (tableCards.size() == 0)
				handStrength = game.getUser().getHand().initialHandStrength();
			else
				handStrength = game.getUser().getHand().updateHandStrength();

			// Hand Strength Bar Background
			g.setColor(Color.DARK_GRAY);
			g.fillRoundRect(600, 494, 296, 31, 10, 10);
			// Fills In Little Ridge
			g.fillRect(600, 490, 5, 10);

			// Writes In Hand Strength
			Map<TextAttribute, Object> map = new Hashtable<TextAttribute, Object>();
			map.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			Font underlineFont = new Font("Calibri", Font.BOLD, 16).deriveFont(map);
			g.setFont(underlineFont);
			String strengthString = game.getUser().getHand().getCurrentHandStrengthString();
			int strWidth = g.getFontMetrics().stringWidth(strengthString);
			g.fillRoundRect(600, 474, strWidth + 10, 20 + BORDER, 10, 10);
			g.setColor(Color.WHITE);
			g.drawString(strengthString, 600 + 5, 474 + 17);

			// Hand Strength Bar
			g.setColor(Color.BLACK);
			g.fillRoundRect(600 + BORDER, 494 + BORDER, 290 - BORDER, 25 - BORDER, 10, 10);

			// Changes Color Of Rectangle
			if (handStrength <= 3)
				g.setColor(Color.RED);
			else if (handStrength <= 7)
				g.setColor(Color.YELLOW);
			else
				g.setColor(Color.GREEN);

			// How Much To Fill
			g.fillRoundRect(600 + BORDER, 494 + BORDER, 29 * handStrength - BORDER, 25 - BORDER, 10, 10);

		}

		// Add Bets
		public void addBets(Graphics g) {
			g.setFont(new Font("Calibri", Font.BOLD, 16));

			// User
			g.setColor(modifiedGrey);
			g.fillRoundRect(game.playerPositions.get(0)[0] + 90, game.playerPositions.get(0)[1] - 90, 60, 20, 15, 15);
			g.setColor(Color.WHITE);
			g.drawString("" + (double) game.getPlayers().get(0).getBetAmount() / 1000 + "K",
					game.playerPositions.get(0)[0] + 100, game.playerPositions.get(0)[1] - 75);
			// Computer
			g.setColor(modifiedGrey);
			g.fillRoundRect(game.playerPositions.get(1)[0] + 185, game.playerPositions.get(1)[1], 60, 20, 15, 15);
			g.fillRoundRect(game.playerPositions.get(2)[0] + userLabel.getWidth() + 50,
					game.playerPositions.get(2)[1] + userLabel.getHeight(), 60, 20, 15, 15);
			g.fillRoundRect(game.playerPositions.get(3)[0] - 100,
					game.playerPositions.get(3)[1] + userLabel.getHeight(), 60, 20, 15, 15);
			g.fillRoundRect(game.playerPositions.get(4)[0] - 85, game.playerPositions.get(4)[1], 60, 20, 15, 15);

			g.setColor(Color.WHITE);
			g.drawString("" + (double) game.getPlayers().get(1).getBetAmount() / 1000 + "K",
					game.playerPositions.get(1)[0] + 195, game.playerPositions.get(1)[1] + 15);
			g.drawString("" + (double) game.getPlayers().get(2).getBetAmount() / 1000 + "K",
					game.playerPositions.get(2)[0] + userLabel.getWidth() + 60,
					game.playerPositions.get(2)[1] + userLabel.getHeight() + 15);
			g.drawString("" + (double) game.getPlayers().get(3).getBetAmount() / 1000 + "K",
					game.playerPositions.get(3)[0] - 90, game.playerPositions.get(3)[1] + userLabel.getHeight() + 15);
			g.drawString("" + (double) game.getPlayers().get(4).getBetAmount() / 1000 + "K",
					game.playerPositions.get(4)[0] - 75, game.playerPositions.get(4)[1] + 15);
		}

		// Add Chips
		public void addChips(Graphics g) {
			int chipsWidth = chips5k.getWidth();
			int chipsHeight = chips5k.getHeight();
			g.drawImage(calculateChips(game.getPlayers().get(0)), FRAME_WIDTH / 2 - chipsWidth / 2 - 10,
					game.playerPositions.get(0)[1] - chipsHeight * 2 - 20, null);
			g.drawImage(calculateChips(game.getPlayers().get(1)), game.playerPositions.get(1)[0] + 175,
					game.playerPositions.get(1)[1] + userLabel.getHeight() - 110, null);
			g.drawImage(calculateChips(game.getPlayers().get(2)), game.playerPositions.get(2)[0] + 170,
					game.playerPositions.get(2)[1] + userLabel.getHeight() - 10, null);
			g.drawImage(calculateChips(game.getPlayers().get(3)), game.playerPositions.get(3)[0] - 40,
					game.playerPositions.get(3)[1] + userLabel.getHeight() - 10, null);
			g.drawImage(calculateChips(game.getPlayers().get(4)), game.playerPositions.get(4)[0] - 40,
					game.playerPositions.get(4)[1] + userLabel.getHeight() - 110, null);
		}

		// Add Tips
		public void addTipEffects(Graphics g) {
			if (userTip) {
				if (counter <= 8) {
					String tipString = "Thanks " + USERNAME + "! You get " + extraCreditPoints
							+ " extra credit points!";
					g.setColor(Color.WHITE);
					g.fillOval(FRAME_WIDTH / 2 - g.getFontMetrics().stringWidth(tipString) - 15, -15,
							g.getFontMetrics().stringWidth(tipString) + 30, 60);

					Polygon speechBubbleTail = new Polygon();
					speechBubbleTail.addPoint(FRAME_WIDTH / 2 - g.getFontMetrics().stringWidth(tipString) / 2 + 30, 40);
					speechBubbleTail.addPoint(FRAME_WIDTH / 2 - g.getFontMetrics().stringWidth(tipString) / 2 + 50, 40);
					speechBubbleTail.addPoint(FRAME_WIDTH / 2 - g.getFontMetrics().stringWidth(tipString) / 2 + 95, 70);
					g.fillPolygon(speechBubbleTail);

					g.setColor(Color.BLACK);
					g.drawString(tipString, FRAME_WIDTH / 2 - g.getFontMetrics().stringWidth(tipString), 20);
					counter++;
				} else {
					extraCreditPoints++;
					counter = 0;
					userTip = false;
				}
			}
		}

		// Draw Pay-out
		public void drawWinner(Graphics g) {
			if (counter <= 40) {
				String payoutString = "";
				if (game.getStrongestPlayers().size() == 1) {
					payoutString = game.getStrongestPlayers().get(0).getName() + " Wins With: "
							+ game.getStrongestPlayers().get(0).getHand().getCurrentHandStrengthString();
				} else {
					for (Player p : game.getStrongestPlayers()) {
						payoutString += p.getName() + ", ";
					}
					payoutString += " Wins With: "
							+ game.getStrongestPlayers().get(0).getHand().getCurrentHandStrengthString();
				}

				g.setColor(Color.WHITE);
				g.fillOval(FRAME_WIDTH / 2 - g.getFontMetrics().stringWidth(payoutString) - 15, -15,
						g.getFontMetrics().stringWidth(payoutString) + 30, 60);

				Polygon speechBubbleTail = new Polygon();
				speechBubbleTail.addPoint(FRAME_WIDTH / 2 - g.getFontMetrics().stringWidth(payoutString) / 2 + 30, 40);
				speechBubbleTail.addPoint(FRAME_WIDTH / 2 - g.getFontMetrics().stringWidth(payoutString) / 2 + 50, 40);
				speechBubbleTail.addPoint(FRAME_WIDTH / 2 - g.getFontMetrics().stringWidth(payoutString) / 2 + 95, 70);
				g.fillPolygon(speechBubbleTail);

				g.setColor(Color.BLACK);
				g.drawString(payoutString, FRAME_WIDTH / 2 - g.getFontMetrics().stringWidth(payoutString), 20);
				counter++;
			} else if (!userTip) {
				counter = 0;
			}
		}

		// Actual Button Removal
		public void buttonRemoval(Graphics g) {
			if (!game.getUser().isTurn()) {
				removeAllButtons();
			} else if (!game.getUser().isFolded()) {
				addAllButtons();
				// Removes Check If Necessary
				if (game.getMaxBetAmount() - game.getUser().getBetAmount() == 0) {
					addCheck();
				} else {
					removeCheck();
				}
			}
		}

		public void paintComponent(Graphics g) {
			// Initialize Stuff
			g.setFont(new Font("Calibri", Font.BOLD, 20));
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// Draw Background
			drawBackground(g);
			drawButtonBackground(g);

			// Add Player Positions Into An Array
			addPlayerPositions();

			// Draw Cards
			drawUserCards(g);
			drawComputerCards(g);

			// DrawBlinds
			drawBlinds(g);

			// Draw Labels
			drawTurnBorder(g);
			drawUserLabel(g);
			drawComputerLabels(g);

			// Draw Action
			drawAction(g);

			// Add Names To Labels
			addPlayerName(g);
			addComputerNames(g);

			// Add Points To Labels
			addPlayerPoints(g);
			addComputerPoints(g);

			// Add Pot
			addPot(g);

			// Add Dealt Cards
			addDealtCards(g);

			// Add Hand Strength Meter
			addHandStrengthMeter(g);

			// Add User + Computer's Bet
			addBets(g);

			// Add chips
			addChips(g);

			// Congratulations You Wasted Money - Tip
			addTipEffects(g);

			// Draw Pay-out If Necessary
			if (game.isPayout()) {
				drawWinner(g);
			}
			// Button Removal
			buttonRemoval(g);
		}
	}

	public BufferedImage calculateChips(Player p) {
		if (p.getPointsInvested() / 50000 >= 1)
			return chips50k;
		else if (p.getPointsInvested() / 25000 >= 1)
			return chips25k;
		else if (p.getPointsInvested() / 10000 >= 1)
			return chips10k;
		else if (p.getPointsInvested() / 5000 >= 1) {
			return chips5k;
		} else if (p.getPointsInvested() / 1000 >= 1) {
			return chips1k;
		}
		return null;
	}

	// Adds Buttons To JFrame
	private class ActionsDisplayPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		private final int BUTTON_WIDTH = FRAME_WIDTH / 4;
		private final int BUTTON_HEIGHT = 50;

		public ActionsDisplayPanel() {
			fold.addActionListener(new ButtonListener());
			check.addActionListener(new ButtonListener());
			call.addActionListener(new ButtonListener());
			raise.addActionListener(new ButtonListener());
			tip.addActionListener(new ButtonListener());

			fold.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
			check.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
			call.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
			raise.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
			tip.setSize(2 * BUTTON_WIDTH / 3, BUTTON_HEIGHT / 2);

			raise.setLocation((FRAME_WIDTH - BUTTON_WIDTH), 525);
			call.setLocation((FRAME_WIDTH - 2 * BUTTON_WIDTH), 525);
			check.setLocation((FRAME_WIDTH - 3 * BUTTON_WIDTH), 525);
			fold.setLocation(0, 525);
			tip.setLocation(373, 130);

			raise.setFont(buttonFont);
			check.setFont(buttonFont);
			call.setFont(buttonFont);
			fold.setFont(buttonFont);
			tip.setFont(buttonFont);

			// Set Tip Button Transparent && Text To White
			tip.setOpaque(false);
			tip.setContentAreaFilled(false);
			tip.setBorderPainted(false);
			tip.setForeground(Color.WHITE);

			frame.add(fold);
			frame.add(check);
			frame.add(call);
			frame.add(raise);
			frame.add(tip);
		}

	}

	// Button Listeners
	public class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			if (evt.getSource() == raise) {
				// Gathers Largest Bet Amount To Raise
				int maxBet = 0;
				int bet = 0;
				for (Player p : game.getPlayers()) {
					bet = p.getBetAmount();
					if (bet > maxBet)
						maxBet = bet;
				}
				// Ask For User Input
				String stringRaiseAmount = (String) JOptionPane.showInputDialog(frame, "Enter Raise Amount:",
						maxBet + 500);
				try {
					stringRaiseAmount = stringRaiseAmount.replaceAll("[^0-9]", "");
					int intRaiseAmount = Integer.parseInt(stringRaiseAmount);

					if (game.getUser().raise(intRaiseAmount)) {
						game.getRound().moveOn();
					} else {
						return;
					}
				} catch (NullPointerException e) {
					System.out.println("Raise Cancelled");
				}
			} else if (evt.getSource() == call) {
				game.getUser().call();
				game.getRound().moveOn();
			} else if (evt.getSource() == check) {
				game.getUser().check();
				game.getRound().moveOn();
			} else if (evt.getSource() == fold) {
				game.getUser().fold();
				game.getRound().moveOn();
			}
			if (evt.getSource() == tip) {
				if (game.getUser().getPoints() >= 2000) {
					game.getUser().setPoints(game.getUser().getPoints() - 4 * game.getBigBlind());
					userTip = true;
					frame.repaint();
				}
			}
		}
	}

	public void run() {
		cardsOnTable = game.getTable().getCardsOnTable();
		reloadImages();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// System.out.println(SwingUtilities.isEventDispatchThread());
				frame.repaint();
			}
		});
	}

	private void reloadImages() {
		tableCards = new ArrayList<BufferedImage>();
		for (int i = 0; i < cardsOnTable.size(); i++) {
			Card card = cardsOnTable.get(i);
			String fileName = "/cards/" + card.getNumber().toString() + "_of_" + card.getSuiteValue() + ".jpg";
			try {
				tableCards.add(ImageIO.read(getClass().getResourceAsStream(fileName)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			String card1Path = "/cards/" + game.getUser().getCurrentHand()[0].getNumber().toString() + "_of_"
					+ game.getUser().getCurrentHand()[0].getSuiteValue() + ".jpg";
			String card2Path = "/cards/" + game.getUser().getCurrentHand()[1].getNumber().toString() + "_of_"
					+ game.getUser().getCurrentHand()[1].getSuiteValue() + ".jpg";
			card1 = ImageIO.read(getClass().getResourceAsStream(card1Path));
			card2 = ImageIO.read(getClass().getResourceAsStream(card2Path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Set Round Title (1,2,3,4, etc.)
	public void setRoundTitle() {
		frame.setTitle("Texas Holdem: Round " + game.getRound().getRoundNumber());
	}

	// Remove Check Button
	public void removeCheck() {
		check.setVisible(false);
		addCall();
	}

	// Add Check Button
	public void addCheck() {
		check.setVisible(true);
		removeCall();
	}

	// Remove Call Button
	public void removeCall() {
		call.setVisible(false);
	}

	// Add Call Button
	public void addCall() {
		call.setVisible(true);
		int setGAmount = game.getMaxBetAmount() - game.getUser().getBetAmount();
		call.setText("Call: " + setGAmount + " Pts");
	}

	// Remove All Buttons
	public void removeAllButtons() {
		fold.setVisible(false);
		check.setVisible(false);
		call.setVisible(false);
		raise.setVisible(false);
	}

	// Add All Buttons
	public void addAllButtons() {
		fold.setVisible(true);
		check.setVisible(true);
		call.setVisible(true);
		raise.setVisible(true);
	}
}
