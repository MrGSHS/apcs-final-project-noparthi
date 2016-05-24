package holdem;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LoseScreen extends JFrame{
	private TransparentPane tp;
	private ArrayList<Confetti> rainDropList = new ArrayList<>();

	public static void main(String[] args){
		new LoseScreen();
	}
	
	public LoseScreen() {
		setTitle("You Lose");
		setSize(900, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tp = new TransparentPane();
		setUndecorated(true);
		setBackground(new Color(1.0f,1.0f,1.0f,0f));
		add(tp);
		setVisible(true);
	}

	private class TransparentPane extends JPanel {
		private static final long serialVersionUID = 1L;
		private BufferedImage loseBackground;
		public TransparentPane() {
			try {
				loseBackground = ImageIO.read(getClass().getResourceAsStream("/other/loseBackground.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			setOpaque(false);
		}

		public void paintComponent(Graphics g) {
			g.drawImage(loseBackground, 0, 0, 900, 600, null);
		}
	}
}
