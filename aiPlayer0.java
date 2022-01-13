import java.util.ArrayList;
import java.util.Random;

/**
 * Currently every instantiation of this class would react the same to a given board state. Future updates to this class will
 * use parameters to alter the metrics used by an AI to decide on a move. Additionally, multiple instantiations of this class will
 * play each another in a tournament-style battle for self-improvement. 
 * 
 * @author Devan Karsann
 */
public class aiPlayer0 {

	MoveCommand aiMove;
	String moveInfo = "";
	Random rand = new Random();
	ArrayList<MoveCommand> noLossMoves = new ArrayList<MoveCommand>();
	ArrayList<MoveCommand> takeOpponentPieceMoves = new ArrayList<MoveCommand>();
	ArrayList<MoveCommand> opponentKingInCheckMoves = new ArrayList<MoveCommand>();
	ArrayList<MoveCommand> acceptableMoves = new ArrayList<MoveCommand>();
	ArrayList<MoveCommand> opponentInCheckMateMoves = new ArrayList<MoveCommand>();
	
	/**
	 * This is the constructor for aiPlayer objects. A new aiPlayer is instantiated each
	 * time a decision is made. This is to reinforce the idea that the AI uses greedy
	 * best first search. 
	 * 
	 * @param board
	 */
	public aiPlayer0(ChessBoard board) {
		
		// looking for check mate state for player black
		acceptableMoves = board.getPlayerBlack().getGoodMoves(board, "black");
		
		// optional print out info for player black move sets put together
		String aIMoveSets = "";
		aIMoveSets += "\nAll acceptable moves for playerBlack: \n" + board.getPlayerBlack().printPossibleMoves(board, "black") + "\n";

		noLossMoves = board.getPlayerBlack().getNoLossMoves(board, "black");
		aIMoveSets += "All no potential loss moves for playerBlack: \n" + board.getPlayerBlack().printNoLossMoves(board, "black") + "\n";

		takeOpponentPieceMoves = board.getPlayerBlack().getTakeOpponentPieceMoves(board, "black");
		aIMoveSets += "All take opponent piece moves for playerBlack: \n" + board.getPlayerBlack().printTakeOpponentPieceMoves(board, "black") + "\n";
		
		opponentKingInCheckMoves = board.getPlayerBlack().getOpponentKingInCheckMoves(board, "black");
		aIMoveSets += "All opponent king in check moves for playerBlack: \n" + board.getPlayerBlack().printOpponentKingInCheckMoves(board, "black") + "\n";
		
		aIMoveSets += "All opponent in check mate moves for playerBlack: \n" + board.getPlayerBlack().printOpponentInCheckMateMoves(board, "black");
        opponentInCheckMateMoves = board.getPlayerBlack().getOpponentInCheckMateMoves(board, "black");
		
		System.out.println(aIMoveSets);
		
		//+++++++++++++++++++THIS++IS++WHERE++THE++AI++PICKS++A++MOVE+++++++++++++++++++++++++++
		// a move is selected from a non-empty intersection of move sets, if all intersections
		// are empty the default move set to use is the list  of all acceptable moves
		aiMove = new MoveCommand();
		if (acceptableMoves.size() == 1)
			aiMove = acceptableMoves.get(0);
		if (aiMove.toString().length() == 0) {			
			if (opponentInCheckMateMoves.size() > 0)
				aiMove = opponentInCheckMateMoves.get(0);
		}
        //priority 1
		if (aiMove.toString().length() == 0) {
			if (noLossMoves.size() > 0 && takeOpponentPieceMoves.size() > 0 && opponentKingInCheckMoves.size() > 0) {
				ArrayList<MoveCommand> noLossTakePieceOpponentKingInCheckMoves = new ArrayList<MoveCommand>(); 
				for (int i = 0; i < noLossMoves.size(); i++) {
					for (int j = 0; j < takeOpponentPieceMoves.size(); j++) {
						for (int k = 0; k < opponentKingInCheckMoves.size(); k++) {
							if (takeOpponentPieceMoves.get(j).toString().equals(noLossMoves.get(i).toString()) && takeOpponentPieceMoves.get(j).toString().equals(opponentKingInCheckMoves.get(k).toString())) {
								for (int x = 0; x < acceptableMoves.size(); x++) {
									if (acceptableMoves.get(x).toString().equals(noLossMoves.get(i).toString())) {
										noLossTakePieceOpponentKingInCheckMoves.add(acceptableMoves.get(x));
									}
								}
							}
						}
					}
				}
				if (noLossTakePieceOpponentKingInCheckMoves.size() > 0) {
					aiMove = returnGreatest(noLossTakePieceOpponentKingInCheckMoves);  
					moveInfo = ("\nplayerBlack selected a noLossTakePieceOpponentKingInCheck move");
				}
			}
		}
		//priority 2
		if (aiMove.toString().length() == 0) {
			if (noLossMoves.size() > 0 && takeOpponentPieceMoves.size() > 0) {
				ArrayList<MoveCommand> takePieceOpponentKingInCheckMoves = new ArrayList<MoveCommand>(); 
				for (int i = 0; i < noLossMoves.size(); i++) {
					for (int j = 0; j < takeOpponentPieceMoves.size(); j++) {
						if (takeOpponentPieceMoves.get(j).toString().equals(noLossMoves.get(i).toString())) {
							for (int k = 0; k < acceptableMoves.size(); k++) {
								if (acceptableMoves.get(k).toString().equals(noLossMoves.get(i).toString())) {
									takePieceOpponentKingInCheckMoves.add(acceptableMoves.get(k));
								}
							}
						}
					}
				}
				if (takePieceOpponentKingInCheckMoves.size() > 0) {
					aiMove = returnGreatest(takePieceOpponentKingInCheckMoves);  
					moveInfo = ("\nplayerBlack selected a noLossTakePiece move");
				}
			}
		}
		//priority 3
		if (aiMove.toString().length() == 0) {
			if (noLossMoves.size() > 0 && opponentKingInCheckMoves.size() > 0) {
				ArrayList<MoveCommand> noLossOpponentKingInCheckMoves = new ArrayList<MoveCommand>(); 
				for (int i = 0; i < noLossMoves.size(); i++) {
					for (int j = 0; j < opponentKingInCheckMoves.size(); j++) {
						if (opponentKingInCheckMoves.get(j).toString().equals(noLossMoves.get(i).toString())) {
							for (int k = 0; k < acceptableMoves.size(); k++) {
								if (acceptableMoves.get(k).toString().equals(noLossMoves.get(i).toString())) {
									noLossOpponentKingInCheckMoves.add(acceptableMoves.get(k));
								}
							}
						}
					}
				}
				if (noLossOpponentKingInCheckMoves.size() > 0) {
					aiMove = returnGreatest(noLossOpponentKingInCheckMoves);  
					moveInfo = ("\nplayerBlack selected a noLossOpponentKingInCheck move12");
				}
			}
		}
		//priority 4
		if (aiMove.toString().length() == 0) {
			if (noLossMoves.size() > 0) {
				ArrayList<MoveCommand> acceptableNoLossMoves = new ArrayList<MoveCommand>();
				for (int i = 0; i < noLossMoves.size(); i++) {
					for (int j = 0; j < acceptableMoves.size(); j++) {
						if (noLossMoves.get(i).toString().equals(acceptableMoves.get(j).toString()))
							acceptableNoLossMoves.add(acceptableMoves.get(j));
					}
				}
				if (acceptableNoLossMoves.size() > 0) {
					aiMove = returnGreatest(acceptableNoLossMoves);  
					moveInfo = ("\nplayerBlack selected a noLoss move");
				}
			}
		}
		//priority 5
		if (aiMove.toString().length() == 0) {
			if (opponentKingInCheckMoves.size() > 0 && takeOpponentPieceMoves.size() > 0) {
				ArrayList<MoveCommand> takePieceOpponentKingInCheckMoves = new ArrayList<MoveCommand>(); 
				for (int i = 0; i < opponentKingInCheckMoves.size(); i++) {
					for (int j = 0; j < takeOpponentPieceMoves.size(); j++) {
						if (takeOpponentPieceMoves.get(j).toString().equals(opponentKingInCheckMoves.get(i).toString())) {
							for (int k = 0; k < acceptableMoves.size(); k++) {
								if (acceptableMoves.get(k).toString().equals(opponentKingInCheckMoves.get(i).toString())) {
									takePieceOpponentKingInCheckMoves.add(acceptableMoves.get(k));
								}
							}
						}
					}
				}
				if (takePieceOpponentKingInCheckMoves.size() > 0) {
					aiMove = returnGreatest(takePieceOpponentKingInCheckMoves);  
					moveInfo = ("\nplayerBlack selected a takePieceOpponentKingInCheck move");
				}
			}
		}			
		//priority 6
		if (aiMove.toString().length() == 0) {
			if (takeOpponentPieceMoves.size() > 0) {
				ArrayList<MoveCommand> acceptableTakeOpponentPieceMoves = new ArrayList<MoveCommand>();
				for (int i = 0; i < takeOpponentPieceMoves.size(); i++) {
					for (int j = 0; j < acceptableMoves.size(); j++) {
						if (takeOpponentPieceMoves.get(i).toString().equals(acceptableMoves.get(j).toString()))
							acceptableTakeOpponentPieceMoves.add(acceptableMoves.get(j));
					}
				}
				if (acceptableTakeOpponentPieceMoves.size() > 0) {
					aiMove = returnGreatest(acceptableTakeOpponentPieceMoves);  
					moveInfo = ("\nplayerBlack selected a takeOpponentPiece move");
				}
			}
		}			
		//priority 7
		if (aiMove.toString().length() == 0) {
			if (opponentKingInCheckMoves.size() > 0) {
				ArrayList<MoveCommand> acceptableOpponentKingInCheckMoves = new ArrayList<MoveCommand>();
				for (int i = 0; i < opponentKingInCheckMoves.size(); i++) {
					for (int j = 0; j < acceptableMoves.size(); j++) {
						if (opponentKingInCheckMoves.get(i).toString().equals(acceptableMoves.get(j).toString()))
							acceptableOpponentKingInCheckMoves.add(acceptableMoves.get(j));
					}
				}
				if (acceptableOpponentKingInCheckMoves.size() > 0) {
					aiMove = returnGreatest(acceptableOpponentKingInCheckMoves);  
					moveInfo = ("\nplayerBlack selected an opponentKingInCheck move");
				}
			}
		}
		//priority 8
		if (aiMove.toString().length() == 0) {
			int x = rand.nextInt(acceptableMoves.size());
			aiMove = acceptableMoves.get(x);
			moveInfo = ("\nplayerBlack selected an acceptable move");
		}
		//+++++++++++++++THE++AI++HAS++CHOSEN++A++MOVE++AT++THIS++POINT+++++++++++++++++++++++++++
	}
	
	/**
	 * This method decides if a random move should be selected or if the MoveCommand with the smallest 
	 * value should be selected. This is to prevent the AI from having the same behavior all the time
	 * @param input
	 * @return retVal
	 */
	public static MoveCommand returnGreatest(ArrayList<MoveCommand> input){
		Random rand = new Random();
		MoveCommand retVal = input.get(rand.nextInt(input.size()));
		if (rand.nextBoolean()) {
			if (input.size() > 1) {
				for (int i = 1; i < input.size(); i++) {
					if (input.get(i).getAvgDistToCenter() < retVal.getAvgDistToCenter())
						retVal = input.get(i);
				}
			}
		}
		return retVal;
	}
	
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