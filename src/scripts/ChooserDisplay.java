package scripts;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChooserDisplay {
	
	private JFrame frame; 
	private ChooserPanel choosePanel;
	public ChooserDisplay() {
		frame = new JFrame();
		choosePanel = new ChooserPanel();
		frame.setTitle("Welcome to Casino Simulator 2K16!");
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(choosePanel);
		frame.setSize(900, 600);
		frame.setVisible(true);
	}

	private class ChooserPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		private JButton blackJackBtn;

		public ChooserPanel() {
			java.net.URL imgURL = getClass().getResource("/menu/blackjack.jpg");
			Icon blackJackPic;
			blackJackBtn = new JButton(blackJackPic);
			blackJackBtn.setLocation(300, 200);
			frame.add(blackJackBtn);
		}

	}
}
