package searchtree;

import java.util.Iterator;
import java.util.Random;

import Board.Board;
import Board.BoardHelper;

public class SearchTreeHelpers {
	private final static int MAX_TREE_DEPTH = 4;
	
	private static SearchTreeHelpers instance;
	
	private SearchTreeHelpers() {
	}
	
	public SearchTreeHelpers getInstance() {
		if (instance == null) {
			instance = new SearchTreeHelpers();
		}
		
		return instance;
	}
	
	public Node createSearchTree() {
		Node tree = new Node(0, 0, 0, 0);
		
		updateSearchTree(tree, Board.getInstance().getBoardMatrix(), 0, Board.getInstance().isAIWhite());
		
		return tree;
	}
	
	public void updateSearchTree(Node node, int[][] boardMatrix, int depth, boolean isValidationForWhite) {
		if (BoardHelper.getInstance().isAWinningPosition(boardMatrix, isValidationForWhite)) {
			if (Board.getInstance().isAIWhite() && isValidationForWhite) {
				node.setValue(21);
			} else {
				node.setValue(-1);
			}
		} else if (BoardHelper.getInstance().isAWinningPosition(boardMatrix, !isValidationForWhite)) {
			if (Board.getInstance().isAIWhite() && !isValidationForWhite) {
				node.setValue(-1);
			} else {
				node.setValue(21);
			}
		} else if (depth == MAX_TREE_DEPTH) {
			Random random = new Random();
			node.setValue(random.nextInt(21));
		} else {
			Iterator<Node> children = node.getChildren();
			if (children.hasNext()) {
				int[][] boardMatrixCopy = null;
				while (children.hasNext()) {
					Node child = children.next();
					boardMatrixCopy = boardMatrix.clone();
					BoardHelper.getInstance().makeMove(boardMatrixCopy, child.getAtX(), child.getAtY(), child.getToX(), child.getToY());
					updateSearchTree(child, boardMatrixCopy, depth + 1, !isValidationForWhite);
				}
			} else {
				int pieceToLookFor = isValidationForWhite ? BoardHelper.WHITE_PIECE : BoardHelper.BLACK_PIECE;
				int oppositePiece = isValidationForWhite ? BoardHelper.BLACK_PIECE : BoardHelper.WHITE_PIECE;
				
				for (int y = 0; y != BoardHelper.BOARD_SIZE; ++y) {
					for (int x = 0; x != BoardHelper.BOARD_SIZE; ++x) {
						if (boardMatrix[x][y] == pieceToLookFor) {
							boolean isAValidMove;
							int linePieceCount = 0;
							
							/* ******* Look at row ******* */
							linePieceCount = 0;
							for (int i = 0; i != BoardHelper.BOARD_SIZE; ++i) {
								if (boardMatrix[i][y] != BoardHelper.EMPTY_TILE) {
									++linePieceCount;
								}
							}
							
							// Left move
							isAValidMove = true;
							if (x - linePieceCount < 0) {
								isAValidMove = false;
							} else if (boardMatrix[x - linePieceCount][y] == pieceToLookFor) {
								isAValidMove = false;
							} else {
								for (int i = 1; i != linePieceCount; ++i) {
									if (boardMatrix[x - i][y] == oppositePiece) {
										isAValidMove = false;
									}
								}
							}
							
							if (isAValidMove) {
								Node child = new Node(x, y, x - linePieceCount, y);
								node.addChild(child);
								int[][] boardMatrixCopy = boardMatrix.clone();
								BoardHelper.getInstance().makeMove(boardMatrixCopy, child.getAtX(), child.getAtY(), child.getToX(), child.getToY());
								updateSearchTree(child, boardMatrixCopy, depth + 1, !isValidationForWhite);
							}
							
							// Right move
							isAValidMove = true;
							if (x + linePieceCount >= BoardHelper.BOARD_SIZE) {
								isAValidMove = false;
							} else if (boardMatrix[x + linePieceCount][y] == pieceToLookFor) {
								isAValidMove = false;
							} else {
								for (int i = 1; i != linePieceCount; ++i) {
									if (boardMatrix[x + i][y] == oppositePiece) {
										isAValidMove = false;
									}
								}
							}
							
							if (isAValidMove) {
								Node child = new Node(x, y, x + linePieceCount, y);
								node.addChild(child);
								int[][] boardMatrixCopy = boardMatrix.clone();
								BoardHelper.getInstance().makeMove(boardMatrixCopy, child.getAtX(), child.getAtY(), child.getToX(), child.getToY());
								updateSearchTree(child, boardMatrixCopy, depth + 1, !isValidationForWhite);
							}
							
							/* ******* Look at column ******* */
							linePieceCount = 0;
							for (int i = 0; i != BoardHelper.BOARD_SIZE; ++i) {
								if (boardMatrix[x][i] != BoardHelper.EMPTY_TILE) {
									++linePieceCount;
								}
							}
							
							// Up move
							isAValidMove = true;
							if (y - linePieceCount < 0) {
								isAValidMove = false;
							} else if (boardMatrix[x][y - linePieceCount] == pieceToLookFor) {
								isAValidMove = false;
							} else {
								for (int i = 1; i != linePieceCount; ++i) {
									if (boardMatrix[x][y - i] == oppositePiece) {
										isAValidMove = false;
									}
								}
							}
							
							if (isAValidMove) {
								Node child = new Node(x, y, x, y - linePieceCount);
								node.addChild(child);
								int[][] boardMatrixCopy = boardMatrix.clone();
								BoardHelper.getInstance().makeMove(boardMatrixCopy, child.getAtX(), child.getAtY(), child.getToX(), child.getToY());
								updateSearchTree(child, boardMatrixCopy, depth + 1, !isValidationForWhite);
							}
							
							// Down move
							isAValidMove = true;
							if (y + linePieceCount >= BoardHelper.BOARD_SIZE) {
								isAValidMove = false;
							} else if (boardMatrix[x][y + linePieceCount] == pieceToLookFor) {
								isAValidMove = false;
							} else {
								for (int i = 1; i != linePieceCount; ++i) {
									if (boardMatrix[x][y + i] == oppositePiece) {
										isAValidMove = false;
									}
								}
							}
							
							if (isAValidMove) {
								Node child = new Node(x, y, x, y + linePieceCount);
								node.addChild(child);
								int[][] boardMatrixCopy = boardMatrix.clone();
								BoardHelper.getInstance().makeMove(boardMatrixCopy, child.getAtX(), child.getAtY(), child.getToX(), child.getToY());
								updateSearchTree(child, boardMatrixCopy, depth + 1, !isValidationForWhite);
							}
							
							/* ******* Look at top-left to bottom-right diagonal ******* */
							linePieceCount = 0;
							if (x >= y) {
								for (int i = 0; i != BoardHelper.BOARD_SIZE - (x - y); ++i) {
									if (boardMatrix[x - y + i][i] != BoardHelper.EMPTY_TILE) {
										++linePieceCount;
									}
								}
							} else {
								for (int i = 0; i != BoardHelper.BOARD_SIZE - (y - x); ++i) {
									if (boardMatrix[i][y - x + i] != BoardHelper.EMPTY_TILE) {
										++linePieceCount;
									}
								}
							}
							
							// Up-left move
							isAValidMove = true;
							if (y - linePieceCount < 0 || x - linePieceCount < 0) {
								isAValidMove = false;
							} else if (boardMatrix[x - linePieceCount][y - linePieceCount] == pieceToLookFor) {
								isAValidMove = false;
							} else {
								for (int i = 1; i != linePieceCount; ++i) {
									if (boardMatrix[x - i][y - i] == oppositePiece) {
										isAValidMove = false;
									}
								}
							}
							
							if (isAValidMove) {
								Node child = new Node(x, y, x - linePieceCount, y - linePieceCount);
								node.addChild(child);
								int[][] boardMatrixCopy = boardMatrix.clone();
								BoardHelper.getInstance().makeMove(boardMatrixCopy, child.getAtX(), child.getAtY(), child.getToX(), child.getToY());
								updateSearchTree(child, boardMatrixCopy, depth + 1, !isValidationForWhite);
							}
							
							// Down-right move
							isAValidMove = true;
							if (y + linePieceCount >= BoardHelper.BOARD_SIZE || x + linePieceCount >= BoardHelper.BOARD_SIZE) {
								isAValidMove = false;
							} else if (boardMatrix[x + linePieceCount][y + linePieceCount] == pieceToLookFor) {
								isAValidMove = false;
							} else {
								for (int i = 1; i != linePieceCount; ++i) {
									if (boardMatrix[x + i][y + i] == oppositePiece) {
										isAValidMove = false;
									}
								}
							}
							
							if (isAValidMove) {
								Node child = new Node(x, y, x + linePieceCount, y + linePieceCount);
								node.addChild(child);
								int[][] boardMatrixCopy = boardMatrix.clone();
								BoardHelper.getInstance().makeMove(boardMatrixCopy, child.getAtX(), child.getAtY(), child.getToX(), child.getToY());
								updateSearchTree(child, boardMatrixCopy, depth + 1, !isValidationForWhite);
							}
							
							/* ******* Look at bottom-left to top-right diagonal ******* */
							linePieceCount = 0;
							if (x >= BoardHelper.BOARD_SIZE - y - 1) {
								for (int i = 0; i != BoardHelper.BOARD_SIZE - (x - (BoardHelper.BOARD_SIZE - y - 1)); ++i) {
									if (boardMatrix[x - (BoardHelper.BOARD_SIZE - y - 1) + i][BoardHelper.BOARD_SIZE - i - 1] != BoardHelper.EMPTY_TILE) {
										++linePieceCount;
									}
								}
							} else {
								for (int i = 0; i != y + x + 1; ++i) {
									if (boardMatrix[i][y + x - i] != BoardHelper.EMPTY_TILE) {
										++linePieceCount;
									}
								}
							}
							
							// Down-left move
							isAValidMove = true;
							if (y + linePieceCount >= BoardHelper.BOARD_SIZE || x - linePieceCount < 0) {
								isAValidMove = false;
							} else if (boardMatrix[x - linePieceCount][y + linePieceCount] == pieceToLookFor) {
								isAValidMove = false;
							} else {
								for (int i = 1; i != linePieceCount; ++i) {
									if (boardMatrix[x - i][y + i] == oppositePiece) {
										isAValidMove = false;
									}
								}
							}
							
							if (isAValidMove) {
								Node child = new Node(x, y, x - linePieceCount, y + linePieceCount);
								node.addChild(child);
								int[][] boardMatrixCopy = boardMatrix.clone();
								BoardHelper.getInstance().makeMove(boardMatrixCopy, child.getAtX(), child.getAtY(), child.getToX(), child.getToY());
								updateSearchTree(child, boardMatrixCopy, depth + 1, !isValidationForWhite);
							}
							
							// Up-right move
							isAValidMove = true;
							if (y - linePieceCount < 0 || x + linePieceCount >= BoardHelper.BOARD_SIZE) {
								isAValidMove = false;
							} else if (boardMatrix[x + linePieceCount][y - linePieceCount] == pieceToLookFor) {
								isAValidMove = false;
							} else {
								for (int i = 1; i != linePieceCount; ++i) {
									if (boardMatrix[x + i][y - i] == oppositePiece) {
										isAValidMove = false;
									}
								}
							}
							
							if (isAValidMove) {
								Node child = new Node(x, y, x + linePieceCount, y - linePieceCount);
								node.addChild(child);
								int[][] boardMatrixCopy = boardMatrix.clone();
								BoardHelper.getInstance().makeMove(boardMatrixCopy, child.getAtX(), child.getAtY(), child.getToX(), child.getToY());
								updateSearchTree(child, boardMatrixCopy, depth + 1, !isValidationForWhite);
							}
						}
					}
				}
			}
		}
	}
}
