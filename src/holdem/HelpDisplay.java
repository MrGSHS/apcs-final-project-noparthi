package holdem;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class HelpDisplay extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage chart;
	public HelpDisplay(){
		try {
			chart = ImageIO.read(getClass().getResourceAsStream("/other/poker-hands.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setTitle("Hand Chart");
		setSize(chart.getWidth(), chart.getHeight());
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void paint(Graphics g){
		g.drawImage(chart, 0, 0, null);
	}
}
