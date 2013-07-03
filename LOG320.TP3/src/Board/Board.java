package Board;

/*
Numero piece
0 Case vide
2 Pions noir
4 Pions blanc

1 . o o o o o o .
2 x . . . . . . x
3 x . . . . . . x
4 x . . . . . . x
5 x . . . . . . x
6 x . . . . . . x
7 x . . . . . . x
8 . o o o o o o .
  A B C D E F G H
*/

public class Board {
	private static final Board instance = new Board();
	public int[][] board = new int[8][8];
	
	/**
	 * Prints the board to the console
	 */
	public final void printBoard() {
		for(int[] row : board) {
			for (int cell : row) {
	            System.out.print(cell + "\t");
	        }
			System.out.println();
        }
	}
	
	/**
	 * Get the instance of the board.
	 * @return Board
	 */
	public static Board getInstance() {
		return instance;
	}
}
