package scripts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import blackjack.BlackGame;
import holdem.HoldemGame;

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
		private JButton holdemBtn;

		public ChooserPanel() {
			URL imgURL = getClass().getResource("/menu/blackjack.jpg");
			Icon blackJackIcon = new ImageIcon(imgURL);

			imgURL = getClass().getResource("/menu/holdem.jpg");
			Icon pokerIcon = new ImageIcon(imgURL);

			blackJackBtn = new JButton(blackJackIcon);
			blackJackBtn.setSize(blackJackIcon.getIconWidth(), blackJackIcon.getIconHeight());
			blackJackBtn.setLocation(25, 100);
			blackJackBtn.setToolTipText("Black Jack");
			blackJackBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int reply = JOptionPane.showConfirmDialog(null, "Would you like to view a quick tutorial on the rules of Black Jack?", "Black Jack Tutorial", JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.YES_OPTION) {
						JOptionPane.showMessageDialog(null, "HELLO");
					} else if(reply == JOptionPane.NO_OPTION){
						new BlackGame();
					} else {
						System.exit(0);
					}
					frame.dispose();
				}
			});

			holdemBtn = new JButton(pokerIcon);
			holdemBtn.setSize(pokerIcon.getIconWidth(), pokerIcon.getIconHeight());
			holdemBtn.setLocation(475, 100);
			holdemBtn.setToolTipText("Texas Hold'em");
			holdemBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int reply = JOptionPane.showConfirmDialog(null, "Would you like to view a quick tutorial on the rules of Hold'em?", "Hold'em Tutorial", JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.YES_OPTION) {
						JOptionPane.showMessageDialog(null, "HELLO");
					} else if(reply == JOptionPane.NO_OPTION){
						new HoldemGame();
						frame.dispose();
					} else {
						System.exit(0);
					}
				}
			});
			
			frame.add(blackJackBtn);
			frame.add(holdemBtn);

		}

}}
