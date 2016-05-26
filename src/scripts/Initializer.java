package scripts;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Initializer extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private BufferedImage logo;
	private int logowait;
	private boolean logodone = false;
	private URL imgURL = getClass().getResource("/menu/dropLogo.png");

	private BufferedImage gameName;
	private URL imgURL2 = getClass().getResource("/menu/gameName.png");
	private boolean gameNameDone;
	private float alpha = 000f;
	Timer timer;

	private BufferedImage iconImage;

	public boolean getLogoDone() {
		return logodone;
	}

	public boolean getGameNameDone() {
		return gameNameDone;
	}

	public static void main(String args[]) {
		new Initializer();
	}

	public Initializer() {
		// Read Images
		try {
			logo = ImageIO.read(imgURL);
			logowait = -logo.getHeight();
			gameName = ImageIO.read(imgURL2);
			iconImage = ImageIO.read(this.getClass().getResource("/menu/pokerIcon.png"));

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Set Frame Stuff
		setSize(900, 600);
		setTitle("Casino Simulator 2K16!");
		setIconImage(iconImage);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setFocusable(true);
		requestFocus();

		// Starts Timer For GameName
		timer = new Timer(90, this);
		timer.start();

		// Drop The Logo
		Thread th = new Thread() {
			public void run() {
				while (true) {
					repaint();
					if (!logodone)
						logowait += 1;
					try {
						Thread.sleep(5);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		th.start();
	}

	@Override
	public void paint(Graphics gg) {
		// Drop Logo/FadeInGameName
		if (!logodone) {
			Image buffer = createImage(getWidth(), getHeight());
			Graphics g = buffer.getGraphics();
			draw(g);
			gg.drawImage(buffer, 0, 0, null);
			// Blanks Screen In Preparation For GameNameFadeIn If Needed
			if (logowait >= 750) {
				logodone = true;
				try {
					Thread.sleep(2500);
					gg.setColor(Color.WHITE);
					gg.fillRect(0, 0, 900, 600);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			g.dispose();
		} else if (!gameNameDone) {
			Graphics2D g2d = (Graphics2D) gg;
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
			g2d.drawImage(gameName, 0, 0, 900, 600, null);
		}
	}

	// Draw LogoDrop
	public void draw(Graphics g2) {
		if (logowait > getHeight() / 2 - logo.getHeight() / 2) {
			g2.setColor(new Color(logowait / 2.5 < 255 ? (int) (logowait / 2.5) : 255,
					logowait / 2.5 < 255 ? (int) (logowait / 2.5) : 255,
					logowait / 2.5 < 255 ? (int) (logowait / 2.5) : 255));
		}
		g2.fillRect(0, 0, 900, 600);
		if (!logodone) {
			g2.drawImage(logo, getWidth() / 2 - logo.getWidth() / 2, logowait < getHeight() / 2 - logo.getHeight() / 2
					? logowait : getHeight() / 2 - logo.getHeight() / 2, null);

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (logodone) {
			alpha += 0.001f;
			if (alpha > .065) {
				timer.stop();
				gameNameDone = true;
			}
			repaint();
		}
	}
}
