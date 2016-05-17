package holdem;

import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class HelpDisplay extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage chart;

	public HelpDisplay() {
		try {
			chart = ImageIO.read(getClass().getResourceAsStream("/other/poker-hands.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setTitle("Hand Chart");
		setSize(chart.getWidth(), chart.getHeight() + 25);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				dispose();
			}
		});
		setVisible(true);
	}

	public void paint(Graphics g) {
		g.drawImage(chart, 0, 25, null);
	}
}
