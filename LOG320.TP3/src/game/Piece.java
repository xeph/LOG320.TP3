package game;

public class Piece {
	private int x;
	private int y;
	
	public Piece(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public final int getX() {
		return this.x;
	}
	
	public final int getY() {
		return this.y;
	}
}
