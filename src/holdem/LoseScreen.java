package holdem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LoseScreen {
	private JFrame frame;
	private drawLosePanel dwp;
	private JButton newGame = new JButton("Start A New Game");
	private JButton exit = new JButton("Exit");
	private ArrayList<Confetti> rainDropList = new ArrayList<>();

	public static void main(String[] args) {
		new LoseScreen();
	}

	public LoseScreen() {
		frame = new JFrame();
		frame.setTitle("You Lose");
		frame.setSize(900, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dwp = new drawLosePanel();
		frame.add(dwp);
		frame.setVisible(true);
	}

	private class drawLosePanel extends JPanel implements ActionListener {
		private static final long serialVersionUID = 1L;
		private BufferedImage loseBackground;

		public drawLosePanel() {
			try {
				loseBackground = ImageIO.read(getClass().getResourceAsStream("/other/loseBackground.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Adds Button
			setLayout(null);
			exit.addActionListener(this);
			newGame.addActionListener(this);

			exit.setPreferredSize(new Dimension(225, 50));
			newGame.setPreferredSize(new Dimension(225, 50));

			exit.setFont(new Font("SansSerif", Font.PLAIN, 12));
			newGame.setFont(new Font("SansSerif", Font.PLAIN, 12));

			exit.setBounds(25, 500, 225, 50);
			newGame.setBounds(625, 500, 225, 50);

			frame.add(newGame);
			frame.add(exit);
		}

		public void actionPerformed(ActionEvent evt) {
			if (evt.getSource() == newGame) {
				frame.dispose();
				new HoldemGame();			
			}
			if (evt.getSource() == exit) {
				System.exit(0);
			}
		}
		
		public void paintComponent(Graphics g) {
			g.drawImage(loseBackground, 0, 0, 900, 600, null);
		}
	}
}
