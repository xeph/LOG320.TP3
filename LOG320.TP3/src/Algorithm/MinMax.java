package Algorithm;

public class MinMax {
	public static int minMax() {
		int max;
		max = maxValue(node);
		// make move here
		return 0;
	}

	// minValue algorithm
	private static int minValue(Node node) {
		if (node.leaf() == true)
			return node.value;
		
		int min;
		
		for (Node child : node)
			min = Math.min(min, maxValue(child));

		return min;
	}

	// maxValue algorithm
	private static int maxValue(Node node) {
		if (node.leaf() == true)
			return node.value;

		int max;

		for (Node child : node)
			max = Math.max(max, minValue(child));

		return max;
	}
}
