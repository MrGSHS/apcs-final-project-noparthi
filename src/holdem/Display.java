package holdem;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import scripts.Card;

public class Display extends TimerTask{

	private ArrayList<String> NAMES = new ArrayList<String>(){
		private static final long serialVersionUID = 1L;
	{
		add("Andrew"); add("David"); add("Ethan"); add("Jerry"); add("Jonathan");
		add("Jessica"); add("Lily"); add("Sophia"); add("Zoe"); add("Julia"); 
	}};
	private String USERNAME;
	private String COMP1NAME = NAMES.remove((int) (Math.random() * NAMES.size()));
	private String COMP2NAME = NAMES.remove((int) (Math.random() * NAMES.size()));
	private final String COMP3NAME = NAMES.remove((int) (Math.random() * NAMES.size()));
	private final String COMP4NAME = NAMES.remove((int) (Math.random() * NAMES.size()));

	private ArrayList<BufferedImage> LABELS = new ArrayList<BufferedImage>()
	{
		private static final long serialVersionUID =1L;

	{
		try{
			add(ImageIO.read(getClass().getResourceAsStream("/labels/penguin-label.png"))); 
			add(ImageIO.read(getClass().getResourceAsStream("/labels/bird-label.png")));
			add(ImageIO.read(getClass().getResourceAsStream("/labels/monkey-label.png"))); 
			add(ImageIO.read(getClass().getResourceAsStream("/labels/frog-label.png"))); 
			add(ImageIO.read(getClass().getResourceAsStream("/labels/goat-label.png"))); 
		}catch(IOException e){
			e.printStackTrace();
		}
	}};
	private BufferedImage userLabel = LABELS.remove((int) (Math.random() * LABELS.size()));
	private BufferedImage computer1Label = LABELS.remove((int) (Math.random() * LABELS.size()));
	private BufferedImage computer2Label = LABELS.remove((int) (Math.random() * LABELS.size()));
	private BufferedImage computer3Label = LABELS.remove((int) (Math.random() * LABELS.size()));
	private BufferedImage computer4Label = LABELS.remove((int) (Math.random() * LABELS.size()));
	
	private final int CARD_WIDTH = 55;
	private final int CARD_HEIGHT = 80;
	private final int TABLE_WIDTH = 660;
	private final int TABLE_HEIGHT = 286;
	private final int FRAME_WIDTH = 800;
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

	private BufferedImage cardBack;
	private BufferedImage card1;
	private BufferedImage card2;
	private BufferedImage chips5k;
	private BufferedImage chips10k;
	private BufferedImage chips25k;
	private BufferedImage chips50k;
	private ArrayList<BufferedImage> tableCards;

	private Game game;

	private JButton fold = new JButton("Fold");
	private JButton check = new JButton("Check");
	private JButton call = new JButton("Call");
	private JButton raise = new JButton("Raise");
	private JButton tip = new JButton("Tip Mr. G");

	public boolean userTip = false;
	private int extraCreditPoints = 1;

	private ArrayList<Card> cardsOnTable;

	public Display(Game game) {
		this.game = game;
		tableCards = new ArrayList<BufferedImage>();

		// Prompt For User Name
		String[] options = { "OK" };
		JPanel panel = new JPanel();
		JLabel lbl = new JLabel("Enter Your Name: ");
		JTextField txt = new JTextField(10);
		panel.add(lbl);
		panel.add(txt);
		int selectedOption = JOptionPane.showOptionDialog(null, panel, "Welcome to Oker-pay", JOptionPane.NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if (selectedOption == 0){
			if (!txt.getText().trim().equals(""))
				USERNAME = txt.getText();
			else
				USERNAME = NAMES.remove((int) (Math.random() * NAMES.size()));;
		}else{
			System.exit(0);
		}
		try {
			dealer = ImageIO.read(getClass().getResourceAsStream("/other/dealer-face.png"));
			table = ImageIO.read(getClass().getResourceAsStream("/other/poker-table.png"));
			theme = ImageIO.read(getClass().getResourceAsStream("/themes/red-velvet.jpg"));
			cardBack = ImageIO.read(getClass().getResourceAsStream("/other/card-back.png"));
			chips5k = ImageIO.read(getClass().getResourceAsStream("/chips/5k.png"));
			chips10k = ImageIO.read(getClass().getResourceAsStream("/chips/10k.png"));
			chips25k = ImageIO.read(getClass().getResourceAsStream("/chips/25k.png"));
			chips50k = ImageIO.read(getClass().getResourceAsStream("/chips/50k.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Main Frame
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

		// Dealer & Background & Theme
		public void drawBackground(Graphics g) {
			g.drawImage(theme, 0, 0, null);
			g.drawImage(dealer, FRAME_WIDTH / 2 - DEALER_WIDTH / 2,
					(FRAME_HEIGHT / 2 - TABLE_HEIGHT / 2 - 15) - DEALER_HEIGHT, null);
			g.drawImage(table, FRAME_WIDTH / 2 - TABLE_WIDTH / 2, FRAME_HEIGHT / 2 - TABLE_HEIGHT / 2 - 25, null);
		}

		// Button Background
		public void drawButtonBackground(Graphics g) {
			g.setColor(new Color(32, 32, 32));
			g.fillRect(0, 525, FRAME_WIDTH, 50);
			g.setColor(Color.WHITE);
			g.setFont(buttonFont);
			g.drawString("Check", FRAME_WIDTH / 4 + FRAME_WIDTH / 10, 555);
			g.drawString("Call", FRAME_WIDTH / 2 + FRAME_WIDTH / 10, 555);
		}

		// Add Player Positions
		public void addPlayerPositions() {
			game.playerPositions.add(
					new int[] { FRAME_WIDTH / 2 - userLabel.getWidth() / 2, FRAME_HEIGHT / 2 + TABLE_HEIGHT / 2 - 20 });
			game.playerPositions
				.add(new int[] { FRAME_WIDTH / 2 - computer1Label.getWidth() / 2 - 290, FRAME_HEIGHT / 2 + 55 });
			game.playerPositions
					.add(new int[] { FRAME_WIDTH / 2 - computer2Label.getWidth() / 2 - 210, FRAME_HEIGHT / 2 - 190 });
			game.playerPositions
					.add(new int[] { FRAME_WIDTH - computer3Label.getWidth() / 2 - 205, FRAME_HEIGHT / 2 - 190 });
			game.playerPositions
					.add(new int[] { FRAME_WIDTH / 2 - computer4Label.getWidth() / 2 + 290, FRAME_HEIGHT / 2 + 55 });
			
			
		}

		// Draw User Cards
		public void drawUserCards(Graphics g) {
			g.drawImage(card1, FRAME_WIDTH / 2 - userLabel.getWidth() / 2 + 15,
					FRAME_HEIGHT / 2 + TABLE_HEIGHT / 2 - CARD_HEIGHT / 2 - 43, CARD_WIDTH + 30, CARD_HEIGHT + 35,
					null);
			g.drawImage(card2, FRAME_WIDTH / 2 - userLabel.getWidth() / 2 + 15 + CARD_WIDTH + 10,
					FRAME_HEIGHT / 2 + TABLE_HEIGHT / 2  - CARD_HEIGHT / 2 - 43, CARD_WIDTH + 30, CARD_HEIGHT + 35,
					null);
		}

		// Draw Computer Cards
		public void drawComputerCards(Graphics g) {
			if (!game.getPlayers().get(1).isFolded()) {
				g.drawImage(cardBack, game.playerPositions.get(1)[0] + CARD_WIDTH + 25, game.playerPositions.get(1)[1] - 70,
						CARD_WIDTH + 30, CARD_HEIGHT + 40, null);
				g.drawImage(cardBack, game.playerPositions.get(1)[0] + 15, game.playerPositions.get(1)[1] - 70,
						CARD_WIDTH + 30, CARD_HEIGHT + 40, null);
			}
			if (!game.getPlayers().get(2).isFolded()) {
				g.drawImage(cardBack, game.playerPositions.get(2)[0] + CARD_WIDTH + 25, game.playerPositions.get(2)[1] - 70,
						CARD_WIDTH + 30, CARD_HEIGHT + 40, null);
				g.drawImage(cardBack, game.playerPositions.get(2)[0] + 15, game.playerPositions.get(2)[1] - 70,
						CARD_WIDTH + 30, CARD_HEIGHT + 40, null);
			}
			if (!game.getPlayers().get(3).isFolded()) {
				g.drawImage(cardBack, game.playerPositions.get(3)[0] + CARD_WIDTH + 25, game.playerPositions.get(3)[1] - 70,
						CARD_WIDTH + 30, CARD_HEIGHT + 40, null);
				g.drawImage(cardBack, game.playerPositions.get(3)[0] + 15, game.playerPositions.get(3)[1] - 70,
						CARD_WIDTH + 30, CARD_HEIGHT + 40, null);
			}
			if (!game.getPlayers().get(4).isFolded()) {
				g.drawImage(cardBack, game.playerPositions.get(4)[0] + CARD_WIDTH + 25, game.playerPositions.get(4)[1] - 70, 
						CARD_WIDTH + 30, CARD_HEIGHT + 40, null); 
				g.drawImage(cardBack, game.playerPositions.get(4)[0] + 15, game.playerPositions.get(4)[1] - 70, 
						CARD_WIDTH + 30, CARD_HEIGHT + 40, null); 
			}
		}

		// Draw User Label
		public void drawUserLabel(Graphics g) {
			g.drawImage(userLabel, FRAME_WIDTH / 2 - userLabel.getWidth() / 2, FRAME_HEIGHT / 2 + TABLE_HEIGHT / 2 - 20,
					null);
		}

		// Draw Computer Labels
		public void drawComputerLabels(Graphics g) {
			g.drawImage(computer1Label, game.playerPositions.get(1)[0], game.playerPositions.get(1)[1], null);
			g.drawImage(computer2Label, game.playerPositions.get(2)[0], game.playerPositions.get(2)[1], null);
			g.drawImage(computer3Label, game.playerPositions.get(3)[0], game.playerPositions.get(3)[1], null);
			g.drawImage(computer4Label, game.playerPositions.get(4)[0], game.playerPositions.get(4)[1], null);
		}

		//Draw Blinds
		public void drawBlinds(Graphics g){
			g.setFont(new Font("Calibri", Font.BOLD, 16));
			g.setColor(modifiedGrey);
			g.fillRoundRect(game.playerPositions.get(game.getBigBlindIndex())[0], game.playerPositions.get(game.getBigBlindIndex())[1] + userLabel.getHeight(), 100, 20, 15, 15); 
			g.fillRoundRect(game.playerPositions.get(game.getSmallBlindIndex())[0], game.playerPositions.get(game.getSmallBlindIndex())[1] + userLabel.getHeight(), 100, 20, 15, 15);
			g.setColor(Color.WHITE);
			g.drawString("Big Blind", game.playerPositions.get(game.getBigBlindIndex())[0] + 20, game.playerPositions.get(game.getBigBlindIndex())[1] + userLabel.getHeight() + 15);
			g.drawString("SM. Blind", game.playerPositions.get(game.getSmallBlindIndex())[0] + 16, game.playerPositions.get(game.getSmallBlindIndex())[1] + userLabel.getHeight() + 15);
		}
		
		//Draw Check/Call/Raise
		public void drawAction(Graphics g){
			g.setFont(new Font("Calibri", Font.BOLD, 16));
			g.setColor(modifiedGrey);
			for(int i = 1; i < game.getPlayers().size(); i++){
				if(i == game.getBigBlindIndex() && game.getTable().getCardsOnTable().size()==0 && game.getPlayers().get(game.getBigBlindIndex()).getRaiseBoolean()){
				}
				else if(!game.getPlayers().get(i).getCheckBoolean() && !game.getPlayers().get(i).getCallBoolean() && !game.getPlayers().get(i).getRaiseBoolean()){
					System.out.println("DrawActionFailed: NoAction" + "\t" + i);
				}
				else if(game.getPlayers().get(i).isFolded()){
				}
				else if (game.getPlayers().get(i).getCheckBoolean() || game.getPlayers().get(i).getCallBoolean()){
					g.setColor(new Color(53,192,18));
					g.fillRoundRect(game.playerPositions.get(i)[0] + 100, game.playerPositions.get(i)[1] + userLabel.getHeight(), 80, 20, 10, 10);
				    g.setColor(Color.BLACK);
					if(game.getPlayers().get(i).getCheckBoolean())
				    	g.drawString("Check", game.playerPositions.get(i)[0] + 120, game.playerPositions.get(i)[1] + userLabel.getHeight() + 15);
				    else
				    	g.drawString("Call", game.playerPositions.get(i)[0] + 130, game.playerPositions.get(i)[1] + userLabel.getHeight() + 15);
				}
				else if (game.getPlayers().get(i).getRaiseBoolean()){
					g.setColor(Color.YELLOW);
					g.fillRoundRect(game.playerPositions.get(i)[0] + 100, game.playerPositions.get(i)[1] + userLabel.getHeight(), 80, 20, 10, 10);
					g.setColor(Color.BLACK);
					g.drawString("Raise", game.playerPositions.get(i)[0] + 120, game.playerPositions.get(i)[1] + userLabel.getHeight() + 15);
				}
			}
		}
		
		// Add Player Names
		public void addPlayerName(Graphics g) {
			g.setColor(Color.BLACK);
			g.drawString(USERNAME,
					FRAME_WIDTH / 2 - userLabel.getWidth() / 2
							+ (userLabel.getWidth() - g.getFontMetrics().stringWidth(USERNAME)) / 2 + 15,
					FRAME_HEIGHT / 2 + TABLE_HEIGHT / 2 + 2);
		}

		// Add Computer Names
		public void addComputerNames(Graphics g) {
			g.drawString(COMP1NAME,
					game.playerPositions.get(4)[0] + 58
							+ (128 - g.getFontMetrics().stringWidth(COMP1NAME)) / 3,
					game.playerPositions.get(4)[1] + 20);
			g.drawString(COMP2NAME,
					game.playerPositions.get(3)[0] + 58
							+ (128 - g.getFontMetrics().stringWidth(COMP2NAME)) / 3,
					game.playerPositions.get(3)[1] + 20);
			g.drawString(COMP3NAME,
					game.playerPositions.get(2)[0] + 58
							+ (128 - g.getFontMetrics().stringWidth(COMP2NAME)) / 3,
					game.playerPositions.get(2)[1] + 20);
			g.drawString(COMP4NAME,
					game.playerPositions.get(1)[0] + 58
							+ (128 - g.getFontMetrics().stringWidth(COMP2NAME)) / 3,
					game.playerPositions.get(1)[1] + 20);
		}

		// Add Player Points
		public void addPlayerPoints(Graphics g) {
			g.setFont(new Font("Calibri", Font.PLAIN, 20));
			g.setColor(new Color(5, 145, 60));
			g.drawString(game.getUser().getPoints() + " Pts", FRAME_WIDTH / 2 - userLabel.getWidth() / 2 + 70,
					FRAME_HEIGHT / 2 + TABLE_HEIGHT / 2 + 30);
		}

		// Add Computer Points
		public void addComputerPoints(Graphics g) {
			g.drawString(game.getPlayers().get(4).getPoints() + " Pts", game.playerPositions.get(4)[0] + 73,
					game.playerPositions.get(4)[1] + computer1Label.getHeight() - 14);
			g.drawString(game.getPlayers().get(2).getPoints() + " Pts", game.playerPositions.get(2)[0] + 73,
					game.playerPositions.get(2)[1] + computer2Label.getHeight() - 14);
			g.drawString(game.getPlayers().get(3).getPoints() + " Pts", game.playerPositions.get(3)[0] + 73,
					game.playerPositions.get(3)[1] + computer3Label.getHeight() - 14);
			g.drawString(game.getPlayers().get(1).getPoints() + " Pts", game.playerPositions.get(1)[0] + 73,
					game.playerPositions.get(1)[1] + computer4Label.getHeight() - 14);
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
					g.drawImage(tableCards.get(i), 227 + (CARD_WIDTH + 15) * i, FRAME_HEIGHT / 2 - CARD_HEIGHT / 2 - 45,
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
			g.fillRoundRect(500, 494, 296, 31, 10, 10);

			// Writes In Hand Strength
			Map<TextAttribute, Object> map = new Hashtable<TextAttribute, Object>();
			map.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			Font underlineFont = new Font("Calibri", Font.BOLD, 16).deriveFont(map);
			g.setFont(underlineFont);
			String strengthString = game.getUser().getHand().getCurrentHandStrengthString();
			int strWidth = g.getFontMetrics().stringWidth(strengthString);
			g.fillRoundRect(500, 474, strWidth + 10, 20 + BORDER, 10, 10);
			g.setColor(Color.WHITE);
			g.drawString(strengthString, 500 + 5, 474 + 17);

			// Hand Strength Bar
			g.setColor(Color.BLACK);
			g.fillRoundRect(500 + BORDER, 494 + BORDER, 290 - BORDER, 25 - BORDER, 10, 10);

			// Changes Color Of Rectangle
			if (handStrength <= 3)
				g.setColor(Color.RED);
			else if (handStrength <= 7)
				g.setColor(Color.YELLOW);
			else
				g.setColor(Color.GREEN);

			// How Much To Fill
			g.fillRoundRect(500 + BORDER, 494 + BORDER, 29 * handStrength - BORDER, 25 - BORDER, 10, 10);

		}

		// Add Computers Bet
		public void addComputerBet(Graphics g) {
			g.setColor(modifiedGrey);
			g.fillRoundRect(game.playerPositions.get(1)[0] + 185,
					game.playerPositions.get(1)[1], 50, 20, 15, 15);
			g.fillRoundRect(game.playerPositions.get(2)[0] + 185,
					game.playerPositions.get(2)[1] + userLabel.getHeight() - 10, 50, 20, 15, 15);
			g.fillRoundRect(game.playerPositions.get(3)[0] - 55,
					game.playerPositions.get(3)[1] + userLabel.getHeight() - 10, 50, 20, 15, 15);
			g.fillRoundRect(game.playerPositions.get(4)[0] - 55,
					game.playerPositions.get(4)[1], 50, 20, 15, 15);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Calibri", Font.BOLD, 16));
			g.drawString("" + (double)game.getPlayers().get(1).getBetAmount() / 1000 + "K",
					game.playerPositions.get(1)[0] + 195, game.playerPositions.get(1)[1] + 15);
			g.drawString("" + (double)game.getPlayers().get(2).getBetAmount() / 1000 + "K", game.playerPositions.get(2)[0] + 190,
					game.playerPositions.get(2)[1] + userLabel.getHeight() + 5);
			g.drawString("" + (double)game.getPlayers().get(3).getBetAmount() / 1000 + "K", game.playerPositions.get(3)[0] - 45,
					game.playerPositions.get(3)[1] + userLabel.getHeight() + 5);
			g.drawString("" + (double)game.getPlayers().get(4).getBetAmount() / 1000 + "K", game.playerPositions.get(4)[0] - 45,
					game.playerPositions.get(4)[1] + 15);
		}

		// Add Chips
		public void addChips(Graphics g) {
			int chipsWidth = chips5k.getWidth();
			int chipsHeight = chips5k.getHeight();
			g.drawImage(calculateChips(game.getPlayers().get(0)), FRAME_WIDTH / 2 - chipsWidth / 2,
					game.playerPositions.get(0)[1] - chipsHeight * 2 - 10, null);
			g.drawImage(calculateChips(game.getPlayers().get(1)), game.playerPositions.get(1)[0] + 185,
					game.playerPositions.get(1)[1] + userLabel.getHeight() - 120, null);
			g.drawImage(calculateChips(game.getPlayers().get(2)), game.playerPositions.get(2)[0] + 70,
					game.playerPositions.get(2)[1] + userLabel.getHeight() - 10, null);
			g.drawImage(calculateChips(game.getPlayers().get(3)), game.playerPositions.get(3)[0] + 70,
					game.playerPositions.get(3)[1] + userLabel.getHeight() - 10, null);
			g.drawImage(calculateChips(game.getPlayers().get(4)), game.playerPositions.get(4)[0] - 40,
					game.playerPositions.get(4)[1] + userLabel.getHeight() - 120, null);
		}

		// Add Tips
		public void addTipEffects(Graphics g) {
			if (userTip) {
				String tipString = "Thanks " + USERNAME + "! You get " + (extraCreditPoints++)
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

				userTip = false;
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

			// Draw Labels
			drawUserLabel(g);
			drawComputerLabels(g);

			//DrawBlinds
			drawBlinds(g);
			
			//Draw Action
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

			// Add Computers' Bet
			addComputerBet(g);

			// Add chips
			addChips(g);

			// Congratulations You Wasted Money - Tip
			addTipEffects(g);

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
			else if (evt.getSource() == call) {
				System.out.println("called");
				game.getUser().call();
				game.allComputersTakeAction();
				game.getRound().moveOn();
			}
			else if (evt.getSource() == check) {
				game.getUser().check();
				game.allComputersTakeAction();
				game.getRound().moveOn();
			}
			else if (evt.getSource() == fold) {
				System.out.println("Stop being a fucking pussy");
			}
			if (evt.getSource() == tip) {
				if (game.getUser().getPoints() >= 2000) {
					game.getUser().setPoints(game.getUser().getPoints() - 2000);
					run();
					userTip = true;
				}
			}
		}
	}

	public void run() {
		cardsOnTable = game.getTable().getCardsOnTable();
		reloadImages();
		frame.repaint();
		// Removes Check If Necessary
		if (game.getMaxBetAmount() - game.getUser().getBetAmount() == 0) {
			addCheck();
		} else {
			removeCheck();
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
}
