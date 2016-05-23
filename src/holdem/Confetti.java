package holdem;

import java.awt.Color;

public class Confetti{
	Color randColor;
	int totalDropped;
	int x;
	int y;

	public Confetti(int x, int y) {
		this.x = x;
		this.y = y;
		totalDropped = 0;
		randColor = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255))
				.brighter().brighter().brighter();
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getTotalDropped(){
		return totalDropped;
	}
	public Color getColor() {
		return randColor;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		totalDropped += y;
		this.y = y;
	}
}
