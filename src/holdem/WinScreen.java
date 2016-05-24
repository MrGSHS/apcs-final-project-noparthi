package holdem;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class WinScreen extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private TransparentPane tp;
	
	public WinScreen(){
		setTitle("You Win!!!");
		setSize(900,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tp = new TransparentPane();
		setUndecorated(true);
		setBackground(new Color(1.0f,1.0f,1.0f,0f));
		add(tp);
		setVisible(true);
	}

	private class TransparentPane extends JPanel{
		private static final long serialVersionUID = 1L;
		private ArrayList<Confetti> confettiList = new ArrayList<>();
		private ArrayList<Integer> removeIndex;
		
		public TransparentPane(){
			for (int i = 1; i <= 150; i++) {
				confettiList.add(new Confetti((int) (Math.random() * 900), (int) (Math.random() * 901),
						(int) (Math.random() * 11 + 5)));
			}
			setOpaque(false);
		}
		public void paintComponent(Graphics g){
			super.paintComponent(g);
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
