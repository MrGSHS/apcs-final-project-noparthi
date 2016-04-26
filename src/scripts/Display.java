package scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Display {

	private final int CARD_WIDTH = 55;
	private final int CARD_HEIGHT = 80;
	private final int TABLE_WIDTH = 608;
	private final int TABLE_HEIGHT = 308;
	private final int FRAME_WIDTH = 800;
	private final int FRAME_HEIGHT = 600;
	
	private JFrame frame;
	private TableDisplayPanel tablePanel;
	private ActionsDisplayPanel actionsPanel;
	private BufferedImage theme;
	private BufferedImage table;
	//private BufferedImage card1;
	//private BufferedImage card2;
	private ArrayList<BufferedImage> tableCards;
	private Game game;
	
	private ArrayList<Card> cardsOnTable;
	
	public Display(Game game) {
		this.game = game;
		tableCards = new ArrayList<BufferedImage>();
		try {
			table = ImageIO.read(getClass().getResourceAsStream("/other/poker-table.png"));
			theme = ImageIO.read(getClass().getResourceAsStream("/themes/red-velvet.jpg"));
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
		//frame.setLayout(null);
		frame.add(actionsPanel);
		frame.add(tablePanel);
		frame.setVisible(true);
	}
	
	public JFrame getFrame(){ return frame; }
	public JPanel getTablePanel(){ return tablePanel; }
	
	private class TableDisplayPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g.drawImage(theme, 0, 0, null);
			g.drawImage(table, FRAME_WIDTH/2-TABLE_WIDTH/2, FRAME_HEIGHT/2-TABLE_HEIGHT/2, null);
			if (tableCards.size() > 0) {
				for (int i = 0; i < tableCards.size(); i++) {
					g.drawImage(tableCards.get(i), 225 + (CARD_WIDTH + 15)* i , FRAME_HEIGHT/2-CARD_HEIGHT/2-18, null);
				}
			}
			g.setColor(new Color(55,53,53));
			g.fillRoundRect(FRAME_WIDTH/2-70, FRAME_HEIGHT/2+CARD_WIDTH-20, 140, 20, 15, 15);
			g.setColor(new Color(246,246,246));
			g.setFont(new Font("Calibri", Font.BOLD, 16));
			String potSize = "POT: " + game.getRound().getPot();
			int potSizeWidth = g2d.getFontMetrics().stringWidth(potSize);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.drawString(potSize, FRAME_WIDTH/2-(int)(potSizeWidth/2), FRAME_HEIGHT/2+CARD_WIDTH-5);
		}
	}
	private class ActionsDisplayPanel extends JPanel{

		private static final long serialVersionUID = 1L;

		private JButton fold; 
		private JButton check;
		private JButton call;
		private JButton raise;
		
		private final int BUTTON_WIDTH = 120;
		private final int BUTTON_HEIGHT = 40;
		
		public ActionsDisplayPanel(){
			
			fold = new JButton("Fold");
			check = new JButton("Check");
			call = new JButton("Call");
			raise = new JButton("Raise");
			
			fold.addActionListener( new ActionListener () {
				public void actionPerformed(ActionEvent e){
					game.getUser().fold();
				}
			});
			check.addActionListener( new ActionListener () {
				public void actionPerformed(ActionEvent e){
					game.getUser().check();
					game.getRound().moveOn();
				}
			});
			call.addActionListener( new ActionListener () {
				public void actionPerformed(ActionEvent e){
					game.getUser().call();
					game.getRound().moveOn();
				}
			});
			raise.addActionListener( new ActionListener () {
				public void actionPerformed(ActionEvent e){
					//game.getUser().raise(1000);
					game.getRound().moveOn();
				}
			});
			
			fold.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
			check.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
			call.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
			raise.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
			
			final int btnH = 515;
			
			raise.setLocation(660, btnH);
			call.setLocation(530, btnH);
			check.setLocation(400, btnH);
			fold.setLocation(270, btnH);
			
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
	}

	private void reloadImages() {	
		tableCards = new ArrayList<BufferedImage>();
		for(Card card : cardsOnTable){
			String fileName = "/cards/" + card.getNumber().toString() + "_of_" + card.getSuiteValue() + ".jpg";
			try {
				tableCards.add(ImageIO.read(getClass().getResourceAsStream(fileName)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void setRoundTitle() {
		frame.setTitle("Oker-pay: Round " + game.getRound().getRoundNumber());
	}
}
