package scripts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.ToolTipManager;

import blackjack.BlackGame;
import holdem.HoldemGame;

public class Runner {

	private ArrayList<BufferedImage> images = new ArrayList<>();

	public class ChooserDisplay {

		private JFrame frame;
		private ChooserPanel choosePanel;

		private JButton blackJackBtn;
		private JButton holdemBtn;

		private BufferedImage iconImage;
		
		private boolean mouseOverBlackJack = false;
		private boolean mouseOverHoldem = false;

		public ChooserDisplay() {
			try {
				iconImage = ImageIO.read(this.getClass().getResource("/menu/pokerIcon.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			frame = new JFrame();
			choosePanel = new ChooserPanel();
			frame.setTitle("Casino Simulator 2K16!");
			frame.setIconImage(iconImage);
			frame.setSize(900, 600);
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.add(choosePanel);
			frame.setVisible(true);
		}

		private class ChooserPanel extends JPanel {

			private static final long serialVersionUID = 1L;

			public ChooserPanel() {
				URL imgURL = getClass().getResource("/menu/blackjack.jpg");
				Icon blackJackIcon = new ImageIcon(imgURL);

				imgURL = getClass().getResource("/menu/holdem.jpg");
				Icon pokerIcon = new ImageIcon(imgURL);

				ToolTipManager.sharedInstance().setInitialDelay(1000);

				blackJackBtn = new JButton(blackJackIcon);
				blackJackBtn.setSize(blackJackIcon.getIconWidth(), blackJackIcon.getIconHeight());
				blackJackBtn.setLocation(25, 100);
				blackJackBtn.setToolTipText("Black Jack");
				blackJackBtn.addMouseListener(new MouseListener() {

					@Override
					public void mouseClicked(MouseEvent e) {}

					@Override
					public void mousePressed(MouseEvent e) {}

					@Override
					public void mouseReleased(MouseEvent e) {}

					@Override
					public void mouseEntered(MouseEvent e) {
						mouseOverBlackJack = true;
						frame.repaint();
					}

					@Override
					public void mouseExited(MouseEvent e) {
						mouseOverBlackJack = false;
						frame.repaint();
					}

				});
				blackJackBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						int reply = JOptionPane.showConfirmDialog(null,
								"Would you like to view a quick tutorial on the rules of Black Jack?",
								"Black Jack Tutorial", JOptionPane.YES_NO_CANCEL_OPTION);
						if (reply == JOptionPane.YES_OPTION) {
							JOptionPane.showMessageDialog(null, "HELLO");
						} else if (reply == JOptionPane.NO_OPTION) {
							new BlackGame();
							frame.dispose();
						}
						if(reply != JOptionPane.CANCEL_OPTION && reply != JOptionPane.CLOSED_OPTION){
							frame.dispose();
						}
					}
				});

				holdemBtn = new JButton(pokerIcon);
				holdemBtn.setSize(pokerIcon.getIconWidth(), pokerIcon.getIconHeight());
				holdemBtn.setLocation(475, 100);
				holdemBtn.setToolTipText("Texas Hold'em");
				holdemBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						int reply = JOptionPane.showConfirmDialog(null,
								"Would you like to view a quick tutorial on the rules of Hold'em?", "Hold'em Tutorial",
								JOptionPane.YES_NO_CANCEL_OPTION);
						if (reply == JOptionPane.YES_OPTION) {
							new TutorialDisplay(images);
						} else if (reply == JOptionPane.NO_OPTION) {
							new HoldemGame();
						} 
						if(reply != JOptionPane.CANCEL_OPTION && reply != JOptionPane.CLOSED_OPTION){
							frame.dispose();
						}		
					}
				});
				holdemBtn.addMouseListener(new MouseListener() {
					@Override
					public void mouseClicked(MouseEvent e) {}
					@Override
					public void mousePressed(MouseEvent e) {}
					@Override
					public void mouseReleased(MouseEvent e) {}
					@Override
					public void mouseEntered(MouseEvent e) {
						mouseOverHoldem = true;
						revalidate();
						frame.repaint();
					}
					@Override
					public void mouseExited(MouseEvent e) {
						mouseOverHoldem = false;
						revalidate();
						frame.repaint();
					}
				});
				frame.add(blackJackBtn);
				frame.add(holdemBtn);
			}

			private final int BORDER = 4;

			public void paint(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setStroke(new BasicStroke(5));
				g.setColor(Color.YELLOW);
				if (mouseOverBlackJack)
					g.drawRect(blackJackBtn.getX() - BORDER, blackJackBtn.getY() - BORDER,
							blackJackBtn.getWidth() + 2 * BORDER, blackJackBtn.getHeight() + 2 * BORDER);
				if (mouseOverHoldem)
					g.drawRect(holdemBtn.getX() - BORDER, holdemBtn.getY() - BORDER, holdemBtn.getWidth() + 2 * BORDER,
							holdemBtn.getHeight() + 2 * BORDER);
			}
		}
	}

	protected class ImageLoader extends SwingWorker<ArrayList<BufferedImage>, String>{
		@Override
		protected ArrayList<BufferedImage> doInBackground() throws IOException {
			for (int i = 1; i <= 17; i++) {
				String path = "/tutorial/holdem-slide" + i + ".png";
				try {
					images.add(ImageIO.read(getClass().getResourceAsStream(path)));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return images;
		}
	}

	public Runner() {
		new ImageLoader().execute();
		Initializer init = new Initializer();
		while (!init.getGameNameDone()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		init.dispose();
		new ChooserDisplay();
	}
	
	public static void main(String[] args) {
		new Runner();
	}
}