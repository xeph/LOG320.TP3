package Algorithm;

public class AlphaBeta {
	// AlphaBeta algorithm (wikipedia)
	public static int Alpha_Beta(Node node, int depth, int alpha, int beta, boolean maximizingPlayer) {
		if (depth = 0 || node.leaf() == true)
			return node.value();

		if (maximizingPlayer) {
			for (Node child : node) {
				alpha = Math.max(alpha, AlphaBeta(child, depth - 1, alpha, beta, !maximizingPlayer));
				if (beta <= alpha)
					break;
			}
			return alpha;
		} else {
			for (Node child : node) {
				beta = Math.min(beta, AlphaBeta(child, depth - 1, alpha, beta, !maximizingPlayer));
				if (beta <= alpha)
					break;
			}
			return beta;
		}
	}
}
