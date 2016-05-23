package holdem;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class WinLoseScreen {
	private drawStuffPanel drawStuff;
	private ArrayList<Confetti> confettiList = new ArrayList<>();

	public WinLoseScreen(JFrame frame) {
		// Creates 100 Confetti Pieces
		for (int i = 1; i <= 150; i++) {
			confettiList.add(new Confetti((int) (Math.random() * 900), (int) (Math.random() * 901),
					(int) (Math.random() * 11 + 5)));
		}
		drawStuff = new drawStuffPanel();
		frame.setBackground(Color.BLACK);
		frame.setContentPane(drawStuff);
		frame.repaint();
	}

	private class drawStuffPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private ArrayList<Integer> removeIndex;
		
		public void paintComponent(Graphics g) {
			removeIndex = new ArrayList<>();
			g.fillRect(0, 0, 900, 600);
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
