package scripts;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import holdem.HoldemGame;

public class TutorialDisplay {
	
	ArrayList<BufferedImage> loadedImages = new ArrayList<>();
	private JFrame frame;
	private JPanel imagesPanel;
	
	@SuppressWarnings("unused")
	private JPanel buttonsPanel;
	
	private int imageIndex = 0;
	
	private class ImagesPanel extends JPanel{		

		private static final long serialVersionUID = 1L;

		public void paint(Graphics g){
			g.drawImage(loadedImages.get(imageIndex), 0, -100, null);
		}
	}
	
	private class ButtonsPanel extends JPanel{
		private static final long serialVersionUID = 1L;
		
		private JButton back;
		private JButton next;
		private JButton play;
		
		public ButtonsPanel(){
			back = new JButton("Back");
			next = new JButton("Next");
			play = new JButton("Play");
			
			back.setSize(100, 50);
			back.setLocation(100, 600);
			back.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					if(imageIndex > 0) imageIndex--;
					imagesPanel.repaint();
				}
			});
			
			next.setSize(100, 50);
			next.setLocation(700, 600);
			next.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					if(imageIndex < loadedImages.size()-1) imageIndex++;
					imagesPanel.repaint();
				}
			});
			
			play.setSize(200, 50);
			play.setLocation(350, 600);
			play.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					new HoldemGame();
					frame.dispose();
				}
			});							
			
			frame.add(back);
			frame.add(next);
			frame.add(play);
		}
	}
	public TutorialDisplay(ArrayList<BufferedImage> images){
		loadedImages = images;
		frame = new JFrame();
		imagesPanel = new ImagesPanel();
		buttonsPanel = new ButtonsPanel();
		frame.setTitle("Tutorial");
		frame.setSize(900, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(imagesPanel);
		//frame.add(buttonsPanel);
		frame.setVisible(true);
	}
	
}