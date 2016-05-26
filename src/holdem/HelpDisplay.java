package holdem;

import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
* This class creates frame which has
* the poker hand rankings.
* 
* @author  Jerry Zhou, Jonathan Xue
* @version 1.0
* @since   2016-5-26
*/


public class HelpDisplay extends JFrame {
	private static final long serialVersionUID = 1L;
	private BufferedImage chart;
	private BufferedImage iconImage;

	public HelpDisplay() {
		try {
			chart = ImageIO.read(getClass().getResourceAsStream("/other/poker-hands.gif"));
			iconImage = ImageIO.read(this.getClass().getResource("/menu/pokerIcon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setTitle("Hand Chart");
		setIconImage(iconImage);
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
