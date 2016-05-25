package blackjack;

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
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import scripts.Card;

public class BlackDisplay {

	private ArrayList<String> NAMES = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("Aaron");
			add("Amy");
			add("Alex");
			add("Caitlyn");
			add("Andrew");
			add("Jessica");
			add("Artsiom");
			add("Julia");
			add("Daniel");
			add("Lily");
			add("Deepak");
			add("Sophia");
			add("Jerry");
			add("Taylor");
			add("John");
			add("Zoe");
			add("Jonathan");
			add("Emma");
			add("Maor");
			add("Olivia");
			add("Marcus");
			add("Isabella");
			add("Matthew");
			add("Ava");
			add("Matthias");
			add("Mia");
			add("Patrick");
			add("Emily");
			add("Samarth");
			add("Abby");
			add("Sanketh");
			add("Madison");
			add("Steven");
			add("Victoria");
			add("Wilson");
			add("Grace");
		}
	};
	private String USERNAME;
	private String COMP1NAME;
	

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

	private BlackGame game;

	private JButton hit = new JButton("hit");
	private JButton stand = new JButton("stand");
	private JButton tip = new JButton("Tip Mr. G");

	public boolean userTip = false;
	private int extraCreditPoints = 1;
	private int counter = 0;
	private ArrayList<Card> cardsOnTable;

	public BlackDisplay(BlackGame game) {
		this.game = game;

		// Prompt For User Name
		String[] options = { "OK" };
		JPanel panel = new JPanel();
		JLabel lbl = new JLabel("Enter Your Name: ");
		JTextField txt = new JTextField(10);
		panel.add(lbl);
		panel.add(txt);
		int selectedOption = JOptionPane.showOptionDialog(null, panel, "Welcome to Black Jack",
				JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if (selectedOption == 0) {
			if (!txt.getText().trim().equals(""))
				USERNAME = txt.getText();
			else
				USERNAME = NAMES.remove((int) (Math.random() * NAMES.size()));
		} else {
			System.exit(0);
		}

		// Removes User Name From Naming List If Exists
		for (String name : NAMES)
			if (USERNAME.equals(name))
				NAMES.remove(name);

		// Set Computer Names
		COMP1NAME = NAMES.remove((int) (Math.random() * NAMES.size()));

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
		frame.setTitle("BlackJack");
		frame.setIconImage(iconImage);
		tablePanel = new TableDisplayPanel();
		actionsPanel = new ActionsDisplayPanel();
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(actionsPanel);
		frame.add(tablePanel);
		frame.setVisible(true);
		// WinScreen();
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
			g.drawString("hit", FRAME_WIDTH / 10, 555);
			g.drawString("Check", FRAME_WIDTH / 4 + FRAME_WIDTH / 10, 555);
			g.drawString("stand", FRAME_WIDTH / 2 + FRAME_WIDTH / 10, 555);
			g.drawString("Raise", 3 * FRAME_WIDTH / 4 + FRAME_WIDTH / 10, 555);
		}

		
	

		// Draw User Cards
		public void drawUserCards(Graphics g) {

		}

		// Draw Computer Cards
		public void drawComputerCards(Graphics g) {
			
		}

		
		

	

		// Draw User Label
		public void drawUserLabel(Graphics g) {
			g.drawImage(userLabel, FRAME_WIDTH / 2 - userLabel.getWidth() / 2, FRAME_HEIGHT / 2 + TABLE_HEIGHT / 2 + 5,
					null);
		}

		// Draw Computer Labels
		public void drawComputerLabels(Graphics g) {
			g.drawImage(computer1Label, 100, 100, null);
			
		}

		
		// Add Player Names
		public void addPlayerName(Graphics g) {
			g.setFont(new Font("Calibri", Font.BOLD, 16));
			g.setColor(Color.BLACK);
			//TODO: add name
		}

		// Add Computer Names
		public void addComputerNames(Graphics g) {
			
		}

		// Add Player Points
		public void addPlayerPoints(Graphics g) {
			
		}

		// Add Computer Points
		public void addComputerPoints(Graphics g) {
		}

		// Add Pot
		public void addPot(Graphics g) {
			g.setColor(modifiedGrey);
			g.fillRoundRect(FRAME_WIDTH / 2 - 70, FRAME_HEIGHT / 2 + CARD_WIDTH - 55, 140, 20, 15, 15);
			g.setColor(new Color(246, 246, 246));
			g.setFont(new Font("Calibri", Font.BOLD, 16));
		}

		// Add Bets
		public void addBets(Graphics g) {
			
		}

		// Add Chips
		public void addChips(Graphics g) {
			int chipsWidth = chips5k.getWidth();
			int chipsHeight = chips5k.getHeight();			
		}

		public void paintComponent(Graphics g) {
			// Initialize Stuff
			g.setFont(new Font("Calibri", Font.BOLD, 20));
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// Draw Background
			drawBackground(g);
			drawButtonBackground(g);
			
			// Draw Cards
			drawUserCards(g);
			drawComputerCards(g);

			// Draw Labels		
			drawUserLabel(g);
			drawComputerLabels(g);			

			// Add Names To Labels
			addPlayerName(g);
			addComputerNames(g);

			// Add Points To Labels
			addPlayerPoints(g);
			addComputerPoints(g);

			// Add Pot
			addPot(g);

			// Add User + Computer's Bet
			addBets(g);

			// Add chips
			addChips(g);

		}
	}

	public BufferedImage calculateChips(BlackPlayer p) {
		return null;
	}

	// Adds Buttons To JFrame
	private class ActionsDisplayPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		private final int BUTTON_WIDTH = FRAME_WIDTH / 2;
		private final int BUTTON_HEIGHT = 50;

		public ActionsDisplayPanel() {
			hit.addActionListener(new ButtonListener());
			stand.addActionListener(new ButtonListener());
			tip.addActionListener(new ButtonListener());

			hit.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
			stand.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
			tip.setSize(2 * BUTTON_WIDTH / 3, BUTTON_HEIGHT / 2);
			
			stand.setLocation((FRAME_WIDTH / 2), 525);
			hit.setLocation(0, 525);
			tip.setLocation(FRAME_WIDTH/2, 100);

		
			stand.setFont(buttonFont);
			hit.setFont(buttonFont);
			tip.setFont(buttonFont);

			// Set Tip Button Transparent && Text To White
			tip.setOpaque(false);
			tip.setContentAreaFilled(false);
			tip.setBorderPainted(false);
			tip.setForeground(Color.WHITE);

			frame.add(hit);
			frame.add(stand);
			frame.add(tip);
		}

	}

	// Button Listeners
	public class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			if (evt.getSource() == hit) {
				game.getRound().getPlayer().hit();
			} else if (evt.getSource() == stand) {

			}
		}
	}

	public void update() {
		reloadImages();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frame.repaint();
			}
		});
	}

	private void reloadImages() {

	}

}
