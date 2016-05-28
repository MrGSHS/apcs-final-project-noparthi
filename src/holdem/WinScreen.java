package holdem;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;

import java.io.*;
/**
* This class initializes the win screen
* in which confetti falls in a black background.
* 
* @author  Jerry Zhou, Jonathan Xue
* @version 1.0
* @since   2016-5-26
*/

public class WinScreen {
	private JButton newGame = new JButton("Start A New Game");
	private JButton exit = new JButton("Exit");
	private drawWinPanel dwp;
	private JFrame frame;
	private BufferedImage iconImage;

	@SuppressWarnings("static-access")
	public WinScreen() {
		// Rain Sound
		try {
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(new File("trumpetSound.wav").getAbsoluteFile());
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
		frame.setTitle("You Win!!!");
		frame.setSize(900, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dwp = new drawWinPanel();
		frame.add(dwp);
		frame.setIconImage(iconImage);
		frame.setVisible(true);
	}

	private class drawWinPanel extends JPanel implements ActionListener {
		private static final long serialVersionUID = 1L;
		private ArrayList<Confetti> confettiList = new ArrayList<>();
		private ArrayList<Confetti> removeList;

		public drawWinPanel() {
			// Adds In Confetti
			for (int i = 1; i <= 150; i++) {
				confettiList.add(new Confetti((int) (Math.random() * 900), (int) (Math.random() * 901),
						(int) (Math.random() * 11 + 5)));
			}

			// Adds Button
			setLayout(null);
			exit.addActionListener(this);
			newGame.addActionListener(this);

			exit.setPreferredSize(new Dimension(225, 50));
			newGame.setPreferredSize(new Dimension(225, 50));

			exit.setFont(new Font("SansSerif", Font.PLAIN, 12));
			newGame.setFont(new Font("SansSerif", Font.PLAIN, 12));

			exit.setBounds(25, 500, 255, 50);
			newGame.setBounds(635, 500, 225, 50);
			add(newGame);
			add(exit);
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
			super.paintComponent(g);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 900, 600);
			removeList = new ArrayList<>();
			// Draws Confetti
			for (Confetti confetti : confettiList) {
				g.setColor(confetti.getColor());
				g.fillOval(confetti.getX(), confetti.getY(), confetti.getDiameter(), confetti.getDiameter());
				confetti.setY(confetti.getY() + 1);
				if (confetti.getY() > 850) {
					removeList.add(confetti);
				}
			}
			// Removes Confetti And Adds In New Ones
			for (Confetti remove : removeList) {
				confettiList.remove(remove);
				confettiList.add(new Confetti((int) (Math.random() * 900), (int) (Math.random() * 250),
						(int) (Math.random() * 11 + 5)));
			}
			repaint();
		}
	}
}
