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

	public static void main(String[] args) {
		new LoseScreen();
	}

	public LoseScreen() {
		frame = new JFrame();
		frame.setTitle("You Lose ☹☹☹");
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
		private ArrayList<Raindrop> raindropList = new ArrayList<>();
		private ArrayList<Raindrop> removeList;
		private int initialX = -500;
		private int initialY = 0;
		private int addX = 0;
		private int addY = 0;
		private int grayShade;

		public drawLosePanel() {
			try {
				loseBackground = ImageIO.read(getClass().getResourceAsStream("/other/loseBackground.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Adds In Raindrop Lines
			for (int i = 0; i <= 54; i++) {
				grayShade = (int) (Math.random() * 181 + 75);
				addX = (int) (Math.random() * 5 + 5);
				addY = (int) (Math.random() * 11 + 15);
				raindropList.add(new Raindrop(initialX, initialY, initialX + addX, initialY + addY,
						new Color(grayShade, grayShade, grayShade)));
				raindropList.get(0).setX1Y1(initialX,  initialY);
				raindropList.get(0).setX2Y2(initialX + addX, initialY + addY);
				initialX += addX + 20;
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
			removeList = new ArrayList<>();
			//Draws Raindrop
			for(Raindrop raindrop : raindropList){
				g.setColor(raindrop.getWhiteShade());
				g.drawLine(raindrop.getx1(), raindrop.gety1(), raindrop.getx2(), raindrop.gety2());
				raindrop.setX1Y1(raindrop.getx1() + 10, raindrop.gety1() + 10);
				raindrop.setX2Y2(raindrop.getx2() + 10, raindrop.gety2() + 10);
				if(raindrop.getx2() > 900 || raindrop.gety2() > 600){
					removeList.add(raindrop);
				}
			}
			//Removes Raindrop And Adds In New Ones
			for(Raindrop raindrop : removeList){
				raindropList.remove(raindrop);
				
				grayShade = (int) (Math.random() * 181 + 75);
				addX = (int) (Math.random() * 5 + 5);
				addY = (int) (Math.random() * 11 + 15);
				Raindrop r1 = new Raindrop(initialX, initialY, initialX + addX, initialY + addY,
						new Color(grayShade, grayShade, grayShade));
				r1.setX1Y1(initialX,  initialY);
				r1.setX2Y2(initialX + addX, initialY + addY);
				raindropList.add(r1);
				
				initialX += addX + 20;
				if(initialX > 900){
					initialX = -500;
				}
			}
			
			//Lightning
			if((int)(Math.random()*101 + 1)==1){
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, 900, 600);
			}
			repaint();
		}
	}
}
