package scripts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class LogoDrop extends JFrame {
	private BufferedImage logo;
	private int logowait;
	private boolean logodone = false;
	private URL imgURL = getClass().getResource("/other/pFusionSplash.png");
	
	public boolean getLogoDone(){return logodone;}

	public LogoDrop() {
		try {
			logo = ImageIO.read(imgURL);
			logowait = -logo.getHeight();
			// setIconImage(ImageIO.read(this.getClass().getResource("zlogo.png")));

		} catch (Exception e) {
			e.printStackTrace();
		}

		setSize(800, 600);
		setTitle("Casino Simulator 2K16!");
		setResizable(false);
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 400,
				Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 300);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setFocusable(true);
		requestFocus();

		// Drop The Logo
		Thread th = new Thread() {
			public void run() {
				while (true) {
					repaint();

					if (!logodone)
						logowait += 5;
					try {
						Thread.sleep(20);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		th.start();
	}
	
	public static void main(String[] string) {
		new LogoDrop();
	}

	@Override
	public void paint(Graphics gg) {
		Image buffer = createImage(getWidth(), getHeight());
		Graphics g = buffer.getGraphics();
		draw(g);
		gg.drawImage(buffer, 0, 0, null);
		g.dispose();
	}

	public void draw(Graphics g2) {
		if (logowait > getHeight() / 2 - logo.getHeight() / 2)
			g2.setColor(new Color(logowait / 1.5 < 255 ? (int) (logowait / 1.5) : 255,
					logowait / 1.5 < 255 ? (int) (logowait / 1.5) : 255,
					logowait / 1.5 < 255 ? (int) (logowait / 1.5) : 255));
		g2.fillRect(0, 0, 900, 600);
		if (!logodone) {
			g2.drawImage(logo, getWidth() / 2 - logo.getWidth() / 2, logowait < getHeight() / 2 - logo.getHeight() / 2
					? logowait : getHeight() / 2 - logo.getHeight() / 2, null);
			if (logowait >= 750)
				logodone = true;
		}
	}
}
