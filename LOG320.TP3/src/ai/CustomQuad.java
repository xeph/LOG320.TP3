package ai;

import game.Board;

public class CustomQuad implements Heuristic {
	private static CustomQuad instance;
	
	private CustomQuad() {
		
	}
	
	public static CustomQuad getInstance() {
		if (instance == null) {
			instance = new CustomQuad();
		}
		
		return instance;
	}
	
	public double getScore(boolean isScoreForWhite) {
		double score = 0;
		int pieceInQuad = 0;
		
		int[][] boardMatrix = Board.getInstance().getBoardMatrix();
		int[][] quad = new int[2][2];
		int pieceToLookFor = isScoreForWhite ? Board.WHITE_PIECE : Board.BLACK_PIECE;
		
		for (int x = 0; x != Board.BOARD_SIZE + 1; ++x) {
			for (int y = 0; y != Board.BOARD_SIZE + 1; ++y) {
				pieceInQuad = 0;
				
				if (x - 1 < 0 || y - 1 < 0) {
					quad[0][0] = 0;
				} else {
					if ((quad[0][0] = boardMatrix[x - 1][y - 1]) == pieceToLookFor) {
						++pieceInQuad;
					}
				}
				
				if (x - 1 < 0 || y == Board.BOARD_SIZE) {
					quad[0][1] = 0;
				} else {
					if ((quad[0][1] = boardMatrix[x - 1][y]) == pieceToLookFor) {
						++pieceInQuad;
					}
				}
				
				if (x == Board.BOARD_SIZE || y - 1 < 0) {
					quad[1][0] = 0;
				} else {
					if ((quad[1][0] = boardMatrix[x][y - 1]) == pieceToLookFor) {
						++pieceInQuad;
					}
				}
				
				if (x == Board.BOARD_SIZE || y == Board.BOARD_SIZE) {
					quad[1][1] = 0;
				} else {
					if ((quad[1][1] = boardMatrix[x][y]) == pieceToLookFor) {
						++pieceInQuad;
					}
				}
				
				if (pieceInQuad == 1) {
					score += 0.25;
				} else if (pieceInQuad == 2) {
					if ((quad[0][0] == pieceToLookFor && quad[1][1] == pieceToLookFor)
							|| (quad[0][1] == pieceToLookFor && quad[1][0] == pieceToLookFor)) {
						score -= 0.5;
					}
				} else if (pieceInQuad == 3) {
					score -= 0.25;
				}
			}
		}
		
		return score;
	}
}
