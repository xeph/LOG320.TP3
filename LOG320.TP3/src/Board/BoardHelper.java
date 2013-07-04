package Board;

import java.util.ArrayList;

public class BoardHelper {
	private static BoardHelper instance;
	public static final int EMPTY_TILE = 0;
	public static final int BLACK_PIECE = 2;
	public static final int WHITE_PIECE = 4;
	public static final int BOARD_SIZE = 8;
	
	private BoardHelper() {
	}
	
	public static BoardHelper getInstance() {
		if (instance == null) {
			instance = new BoardHelper();
		}
		
		return instance;
	}
	
	public final boolean isAWinningPosition(int[][] boardMatrix, boolean isValidationForWhite) {
		int pieceToLookFor = isValidationForWhite ? WHITE_PIECE : BLACK_PIECE;
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		
		for (int y = 0; y != BOARD_SIZE; ++y) {
			for (int x = 0; x != BOARD_SIZE; ++x) {
				if (boardMatrix[x][y] == pieceToLookFor) {
					pieces.add(new Piece(x, y));
				}
			}
		}
		
		Piece currentPiece = pieces.get(0);
		pieces.remove(0);
		areAllAdjacentPieces(pieces, currentPiece);
		
		return pieces.size() == 0;
	}
	
	private final void areAllAdjacentPieces(ArrayList<Piece> pieces, Piece currentPiece) {
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
	
	public final void makeMove(int[][] boardMatrix, int atX, int atY, int toX, int toY) {
		int piece = boardMatrix[atX][atY];
		boardMatrix[atX][atY] = EMPTY_TILE;
		boardMatrix[toX][toY] = piece;
	}
}
