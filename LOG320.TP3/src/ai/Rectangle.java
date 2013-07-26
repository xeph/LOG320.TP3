package ai;

import game.Board;

public class Rectangle implements Heuristic {
	
	private static Rectangle instance;

	private Rectangle() {
		
	}
	
	public static Rectangle getInstance() {
		if (instance == null) {
			instance = new Rectangle();
		}
		
		return instance;
 	}

	public double getScore(boolean isScoreForWhite) {
		int pieceToLookFor = isScoreForWhite ? Board.WHITE_PIECE : Board.BLACK_PIECE;
		int minX = -1;
		int maxX = -1;
		int minY = -1;
		int maxY = -1;
		int[][] boardMatrix = Board.getInstance().getBoardMatrix();
		
		for (int x = 0; x != Board.BOARD_SIZE; ++x) {
			for (int y = 0; y != Board.BOARD_SIZE; ++y) {
				if (boardMatrix[x][y] == pieceToLookFor) {
					if (minX == -1) {
						minX = x;
					} else {
						minX = Math.min(minX, x);
					}
					
					if (maxX == -1) {
						maxX = x;
					} else {
						maxX = Math.max(maxX, x);
					}
					
					if (minY == -1) {
						minY = y;
					} else {
						minY = Math.min(minY, y);
					}
					
					if (maxY == -1) {
						maxY = y;
					} else {
						maxY = Math.max(maxY, y);
					}
				}
			}
		}
		
		return (maxY - minY) * (maxX - minX);
	}
}
