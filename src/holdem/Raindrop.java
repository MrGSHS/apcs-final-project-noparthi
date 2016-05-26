package holdem;

import java.awt.Color;

/**
* This class is used by the losescreen.java file
* 
* @author  Jerry Zhou, Jonathan Xue
* @version 1.0
* @since   2016-5-26
*/

public class Raindrop {
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private int colorVar;
	private Color grayShade;
	private boolean delete;

	public Raindrop(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		colorVar = (int) (Math.random() * 206 + 50);
		grayShade = new Color(colorVar, colorVar, colorVar);
	}

	public int getx1() {
		return x1;
	}

	public int gety1() {
		return y1;
	}

	public int getx2() {
		return x2;
	}

	public int gety2() {
		return y2;
	}

	public boolean getDelete() {
		return delete;
	}

	public Color getGrayShade() {
		return grayShade;
	}

	public void setX1Y1(int x1, int y1) {
		this.x1 = x1;
		this.y1 = y1;
	}

	public void setX2Y2(int x2, int y2) {
		this.x2 = x2;
		this.y2 = y2;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public void move() {
		x1 += 10;
		y1 += 10;
		x2 += 10;
		y2 += 10;
		if (x2 >= 900) {
			x2 -= (x1 + 600);
			x1 = -600;
		}
		if (y2 >= 600) {
			y2 -= y1;
			y1 = 0;
		}
	}
}
