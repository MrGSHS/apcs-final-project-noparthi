package holdem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class WinScreen{
	private static final long serialVersionUID = 1L;
	private JButton newGame = new JButton("Start A New Game");
	private JButton exit = new JButton("Exit");
	private TransparentPane tp;
	private JFrame frame;
	
	public static void main(String args[]){
		new WinScreen();
	}
	
	public WinScreen() {
		frame = new JFrame();
		frame.setTitle("You Win!!!");
		frame.setSize(900, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tp = new TransparentPane();
		frame.setBackground(Color.black);
		frame.add(tp);
		frame.setVisible(true);
	}

	private class TransparentPane extends JPanel implements ActionListener{
		private static final long serialVersionUID = 1L;
		private ArrayList<Confetti> confettiList = new ArrayList<>();
		private ArrayList<Integer> removeIndex;

		public TransparentPane() {
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
			
			exit.setBounds(50, 525, 225, 50);
			newGame.setBounds(625, 525, 225, 50);

			frame.add(newGame);
			frame.add(exit);
			
			setOpaque(false);
		}
		
		@Override
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
			removeIndex = new ArrayList<>();
			for (int i = 0; i < confettiList.size(); i++) {
				g.setColor(confettiList.get(i).getColor());
				g.fillOval(confettiList.get(i).getX(), confettiList.get(i).getY(), confettiList.get(i).getDiameter(),
						confettiList.get(i).getDiameter());
				confettiList.get(i).setY(confettiList.get(i).getY() + 1);
				if (confettiList.get(i).getY() > 850) {
					removeIndex.add(i);
				}
			}
			for (int remove : removeIndex) {
				confettiList.remove(remove);
				confettiList.add(new Confetti((int) (Math.random() * 900), (int) (Math.random() * 250),
						(int) (Math.random() * 11 + 5)));
			}
			repaint();
		}
	}
}