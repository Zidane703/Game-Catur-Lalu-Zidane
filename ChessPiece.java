import java.util.ArrayList;

/**
 * This interface establishes the methods implemented in specific chess piece classes.
 * 
 * @author Devan
 */
public interface ChessPiece {
	
	/**
	 * this method generates an ArrayList of valid MoveCommand objects according to the state of
	 * the ChessBoard object passed in as a parameter 
	 * @param board
	 */
	public void generateGoodMoves(ChessBoard board);

	/**
	 * this method generates an ArrayList of MoveCommand objects representing moves in which a 
	 * player will not lose a non-pawn chess piece within the next turn
	 * @param board
	 */
	public void generateNoLossMoves(ChessBoard board);

	/**
	 * this method generates an ArrayList of MoveCommand objects representing moves in which a 
	 * player will take an opponent's chess piece
	 * @param board
	 */
	public void generateTakeOpponentPieceMoves(ChessBoard board);
	
	/**
	 * this method generates an ArrayList of MoveCommand objects representing moves in which a 
	 * player will put the opponent's king in check
	 * @param board
	 */
	public void generateOpponentKingInCheckMoves(ChessBoard board);
	
	/**
	 * this method generates an ArrayList of MoveCommand objects representing moves in which a 
	 * player will put the opponent in check mate
	 * @param board
	 */
	public void generateOpponentInCheckMateMoves(ChessBoard board);
	
	public void generateCanTakePieceAfterwardsMoves(ChessBoard board);
	
	public ArrayList<MoveCommand> getCanTakePieceAfterwardsMoves();
	
	/**
	 * returns MoveCommand ArrayList of moves which result in no potential loss of non-pawn chess pieces
	 * @return
	 */
	public ArrayList<MoveCommand> getNoLossMoves();
	
	/**
	 * returns MoveCommand ArrayList of moves which are valid
	 * @return
	 */
	public ArrayList<MoveCommand> getGoodMoves();
	
	/**
	 * returns MoveCommand ArrayList of moves resulting in a player capturing an opponent's chess piece
	 * @return
	 */
	public ArrayList<MoveCommand> getTakeOpponentPieceMoves();

	/**
	 * returns MoveCommand ArrayList of moves resulting in the opponent's king being in check
	 * @return
	 */
	public ArrayList<MoveCommand> getOpponentKingInCheckMoves();
	
	/**
	 * returns MoveCommand ArrayList of moves resulting in the opponent being in check mate
	 * @return
	 */
	public ArrayList<MoveCommand> getOpponentInCheckMateMoves();
	
	/**
	 * sets the current row index of chess piece
	 * @param startRow
	 */
	public void setStartRow(int startRow);
	
	/**
	 * sets the current column index of chess piece
	 * @param startColumn
	 */
	public void setStartColumn(int startColumn);
	
	/**
	 * returns single character name of chess piece
	 * @return
	 */
	public char getChar();
	
	/**
	 * returns current row index of chess piece
	 * @return
	 */
	public int getStartRow();
	
	/**
	 * returns current column index of chess piece
	 * @return
	 */
	public int getStartColumn();
	
	/**
	 * returns color of chess piece
	 * @return
	 */
	public String getColor();

	/**
	 * filters moves out which could result in a chess piece getting taken by an opponent
	 * @param board
	 * @param playerPieceLocations
	 * @param maybeNoLossMoves
	 * @return
	 */
	public ArrayList<MoveCommand> filteredMaybeNoLossMoves(ChessBoard board, ArrayList<ArrayList<Integer>> playerPieceLocations, ArrayList<MoveCommand> maybeNoLossMoves);

	/**
	 * Returns a deep copy of a ChessPiece object
	 * @return ChessPiece
	 */
	public ChessPiece copyOf();

	public int getPieceValue();
	
}
