package ai;

import game.Board;

public class Walling implements Heuristic {
	
	private static Walling instance;
	private static final double TWO_SIDE_WALL_VALUE = 5.0;
	private static final double THREE_SIDE_WALL_VALUE = 8.0;

	private Walling() {
		
	}
	
	public static Walling getInstance() {
		if (instance == null) {
			instance = new Walling();
		}
		
		return instance;
 	}

	public double getScore(boolean isScoreForWhite) {
		double score = 0;
		int pieceToWall = isScoreForWhite ? Board.BLACK_PIECE : Board.WHITE_PIECE;
		int pieceWalling = isScoreForWhite ? Board.WHITE_PIECE : Board.BLACK_PIECE;
		int[][] boardMatrix = Board.getInstance().getBoardMatrix();
		
		for (int i = 0; i != Board.BOARD_SIZE / 2; ++i) {
			//// B-column walls
			// Upper part
			if (boardMatrix[0][Board.BOARD_SIZE - i - 1] == pieceToWall) {
				score += getScoreWallUnder(boardMatrix, 0, Board.BOARD_SIZE - i - 1, pieceWalling);
				score += getScoreWallAtRight(boardMatrix, 0, Board.BOARD_SIZE - i - 1, pieceWalling);
			}
			
			// Lower part
			if (boardMatrix[0][i] == pieceToWall) {
				score += getScoreWallOver(boardMatrix, 0, i, pieceWalling);
				score += getScoreWallAtRight(boardMatrix, 0, i, pieceWalling);
			}
			
			//// G-column walls
			// Upper part
			if (boardMatrix[Board.BOARD_SIZE - 1][Board.BOARD_SIZE - i - 1] == pieceToWall) {
				score += getScoreWallUnder(boardMatrix, Board.BOARD_SIZE - 1, Board.BOARD_SIZE - i - 1, pieceWalling);
				score += getScoreWallAtLeft(boardMatrix, Board.BOARD_SIZE - 1, Board.BOARD_SIZE - i - 1, pieceWalling);
			}
			
			// Lower part
			if (boardMatrix[Board.BOARD_SIZE - 1][i] == pieceToWall) {
				score += getScoreWallOver(boardMatrix, Board.BOARD_SIZE - 1, i, pieceWalling);
				score += getScoreWallAtLeft(boardMatrix, Board.BOARD_SIZE - 1, i, pieceWalling);
			}
			
			//// 2nd-row walls
			// Left side
			if (boardMatrix[i][0] == pieceToWall) {
				score += getScoreWallOver(boardMatrix, i, 0, pieceWalling);
				score += getScoreWallAtRight(boardMatrix, i, 0, pieceWalling);
			}
			
			// Right side
			if (boardMatrix[Board.BOARD_SIZE - i - 1][0] == pieceToWall) {
				score += getScoreWallOver(boardMatrix, Board.BOARD_SIZE - 1, 0, pieceWalling);
				score += getScoreWallAtLeft(boardMatrix, Board.BOARD_SIZE - 1, 0, pieceWalling);
			}
			
			//// 7th-row walls
			// Left side
			if (boardMatrix[i][Board.BOARD_SIZE - 1] == pieceToWall) {
				score += getScoreWallUnder(boardMatrix, i, Board.BOARD_SIZE - 1, pieceWalling);
				score += getScoreWallAtRight(boardMatrix, i, Board.BOARD_SIZE - 1, pieceWalling);
			}
			
			// Right side
			if (boardMatrix[Board.BOARD_SIZE - i - 1][Board.BOARD_SIZE - 1] == pieceToWall) {
				score += getScoreWallUnder(boardMatrix, Board.BOARD_SIZE - 1, Board.BOARD_SIZE - 1, pieceWalling);
				score += getScoreWallAtLeft(boardMatrix, Board.BOARD_SIZE - 1, Board.BOARD_SIZE - 1, pieceWalling);
			}
		}
		
		return score;
	}
	
	private double getScoreWallOver(int[][] boardMatrix, int x, int y, int pieceWalling) {
		double score = 0;
		int pieceInWall = 0;
		
		if (x != 0) {
			if (boardMatrix[x - 1][y + 1] == pieceWalling) {
				++pieceInWall;
			}
		} else {
			++pieceInWall;
		}
		
		if (boardMatrix[x][y + 1] == pieceWalling) {
			++pieceInWall;
		}
		
		if (x + 1 != Board.BOARD_SIZE) {
			if (boardMatrix[x + 1][y + 1] == pieceWalling) {
				++pieceInWall;
			}
		}
		
		if (pieceInWall == 2) {
			score += TWO_SIDE_WALL_VALUE;
		} else if (pieceInWall == 3) {
			score += THREE_SIDE_WALL_VALUE;
		}
		
		return score;
	}
	
	private double getScoreWallUnder(int[][] boardMatrix, int x, int y, int pieceWalling) {
		double score = 0;
		int pieceInWall = 0;
		
		if (x != 0) {
			if (boardMatrix[x - 1][y - 1] == pieceWalling) {
				++pieceInWall;
			}
		} else {
			++pieceInWall;
		}
		
		if (boardMatrix[x][y - 1] == pieceWalling) {
			++pieceInWall;
		}
		
		if (x + 1 != Board.BOARD_SIZE) {
			if (boardMatrix[x + 1][y - 1] == pieceWalling) {
				++pieceInWall;
			}
		}
		
		if (pieceInWall == 2) {
			score += TWO_SIDE_WALL_VALUE;
		} else if (pieceInWall == 3) {
			score += THREE_SIDE_WALL_VALUE;
		}
		
		return score;
	}
	
	private double getScoreWallAtRight(int[][] boardMatrix, int x, int y, int pieceWalling) {
		double score = 0;
		int pieceInWall = 0;
		
		if (y != 0) {
			if (boardMatrix[x + 1][y - 1] == pieceWalling) {
				++pieceInWall;
			}
		} else {
			++pieceInWall;
		}
		
		if (boardMatrix[x + 1][y] == pieceWalling) {
			++pieceInWall;
		}
		
		if (y + 1 != Board.BOARD_SIZE) {
			if (boardMatrix[x + 1][y + 1] == pieceWalling) {
				++pieceInWall;
			}
		}
		
		if (pieceInWall == 2) {
			score += TWO_SIDE_WALL_VALUE;
		} else if (pieceInWall == 3) {
			score += THREE_SIDE_WALL_VALUE;
		}
		
		return score;
	}
	
	private double getScoreWallAtLeft(int[][] boardMatrix, int x, int y, int pieceWalling) {
		double score = 0;
		int pieceInWall = 0;
		
		if (y != 0) {
			if (boardMatrix[x - 1][y - 1] == pieceWalling) {
				++pieceInWall;
			}
		} else {
			++pieceInWall;
		}
		
		if (boardMatrix[x - 1][y] == pieceWalling) {
			++pieceInWall;
		}
		
		if (y + 1 != Board.BOARD_SIZE) {
			if (boardMatrix[x - 1][y + 1] == pieceWalling) {
				++pieceInWall;
			}
		}
		
		if (pieceInWall == 2) {
			score += TWO_SIDE_WALL_VALUE;
		} else if (pieceInWall == 3) {
			score += THREE_SIDE_WALL_VALUE;
		}
		
		return score;
	}
}
