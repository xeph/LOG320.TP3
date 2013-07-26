package ai;

import game.Board;

class Centralizing implements Heuristic {
	private static Centralizing instance;
	
	private Centralizing() {
		
	}
	
	public static Centralizing getInstance() {
		if (instance == null) {
			instance = new Centralizing();
		}
		
		return instance;
	}
	
	public double getScore(boolean isScoreForWhite) {
		int[][] boardMatrix = Board.getInstance().getBoardMatrix();
		int score = 0;
		int tempX = 0;
		int tempY = 0;
		int pieceToLookFor = isScoreForWhite ? Board.WHITE_PIECE : Board.BLACK_PIECE;
		
		for (int x = 0; x != Board.BOARD_SIZE; ++x) {
			for (int y = 0; y != Board.BOARD_SIZE; ++y) {
				if (boardMatrix[x][y] == pieceToLookFor) {
					tempX = x >= 4 ? Board.BOARD_SIZE - x - 1 : x;
					tempY = y >= 4 ? Board.BOARD_SIZE - y - 1 : y;
					score += Math.min(tempX, tempY);
				}
			}
		}
		
		return score;
	}
}
