package scripts;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

import holdem.HoldemGame;

/**
* This class starts a slide show 
* with the tutorial for POKER.
* 
* @author  Jerry Zhou, Jonathan Xue
* @version 1.0
* @since   2016-5-26
*/

public class TutorialDisplay {
	
	ArrayList<BufferedImage> loadedImages = new ArrayList<>();
	private BufferedImage iconImage;
	
	private JFrame frame;
	private JPanel imagesPanel;
	private JPanel buttonsPanel;
	
	private int imageIndex = 0;
	private String slideTitle = "";
	
	private class ImagesPanel extends JPanel{		

		private static final long serialVersionUID = 1L;

		public void paint(Graphics g){
			g.drawImage(loadedImages.get(imageIndex), 0, 0, null);
			g.setFont(new Font("Times New Roman", Font.BOLD, 52));
			slideTitle = "Slide "+ (imageIndex+1) + "/"+ loadedImages.size();
			g.drawString(slideTitle, (900 - g.getFontMetrics().stringWidth(slideTitle))/2, 45);
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
			back.setLocation(100, 607);
			back.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					if(imageIndex > 0) imageIndex--;
					imagesPanel.repaint();
				}
			});
			
			next.setSize(100, 50);
			next.setLocation(700, 607);
			next.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					if(imageIndex < loadedImages.size()-1) imageIndex++;
					imagesPanel.repaint();
				}
			});
			
			play.setSize(200, 50);
			play.setLocation(350, 607);
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
		try {
			iconImage = ImageIO.read(this.getClass().getResource("/menu/pokerIcon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.setTitle("Tutorial");
		frame.setIconImage(iconImage);
		frame.setSize(900, 700);
		frame.setBackground(Color.WHITE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(buttonsPanel);
		frame.add(imagesPanel);
		frame.setVisible(true);
	}
	
}