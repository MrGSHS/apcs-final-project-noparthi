package holdem;

import java.awt.Color;

public class Raindrop {
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private Color whiteShade;
	private boolean delete;

	public Raindrop(int x1, int y1, int x2, int y2, Color whiteShade) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.whiteShade = whiteShade;
	}

	public int getx1() {
		return x1;
	}

	public int gety1() {
		return y1;
	}

	public int getx2(){
		return x2;
	}
	
	public int gety2(){
		return y2;
	}
	
	public boolean getDelete(){
		return delete;
	}

	public void setX1Y1(int x1, int y1) {
		this.x1= x1;
		this.y1 = y1;
	}

	public void sety1(int x2, int y2){
		this.x2 = x2;
		this.y2 = y2;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}
}
