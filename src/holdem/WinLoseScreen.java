package holdem;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class WinLoseScreen {
	private final int FRAME_WIDTH = 900;
	private final int FRAME_HEIGHT = 600;
	private JFrame frame;
	private drawStuffPanel drawStuff;
	private BufferedImage iconImage;
	private ArrayList<Confetti> confetti = new ArrayList<>();

	public WinLoseScreen() {
		try {
			iconImage = ImageIO.read(this.getClass().getResource("/menu/pokerIcon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Creates 100 Confetti Pieces
		for (int i = 0; i < 150; i++) {
			confetti.add(new Confetti((int) (Math.random() * 900), (int) (Math.random() * 500)));
		}
		frame = new JFrame();
		frame.setTitle("Texas Holdem: Round 1"); // +
													// game.getRound().getRoundNumber());
		frame.setIconImage(iconImage);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setBackground(Color.BLACK);
		drawStuff = new drawStuffPanel();
		frame.add(drawStuff);
		frame.setVisible(true);
		frame.repaint();
	}

	private class drawStuffPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {
			g.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
			for (Confetti confetti : confetti) {
				g.setColor(confetti.getColor());
				g.fillOval(confetti.getX(), confetti.getY(), 15, 15);
				confetti.setX(confetti.getX() + ((int)(Math.random()*3)-1));
				confetti.setY(confetti.getY() + 2);				
			}
			repaint();
		}
	}

	public static void main(String args[]) {
		new WinLoseScreen();
	}
}
