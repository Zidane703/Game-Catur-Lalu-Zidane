
import java.util.ArrayList;

/**
 * Blank/empty spots in ChessBoard objects are represented by Hyphen objects.
 * 
 * @author Devan
 */
public class Hyphen implements ChessPiece{
	
	private char name = '-';
	
	Hyphen () { }

	@Override
	public ChessPiece copyOf() {return new Hyphen(); }
	
	@Override
	public void generateGoodMoves(ChessBoard board) { System.out.println("generateGoodMoves was called on a hyphen"); System.exit(0); }

	@Override
	public void generateTakeOpponentPieceMoves(ChessBoard board) {	System.out.println("generateTakeOpponentPieceMoves was called on a hyphen"); System.exit(0); }

	@Override
	public void generateNoLossMoves(ChessBoard board) { System.out.println("generateNoLossMoves was called on a hyphen"); System.exit(0); }
	
	@Override
	public void generateOpponentInCheckMateMoves(ChessBoard board) { System.out.println("generateOpponentInCheckMateMoves was called on a hyphen"); System.exit(0); }

	@Override
	public ArrayList getGoodMoves() { return null; }

	@Override
	public ArrayList<MoveCommand> getNoLossMoves() { return null; }
	
	@Override
	public ArrayList<MoveCommand> getTakeOpponentPieceMoves() {	return null; }

	@Override
	public ArrayList<MoveCommand> getOpponentInCheckMateMoves() { return null; }
	
	@Override
	public String getColor() { return "grey"; }

	@Override
	public char getChar() {	return name; }

	@Override
	public int getStartRow() { return -1; }

	@Override
	public void setStartRow(int startRow) { }

	@Override
	public int getStartColumn() { return -1; }

	@Override
	public void setStartColumn(int startColumn) { }

	@Override
	public ArrayList<MoveCommand> filteredMaybeNoLossMoves(ChessBoard board, ArrayList<ArrayList<Integer>> playerPieceLocations, ArrayList<MoveCommand> maybeNoLossMoves) {	return null; }

	@Override
	public void generateOpponentKingInCheckMoves(ChessBoard board) { System.out.println("generateOpponentKingInCheckMoves was called on a hyphen"); System.exit(0); }

	@Override
	public ArrayList<MoveCommand> getOpponentKingInCheckMoves() { return null; }

	@Override
	public void generateCanTakePieceAfterwardsMoves(ChessBoard board) {	}

	@Override
	public ArrayList<MoveCommand> getCanTakePieceAfterwardsMoves() { return null; }

	@Override
	public int getPieceValue() { return 0; }

}