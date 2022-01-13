import java.util.ArrayList;
import java.util.Random;

/**
 * Currently every instantiation of this class would react the same to a given board state. Future updates to this class will
 * use parameters to alter the metrics used by an AI to decide on a move. Additionally, multiple instantiations of this class will
 * play each another in a tournament-style battle for self-improvement. 
 * 
 * @author Devan Karsann
 */
public class aiPlayer01 {

	private MoveCommand aiMove;
	private String moveInfo = "";
	private Random rand = new Random();
	
	private ArrayList<MoveCommand> acceptableMoves = new ArrayList<MoveCommand>();

	private ArrayList<MoveCommand> noLossMoves = new ArrayList<MoveCommand>();
	private int noLossMovePriority;
	
	private ArrayList<MoveCommand> takeOpponentPieceMoves = new ArrayList<MoveCommand>();
	private int takeOpponentPieceMovePriority;
	
	private ArrayList<MoveCommand> opponentKingInCheckMoves = new ArrayList<MoveCommand>();
	private int opponentKingInCheckMovePriority;
	
	private ArrayList<MoveCommand> opponentInCheckMateMoves = new ArrayList<MoveCommand>();
	private int opponentInCheckMateMovePriority = 1000;
	
	private ArrayList<MoveCommand> canTakePieceAfterwardsMoves = new ArrayList<MoveCommand>();
	private int canTakePieceAfterwardsMovePriority;
	
	private ArrayList<MoveCommand> minimumLossMovesList = new ArrayList<MoveCommand>();
	private int minimumLossMovePriority;
	
	private ArrayList<MoveCommand> everyNoLossMovesList = new ArrayList<MoveCommand>();
	
	private int turn;
	
	/**
	 * This is the constructor for aiPlayer objects. A new aiPlayer is instantiated each
	 * time a decision is made. This is to reinforce the idea that the AI uses greedy
	 * best first search. 
	 * 
	 * @param board
	 */
	public aiPlayer01(ChessBoard board, int turn) {		
		this.turn = turn;
		
		// optional print out info for player black move sets put together
		String aIMoveSets = "";		
		
		// looking for check mate state for player black
		acceptableMoves = board.getPlayerBlack().getGoodMoves(board, "black");
		aIMoveSets += "\nAll acceptable moves for playerBlack: \n" + board.getPlayerBlack().printPossibleMoves(board, "black") + "\n";
		
		noLossMoves = board.getPlayerBlack().getNoLossMoves(board, "black");
		aIMoveSets += "All no potential loss moves for playerBlack: \n" + board.getPlayerBlack().printNoLossMoves(board, "black") + "\n";
		recalculateNoLossMovePriority();
		everyNoLossMovesList = board.getPlayerBlack().getEveryNoLossMoves(board, "black");
		
		opponentInCheckMateMoves = board.getPlayerBlack().getOpponentInCheckMateMoves(board, "black");
		aIMoveSets += "All opponent in check mate moves for playerBlack: \n" + board.getPlayerBlack().printOpponentInCheckMateMoves(board, "black") + "\n";
        // priority will always stay at 1000
		
		takeOpponentPieceMoves = board.getPlayerBlack().getTakeOpponentPieceMoves(board, "black");
		aIMoveSets += "All take opponent piece moves for playerBlack: \n" + board.getPlayerBlack().printTakeOpponentPieceMoves(board, "black") + "\n";
		recalculateTakeOpponentPieceMovePriorirty();
		
		opponentKingInCheckMoves = board.getPlayerBlack().getOpponentKingInCheckMoves(board, "black");
		aIMoveSets += "All opponent king in check moves for playerBlack: \n" + board.getPlayerBlack().printOpponentKingInCheckMoves(board, "black") + "\n";
		recalculateOpponentKingInCheckMovePriority();
		
        canTakePieceAfterwardsMoves = board.getPlayerBlack().getCanTakePieceAfterwardsMoves(board, "black");
        aIMoveSets += "All can take piece afterwards moves for playerBlack: \n" + board.getPlayerBlack().printCanTakePieceAfterwardsMoves(board, "black" + "\n");
        recalculateCanTakePieceAfterwardsMovePriority();
        
        minimumLossMovesList = board.getPlayerBlack().getMinumumLossMoves(board, "black"); // should be sorted by the number of safe points, the sum of safe piece values
        aIMoveSets += "sorted minimumLossMoves: " + minimumLossMovesList.toString() + "\n";
        //recalculateMinimumLossMovePriority(); // not actually needed?...

        System.out.println(aIMoveSets);
		
		//+++++++++++++++++++THIS++IS++WHERE++THE++AI++PICKS++A++MOVE+++++++++++++++++++++++++++
		aiMove = acceptableMoves.get(0); // default move
        
		for (int j = 0; j < acceptableMoves.size(); j++){
			for (int i = 0; i < everyNoLossMovesList.size(); i++){
				if (acceptableMoves.get(j).toString().equals(everyNoLossMovesList.get(i).toString())){
					int temp = 10*everyNoLossMovesList.get(i).getDiffNumSafeMoves();
					acceptableMoves.get(j).addToPriority(temp);
					acceptableMoves.get(j).setDiffNumSafeMovesPriority(temp);
					temp = 2*everyNoLossMovesList.get(i).getNumSafePoints();
		    		acceptableMoves.get(j).addToPriority(temp);
		    		acceptableMoves.get(j).setNumSafePointsPriority(temp);
				}
			}
    	}
	
		for (int j = 0; j < acceptableMoves.size(); j++){
			int temp = 25*(4 - acceptableMoves.get(j).getAvgDistToCenter());
    		acceptableMoves.get(j).addToPriority(temp);
    		acceptableMoves.get(j).setAvgDistToCenterPriority(temp);
    	}
		
	    for (int i = 0; i < opponentInCheckMateMoves.size(); i++){
	    	for (int j = 0; j < acceptableMoves.size(); j++){
	    		if (acceptableMoves.get(j).toString().equals(opponentInCheckMateMoves.get(i).toString())){
	    			int temp = opponentInCheckMateMovePriority;
	    			acceptableMoves.get(j).addToPriority(temp);
	    			acceptableMoves.get(j).setOpponentInCheckMateMovePriority(temp);
	    		}
	    	}
	    }
	    for (int i = 0; i < noLossMoves.size(); i++){
	    	for (int j = 0; j < acceptableMoves.size(); j++){
	    		if (acceptableMoves.get(j).toString().equals(noLossMoves.get(i).toString())){
	    			int temp = noLossMovePriority;
	    			acceptableMoves.get(j).addToPriority(temp);
	    			acceptableMoves.get(j).setNoLossMovePriority(temp);
	    			for (int k = 0; k < canTakePieceAfterwardsMoves.size(); k++){
	    	    		if (canTakePieceAfterwardsMoves.get(k).toString().equals(noLossMoves.get(i).toString())){
	    	    			acceptableMoves.get(j).addToPriority(100*canTakePieceAfterwardsMovePriority);
	    	    		}
	    			}
	    		}
	    	}
	    }
	    for (int i = 0; i < canTakePieceAfterwardsMoves.size(); i++){
	    	for (int j = 0; j < acceptableMoves.size(); j++){
	    		if (acceptableMoves.get(j).toString().equals(canTakePieceAfterwardsMoves.get(i).toString())){
	    			int temp = canTakePieceAfterwardsMovePriority;
	    			acceptableMoves.get(j).addToPriority(temp);
	    			acceptableMoves.get(j).setCanTakePieceAfterwardsMovePriority(temp);
	    		}
	    	}	
	    }
	    for (int i = 0; i < takeOpponentPieceMoves.size(); i++){
	    	System.out.println("random takeOpponentPieceMoves value for takePieceDiff: " + takeOpponentPieceMoves.get(i).getTakePieceDiff());
	    	for (int j = 0; j < acceptableMoves.size(); j++){
	    		if (acceptableMoves.get(j).toString().equals(takeOpponentPieceMoves.get(i).toString())){
	    			boolean found = false;
	    			for (int k = 0; k < noLossMoves.size(); k++){
	    				if (takeOpponentPieceMoves.get(i).equals(noLossMoves.get(k).toString())) {
	    					int temp = takeOpponentPieceMovePriority;
	    					acceptableMoves.get(j).addToPriority(temp);
	    					acceptableMoves.get(j).setTakeOpponentPieceMovePriority(temp);
	    					found = true;
	    				}	    				
	    			}
	    			if (!found){
	    				if (takeOpponentPieceMoves.get(i).getTakePieceDiff() < 0){
	    					int temp = -100*takeOpponentPieceMovePriority;
	    					acceptableMoves.get(j).addToPriority(temp);
	    					acceptableMoves.get(j).setTakeOpponentPieceMovePriority(temp);
	    				}
	    				else if (takeOpponentPieceMoves.get(i).getTakePieceDiff() > 0){
	    					int temp = 10*takeOpponentPieceMovePriority;
	    					acceptableMoves.get(j).addToPriority(temp);
	    					acceptableMoves.get(j).setTakeOpponentPieceMovePriority(temp);
	    				}
	    				else if (turn >= 20){
	    					int temp = 10*takeOpponentPieceMovePriority;
	    					acceptableMoves.get(j).addToPriority(temp);
	    					acceptableMoves.get(j).setTakeOpponentPieceMovePriority(temp);
	    				}
	    			}
	    		}
	    	}
	    }
		for (int n = 0; n < acceptableMoves.size(); n++){
			if (acceptableMoves.get(n).getPriority() > aiMove.getPriority())
				aiMove = acceptableMoves.get(n);
		}
		System.out.println("chosen aiMove: " + aiMove.toString() + ", priority level: " + aiMove.getPriority());
		System.out.println("diffNumSafeMovePriority: " + aiMove.getDiffNumSafeMovesPriority());
		System.out.println("avgDistToCenterMovePriority: " + aiMove.getAvgDistToCenterPriority());
		System.out.println("numSafePointMovePriority: " + aiMove.getGetNumSafePointsPriority());
		System.out.println("noLossMovePriority: " + aiMove.getNoLossMovePriority());
		System.out.println("takeOpponentPieceMovePriority: " + aiMove.getTakeOpponentPieceMovePriority());
		System.out.println("opponentKingInCheckMovePriority: " + aiMove.getOpponentInCheckMateMovePriority());
		System.out.println("canTakePieceAfterwardsMovePriority: " + aiMove.getCanTakePieceAfterwardsMovePriority());
		System.out.println("opponentInCheckmateMovePriority: " + aiMove.getOpponentInCheckMateMovePriority());
		//++++++++++++++++++THE++AI++HAS++PICKED++A++MOVE+++++++++++++++++++++++++++++++++++++++
	}
	
	private void recalculateNoLossMovePriority(){
		noLossMovePriority = 180 - 20*(turn/5); // it's less important to keep pieces safe later in the game
	}
	
	private void recalculateTakeOpponentPieceMovePriorirty(){
		takeOpponentPieceMovePriority = 80 - 20*(turn/20); // taking piece becomes less important later in the game
	}
	
	private void recalculateOpponentKingInCheckMovePriority(){
		opponentKingInCheckMovePriority = 0 + 50*(turn/10);; // it's more important to get the opponent in check later in the game
	}
	
	private void recalculateCanTakePieceAfterwardsMovePriority(){
		canTakePieceAfterwardsMovePriority = 60;
	}
	
//	private void recalculateMinimumLossMovePriority(){
//		minimumLossMovePriority = 0;
//	}
	
	/**
	 * This method returns the move decided on by the AI.
	 * 
	 * @return MoveCommand
	 */
	public MoveCommand getaiMove() {
		return aiMove;
	}
	
	/**
	 * This method returns the info about all of the move sets the AI analyzed.
	 * 
	 * @return String
	 */
	public String getMoveInfo() {
		return moveInfo;
	}
}