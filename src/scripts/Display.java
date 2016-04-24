package scripts;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Display {

	private final int CARD_WIDTH = 111;
	private final int CARD_HEIGHT = 190;
	private final int WIDTH = 5*CARD_WIDTH+100;
	private final int HEIGHT = CARD_HEIGHT;
	
	private JFrame frame;
	private TableDisplayPanel tablePanel;

	private BufferedImage card1;
	private BufferedImage card2;
	private ArrayList<BufferedImage> tableCards;
	private Game game;
	
	private ArrayList<Card> cardsOnTable;
	
	public Display(Game game) {
		this.game = game;
		tableCards = new ArrayList<BufferedImage>();
		frame = new JFrame();
		frame.setTitle("Oker-pay: Table 1");
		tablePanel = new TableDisplayPanel();
		frame.setSize(new Dimension(WIDTH, HEIGHT));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(tablePanel, BorderLayout.CENTER);
		frame.setVisible(true);
	}
	
	public JPanel getTablePanel(){ return tablePanel; }
	
	private class TableDisplayPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		public void paint(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setFont(new Font("Calibri", Font.BOLD, 32));
			
			if (tableCards.size() > 0) {
				for (int i = 0; i < tableCards.size(); i++) {
					g.drawImage(tableCards.get(i), CARD_WIDTH * i + 20*i, 0, null);
				}
			}
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
}
