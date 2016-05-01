package scripts;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Display {

	private final int CARD_WIDTH = 55;
	private final int CARD_HEIGHT = 80;
	private final int TABLE_WIDTH = 660;
	private final int TABLE_HEIGHT = 286;
	private final int FRAME_WIDTH = 800;
	private final int FRAME_HEIGHT = 600;
	private final int DEALER_WIDTH = 140;
	private final int DEALER_HEIGHT = 130;

	public boolean done;
	private Font buttonFont = new Font("SansSerif", Font.PLAIN, 12);
	private Color modifiedGrey = new Color(55, 53, 53);
	private JFrame frame;
	private TableDisplayPanel tablePanel;
	private ActionsDisplayPanel actionsPanel;
	private BufferedImage theme;
	private BufferedImage table;
	private BufferedImage userLabel;
	private BufferedImage dealer;

	private BufferedImage cardBack;
	private BufferedImage card1;
	private BufferedImage card2;
	private ArrayList<BufferedImage> tableCards;
	private BufferedImage chips5k;
	private BufferedImage chips10k;
	private BufferedImage chips25k;
	private BufferedImage chips50k;
	private Game game;

	private JButton fold = new JButton("Fold");
	private JButton check = new JButton("Check");
	private JButton call = new JButton("Call");
	private JButton raise = new JButton("Raise");
	private JButton tip = new JButton("Tip Mr. G");

	public boolean userTip = false;

	private ArrayList<Card> cardsOnTable;

	public Display(Game game) {
		this.game = game;
		tableCards = new ArrayList<BufferedImage>();
		try {
			dealer = ImageIO.read(getClass().getResourceAsStream("/other/dealer-face.png"));
			table = ImageIO.read(getClass().getResourceAsStream("/other/poker-table.png"));
			theme = ImageIO.read(getClass().getResourceAsStream("/themes/red-velvet.jpg"));
			userLabel = ImageIO.read(getClass().getResourceAsStream("/other/player-label.png"));
			cardBack = ImageIO.read(getClass().getResourceAsStream("/other/card-back.png"));
			chips5k = ImageIO.read(getClass().getResourceAsStream("/other/5k.png"));
			chips10k = ImageIO.read(getClass().getResourceAsStream("/other/10k.png"));
			chips25k = ImageIO.read(getClass().getResourceAsStream("/other/25k.png"));
			chips50k = ImageIO.read(getClass().getResourceAsStream("/other/50k.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame = new JFrame();
		frame.setTitle("Oker-pay: Round ");
		tablePanel = new TableDisplayPanel();
		actionsPanel = new ActionsDisplayPanel();
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		// frame.setLayout(null);
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

		public void paintComponent(Graphics g) {
			int chipsWidth = chips5k.getWidth();
			int chipsHeight = chips5k.getHeight();

			g.setFont(new Font("Calibri", Font.BOLD, 20));
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// Dealer & Background & Theme
			g.drawImage(theme, 0, 0, null);
			g.drawImage(dealer, FRAME_WIDTH / 2 - DEALER_WIDTH / 2,
					(FRAME_HEIGHT / 2 - TABLE_HEIGHT / 2 - 15) - DEALER_HEIGHT, null);
			g.drawImage(table, FRAME_WIDTH / 2 - TABLE_WIDTH / 2, FRAME_HEIGHT / 2 - TABLE_HEIGHT / 2 - 25, null);

			// Button Background
			g.setColor(new Color(32, 32, 32));
			g.fillRect(0, 525, FRAME_WIDTH, 50);
			g.setColor(Color.WHITE);
			g.setFont(buttonFont);
			g.drawString("Check", FRAME_WIDTH / 4 + FRAME_WIDTH / 10, 555);
			g.drawString("Call", FRAME_WIDTH / 2 + FRAME_WIDTH / 10, 555);

			// Draw User Cards
			g.drawImage(card1, FRAME_WIDTH / 2 - userLabel.getWidth() / 2 + 15,
					FRAME_HEIGHT / 2 + TABLE_HEIGHT / 2 - 20 - CARD_HEIGHT / 2 - 18, CARD_WIDTH + 30, CARD_HEIGHT + 40,
					null);
			g.drawImage(card2, FRAME_WIDTH / 2 - userLabel.getWidth() / 2 + 15 + CARD_WIDTH + 10,
					FRAME_HEIGHT / 2 + TABLE_HEIGHT / 2 - 20 - CARD_HEIGHT / 2 - 18, CARD_WIDTH + 30, CARD_HEIGHT + 40,
					null);

			// Draw Computer Cards
			if (!game.getPlayers().get(1).isFolded()) {
				g.drawImage(cardBack, FRAME_WIDTH / 2 - userLabel.getWidth() / 2 + CARD_WIDTH - 185,
						FRAME_HEIGHT / 2 - 250, CARD_WIDTH + 30, CARD_HEIGHT + 40, null);
				g.drawImage(cardBack, FRAME_WIDTH / 2 - userLabel.getWidth() / 2 - 195, FRAME_HEIGHT / 2 - 250,
						CARD_WIDTH + 30, CARD_HEIGHT + 40, null);
			}
			if (!game.getPlayers().get(2).isFolded()) {
				g.drawImage(cardBack, FRAME_WIDTH - userLabel.getWidth() / 2 + CARD_WIDTH - 180, FRAME_HEIGHT / 2 - 250,
						CARD_WIDTH + 30, CARD_HEIGHT + 40, null);
				g.drawImage(cardBack, FRAME_WIDTH - userLabel.getWidth() / 2 - 190, FRAME_HEIGHT / 2 - 250,
						CARD_WIDTH + 30, CARD_HEIGHT + 40, null);
			}

			// Player Labels
			g.drawImage(userLabel, FRAME_WIDTH / 2 - userLabel.getWidth() / 2, FRAME_HEIGHT / 2 + TABLE_HEIGHT / 2 - 20,
					null);
			g.drawImage(userLabel, FRAME_WIDTH / 2 - userLabel.getWidth() / 2 - 210, FRAME_HEIGHT / 2 - 190, null);
			g.drawImage(userLabel, FRAME_WIDTH - userLabel.getWidth() / 2 - 205, FRAME_HEIGHT / 2 - 190, null);

			game.playerPositions.add(
					new int[] { FRAME_WIDTH / 2 - userLabel.getWidth() / 2, FRAME_HEIGHT / 2 + TABLE_HEIGHT / 2 - 20 });
			game.playerPositions
					.add(new int[] { FRAME_WIDTH / 2 - userLabel.getWidth() / 2 - 210, FRAME_HEIGHT / 2 - 190 });
			game.playerPositions
					.add(new int[] { FRAME_WIDTH - userLabel.getWidth() / 2 - 205, FRAME_HEIGHT / 2 - 190 });

			// Player Name & Player Points & Pot Size 
			g.setColor(Color.BLACK);
			g.drawString("Jerry", FRAME_WIDTH / 2 - userLabel.getWidth() / 2 + 95,
					FRAME_HEIGHT / 2 + TABLE_HEIGHT / 2 + 2);
			g.setFont(new Font("Calibri", Font.PLAIN, 20));
			g.setColor(new Color(5, 145, 60));
			g.drawString(game.getUser().getPoints() + " Pts", FRAME_WIDTH / 2 - userLabel.getWidth() / 2 + 70,
					FRAME_HEIGHT / 2 + TABLE_HEIGHT / 2 + 30);
			
			//Computer Points			
			g.drawString(game.getPlayers().get(1).getPoints() + " Pts", game.playerPositions.get(1)[0] + 70, game.playerPositions.get(1)[1]+userLabel.getHeight()-14);
			g.drawString(game.getPlayers().get(2).getPoints() + " Pts", game.playerPositions.get(2)[0] + 70, game.playerPositions.get(2)[1]+userLabel.getHeight()-14);
			
			if (tableCards.size() > 0) {
				for (int i = 0; i < tableCards.size(); i++) {
					g.drawImage(tableCards.get(i), 227 + (CARD_WIDTH + 15) * i, FRAME_HEIGHT / 2 - CARD_HEIGHT / 2 - 55,
							CARD_WIDTH, CARD_HEIGHT, null);
				}
			}
			g.setColor(modifiedGrey);
			g.fillRoundRect(FRAME_WIDTH / 2 - 70, FRAME_HEIGHT / 2 + CARD_WIDTH - 65, 140, 20, 15, 15);
			g.setColor(new Color(246, 246, 246));
			g.setFont(new Font("Calibri", Font.BOLD, 16));
			String potSize = "POT: " + game.getRound().getPot() + " Pts";
			int potSizeWidth = g2d.getFontMetrics().stringWidth(potSize);
			g.drawString(potSize, FRAME_WIDTH / 2 - (int) (potSizeWidth / 2), FRAME_HEIGHT / 2 + CARD_WIDTH - 50);

			
			// Hand Strength Meter - 6 Pixel Border
			final int BORDER = 5;
			int handStrength = 0;
			g.setColor(Color.DARK_GRAY);

			if (tableCards.size() == 0)
				handStrength = game.getUser().getHand().initialHandStrength();
			else
				handStrength = game.getUser().getHand().updateHandStrength();

			// Writes In Hand Strength
			Map<TextAttribute, Object> map = new Hashtable<TextAttribute, Object>();
			map.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			Font underlineFont = new Font("Calibri", Font.BOLD, 16).deriveFont(map);
			g.setFont(underlineFont);
			String strengthString = game.getUser().getHand().getCurrentHandStrengthString();
			int strWidth = g2d.getFontMetrics().stringWidth(strengthString);
			g.fillRoundRect(500, 474, strWidth + 10, 20 + BORDER, 10, 10);
			g.setColor(Color.WHITE);
			g.drawString(strengthString, 500 + 5, 474 + 17);

			// Computers' Bet
			g.setColor(modifiedGrey);
			g.fillRoundRect(game.playerPositions.get(1)[0] + 120, game.playerPositions.get(1)[1] + userLabel.getHeight() + 10, 40, 20, 15, 15);
			g.fillRoundRect(game.playerPositions.get(2)[0] + 20, game.playerPositions.get(2)[1] + userLabel.getHeight() + 10, 40, 20, 15, 15);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Calibri", Font.BOLD, 16));
			g.drawString("" + game.getPlayers().get(1).getBetAmount()/1000 + "K", game.playerPositions.get(1)[0] + 125, game.playerPositions.get(1)[1] + userLabel.getHeight() + 25);
			g.drawString("" + game.getPlayers().get(2).getBetAmount()/1000 + "K", game.playerPositions.get(2)[0] + 25, game.playerPositions.get(2)[1] + userLabel.getHeight() + 25);
			
			// Hand Strength Bar Background
			g.setColor(Color.DARK_GRAY);
			g.fillRoundRect(500, 494, 296, 31, 10, 10);
			g.setColor(Color.BLACK);
			g.fillRoundRect(500 + BORDER, 494 + BORDER, 290 - BORDER, 25 - BORDER, 10, 10);

			// Changes Color Of Rectangle
			if (handStrength <= 3)
				g.setColor(Color.RED);
			else if (handStrength <= 7)
				g.setColor(Color.YELLOW);
			else
				g.setColor(Color.GREEN);

			// Hand Strength Meter
			g.fillRoundRect(500 + BORDER, 494 + BORDER, 29 * handStrength - BORDER, 25 - BORDER, 10, 10);

			// Draw chips
			g.drawImage(calculateChips(game.getPlayers().get(0)), FRAME_WIDTH / 2 - chipsWidth / 2,
					game.playerPositions.get(0)[1] - chipsHeight * 2 - 10, null);
			g.drawImage(calculateChips(game.getPlayers().get(1)), game.playerPositions.get(1)[0] + 70,
					game.playerPositions.get(1)[1] + userLabel.getHeight() - 10, null);
			g.drawImage(calculateChips(game.getPlayers().get(2)), game.playerPositions.get(2)[0] + 70,
					game.playerPositions.get(2)[1] + userLabel.getHeight() - 10, null);
		}
	}

	public BufferedImage calculateChips(Player p) {
		if (p.getPointsInvested() / 50000 >= 1)
			return chips50k;
		else if (p.getPointsInvested() / 25000 >= 1)
			return chips25k;
		else if (p.getPointsInvested() / 10000 >= 1)
			return chips10k;
		else if(p.getPointsInvested() / 5000 >= 1)
			return chips5k;
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

			raise.setLocation(600, 525);
			call.setLocation(400, 525);
			check.setLocation(200, 525);
			fold.setLocation(0, 525);
			tip.setLocation(332, 130);

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
				// Ask For User Input
				String stringRaiseAmount = (String) JOptionPane.showInputDialog(frame, "Enter Raise Amount:",
						game.getMaxBetAmount() + game.getSmallBlind());
				try {
					stringRaiseAmount = stringRaiseAmount.replaceAll("[^0-9]", "");
					int intRaiseAmount = Integer.parseInt(stringRaiseAmount);

					if (game.getUser().raise(intRaiseAmount)) {
						game.allComputersTakeAction();
						game.getRound().moveOn();
					} else {
						return;
					}

				} catch (NullPointerException e) {
					System.out.println("User has cancelled raise.");
				}
			}
			if (evt.getSource() == call) {
				game.getUser().call();
				game.allComputersTakeAction();
				game.getRound().moveOn();
			}
			if (evt.getSource() == check) {
				game.getUser().check();
				game.allComputersTakeAction();
				game.getRound().moveOn();
			}
			if (evt.getSource() == fold) {
				game.getUser().fold();
			}
			if (evt.getSource() == tip) {
				if (game.getUser().getPoints() >= 2000) {
					game.getUser().setPoints(game.getUser().getPoints() - 2000);
					update();
					userTip = true;
				}
			}
		}
	}

	public void update() {
		cardsOnTable = game.getTable().getCardsOnTable();
		reloadImages();
		frame.repaint();
		// Removes Check If Necessary
		for (int i = 1; i < game.getPlayers().size(); i++) {
			if (game.getPlayers().get(i).getRaiseBoolean()) {
				removeCheck();
				break;
			} else {
				addCheck();
			}
		}
		// Removes Call If Necessary
		for (int i = 1; i < game.getPlayers().size(); i++) {
			if (game.getPlayers().get(i).getCheckBoolean() || game.getUser().getRaiseBoolean()) {
				removeCall();
				break;
			} else {
				addCall();
			}
		}
	}

	private void reloadImages() {
		tableCards = new ArrayList<BufferedImage>();
		for (Card card : cardsOnTable) {
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
		frame.setTitle("Oker-pay: Round " + game.getRound().getRoundNumber());
	}

	// Remove Check Button
	public void removeCheck() {
		check.setVisible(false);
	}

	// Add Check Button
	public void addCheck() {
		check.setVisible(true);
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
}
