package scripts;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TutorialDisplay {
	
	private ArrayList<BufferedImage> images = new ArrayList<>(); 
	private JFrame frame;
	private JPanel imagesPanel;
	private JPanel buttonsPanel;
	private class ImagesPanel extends JPanel{
		private int imageIndex = 0;
		
		public void paint(Graphics g){
			g.drawImage(images.get(imageIndex), 0, 0, null);
		}
	}
	
	private class ButtonsPanel extends JPanel{
		private JButton back;
		private JButton next;
		private JButton play;
		
		public ButtonsPanel(){
			back = new JButton("Back");
			next = new JButton("Next");
			play = new JButton("Play");
			
			back.setSize(100, 50);
			back.setLocation(100, 900);
			
			next.setSize(100, 50);
			next.setLocation(1000, 900);
			
			play.setSize(200, 50);
			play.setLocation(500, 900);
			
			frame.add(back);
			frame.add(play);
			frame.add(next);
		}
	}
	public TutorialDisplay(){
		for(int i = 2; i < 5; i++){
			String path = "/tutorial/slide-" + i + ".jpg";
			try {
				images.add(ImageIO.read(getClass().getResourceAsStream(path)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		frame = new JFrame();
		imagesPanel = new ImagesPanel();
		buttonsPanel = new ButtonsPanel();
		frame.setTitle("Tutorial");
		frame.setSize(1200, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(imagesPanel);
		//frame.add(buttonsPanel);
		frame.setVisible(true);
		
	}
	
}