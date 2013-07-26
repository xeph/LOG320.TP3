package ai;

import game.Board;
import game.Piece;

import java.util.ArrayList;

class WinningPosition {
	
	private WinningPosition() {
		
	}
	
	public static final boolean isAWinningPosition(boolean isValidationForWhite) {
		int pieceToLookFor = isValidationForWhite ? Board.WHITE_PIECE : Board.BLACK_PIECE;
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		int[][] board = Board.getInstance().getBoardMatrix();
		
		for (int y = 0; y != Board.BOARD_SIZE; ++y) {
			for (int x = 0; x != Board.BOARD_SIZE; ++x) {
				if (board[x][y] == pieceToLookFor) {
					pieces.add(new Piece(x, y));
				}
			}
		}
		
		if (pieces.size() != 0) {
			Piece currentPiece = pieces.get(0);
			pieces.remove(0);
			areAllAdjacentPieces(pieces, currentPiece);
		}
		
		return pieces.size() == 0;
	}
	
	private static final void areAllAdjacentPieces(ArrayList<Piece> pieces, Piece currentPiece) {
		ArrayList<Piece> adjacentPieces = new ArrayList<Piece>();
		
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
			areAllAdjacentPieces(pieces, piece);
		}
	}
}
