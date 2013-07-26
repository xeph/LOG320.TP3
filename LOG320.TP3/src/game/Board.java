package game;

import java.util.Stack;

public class Board {
	private static Board instance;
	public static final int EMPTY_TILE = 0;
	public static final int BLACK_PIECE = 2;
	public static final int WHITE_PIECE = 4;
	public static final int BOARD_SIZE = 8;
	private int[][] board;
	private Stack<Move> moves;
	private boolean isPlayerWhite;
	private int turn;
	
	private Board() {
		moves = new Stack<Move>();
		board = new int[BOARD_SIZE][BOARD_SIZE];
	}
	
	public final static Board getInstance() {
		if (instance == null) {
			instance = new Board();
		}
		
		return instance;
	}
	
	public int getTurn() {
		return this.turn;
	}
	
	public void setTile(int x, int y, int value) {
		this.board[x][y] = value;
	}
	
	public int[][] getBoardMatrix() {
		int[][] tempTable = new int[BOARD_SIZE][];
		
		for (int i = 0; i != BOARD_SIZE; ++i) {
			int[] tempBoardRow = board[i];
			tempTable[i] = new int[BOARD_SIZE];
			System.arraycopy(tempBoardRow, 0, tempTable[i], 0, BOARD_SIZE);
		}
		
		return tempTable;
	}
	
	public void printBoard() {
		for (int x = 0; x != BOARD_SIZE; ++x) {
			for (int y = 0; y != BOARD_SIZE; ++y) {
				System.out.print(board[x][y] + " ");
			}
			
			System.out.println("");
		}
	}
	
	public final void makeMove(Move move) {
		this.moves.add(move);
		
		if (this.board[move.getToX()][move.getToY()] != EMPTY_TILE) {
			move.setIsCapturingAPiece(true);
		} else {
			move.setIsCapturingAPiece(false);
		}
		
		++this.turn;
		
		this.board[move.getToX()][move.getToY()] = this.board[move.getAtX()][move.getAtY()];
		this.board[move.getAtX()][move.getAtY()] = EMPTY_TILE;
	}
	
	public final void undoLastMove() {
		Move move = this.moves.pop();
		this.board[move.getAtX()][move.getAtY()] = move.isMovingPieceWhite() ? WHITE_PIECE : BLACK_PIECE;
		
		if (move.isCapturingAPiece()) {
			this.board[move.getToX()][move.getToY()] = move.isMovingPieceWhite() ? BLACK_PIECE : WHITE_PIECE;
		} else {
			this.board[move.getToX()][move.getToY()] = EMPTY_TILE;
		}
		
		--this.turn;
	}
	
	public boolean isPlayerWhite() {
		return this.isPlayerWhite;
	}
	
	public void setIsPlayerWhite(boolean isPlayerWhite) {
		this.isPlayerWhite = isPlayerWhite;
	}
}
