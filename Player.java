

import java.util.ArrayList;

/**
 * Each ChessBoard object contains two Players objects.
 * 
 * @author Devan
 */
public class Player {
	
	private String color; // the default colors used for white for the user and black for the AI
	
	private boolean moveIsValid;  // keeps track of the validity of the chosen move by a player
	// resets after a valid move is executed

	private int numGamesWon = 0;  // this variable will be used when multiple AI vs. AI games are played

	// these variables are used as a quick reference for the location of a player's king
	private int kingRow = -1;
	private int kingColumn = -1;
	private int tempKingRow;
	private int tempKingColumn;

	private boolean canDoKSC = true; // KSC: king side castle
	private boolean canDoQSC = true; // QSC: queen side castle

	/**
	 * This is the default Player object constructor.
	 * @param color
	 */
	Player (String color) {	this.color = color;	}
	
	/**
	 * This constructor method is used when copies of a ChessBoard object are made.
	 * Shallow copies are avoided by making a deep copy (copying values instead of
	 * references to values).
	 * 
	 * @param player
	 * @return Player
	 */
	public Player copyOf() {
		Player copy = new Player(color);
		copy.setCanDoKSC(canDoKSC);
		copy.setCanDoQSC(canDoQSC);
		copy.setKingColumn(kingColumn);
		copy.setKingRow(kingRow);
		copy.setNumGamesWon(numGamesWon);
		copy.setMoveIsValid(moveIsValid);
		copy.setTempKingRow(tempKingRow);
		copy.setTempKingColumn(tempKingColumn);
		return copy;
	}
	
	/**
	 * This method combines all of the good moves for a player into a single list
	 * @param color
	 * @return ArrayList<MoveCommand>
	 */
	public ArrayList<MoveCommand> getGoodMoves(ChessBoard board, String color) {
		ArrayList<MoveCommand> temp = new ArrayList<MoveCommand>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.get(i, j).getColor().equals(color)) {
					ChessPiece tempPiece = board.get(i, j);
					tempPiece.generateGoodMoves(board);
					ArrayList<MoveCommand> goodMoves = tempPiece.getGoodMoves();
					for (int k = 0; k < goodMoves.size(); k++)
						temp.add(goodMoves.get(k));
				}
			}
		}
		for (int j = 0; j < temp.size(); j++) {
			for (int i = 0; i < temp.size() - 1; i++){
				if (temp.get(i).getAvgDistToCenter() > temp.get(i+1).getAvgDistToCenter()){
					MoveCommand tempMove = temp.get(i);
					temp.remove(i);
					temp.add(tempMove);
				}
			}
		}
		//System.out.println("sorted goodMoves: " + temp.toString());
		return temp;
	}
	
	/**
	 * This method combines all of the takeOpponentPieceMoves ArrayLists for a player into a single list
	 * @param color
	 * @return ArrayList<MoveCommand>
	 */
	public ArrayList<MoveCommand> getTakeOpponentPieceMoves(ChessBoard board, String color) {
		ArrayList<MoveCommand> temp = new ArrayList<MoveCommand>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.get(i, j).getColor().equals(color)) {
					ChessPiece tempPiece = board.get(i, j);
					tempPiece.generateTakeOpponentPieceMoves(board);
					ArrayList<MoveCommand> takeOpponentPieceMoves = tempPiece.getTakeOpponentPieceMoves();
					for (int k = 0; k < takeOpponentPieceMoves.size(); k++)
						temp.add(takeOpponentPieceMoves.get(k));
				}
			}
		}
		for (int j = 0; j < temp.size(); j++) {
			for (int i = 0; i < temp.size() - 1; i++){
				if (temp.get(i).getTakePieceDiff() < temp.get(i+1).getTakePieceDiff()){
					MoveCommand tempMove = temp.get(i);
					temp.remove(i);
					temp.add(tempMove);
				}
			}
		}
		System.out.println("sorted takeOpponentPieceMoves: " + temp.toString());
		return temp;
	}

	/**
	 * This method combines all of the noLossMoves ArrayLists for a player into a single list
	 * @param color
	 * @return ArrayList<MoveCommand>
	 */
	public ArrayList<MoveCommand> getEveryNoLossMoves(ChessBoard board, String color) {
		ArrayList<MoveCommand> temp = new ArrayList<MoveCommand>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.get(i, j).getColor().equals(color)) {
					ChessPiece tempPiece = board.get(i, j);
					tempPiece.generateNoLossMoves(board);
					ArrayList<MoveCommand> noLossMoves = tempPiece.getNoLossMoves();
					for (int k = 0; k < noLossMoves.size(); k++){
						temp.add(noLossMoves.get(k));
					}
				}
			}
		}
		return temp;
	}
	
	/**
	 * This method combines all of the noLossMoves ArrayLists for a player into a single list
	 * @param color
	 * @return ArrayList<MoveCommand>
	 */
	public ArrayList<MoveCommand> getNoLossMoves(ChessBoard board, String color) {
		ArrayList<MoveCommand> temp = new ArrayList<MoveCommand>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.get(i, j).getColor().equals(color)) {
					ChessPiece tempPiece = board.get(i, j);
					tempPiece.generateNoLossMoves(board);
					ArrayList<MoveCommand> noLossMoves = tempPiece.getNoLossMoves();
					for (int k = 0; k < noLossMoves.size(); k++){
						if (noLossMoves.get(k).getActuallySafe()){
							temp.add(noLossMoves.get(k));
						}
					}
				}
			}
		}
		for (int j = 0; j < temp.size(); j++) {
			for (int i = 0; i < temp.size() - 1; i++){
				if (temp.get(i).getDiffNumSafeMoves() < temp.get(i+1).getDiffNumSafeMoves()){
					MoveCommand tempMove = temp.get(i);
					temp.remove(i);
					temp.add(tempMove);
				}
			}
		}
		return temp;
	}
	
	/**
	 * This method combines all of the noLossMoves ArrayLists for a player into a single list
	 * @param color
	 * @return ArrayList<MoveCommand>
	 */
	public ArrayList<MoveCommand> getMinumumLossMoves(ChessBoard board, String color) {
		ArrayList<MoveCommand> temp = new ArrayList<MoveCommand>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.get(i, j).getColor().equals(color)) {
					ChessPiece tempPiece = board.get(i, j);
					tempPiece.generateNoLossMoves(board);
					ArrayList<MoveCommand> noLossMoves = tempPiece.getNoLossMoves();
					for (int k = 0; k < noLossMoves.size(); k++)
						temp.add(noLossMoves.get(k));
				}
			}
		}
		for (int j = 0; j < temp.size(); j++) {
			for (int i = 0; i < temp.size() - 1; i++){
				if (temp.get(i).getNumSafePoints() < temp.get(i+1).getNumSafePoints()){
					MoveCommand tempMove = temp.get(i);
					temp.remove(i);
					temp.add(tempMove);
				}
			}
		}
		return temp;
	}
	
	/**
	 * This method combines all of the opponentKingInCheckMoves for a player into a single list
	 * @param color
	 * @return ArrayList<MoveCommand>
	 */
	public ArrayList<MoveCommand> getOpponentKingInCheckMoves(ChessBoard board, String color) {
		ArrayList<MoveCommand> temp = new ArrayList<MoveCommand>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.get(i, j).getColor().equals(color)) {
					ChessPiece tempPiece = board.get(i, j);
					tempPiece.generateOpponentKingInCheckMoves(board);
					ArrayList<MoveCommand> opponentKingInCheck = tempPiece.getOpponentKingInCheckMoves();
					for (int k = 0; k < opponentKingInCheck.size(); k++)
						temp.add(opponentKingInCheck.get(k));
				}
			}
		}
		return temp;
	}

	/**
	 * This method combines all of the opponentInCheckMateMoves for a player into a single list
	 * @param color
	 * @return ArrayList<MoveCommand>
	 */
	public ArrayList<MoveCommand> getOpponentInCheckMateMoves(ChessBoard board, String color) {
		ArrayList<MoveCommand> temp = new ArrayList<MoveCommand>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.get(i, j).getColor().equals(color)) {
					ChessPiece tempPiece = board.get(i, j);
					tempPiece.generateOpponentInCheckMateMoves(board);
					ArrayList<MoveCommand> opponentInCheckMate = tempPiece.getOpponentInCheckMateMoves();
					for (int k = 0; k < opponentInCheckMate.size(); k++)
						temp.add(opponentInCheckMate.get(k));
				}
			}
		}
		return temp;
	}
	
	/**
	 * This method combines all of the opponentInCheckMateMoves for a player into a single list
	 * @param color
	 * @return ArrayList<MoveCommand>
	 */
	public ArrayList<MoveCommand> getCanTakePieceAfterwardsMoves(ChessBoard board, String color) {
		ArrayList<MoveCommand> temp = new ArrayList<MoveCommand>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.get(i, j).getColor().equals(color)) {
					ChessPiece tempPiece = board.get(i, j);
					tempPiece.generateCanTakePieceAfterwardsMoves(board);
					ArrayList<MoveCommand> canTakePieceAfterwardsMoves = tempPiece.getCanTakePieceAfterwardsMoves();
					for (int k = 0; k < canTakePieceAfterwardsMoves.size(); k++)
						temp.add(canTakePieceAfterwardsMoves.get(k));
				}
			}
		}
		return temp;
	}
	
	public String printCanTakePieceAfterwardsMoves(ChessBoard board, String color) {
		String str = "";
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				ChessPiece temp = board.get(i, j);
				if (temp.getColor().equals(color)) {
					temp.generateCanTakePieceAfterwardsMoves(board);
					str += temp.getChar() + " at column " + (char)('A' + j) + " row " + (8 - i) + ": " + temp.getCanTakePieceAfterwardsMoves().toString() + "\n";
				}
			}
		}
		return str;
	}
	
	/**
	 * This method returns a string of all of the takeOpponentPieceMoves for a player
	 * @param color
	 * @return String
	 */	
	public String printTakeOpponentPieceMoves(ChessBoard board, String color) {
		String str = "";
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				ChessPiece temp = board.get(i, j);
				if (temp.getColor().equals(color)) {
					temp.generateTakeOpponentPieceMoves(board);
					str += temp.getChar() + " at column " + (char)('A' + j) + " row " + (8 - i) + ": " + temp.getTakeOpponentPieceMoves().toString() + "\n";
				}
			}
		}
		return str;
	}

	/**
	 * This method returns a string of all of the possible moves for player
	 * @param color
	 * @return String
	 */
	public String printPossibleMoves(ChessBoard board, String color) {
		String str = "";

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				ChessPiece temp = board.get(i, j);
				if (temp.getColor().equals(color)) {
					temp.generateGoodMoves(board);
					str += temp.getChar() + " at column " + (char)('A' + j) + " row " + (8 - i) + ": " + temp.getGoodMoves().toString() + "\n";
				}
			}
		}

		return str;
	}

	/**
	 * This method returns a string of all of the no loss moves for player
	 * @param color
	 * @return String
	 */	
	public String printNoLossMoves(ChessBoard board, String color) {
		String str = "";

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				ChessPiece temp = board.get(i, j);
				if (temp.getColor().equals(color)) {
					temp.generateNoLossMoves(board);
					str += temp.getChar() + " at column " + (char)('A' + j) + " row " + (8 - i) + ": " + temp.getNoLossMoves().toString() + "\n";
				}
			}
		}

		return str;
	}

	/**
	 * This method returns a string of all of the moves which result in the opponent king being in check
	 * @param color
	 * @return String
	 */	
	public String printOpponentKingInCheckMoves(ChessBoard board, String color) {
		String str = "";

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				ChessPiece temp = board.get(i, j);
				if (temp.getColor().equals(color)) {
					temp.generateOpponentKingInCheckMoves(board);
					str += temp.getChar() + " at column " + (char)('A' + j) + " row " + (8 - i) + ": " + temp.getOpponentKingInCheckMoves().toString() + "\n";
				}
			}
		}

		return str;
	}
	
	/**
	 * This method returns a string of all of the moves which result in the opponent being in checkmate
	 * @param color
	 * @return String
	 */	
	public String printOpponentInCheckMateMoves(ChessBoard board, String color) {
		String str = "";

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				ChessPiece temp = board.get(i, j);
				if (temp.getColor().equals(color)) {
					temp.generateOpponentInCheckMateMoves(board);
					str += temp.getChar() + " at column " + (char)('A' + j) + " row " + (8 - i) + ": " + temp.getOpponentInCheckMateMoves().toString() + "\n";
				}
			}
		}

		return str;
	}
	
	/**
	 * returns temporary row index of the player's king
	 * @return int
	 */
	public int getTempKingRow() { return tempKingRow; }

	/**
	 * sets temporary row index of the player's king
	 * @param tempKingRow
	 */
	public void setTempKingRow(int tempKingRow) { this.tempKingRow = tempKingRow; }

	/**
	 * returns temporary column index of the player's king
	 * @return int
	 */
	public int getTempKingColumn() { return tempKingColumn; }

	/**
	 * sets the column index of the player's king 
	 * @param tempKingColumn
	 */
	public void setTempKingColumn(int tempKingColumn) {	this.tempKingColumn = tempKingColumn; }	

	/**
	 * returns the color of the player
	 * @return String
	 */
	public String getColor() { return color; }

	/**
	 * sets if the player can do a king side castle
	 * @return boolean
	 */
	public boolean canDoKSC() {	return canDoKSC; }

	/**
	 * sets if the player can do a king side castle
	 * @param canDoKSC
	 */
	public void setCanDoKSC(boolean canDoKSC) {	this.canDoKSC = canDoKSC; }

	/**
	 * returns if the player can do a queen side castle
	 * @return boolean
	 */
	public boolean canDoQSC() {	return canDoQSC; }

	/**
	 * sets if the player can do a queen side castle
	 * @param canDoQSC
	 */
	public void setCanDoQSC(boolean canDoQSC) {	this.canDoQSC = canDoQSC; }

	/**
	 * returns the number of games won by a player
	 * @return int
	 */
	public int getNumGamesWon() { return numGamesWon; }

	/**
	 * sets number of games won by a player
	 */
	public void setNumGamesWon(int numWon) { numGamesWon = numWon; }

	/**
	 * increments number of games won by a player
	 */
	public void addToNumGamesWon() { numGamesWon++; }

	/**
	 * returns the row index of the player's king
	 * @return int
	 */
	public int getKingRow() { return kingRow; }

	/**
	 * sets the row index of the player's king
	 * @param kingRow
	 */
	public void setKingRow(int kingRow) { this.kingRow = kingRow; }

	/**
	 * returns the column index of the player's king
	 * @return int
	 */
	public int getKingColumn() { return kingColumn;	}

	/**
	 * sets the column index of a player's king
	 * @param kingColumn
	 */
	public void setKingColumn(int kingColumn) {	this.kingColumn = kingColumn; }

	/**
	 * return validity of the MoveCommand currently stored by the player
	 * @return boolean
	 */
	public boolean getMoveIsValid() { return moveIsValid; }

	/**
	 * sets validity of MoveCommand currently stored by the player
	 * @param moveIsValid
	 */
	public void setMoveIsValid(boolean moveIsValid) { this.moveIsValid = moveIsValid; }

}