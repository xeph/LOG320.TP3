package ai;

import game.Board;
import game.Piece;

import java.util.ArrayList;

public class HighestGroupSize implements Heuristic {
	private static HighestGroupSize instance;
	
	private HighestGroupSize() {
		
	}
	
	public static HighestGroupSize getInstance() {
		if (instance == null) {
			instance = new HighestGroupSize();
		}
		
		return instance;
 	}
	
	public double getScore(boolean isScoreForWhite) {
		int pieceToLookFor = isScoreForWhite ? Board.WHITE_PIECE : Board.BLACK_PIECE;
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		int[][] board = Board.getInstance().getBoardMatrix();
		
		for (int y = 0; y != Board.BOARD_SIZE; ++y) {
			for (int x = 0; x != Board.BOARD_SIZE; ++x) {
				if (board[x][y] == pieceToLookFor) {
					pieces.add(new Piece(x, y));
				}
			}
		}
		
		int piecesRemoved = 12 - pieces.size();
		int highestGroupSize = 0;
		
		while (pieces.size() != 0) {
			Piece currentPiece = pieces.get(0);
			pieces.remove(0);
			highestGroupSize = Math.max(highestGroupSize, groupSizeByPiece(pieces, currentPiece));
		}
		
		return highestGroupSize + piecesRemoved;
	}
	
	private static final int groupSizeByPiece(ArrayList<Piece> pieces, Piece currentPiece) {
		ArrayList<Piece> adjacentPieces = new ArrayList<Piece>();
		int pieceInGroup = 1;
		
		for (Piece piece : pieces) {
			if (
					(currentPiece.getY() == piece.getY() - 1 
						&& (currentPiece.getX() == piece.getX() - 1
							|| currentPiece.getX() == piece.getX()
							|| currentPiece.getX() == piece.getX() + 1
							)
						)
					|| (currentPiece.getY() == piece.getY()
						&& (currentPiece.getX() == piece.getX() - 1
							|| currentPiece.getX() == piece.getX() + 1
							)
						)
					|| (currentPiece.getY() == piece.getY() + 1 
						&& (currentPiece.getX() == piece.getX() - 1
							|| currentPiece.getX() == piece.getX()
							|| currentPiece.getX() == piece.getX() + 1
							)
						)
				) {
				
				adjacentPieces.add(piece);
			}
		}
		
		pieces.removeAll(adjacentPieces);
		
		for (Piece piece : adjacentPieces) {
			pieceInGroup += groupSizeByPiece(pieces, piece);
		}
		
		return pieceInGroup;
	}
}
