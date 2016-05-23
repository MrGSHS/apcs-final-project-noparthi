package holdem;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class WinLoseScreen {
	private final int FRAME_WIDTH = 900;
	private final int FRAME_HEIGHT = 600;
	private drawStuffPanel drawStuff;
	private ArrayList<Confetti> confettiList = new ArrayList<>();

	public WinLoseScreen(JFrame frame) {
		// Creates 100 Confetti Pieces
		for (int i = 1; i <= 100; i++) {
			confettiList.add(new Confetti((int) (Math.random() * 900), (int) (Math.random() * 501),
					(int) (Math.random() * 6 + 10)));
		}
		drawStuff = new drawStuffPanel();
		frame.setOpacity(0f);
		frame.add(drawStuff);
		frame.setVisible(true);
		frame.repaint();
	}

	private class drawStuffPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private ArrayList<Integer> removeIndex;

		public void paintComponent(Graphics g) {
			removeIndex = new ArrayList<>();
			g.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
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
				confettiList.add(new Confetti((int) (Math.random() * 900), (int) (Math.random() * 301),
						(int) (Math.random() * 6 + 10)));
			}
			repaint();
		}
	}
}
