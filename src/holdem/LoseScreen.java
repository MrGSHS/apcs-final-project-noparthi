package holdem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
* This class initializes the 
* lose screen.
* 
* @author  Jerry Zhou, Jonathan Xue
* @version 1.0
* @since   2016-5-26
*/


public class LoseScreen {
	private JFrame frame;
	private drawLosePanel dwp;
	private JButton newGame = new JButton("Start A New Game");
	private JButton exit = new JButton("Exit");
	private BufferedImage iconImage;

	@SuppressWarnings("static-access")
	public LoseScreen() {
		// Rain Sound
		try {
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(new File("rainSound.wav").getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.loop(clip.LOOP_CONTINUOUSLY);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// Icon Image
		try {
			iconImage = ImageIO.read(this.getClass().getResource("/menu/pokerIcon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// New Frame
		frame = new JFrame();
		frame.setTitle("You Lose ☹☹☹");
		frame.setSize(900, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dwp = new drawLosePanel();
		frame.add(dwp);
		frame.setIconImage(iconImage);
		frame.setVisible(true);
	}

	private class drawLosePanel extends JPanel implements ActionListener {
		private static final long serialVersionUID = 1L;
		private BufferedImage loseBackground;
		private ArrayList<Raindrop> raindropList = new ArrayList<>();
		private int x1 = -600;
		private int y1 = 0;
		private int addX = 0;
		private int addY = 0;

		public drawLosePanel() {
			try {
				loseBackground = ImageIO.read(getClass().getResourceAsStream("/other/loseBackground.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Adds In Raindrop Lines, Row 1
			for (int j = 0; j < 12; j++) {
				y1 = j * 50;
				x1 = -600;
				for (int i = 1; i <= 55; i++) {
					addX = (int) (Math.random() * 5 + 5);
					addY = (int) (Math.random() * 11 + 15);
					raindropList.add(new Raindrop(x1, y1, x1 + addX, y1 + addY));
					x1 += addX + 20;
				}
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
			newGame.setBounds(635, 500, 225, 50);

			add(newGame);
			add(exit);
		}

		public void actionPerformed(ActionEvent evt) {
			if (evt.getSource() == newGame) {
				frame.dispose();
				new HoldemGame();
			} else {
				frame.dispose();
			}
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(loseBackground, 0, 0, 900, 600, null);
			// Draws Raindrop
			for (Raindrop raindrop : raindropList) {
				g.setColor(raindrop.getGrayShade());
				g.drawLine(raindrop.getx1(), raindrop.gety1(), raindrop.getx2(), raindrop.gety2());
				raindrop.move();
			}

			// Lightning
			if ((int) (Math.random() * 251) == 0) {
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, 900, 600);
			}

			// Waits Before Repainting
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}
	}
}
