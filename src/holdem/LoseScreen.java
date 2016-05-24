package holdem;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LoseScreen {
	private drawLosePanel drawLose;
	private ArrayList<Confetti> rainDropList = new ArrayList<>();

	public static void main(String[] args){
		new LoseScreen();
	}
	
	public LoseScreen() {
		JFrame frame = new JFrame();
		frame.setTitle("You Lose");
		frame.setSize(900, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		drawLose = new drawLosePanel();
		frame.setContentPane(drawLose);
		frame.setVisible(true);
		frame.repaint();
	}

	private class drawLosePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private BufferedImage loseBackground;
		public drawLosePanel() {
			try {
				loseBackground = ImageIO.read(getClass().getResourceAsStream("/other/loseBackground.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void paintComponent(Graphics g) {
			g.drawImage(loseBackground, 0, 0, 900, 600, null);
		}
	}
}
