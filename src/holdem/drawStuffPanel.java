package holdem;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class drawStuffPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private ArrayList<Integer> removeIndex;
	private ArrayList<Confetti> confettiList = new ArrayList<>();

	public drawStuffPanel(){
		// Creates 100 Confetti Pieces
		for (int i = 1; i <= 150; i++) {
			confettiList.add(new Confetti((int) (Math.random() * 900), (int) (Math.random() * 901),
					(int) (Math.random() * 11 + 5)));
		}
		//setBackground(new Color(0,0,0,0));
		setOpaque(false);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		removeIndex = new ArrayList<>();
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.SrcOver.derive(0.1f));
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());
	}
}