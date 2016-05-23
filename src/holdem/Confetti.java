package holdem;

import java.awt.Color;

public class Confetti {
	Color randColor;
	int x;
	int y;
	int diameter;

	public Confetti(int x, int y, int diameter) {
		this.x = x;
		this.y = y;
		this.diameter = diameter;
		randColor = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255))
				.brighter().brighter();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getDiameter() {
		return diameter;
	}

	public Color getColor() {
		return randColor;
	}

	public void setY(int y) {
		this.y = y;
	}
}
