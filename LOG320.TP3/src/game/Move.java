package game;

public class Move {
	private int atX;
	private int atY;
	private int toX;
	private int toY;
	private String move;
	private Boolean isCapturingAPiece;
	private Boolean isMovingPieceWhite;
	private Double heuristicValue;
	
	public Move(int atX, int atY, int toX, int toY, boolean isMovingPieceWhite) {
		this.atX = atX;
		this.atY = atY;
		this.toX = toX;
		this.toY = toY;
		this.isMovingPieceWhite = isMovingPieceWhite;
	}
	
	public Move(String move, boolean isMovingPieceWhite) {
		this.move = move.replace(" ", "").replace("-", "");
		this.atX = CharToInteger(this.move.charAt(0));
		this.atY = this.move.charAt(1) - 49;
		this.toX = CharToInteger(this.move.charAt(2));
		this.toY = this.move.charAt(3) - 49;
		this.isMovingPieceWhite = isMovingPieceWhite;
	}
	
	public final double getHeuristicValue() {
		return this.heuristicValue;
	}
	
	public final void setHeuristicValue(double heuristicValue) {
		this.heuristicValue = heuristicValue;
	}
	
	public final int getAtX() {
		return this.atX;
	}
	
	public final int getAtY() {
		return this.atY;
	}
	
	public final int getToX() {
		return this.toX;
	}
	
	public final int getToY() {
		return this.toY;
	}
	
	public final boolean isMovingPieceWhite() {
		return this.isMovingPieceWhite;
	}
	
	public final String toString() {
		if (this.move == null) {
			StringBuffer buffer = new StringBuffer();
			buffer.append(IntegerToChar(this.atX));
			buffer.append(this.atY + 1);
			buffer.append(IntegerToChar(this.toX));
			buffer.append(this.toY + 1);
			this.move = buffer.toString();
		}
		
		return this.move;
	}
	
	final boolean isCapturingAPiece() {
		return this.isCapturingAPiece;
	}
	
	final void setIsCapturingAPiece(boolean isCapturingAPiece) {
		this.isCapturingAPiece = isCapturingAPiece;
	}
	
	private final char IntegerToChar(int i) {
		char c = '0';
		
		switch (i) {
			case 0 : c = 'A'; break;
			case 1 : c = 'B'; break;
			case 2 : c = 'C'; break;
			case 3 : c = 'D'; break;
			case 4 : c = 'E'; break;
			case 5 : c = 'F'; break;
			case 6 : c = 'G'; break;
			case 7 : c = 'H'; break;
		}
		
		return c;
	}
	
	private final int CharToInteger(char c) {
		int i = -1;
		
		switch (c) {
			case 'A' : i = 0; break;
			case 'B' : i = 1; break;
			case 'C' : i = 2; break;
			case 'D' : i = 3; break;
			case 'E' : i = 4; break;
			case 'F' : i = 5; break;
			case 'G' : i = 6; break;
			case 'H' : i = 7; break;
		}
		
		return i;
	}
}
