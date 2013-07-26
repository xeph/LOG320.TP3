package ai;

public class BoardEvaluator {
	public static final boolean isAWinningPosition(boolean isValidationForWhite) {
		return WinningPosition.isAWinningPosition(isValidationForWhite);
	}
	
	public static final double getHeuristicValue(boolean isScoreForWhite) {
		
		double quadValue = Quad.getInstance().getScore(!isScoreForWhite) 
				- Quad.getInstance().getScore(isScoreForWhite);

		double highestGroupSizeValue = HighestGroupSize.getInstance().getScore(isScoreForWhite)
				- HighestGroupSize.getInstance().getScore(!isScoreForWhite);
		
		double centralizingValue = CustomCentralizing.getInstance().getScore(isScoreForWhite)
			- CustomCentralizing.getInstance().getScore(!isScoreForWhite);
		
		return quadValue  + highestGroupSizeValue + centralizingValue;
	}
}
