package searchalgorithm;

import game.Board;
import game.Move;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import ai.BoardEvaluator;

public class AlphaBetaAlgorithm {
	
	private static int treeDepth = 4;
	
	private AlphaBetaAlgorithm() {
		
	}
	
	public static final Move getBestMove() {
		Iterator<Move> possibleMoves = findMoves(Board.getInstance().isPlayerWhite());
		ArrayList<Move> bestMoves = new ArrayList<Move>();
		
		if (possibleMoves.hasNext()) {
			bestMoves.add(possibleMoves.next());
			bestMoves.get(0).setHeuristicValue(alphaBeta(bestMoves.get(0), treeDepth - 1, Double.MIN_VALUE, Double.MAX_VALUE, false));
		}
		
		Move currentMove;
		
		while (possibleMoves.hasNext()) {
			currentMove = possibleMoves.next();
			currentMove.setHeuristicValue(alphaBeta(currentMove, treeDepth - 1, bestMoves.get(0).getHeuristicValue(), Double.MAX_VALUE, false));
			
			if (currentMove.getHeuristicValue() > bestMoves.get(0).getHeuristicValue()) {
				bestMoves.removeAll(bestMoves);
				bestMoves.add(currentMove);
			} else if (currentMove.getHeuristicValue() == bestMoves.get(0).getHeuristicValue()) {
				bestMoves.add(currentMove);
			}
		}
		
		Iterator<Move> itrBestMoves = bestMoves.iterator();
		ArrayList<Move> bestBestMoves = new ArrayList<Move>();
		
		while (itrBestMoves.hasNext()) {
			Move possibleBestMove = itrBestMoves.next();
			Board.getInstance().makeMove(possibleBestMove);
			possibleBestMove.setHeuristicValue(BoardEvaluator.getHeuristicValue(Board.getInstance().isPlayerWhite()));
			
			if (bestBestMoves.size() == 0) {
				bestBestMoves.add(possibleBestMove);
			} else {
				if (possibleBestMove.getHeuristicValue() > bestBestMoves.get(0).getHeuristicValue()) {
					bestBestMoves.removeAll(bestBestMoves);
					bestBestMoves.add(possibleBestMove);
				} else if (possibleBestMove.getHeuristicValue() == bestBestMoves.get(0).getHeuristicValue()) {
					bestBestMoves.add(possibleBestMove);
				}
			}
			
			Board.getInstance().undoLastMove();
		}
		
		Random random = new Random();
		return bestBestMoves.isEmpty() ? null : bestBestMoves.get(random.nextInt(bestBestMoves.size()));
	}
	
	public static final double alphaBeta(Move move, int depth, double alpha, double beta, boolean maximizeForPlayer) {
		Board.getInstance().makeMove(move);
		double returnValue;
		boolean isWhiteTurn = (!maximizeForPlayer && Board.getInstance().isPlayerWhite()) || (maximizeForPlayer && !Board.getInstance().isPlayerWhite());
		
		if (BoardEvaluator.isAWinningPosition(isWhiteTurn)) {
			if ((Board.getInstance().isPlayerWhite() && isWhiteTurn)
					||(!Board.getInstance().isPlayerWhite() && !isWhiteTurn)){
				move.setHeuristicValue(100);
			} else {
				move.setHeuristicValue(-100);
			}
			
			returnValue = move.getHeuristicValue();
		} else if (depth == 0) {
			move.setHeuristicValue(BoardEvaluator.getHeuristicValue(isWhiteTurn));
			returnValue = move.getHeuristicValue();
		} else {
			Iterator<Move> children = findMoves(isWhiteTurn);
			
			if (!children.hasNext()) {
				if ((Board.getInstance().isPlayerWhite() && isWhiteTurn) || (!Board.getInstance().isPlayerWhite() && !isWhiteTurn)) {
					move.setHeuristicValue(Double.MIN_VALUE);
				} else {
					move.setHeuristicValue(Double.MAX_VALUE);
				}
				
				returnValue = move.getHeuristicValue();
			} else if (maximizeForPlayer) {
				Move child = null;
				while (children.hasNext() && alpha < beta) {
					child = children.next();
					alpha = Math.max(alpha, alphaBeta(child, depth - 1, alpha, beta, !maximizeForPlayer));
				}
				
				returnValue = alpha;
			} else {
				Move child = null;
				while (children.hasNext() && alpha < beta) {
					child = children.next();
					beta = Math.min(beta, alphaBeta(child, depth - 1, alpha, beta, !maximizeForPlayer));
				}
				
				returnValue = beta;
			}
		}
		
		Board.getInstance().undoLastMove();
		
		return returnValue;
	}
	
	private static final Iterator<Move> findMoves(boolean isWhiteTurn) {
		ArrayList<Move> moves = new ArrayList<Move>();
		int[][] boardMatrix = Board.getInstance().getBoardMatrix();
		int pieceToLookFor;
		int oppositePiece;
		boolean isAValidMove;
		int linePieceCount = 0;
		
		if (isWhiteTurn) {
			pieceToLookFor = Board.WHITE_PIECE;
			oppositePiece = Board.BLACK_PIECE;
		} else {
			pieceToLookFor = Board.BLACK_PIECE;
			oppositePiece = Board.WHITE_PIECE;
		}
		
		for (int x = 0; x != Board.BOARD_SIZE; ++x) {
			for (int y = 0; y != Board.BOARD_SIZE; ++y) {
				if (boardMatrix[x][y] == pieceToLookFor) {
					/* ******* Look at row ******* */
					linePieceCount = 0;
					for (int i = 0; i != Board.BOARD_SIZE; ++i) {
						if (boardMatrix[i][y] != Board.EMPTY_TILE) {
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
						moves.add(new Move(x, y, x - linePieceCount, y, isWhiteTurn));
					}
					
					// Right move
					isAValidMove = true;
					if (x + linePieceCount >= Board.BOARD_SIZE) {
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
						moves.add(new Move(x, y, x + linePieceCount, y, isWhiteTurn));
					}
					
					/* ******* Look at column ******* */
					linePieceCount = 0;
					for (int i = 0; i != Board.BOARD_SIZE; ++i) {
						if (boardMatrix[x][i] != Board.EMPTY_TILE) {
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
						moves.add(new Move(x, y, x, y - linePieceCount, isWhiteTurn));
					}
					
					// Down move
					isAValidMove = true;
					if (y + linePieceCount >= Board.BOARD_SIZE) {
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
						moves.add(new Move(x, y, x, y + linePieceCount, isWhiteTurn));
					}
					
					/* ******* Look at top-left to bottom-right diagonal ******* */
					linePieceCount = 0;
					if (x >= y) {
						for (int i = 0; i != Board.BOARD_SIZE - (x - y); ++i) {
							if (boardMatrix[x - y + i][i] != Board.EMPTY_TILE) {
								++linePieceCount;
							}
						}
					} else {
						for (int i = 0; i != Board.BOARD_SIZE - (y - x); ++i) {
							if (boardMatrix[i][y - x + i] != Board.EMPTY_TILE) {
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
						moves.add(new Move(x, y, x - linePieceCount, y - linePieceCount, isWhiteTurn));
					}
					
					// Down-right move
					isAValidMove = true;
					if (y + linePieceCount >= Board.BOARD_SIZE || x + linePieceCount >= Board.BOARD_SIZE) {
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
						moves.add(new Move(x, y, x + linePieceCount, y + linePieceCount, isWhiteTurn));
					}
					
					/* ******* Look at bottom-left to top-right diagonal ******* */
					linePieceCount = 0;
					if (x >= Board.BOARD_SIZE - y - 1) {
						for (int i = 0; i != Board.BOARD_SIZE - (x - (Board.BOARD_SIZE - y - 1)); ++i) {
							if (boardMatrix[x - (Board.BOARD_SIZE - y - 1) + i][Board.BOARD_SIZE - i - 1] != Board.EMPTY_TILE) {
								++linePieceCount;
							}
						}
					} else {
						for (int i = 0; i != y + x + 1; ++i) {
							if (boardMatrix[i][y + x - i] != Board.EMPTY_TILE) {
								++linePieceCount;
							}
						}
					}
					
					// Down-left move
					isAValidMove = true;
					if (y + linePieceCount >= Board.BOARD_SIZE || x - linePieceCount < 0) {
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
						moves.add(new Move(x, y, x - linePieceCount, y + linePieceCount, isWhiteTurn));
					}
					
					// Up-right move
					isAValidMove = true;
					if (y - linePieceCount < 0 || x + linePieceCount >= Board.BOARD_SIZE) {
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
						moves.add(new Move(x, y, x + linePieceCount, y - linePieceCount, isWhiteTurn));
					}
				}
			}
		}
		
		return moves.iterator();
	}
}
