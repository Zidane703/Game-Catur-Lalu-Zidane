import java.util.ArrayList;
import java.util.Random;

/**
 * Currently every instantiation of this class would react the same to a given board state. Future updates to this class will
 * use parameters to alter the metrics used by an AI to decide on a move. Additionally, multiple instantiations of this class will
 * play each another in a tournament-style battle for self-improvement. 
 * 
 * @author Devan Karsann
 */
public class aiPlayer00 {

	MoveCommand aiMove;
	String moveInfo = "";
	Random rand = new Random();
	ArrayList<MoveCommand> noLossMoves = new ArrayList<MoveCommand>();
	ArrayList<MoveCommand> takeOpponentPieceMoves = new ArrayList<MoveCommand>();
	ArrayList<MoveCommand> opponentKingInCheckMoves = new ArrayList<MoveCommand>();
	ArrayList<MoveCommand> acceptableMoves = new ArrayList<MoveCommand>();
	ArrayList<MoveCommand> opponentInCheckMateMoves = new ArrayList<MoveCommand>();
	ArrayList<MoveCommand> canTakePieceAfterwardsMoves = new ArrayList<MoveCommand>();
	ArrayList<MoveCommand> minimumLossMovesList = new ArrayList<MoveCommand>();
	
	/**
	 * This is the constructor for aiPlayer objects. A new aiPlayer is instantiated each
	 * time a decision is made. This is to reinforce the idea that the AI uses greedy
	 * best first search. 
	 * 
	 * @param board
	 */
	public aiPlayer00(ChessBoard board) {
		
		// looking for check mate state for player black
		acceptableMoves = board.getPlayerBlack().getGoodMoves(board, "black");
		
		// optional print out info for player black move sets put together
		String aIMoveSets = "";
		aIMoveSets += "\nAll acceptable moves for playerBlack: \n" + board.getPlayerBlack().printPossibleMoves(board, "black") + "\n";

		opponentInCheckMateMoves = board.getPlayerBlack().getOpponentInCheckMateMoves(board, "black");
		aIMoveSets += "All opponent in check mate moves for playerBlack: \n" + board.getPlayerBlack().printOpponentInCheckMateMoves(board, "black") + "\n";
		
		noLossMoves = board.getPlayerBlack().getNoLossMoves(board, "black");
		aIMoveSets += "All no potential loss moves for playerBlack: \n" + board.getPlayerBlack().printNoLossMoves(board, "black") + "\n";
		minimumLossMovesList = board.getPlayerBlack().getMinumumLossMoves(board, "black");
		
		takeOpponentPieceMoves = board.getPlayerBlack().getTakeOpponentPieceMoves(board, "black");
		aIMoveSets += "All take opponent piece moves for playerBlack: \n" + board.getPlayerBlack().printTakeOpponentPieceMoves(board, "black") + "\n";
		
		opponentKingInCheckMoves = board.getPlayerBlack().getOpponentKingInCheckMoves(board, "black");
		aIMoveSets += "All opponent king in check moves for playerBlack: \n" + board.getPlayerBlack().printOpponentKingInCheckMoves(board, "black") + "\n";
		
        canTakePieceAfterwardsMoves = board.getPlayerBlack().getCanTakePieceAfterwardsMoves(board, "black");
        aIMoveSets += "All can take piece afterwards moves for playerBlack: \n" + board.getPlayerBlack().printCanTakePieceAfterwardsMoves(board, "black");
        
		System.out.println(aIMoveSets);
		
		//+++++++++++++++++++THIS++IS++WHERE++THE++AI++PICKS++A++MOVE+++++++++++++++++++++++++++
		// a move is selected from a non-empty intersection of move sets, if all intersections
		// are empty the default move set to use is the list  of all acceptable moves
		aiMove = new MoveCommand();
        
		// new code for simplified decision making
		boolean keepChecking = true;
		while (keepChecking){
			if (acceptableMoves.size() == 1){	
				aiMove = acceptableMoves.get(0);
				keepChecking = false; break;
			}
			if (keepChecking){
				if (opponentInCheckMateMoves.size() > 0){
					aiMove = opponentInCheckMateMoves.get(0);
					for (int m = 0; m < acceptableMoves.size(); m++){
						if (aiMove.toString().equals(acceptableMoves.get(m).toString())){
							System.out.println("opponentInCheckMateMove was used");
							keepChecking = false; break;
						}
					}
					if (keepChecking)
						aiMove = null;
				}
			}
			MoveCommand temp = null;
			if (keepChecking){
				temp = checkFourLists(false, noLossMoves, takeOpponentPieceMoves, canTakePieceAfterwardsMoves, opponentKingInCheckMoves);
				if (temp != null){
					System.out.println("I selected a noLoss, takeOpponentPiece, canTakePieceAfterwards, opponentKingInCheckMove");
					aiMove = temp;
					keepChecking = false; break;
				}
			}
			if (keepChecking){
				temp = checkThreeLists(false, noLossMoves, takeOpponentPieceMoves, canTakePieceAfterwardsMoves);
				if (temp != null){
					System.out.println("I selected a noLoss, takeOpponentPiece, canTakePieceAfterwardsMove");
					aiMove = temp;
					keepChecking = false; break;
				}
			}
			if (keepChecking){
				temp = checkThreeLists(false, noLossMoves, takeOpponentPieceMoves, opponentKingInCheckMoves);
				if (keepChecking && temp != null){
					System.out.println("I selected a noLoss, takeOpponentPiece, opponentKingInCheckMove");
					aiMove = temp;
					keepChecking = false; break;
				}
			}
			if (keepChecking){
				temp = checkTwoLists(false, noLossMoves, takeOpponentPieceMoves);
				if (temp != null){
					System.out.println("I selected a noLoss, takeOpponentPieceMove");
					aiMove = temp;
					keepChecking = false; break;
				}
			}
			if (keepChecking){
				temp = checkThreeLists(false, noLossMoves, canTakePieceAfterwardsMoves, opponentKingInCheckMoves);
				if (temp != null){
					System.out.println("I selected a noLoss, canTakePieceAfterwards, opponentKingInCheckMove");
					aiMove = temp;
					keepChecking = false; break;
				}
			}
			if (keepChecking){
				temp = checkTwoLists(false, noLossMoves, canTakePieceAfterwardsMoves);
				if (temp != null) {
					System.out.println("I selected a noLoss, canTakePieceAfterwardsMove");
					aiMove = temp;
					keepChecking = false; break;
				}
			}
			if (keepChecking){
				temp = checkTwoLists(false, noLossMoves, opponentKingInCheckMoves);
				if (temp != null){
					System.out.println("I selected a noLoss, opponentKingInCheckMove");
					aiMove = temp;
					keepChecking = false; break;
				}
			}
			if (keepChecking){
				System.out.println("noLossMoves: " + noLossMoves.toString());
				temp = checkTwoLists(false, noLossMoves, noLossMoves);
				if (temp != null){
					System.out.println("I selected a noLossMove");
					aiMove = temp;
					keepChecking = false; break;
				}
			}
			if (keepChecking){
				temp = checkFourLists(true, takeOpponentPieceMoves, minimumLossMovesList, canTakePieceAfterwardsMoves, opponentKingInCheckMoves);
				if (temp != null){
					System.out.println("I selected a minimumLoss, takeOpponentPiece, canTakePieceAfterwards, opponentKingInCheckMove");
					aiMove = temp; 
					keepChecking = false; break;
				}
			}
			if (keepChecking){
				temp = checkThreeLists(true, takeOpponentPieceMoves, minimumLossMovesList, canTakePieceAfterwardsMoves);
				if (temp != null){
					System.out.println("I selected a minimumLoss, takeOpponentPiece, canTakePieceAfterwardsMove");
					aiMove = temp; 
					keepChecking = false; break;
				}
			}
			if (keepChecking){
				temp = checkThreeLists(true, takeOpponentPieceMoves, minimumLossMovesList, opponentKingInCheckMoves);
				if (temp != null){
					System.out.println("I selected a minimumLoss, takeOpponentPiece, opponentKingInCheckMove");
					aiMove = temp; 
					keepChecking = false; break;
				}
			}
			if (keepChecking){
				temp = checkTwoLists(true, takeOpponentPieceMoves, minimumLossMovesList);
				if (temp != null){
					System.out.println("I selected a minimumLoss, takeOpponentPieceMove");
					aiMove = temp;
					keepChecking = false; break;
				}
			}
			if (keepChecking){
				temp = checkTwoLists(false, canTakePieceAfterwardsMoves, opponentKingInCheckMoves);
				if (temp != null){
					System.out.println("I selected a canTakePieceAfterwards, opponentKingInCheckMove");
					aiMove = temp; 
					keepChecking = false; break;
				}
			}
			if (keepChecking){
				temp = checkTwoLists(false, minimumLossMovesList, canTakePieceAfterwardsMoves);
				if (temp != null){
					System.out.println("I selected a minimumLoss, canTakePieceAfterwardsMoves");
					aiMove = temp;
					keepChecking = false; break;
				}
			}
			if (keepChecking){
				temp = checkTwoLists(false, minimumLossMovesList, opponentKingInCheckMoves);
				if (temp != null){
					System.out.println("I selected a minimumLoss, opponentKingInCheckMove");
					aiMove = temp;
					keepChecking = false; break;
				}
			}
			if (keepChecking){
				temp = checkTwoLists(false, minimumLossMovesList, acceptableMoves);
				if (temp != null){
					System.out.println("I selected a minimumLoss, acceptableMove");
					aiMove = temp;
					keepChecking = false; break;
				}
			}
			if (keepChecking){
				System.out.println("um... I screwed up somewhere... I guess I'll select an acceptableMove");
				aiMove = acceptableMoves.get(0);
				keepChecking = false; break;
			}
		}
		System.out.println("chosen aiMove: " + aiMove.toString());
		// end of new code
	}
	
	// new check four lists method
		public MoveCommand checkFourLists(boolean checkValueFlag, ArrayList<MoveCommand> list1, ArrayList<MoveCommand> list2, ArrayList<MoveCommand> list3, ArrayList<MoveCommand> list4){
			System.out.println("checkFourLists method was called");
			MoveCommand retval = null;
			boolean leave = false;
			for (int i = 0; i < list1.size(); i++){
				if (leave)
					break;
				for (int j = 0; j < list2.size(); j++){
					if (leave)
						break;
					for (int k = 0; k < list3.size(); k++){
						if (leave)
							break;
						for (int L = 0; L < list4.size(); L++){
							if (list1.get(i).toString().equals(list3.get(j).toString()) && list1.get(i).toString().equals(list3.get(k).toString()) && list1.get(i).toString().equals(list4.get(L).toString())){
								retval = list1.get(i);
								for (int m = 0; m < acceptableMoves.size(); m++){
									if (retval.toString().equals(acceptableMoves.get(m).toString())){
		                                if (checkValueFlag) {
		                                	if (retval.getTakePieceDiff() > 0 || (retval.getTakePieceDiff() == 0 && rand.nextBoolean())){
												System.out.println("checkFourLists found a valid move");
												leave = true;
												break;
									    	}
		                                }
		                                else{
		                                	System.out.println("checkFourLists found a valid move");
											leave = true;
											break;
		                                }
									}
								}
								if (!leave)
									retval = null;
							}
						}
					}
				}
			}
			return retval;
		}
	// end of new method
	
	// new check three lists method
	public MoveCommand checkThreeLists(boolean checkValueFlag, ArrayList<MoveCommand> list1, ArrayList<MoveCommand> list2, ArrayList<MoveCommand> list3){
		System.out.println("checkThreeLists method was called");
		MoveCommand retval = null;
		boolean leave = false;
		for (int i = 0; i < list1.size(); i++){
			if (leave)
				break;
			for (int j = 0; j < list2.size(); j++){
				if (leave)
					break;
				for (int k = 0; k < list3.size(); k++){
					if (list1.get(i).toString().equals(list2.get(j).toString()) && list1.get(i).toString().equals(list3.get(k).toString())){
						retval = list1.get(i);
						for (int m = 0; m < acceptableMoves.size(); m++){
							if (retval.toString().equals(acceptableMoves.get(m).toString())){
								if (checkValueFlag) {
                                	if (retval.getTakePieceDiff() > 0 || (retval.getTakePieceDiff() == 0 && rand.nextBoolean())){
										System.out.println("checkThreeLists found a valid move");
										leave = true;
										break;
							    	}
                                }
                                else{
                                	System.out.println("checkThreeLists found a valid move");
									leave = true;
									break;
                                }
							}
						}
						if (!leave)
							retval = null;
					}
				}
			}
		}
		return retval;
	}
	// end of new method
	
	// new check two lists method
	public MoveCommand checkTwoLists(boolean checkValueFlag, ArrayList<MoveCommand> list1, ArrayList<MoveCommand> list2){
		System.out.println("checkTwoLists method was called");
		MoveCommand retval = null;
		boolean leave = false;
		for (int i = 0; i < list1.size(); i++){
			if (leave)
				break;
			for (int j = 0; j < list2.size(); j++){
				if (list1.get(i).toString().equals(list2.get(j).toString())){
					retval = list1.get(i);
					for (int m = 0; m < acceptableMoves.size(); m++){
						if (retval.toString().equals(acceptableMoves.get(m).toString())){
							if (checkValueFlag) {
                            	if (retval.getTakePieceDiff() > 0 || (retval.getTakePieceDiff() == 0 && rand.nextBoolean())){
									System.out.println("checkTwoLists found a valid move");
									leave = true;
									break;
						    	}
                            }
                            else{
                            	System.out.println("checkTwoLists found a valid move");
								leave = true;
								break;
                            }
						}
					}
					if (!leave)
						retval = null;
				}
			}
		}
		return retval;
	}
	// end of new method
	
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