
/**
 * MoveCommand objects are used to contain information about where a ChessPiece object moves from
 * and where it moves to. Lists of moves are made up of many MoveCommand objects in a common data
 * structure. These lists are used to look for check mate states among other things.
 * 
 * @author Devan Karsann
 */
public class MoveCommand {

	private String move = ""; // format consists of five characters: <a-h><1-8><space><a-h><1-8>
    private int startRow; // refers to the start row of a move
    private int startColumn;
    private int endRow; // refers to the end row of a move
    private int endColumn;
    private int avgDistToCenter;
    private int takePieceDiff;
    private int numSafePoints;
    private boolean actuallySafe;
    private int diffNumSafeMoves;
    private int priority = 0;
    
    private int diffNumSafeMovesPriority;
    private int avgDistToCenterPriority;
    private int numSafePointsPriority;
    private int opponentInCheckMateMovePriority;
    private int noLossMovePriority;
    private int takeOpponentPieceMovePriority;
    private int canTakePieceAfterwardsMovePriority;
    
	public int getDiffNumSafeMovesPriority() {
		return diffNumSafeMovesPriority;
	}

	public void setDiffNumSafeMovesPriority(int diffNumSafeMovesPriority) {
		this.diffNumSafeMovesPriority = diffNumSafeMovesPriority;
	}

	public int getAvgDistToCenterPriority() {
		return avgDistToCenterPriority;
	}

	public void setAvgDistToCenterPriority(int avgDistToCenterPriority) {
		this.avgDistToCenterPriority = avgDistToCenterPriority;
	}

	public int getGetNumSafePointsPriority() {
		return numSafePointsPriority;
	}

	public void setNumSafePointsPriority(int numSafePointsPriority) {
		this.numSafePointsPriority = numSafePointsPriority;
	}

	public int getOpponentInCheckMateMovePriority() {
		return opponentInCheckMateMovePriority;
	}

	public void setOpponentInCheckMateMovePriority(int opponentInCheckMateMovePriority) {
		this.opponentInCheckMateMovePriority = opponentInCheckMateMovePriority;
	}

	public int getNoLossMovePriority() {
		return noLossMovePriority;
	}

	public void setNoLossMovePriority(int noLossMovePriority) {
		this.noLossMovePriority = noLossMovePriority;
	}

	public int getTakeOpponentPieceMovePriority() {
		return takeOpponentPieceMovePriority;
	}

	public void setTakeOpponentPieceMovePriority(int takeOpponentPieceMovePriority) {
		this.takeOpponentPieceMovePriority = takeOpponentPieceMovePriority;
	}

	public int getCanTakePieceAfterwardsMovePriority() {
		return canTakePieceAfterwardsMovePriority;
	}

	public void setCanTakePieceAfterwardsMovePriority(int canTakePieceAfterwardsMovePriority) {
		this.canTakePieceAfterwardsMovePriority = canTakePieceAfterwardsMovePriority;
	}

	/**
	 * This is a constructor for MoveCommand objects used by the AI. An initial location and an 
	 * end location are entered according to their row and column indices.
	 *  
	 * @param startRow
	 * @param startColumn
	 * @param endRow
	 * @param endColumn
	 */
    MoveCommand(int startRow, int startColumn, int endRow, int endColumn) {
    	move += (char)(startColumn + 97);
    	move += 8 - startRow; 
    	move += " ";	
    	move += (char)(endColumn + 97);
    	move += 8 - endRow;	
    	this.startRow = startRow;
    	this.startColumn = startColumn;
    	this.endRow = endRow;
    	this.endColumn = endColumn;
    }
    
    /**
     * This is an alternate constructor for MoveCommand objects. It is used to make MoveCommand
     * objects based on the input of the user. After MoveCommand objects are made with this constructor,
     * their are verified in the hasValidSyntax method below. 
     * @param playerMove
     */
    MoveCommand(String playerMove) {
    	move = playerMove;
    }

	/**
	 * This constructor is used to reset the values in a MoveCommand object.
	 */
    MoveCommand() {
    	;
    }
	
    public void setDiffNumSafeMoves(int val) { diffNumSafeMoves  = val; System.out.println("diffNumSafeMoves: " + diffNumSafeMoves);}
    
    public int getDiffNumSafeMoves() { return diffNumSafeMoves; }
	
    public int getPriority() { return priority; }
    
    public void addToPriority(int val) { priority += val; }
    
    public void resetPriority() { priority = 0; }
    
	/**
	 * sets the difference in number of no potential loss moves before and after this move is executed
	 * @param val
	 */
	public void setAvgDistToCenter(int val) { avgDistToCenter = val; }
	
	/**
	 * returns the difference in number of no potential loss moves before and after this move is executed
	 * @return diffNumMovesBeforeAfterexec
	 */
	public int getAvgDistToCenter() { return avgDistToCenter; }
	
	/**
	 * returns startRow integer variable
	 * @return startRow
	 */
	public int getStartRow() { return startRow; }

	/**
	 * returns startColumn integer variable
	 * @return startColumn
	 */
	public int getStartColumn() { return startColumn; }

	public void setTakePieceDiff(int value) { takePieceDiff = value; }
	
	public int getTakePieceDiff() { return takePieceDiff; }
	
	public int getNumSafePoints() { return numSafePoints; }
	
	public void setNumSafePoints(int val) { numSafePoints = val; }
	
	public void setActuallySafe() { actuallySafe = true; }
	
	public boolean getActuallySafe() { return actuallySafe; }
	
	/**
	 * returns endRow integer variable
	 * @return endRow
	 */
	public int getEndRow() { return endRow; }

	/**
	 * returns endColumn integer variable
	 * @return endColumn
	 */
	public int getEndColumn() {	return endColumn; }

	/**
	 * returns move string variable
	 * @return move
	 */
	public String toString() { return move; }
	
}
