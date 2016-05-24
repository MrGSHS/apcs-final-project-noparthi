package holdem;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class WinScreen {
	private drawStuffPanel drawStuff;
	private ArrayList<Confetti> confettiList = new ArrayList<>();

	public WinScreen(JFrame frame) {
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
		ArrayList<Confetti> tempConfettiList = new ArrayList<>();
		
		public void paintComponent(Graphics g) {
			g.fillRect(0, 0, 900, 600);
			for (Confetti confetti : confettiList) {
				g.setColor(confetti.getColor());
				g.fillOval(confetti.getX(), confetti.getY(), confetti.getDiameter(),
						confetti.getDiameter());
				confetti.setY(confetti.getY() + 1);
				if(confetti.getY()>875){
					confetti.setDelete(true);
				}
			}
			tempConfettiList = confettiList;
			for(Confetti confetti : tempConfettiList){
				if(confetti.shouldDelete()){
					confettiList.remove(confetti);
				}
			}
			repaint();
		}
	}
}
