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
	private float alpha = 0f;

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
		setSize(900, 600);
		setTitle("Casino Simulator 2K16!");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setFocusable(true);
		requestFocus();
		
		timer = new Timer(100, this);
		timer.start();
	}

	public void actionPerformed(ActionEvent e) {
		alpha += 0.001f;
		System.out.println(alpha);
		if (alpha > .125) {
			timer.stop();
			gameNameDone = true;
		}
		repaint();
	}
	
	public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.drawImage(gameName, 0, 0, 900, 600, null);
    }
	
	public static void main(String args []){
		new GameName();
	}
	
}
