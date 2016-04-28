package scripts;

import java.awt.Color;
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

	private boolean checkBtn = true;
	private boolean callBtn = true;

	private JFrame frame;
	private TableDisplayPanel tablePanel;
	private ActionsDisplayPanel actionsPanel;
	private BufferedImage theme;
	private BufferedImage table;
	private BufferedImage userLabel;
	private BufferedImage computer1Label;
	private BufferedImage dealer;

	private BufferedImage card1;
	private BufferedImage card2;
	private ArrayList<BufferedImage> tableCards;
	private Game game;

	private JButton fold = new JButton("Fold");
	private JButton check = new JButton("Check");
	private JButton call = new JButton("Call");
	private JButton raise = new JButton("Raise");

	private ArrayList<Card> cardsOnTable;

	public Display(Game game) {
		this.game = game;
		tableCards = new ArrayList<BufferedImage>();
		try {
			dealer = ImageIO.read(getClass().getResourceAsStream("/other/dealer-face.png"));
			table = ImageIO.read(getClass().getResourceAsStream("/other/poker-table.png"));
			theme = ImageIO.read(getClass().getResourceAsStream("/themes/red-velvet.jpg"));
			userLabel = ImageIO.read(getClass().getResourceAsStream("/other/player-label.png"));
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

	public boolean getCheckBtn() {
		return checkBtn;
	}

	private class TableDisplayPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {
			g.setFont(new Font("Calibri", Font.BOLD, 20));
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// Dealer & Background & Theme
			g.drawImage(theme, 0, 0, null);
			g.drawImage(dealer, FRAME_WIDTH / 2 - DEALER_WIDTH / 2,
					(FRAME_HEIGHT / 2 - TABLE_HEIGHT / 2 - 15) - DEALER_HEIGHT, null);
			g.drawImage(table, FRAME_WIDTH / 2 - TABLE_WIDTH / 2, FRAME_HEIGHT / 2 - TABLE_HEIGHT / 2 - 25, null);

			// Draw Cards
			g.drawImage(card1, FRAME_WIDTH / 2 - userLabel.getWidth() / 2 + 15,
					FRAME_HEIGHT / 2 + TABLE_HEIGHT / 2 - 20 - CARD_HEIGHT / 2 - 18, CARD_WIDTH + 30, CARD_HEIGHT + 40,
					null);
			g.drawImage(card2, FRAME_WIDTH / 2 - userLabel.getWidth() / 2 + 15 + CARD_WIDTH + 10,
					FRAME_HEIGHT / 2 + TABLE_HEIGHT / 2 - 20 - CARD_HEIGHT / 2 - 18, CARD_WIDTH + 30, CARD_HEIGHT + 40,
					null);

			// Player Labels
			g.drawImage(userLabel, FRAME_WIDTH / 2 - userLabel.getWidth() / 2, FRAME_HEIGHT / 2 + TABLE_HEIGHT / 2 - 20,
					null);

			// Player Name & Pot Size
			g.setColor(Color.BLACK);
			g.drawString("Jerry", FRAME_WIDTH / 2 - userLabel.getWidth() / 2 + 95,
					FRAME_HEIGHT / 2 + TABLE_HEIGHT / 2 + 2);
			g.setFont(new Font("Calibri", Font.PLAIN, 20));
			g.setColor(new Color(5, 145, 60));
			g.drawString("" + game.getUser().getPoints(), FRAME_WIDTH / 2 - userLabel.getWidth() / 2 + 90,
					FRAME_HEIGHT / 2 + TABLE_HEIGHT / 2 + 30);

			if (tableCards.size() > 0) {
				for (int i = 0; i < tableCards.size(); i++) {
					g.drawImage(tableCards.get(i), 227 + (CARD_WIDTH + 15) * i, FRAME_HEIGHT / 2 - CARD_HEIGHT / 2 - 55,
							CARD_WIDTH, CARD_HEIGHT, null);
				}
			}
			g.setColor(new Color(55, 53, 53));
			g.fillRoundRect(FRAME_WIDTH / 2 - 70, FRAME_HEIGHT / 2 + CARD_WIDTH - 45, 140, 20, 15, 15);
			g.setColor(new Color(246, 246, 246));
			g.setFont(new Font("Calibri", Font.BOLD, 16));
			String potSize = "POT: " + game.getRound().getPot();
			int potSizeWidth = g2d.getFontMetrics().stringWidth(potSize);
			g.drawString(potSize, FRAME_WIDTH / 2 - (int) (potSizeWidth / 2), FRAME_HEIGHT / 2 + CARD_WIDTH - 30);

			// Hand Strength Meter - 6 Pixel Border
			final int BORDER = 5;
			int handStrength = 0;
			g.setColor(Color.DARK_GRAY);

			if (tableCards.size() == 0)
				handStrength = game.getUser().getHand().initialHandStrength(); // Get
																				// Hand
																				// Strength
			else
				handStrength = game.getUser().getHand().updateHandStrength();

			Map<TextAttribute, Object> map = new Hashtable<TextAttribute, Object>();
			map.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			Font underlineFont = new Font("Calibri", Font.BOLD, 16).deriveFont(map);
			g.setFont(underlineFont);
			String strengthString = game.getUser().getHand().getCurrentHandStrengthString();
			int strWidth = g2d.getFontMetrics().stringWidth(strengthString);
			g.fillRoundRect(500, 474, strWidth + 10, 20 + BORDER, 10, 10);
			g.setColor(Color.WHITE);
			g.drawString(strengthString, 500 + 5, 474 + 17);

			g.setColor(Color.DARK_GRAY);
			g.fillRoundRect(500, 494, 296, 31, 10, 10);
			g.setColor(Color.BLACK);
			g.fillRoundRect(500 + BORDER, 494 + BORDER, 290 - BORDER, 25 - BORDER, 10, 10);

			if (handStrength <= 3)
				g.setColor(Color.RED); // Changes Color Of Rectangle
			else if (handStrength <= 7)
				g.setColor(Color.YELLOW);
			else
				g.setColor(Color.GREEN);
			g.fillRoundRect(500 + BORDER, 494 + BORDER, 29 * handStrength - BORDER, 25 - BORDER, 10, 10);
			
			//Places Rectangle Over Check Button
		}
	}

	private class ActionsDisplayPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		private final int BUTTON_WIDTH = FRAME_WIDTH / 4;
		private final int BUTTON_HEIGHT = 50;

		public ActionsDisplayPanel() {

			/*fold = new JButton("Fold");
			check = new JButton("Check");
			call = new JButton("Call");
			raise = new JButton("Raise");
			*/

			fold.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					game.getUser().fold();
				}
			});
			check.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					game.getUser().check();
					game.getComputer().takeAction();
					game.getRound().moveOn();		
				}
			});
			call.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					game.getUser().call();
					game.getComputer().takeAction();
					game.getRound().moveOn();
				}
			});
			raise.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					game.getUser().raise(game.getRound().getMinBet()*2);
					game.getComputer().takeAction();
					game.getRound().moveOn();
				}
			});

			fold.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
			check.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
			call.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
			raise.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);

			final int btnH = 525;

			raise.setLocation(600, btnH);
			call.setLocation(400, btnH);
			check.setLocation(200, btnH);
			fold.setLocation(0, btnH);

			frame.add(fold);
			frame.add(check);
			frame.add(call);
			frame.add(raise);
		}
	}

	public void update() {
		cardsOnTable = game.getTable().getCardsOnTable();
		reloadImages();
		frame.repaint();
		//game.getComputer().takeAction();
		if(game.getComputer().getRaiseBoolean()){
			removeCheck();
			game.getComputer().resetActionBoolean();
		}
		else{
			addCheck();
		}
		/*
		if(game.getComputer().getCheckBoolean()){
			removeCall();
			game.getComputer().resetActionBoolean();
		}
		else{
			addCall();
		}
		*/
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

	public void setRoundTitle() {
		frame.setTitle("Oker-pay: Round " + game.getRound().getRoundNumber());
	}

	public void removeCheck() {
		System.out.println("Check Button Has Been Removed");
		check.setVisible(false);
	}

	public void addCheck() {
		System.out.println("Check Button Has Been Added");
		check.setVisible(true);
	}
	
	public void removeCall(){
		System.out.println("Call Button Has Been Removed");
		call.setVisible(false);
	}
	
	public void addCall(){
		System.out.println("Cal Button Has Been Added");
		call.setVisible(true);
	}
}
