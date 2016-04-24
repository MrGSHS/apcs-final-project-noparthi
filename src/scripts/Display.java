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
	private final int FRAMEWIDTH = 800;
	private final int FRAMEHEIGHT = 600;
	
	private JFrame frame;
	private TableDisplayPanel tablePanel;

	private BufferedImage theme;
	private BufferedImage card1;
	private BufferedImage card2;
	private ArrayList<BufferedImage> tableCards;
	private Game game;
	
	private ArrayList<Card> cardsOnTable;
	
	public Display(Game game) {
		this.game = game;
		tableCards = new ArrayList<BufferedImage>();
		try {
			theme = ImageIO.read(getClass().getResourceAsStream("/themes/red-velvet.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame = new JFrame();
		frame.setTitle("Oker-pay: Round ");
		tablePanel = new TableDisplayPanel();
		frame.setSize(new Dimension(FRAMEWIDTH, FRAMEHEIGHT));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(tablePanel);
		frame.setVisible(true);
	}
	
	public JFrame getFrame(){ return frame; }
	public JPanel getTablePanel(){ return tablePanel; }
	
	private class TableDisplayPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setFont(new Font("Calibri", Font.BOLD, 32));
			g2d.drawImage(theme, 0, 0, null);
			if (tableCards.size() > 0) {
				for (int i = 0; i < tableCards.size(); i++) {
					g2d.drawImage(tableCards.get(i), CARD_WIDTH * i + 20*i, FRAMEHEIGHT/2-CARD_HEIGHT/2, null);
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

	public void setRoundTitle() {
		frame.setTitle("Oker-pay: Round " + game.getRound().getRoundNumber());
	}
}
