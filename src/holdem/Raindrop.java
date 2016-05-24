package holdem;

public class Raindrop {
	private int x;
	private int y;
	private boolean delete;

	public Raindrop(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean getDelete(){
		return delete;
	}

	public void setX(int x) {
		this.x= x;
	}

	public void setY(int y){
		this.y = y;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}
}
