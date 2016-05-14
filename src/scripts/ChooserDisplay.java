package scripts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import blackjack.BlackGame;
import holdem.Game;

public class ChooserDisplay {
	
	private JFrame frame; 
	private ChooserPanel choosePanel;
	public ChooserDisplay() {
		frame = new JFrame();
		choosePanel = new ChooserPanel();
		frame.setTitle("Welcome to Casino Simulator 2K16!");
		frame.setSize(900, 600);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(choosePanel);
		frame.setVisible(true);
	}

	private class ChooserPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		
		private JButton blackJackBtn;
		private JButton pokerBtn;
		
		public ChooserPanel() {
			URL imgURL = getClass().getResource("/menu/blackjack.jpg");
			Icon blackJackIcon = new ImageIcon(imgURL);
			
			imgURL = getClass().getResource("/menu/holdem.jpg");
			Icon pokerIcon = new ImageIcon(imgURL);
			
			blackJackBtn = new JButton(blackJackIcon);
			blackJackBtn.setSize(blackJackIcon.getIconWidth(), blackJackIcon.getIconHeight());
			blackJackBtn.setLocation(25, 150);
			
			blackJackBtn.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					new BlackGame();
					frame.dispose();
				}				
			});
			
			pokerBtn = new JButton(pokerIcon);
			pokerBtn.setSize(pokerIcon.getIconWidth(), pokerIcon.getIconHeight());
			pokerBtn.setLocation(475, 150);	
			
			pokerBtn.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					new Game();
					frame.dispose();
				}	
			});
			frame.add(blackJackBtn);
			frame.add(pokerBtn);
		}

	}
}
