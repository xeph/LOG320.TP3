package ai;

import game.Board;

public class CustomCentralizing implements Heuristic {
private static CustomCentralizing instance;
	
	private CustomCentralizing() {
		
	}
	
	public static CustomCentralizing getInstance() {
		if (instance == null) {
			instance = new CustomCentralizing();
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
					
					if (tempX + tempY >= 5) {
						score += 4;
					} else if (tempX + tempY == 4) {
						score += 3;
					} else if (tempX + tempY == 3) {
						score += 2;
					} else if (tempX + tempY == 2) {
						score += 1;
					}
				}
			}
		}
		
		return score;
	}
}
