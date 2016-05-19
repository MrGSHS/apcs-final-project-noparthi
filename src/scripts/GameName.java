package scripts;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.Timer;

import javax.imageio.ImageIO;

public class GameName extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private BufferedImage gameName;
	private URL imgURL = getClass().getResource("/menu/gamename.png");
	private boolean gameNameDone;
	private float alpha = 000f;

	Timer timer;

	public boolean getGameNameDone() {
		return gameNameDone;
	}

	public GameName() {
		try {
			gameName = ImageIO.read(imgURL);
			setIconImage(ImageIO.read(this.getClass().getResource("/menu/pokericon.png")));
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
		
		timer = new Timer(50, this);
		timer.start();
	}
	
	public GameName(float alpha) {
		try {
			gameName = ImageIO.read(imgURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
        this.alpha = alpha;

    }

	public void actionPerformed(ActionEvent e) {
		alpha += 0.001f;
		if (alpha > 1) {
			alpha = 1;
			timer.stop();
			gameNameDone = true;
		}
		repaint();
	}
	
	public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
        g2d.drawImage(gameName, 0, 0, 800, 600, null);
    }
	
	public static void main(String args []){
		new GameName();
	}
	
}
