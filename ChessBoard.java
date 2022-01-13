import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This ChessBoard class has two ChessBoard object constructors and several methods 
 * used to facilitate game play.
 * 
 * @author Devan
 */
public class ChessBoard {
	
	private ChessPiece[][] board;
	
	// three variables are used to keep track of en Passent pawn capturing states
	private static boolean pawnWasMovedTwoSpaces = false;
	private static int enPassentRow;
	private static int enPassentColumn;
	
	private Player playerWhite;
	private Player playerBlack;
	private char tileTaken;
	
	/**
	 * This ChessBoard object constructor is used to read in a chess board from a text file. 
	 * @param playerWhite
	 * @param playerBlack
	 * @param fileName
	 */
	ChessBoard (Player playerWhite, Player playerBlack, String fileName) {
		
		this.playerWhite = playerWhite;
		this.playerBlack = playerBlack;
		
		ChessPiece[][] temp = new ChessPiece[8][8];
		Scanner scan = null;
		File file = new File(fileName);
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
			System.out.println("usage: <fileName> <outputMode>");
			System.out.println("info: name of text file with chess board configuration,\n      output mode in console (0, 1, or 2)");
			System.exit(0);
		}
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				String inputString = scan.next();
				if (inputString.length() > 1) {
					System.out.println("invalid input 1");
					System.exit(0);
				}
				char inputChar = inputString.charAt(0);
				// if it equals a hyphen
				if (inputChar == 45) {
					temp[i][j] = new Hyphen();
				}
				// inputChar is a white piece
				else if (inputChar > 65 && inputChar < 90) {
					// if inputChar is a rook
					if (inputChar == 82)
						temp[i][j] = new Rook("white", i, j);
					// if inputChar is a knight
					if (inputChar == 78)
						temp[i][j] = new Knight("white", i, j);
					// if inputChar is a bishop
					if (inputChar == 66)
						temp[i][j] = new Bishop("white", i, j);
					// if inputChar is a queen
					if (inputChar == 81)
						temp[i][j] = new Queen("white", i, j);
					// if inputChar is a king
					if (inputChar == 75) {
						temp[i][j] = new King("white", i, j);
						playerWhite.setKingRow(i); // location of king is set for playerWhite
						playerWhite.setKingColumn(j);
					}
					// if inputChar is a pawn
					if (inputChar == 80)
						temp[i][j] = new Pawn("white", i, j);
				}
				// inputChar is a black piece
				else if (inputChar > 97 && inputChar < 122) {
					// if inputChar is a rook
					if (inputChar == 114)
						temp[i][j] = new Rook("black", i, j);
					// if inputChar is a knight
					if (inputChar == 110)
						temp[i][j] = new Knight("black", i, j);
					// if inputChar is a bishop
					if (inputChar == 98)
						temp[i][j] = new Bishop("black", i, j);
					// if inputChar is a queen
					if (inputChar == 113)
						temp[i][j] = new Queen("black", i, j);
					// if inputChar is a king
					if (inputChar == 107) {
						temp[i][j] = new King("black", i, j);
						playerBlack.setKingRow(i); // location of king is set for playerBlack
						playerBlack.setKingColumn(j);
					}
					// if inputChar is a pawn
					if (inputChar == 112)
						temp[i][j] = new Pawn("black", i, j);
				}
				// invalid input
				else {
					System.out.println("invalid input 2");
					System.exit(0);
				}
			}
		}
		board = temp;
		if (get(7,0).getChar() != 'R')
			playerWhite.setCanDoQSC(false);
		if (get(7,7).getChar() != 'R')
			playerWhite.setCanDoKSC(false);
		if (get(0,0).getChar() != 'r')
			playerBlack.setCanDoQSC(false);
		if (get(0,7).getChar() != 'r')
			playerBlack.setCanDoKSC(false);
	}
	
	/**
	 * This is the default ChessBoard object used when no input file is entered as a command line arg.
	 * @param playerWhite
	 * @param playerBlack
	 */
	ChessBoard (Player playerWhite, Player playerBlack) {

		this.playerWhite = playerWhite;
		this.playerBlack = playerBlack;

		// row 0 (displayed as 8 on chess board)
		ChessPiece[][] temp = {{new Rook("black", 0, 0), new Knight("black", 0, 1), new Bishop("black", 0, 2), new Queen("black", 0, 3), 
			new King("black", 0, 4), new Bishop("black", 0, 5), new Knight("black", 0, 6), new Rook("black", 0, 7)},
				// row 1 (displayed as 7 on chess board)
				{new Pawn("black", 1, 0), new Pawn("black", 1, 1), new Pawn("black", 1, 2), new Pawn("black", 1, 3), 
				new Pawn("black", 1, 4), new Pawn("black", 1, 5), new Pawn("black", 1, 6), new Pawn("black", 1, 7)},
				// row 2 (displayed as 6 on chess board)
				{new Hyphen(), new Hyphen(), new Hyphen(), new Hyphen(), new Hyphen(), new Hyphen(), new Hyphen(), new Hyphen()},
				// row 3 (displayed as 5 on chess board)
				{new Hyphen(), new Hyphen(), new Hyphen(), new Hyphen(), new Hyphen(), new Hyphen(), new Hyphen(), new Hyphen()},
				// row 4 (displayed as 4 on chess board)
				{new Hyphen(), new Hyphen(), new Hyphen(), new Hyphen(), new Hyphen(), new Hyphen(), new Hyphen(), new Hyphen()},
				// row 5 (displayed as 3 on chess board)
				{new Hyphen(), new Hyphen(), new Hyphen(), new Hyphen(), new Hyphen(), new Hyphen(), new Hyphen(), new Hyphen()},
				// row 6 (displayed as 2 on chess board)
				{new Pawn("white", 6, 0), new Pawn("white", 6, 1), new Pawn("white", 6, 2), new Pawn("white", 6, 3), 
					new Pawn("white", 6, 4), new Pawn("white", 6, 5), new Pawn("white", 6, 6), new Pawn("white", 6, 7)},
				// row 7 (displayed as 1 on chess board)
				{new Rook("white", 7, 0), new Knight("white", 7, 1), new Bishop("white", 7, 2), new Queen("white", 7, 3), 
						new King("white", 7, 4), new Bishop("white", 7, 5), new Knight("white", 7, 6), new Rook("white", 7, 7)}};

		playerWhite.setKingRow(7); // location of king is set for playerWhite0
		playerWhite.setKingColumn(4);
		playerBlack.setKingRow(0); // location of king is set for playerBlack
		playerBlack.setKingColumn(4);

		board = temp;
	}	
	
	/**
	 * This method returns a copy of a ChessBoard object
	 * @return ChessBoard
	 */
	public ChessBoard copyOf() {
		ChessPiece[][] chessPieceCopies = new ChessPiece[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				chessPieceCopies[i][j] = board[i][j].copyOf();
			}
		}
		return new ChessBoard(chessPieceCopies, playerWhite.copyOf(), playerBlack.copyOf(), pawnWasMovedTwoSpaces, enPassentRow, enPassentColumn);
	}
	
	/**
	 * This method is an alternate constructor for ChessBoard objects
	 * @param temp
	 * @param playerWhite
	 * @param playerBlack
	 */
	ChessBoard (ChessPiece[][] temp, Player playerWhite, Player playerBlack, boolean pawnWasMovedTwoSpaces, int enPassentRow, int enPassentColumn) {
		this.playerWhite = playerWhite;
		this.playerBlack = playerBlack;	
		board = temp;
	}
	
	/**
	 * This method executes the specified move passed as an argument.
	 * 
	 * @param copyBoard
	 * @param startRow
	 * @param startColumn
	 * @param endRow
	 * @param endColumn
	 * @return
	 */
	public boolean executeMove(MoveCommand playerMove) {
		
		int startRow = playerMove.getStartRow();
		int startColumn = playerMove.getStartColumn();
		int endRow = playerMove.getEndRow();
		int endColumn = playerMove.getEndColumn();
		ChessPiece pieceToMove = get(startRow, startColumn);
		setBoard(startRow, startColumn, new Hyphen());
		tileTaken = get(endRow, endColumn).getChar();
		setBoard(endRow, endColumn, pieceToMove);
		pieceToMove.setStartRow(endRow);
	    pieceToMove.setStartColumn(endColumn);
        
		if ((pieceToMove.getChar() != 'P' || pieceToMove.getChar() != 'p'))
			resetThePassent();
	    
		if (pieceToMove.getColor().equals("white")) {
			if (get(endRow, endColumn).getChar() == 'K') {
				getPlayerWhite().setKingRow(endRow);
				getPlayerWhite().setKingColumn(endColumn);		
				if (getPlayerWhite().canDoQSC() == true) {
					if (endRow == 7 && endColumn == 2) {
						setBoard(7, 0, new Hyphen());
						setBoard(7, 3, new Rook("white", 7, 3));
						System.out.println("Queen side castle done by white player!\n");
						playerWhite.setCanDoQSC(false);
						playerWhite.setCanDoKSC(false);
					}
				}
				if (getPlayerWhite().canDoKSC()) {
					if (endRow == 7 && endColumn == 6) {
						setBoard(7, 7, new Hyphen());
						setBoard(7, 5, new Rook("white", 7, 5));
						System.out.println("King side castle done by white player!\n");
						playerWhite.setCanDoKSC(false);
						playerWhite.setCanDoQSC(false);
					}
				}
			}
			if (pawnWasMovedTwoSpaces == true) {
				if (pieceToMove.getChar() == 'P') {
				    if (endRow == enPassentRow && endColumn == enPassentColumn) {
					    setBoard(enPassentRow + 1, enPassentColumn, new Hyphen());
				    }
				}
			}
			if (get(endRow, endColumn).getChar() == 'K') {				   
				getPlayerWhite().setCanDoQSC(false);
				getPlayerWhite().setCanDoKSC(false);
			}
			if (get(endRow, endColumn).getChar() == 'R') {
				if (startColumn == 0)
					getPlayerWhite().setCanDoQSC(false);
				if (startColumn == 7)
					getPlayerWhite().setCanDoKSC(false);
			}
			if (get(endRow, endColumn).getChar() == 'P' && endRow == 0) {
				setBoard(endRow, endColumn, new Queen("white", endRow, endColumn));
			}
		}
	    if (pieceToMove.getColor().equals("black")) {
			if (get(endRow, endColumn).getChar() == 'k') {
				getPlayerBlack().setKingRow(endRow);
				getPlayerBlack().setKingColumn(endColumn);		
				if (getPlayerBlack().canDoQSC() == true) {
					if (endRow == 0 && endColumn == 2) {
						setBoard(0, 0, new Hyphen());
						setBoard(0, 3, new Rook("black", 0, 3));
						playerBlack.setCanDoQSC(false);
						playerBlack.setCanDoKSC(false);
						//System.out.println("Queen side castle done by black player!\n");
					}
				}
				if (getPlayerBlack().canDoKSC() == true) {
					if (endRow == 0 && endColumn == 6) {
						setBoard(0, 7, new Hyphen());
						setBoard(0, 5, new Rook("black", 0, 5));
						playerBlack.setCanDoQSC(false);
						playerBlack.setCanDoKSC(false);
						System.out.println("King side castle done by black player!\n");
					}
				}
			}
			if (pawnWasMovedTwoSpaces == true) {
				if (pieceToMove.getChar() == 'p') {
				    if (endRow == enPassentRow && endColumn == enPassentColumn) {
					    setBoard(enPassentRow + 1, enPassentColumn, new Hyphen());
				    }
				}
			}
			if (get(endRow, endColumn).getChar() == 'k') {				   
				getPlayerBlack().setCanDoQSC(false);
				getPlayerBlack().setCanDoKSC(false);
			}
			if (get(endRow, endColumn).getChar() == 'r') {
				if (startColumn == 0)
					getPlayerBlack().setCanDoQSC(false);
				if (startColumn == 7)
					getPlayerBlack().setCanDoKSC(false);
			}
			if (get(endRow, endColumn).getChar() == 'p' && endRow == 7) {
				setBoard(endRow, endColumn, new Queen("black", endRow, endColumn));
			}
	   }
	   if ((get(endRow, endColumn).getChar() == 'P' || get(endRow, endColumn).getChar() == 'p') && Math.abs(endRow - startRow) == 2) {
		   setPawnWasMovedTwoSpaces(true);
		   setEnPassentRow(endRow + 1);
		   setEnPassentColumn(endColumn);
	   }
	   return true;
	}
	
	public char getTileTaken() {
		if (tileTaken != '-') {
			return tileTaken;
		}
		else 
			return ' ';
	}
	
	public boolean isInCheckState(String color) {
		boolean retval = false;
		if (color.equals("white")) {
			int startRow = playerWhite.getKingRow();
			int startColumn = playerWhite.getKingColumn();
			retval = isInCheckState(startRow, startColumn, color);
		}
//		if (color.equals("black"))
//		{
//			int startRow = playerBlack.getKingRow();
//			int startColumn = playerBlack.getKingColumn();			
//		}
		return retval;
	}
	
	/**
	 * This method checks to see if the ChessPiece at the specified coordinates can be captured
	 * by an opponents chess piece on their next turn.
	 * 
	 * @param board
	 * @param startRow
	 * @param startColumn
	 * @param color
	 * @return boolean
	 */
	public boolean isInCheckState(int startRow, int startColumn, String color) {
		
		char rook = 0;
		char knight = 0;
		char bishop = 0;
		char queen = 0;
		char king = 0;
		char pawn = 0;
		int asciiCheckLow = 0;
		int asciiCheckHigh = 0;
		
		if (color.equals("white")) {
			rook = 'r';
			knight = 'n';
			bishop = 'b';
			queen = 'q';
			king = 'k';
			pawn = 'p';
			asciiCheckLow = 65;
			asciiCheckHigh = 90;
		}
		else if (color.equals("black")) {
			rook = 'R';
			knight = 'N';
			bishop = 'B';
			queen = 'Q';
			king = 'K';
			pawn = 'P';
			asciiCheckLow = 97;
			asciiCheckHigh = 122;
		}

		// checking for knights
		if (startRow - 2 > -1 && startRow -2 < 8 && startColumn - 1 > -1 && startColumn - 1 < 8) {
			if (board[startRow - 2][startColumn -1].getChar() == knight) {
				return true;
			}
		}

		if (startRow - 2 > -1 && startRow - 2 < 8 && startColumn + 1 > -1 && startColumn + 1 < 8) {
			if (board[startRow - 2][startColumn + 1].getChar() == knight) {
				return true;
			}
		}

		if (startRow - 1 > -1 && startRow - 1 < 8 && startColumn + 2 > -1 && startColumn + 2 < 8) {
			if (board[startRow - 1][startColumn + 2].getChar() == knight) {
				return true;
			}
		}

		if (startRow + 1 > -1 && startRow + 1 < 8 && startColumn + 2 > -1 && startColumn + 2 < 8) {
			if (board[startRow + 1][startColumn + 2].getChar() == knight) {
				return true;
			}
		}

		if (startRow + 2 > -1 && startRow + 2 < 8 && startColumn + 1 > -1 && startColumn + 1 < 8) {
			if (board[startRow + 2][startColumn + 1].getChar() == knight) {
				return true;
			}
		}

		if (startRow + 2 > -1 && startRow + 2 < 8 && startColumn - 1 > -1 && startColumn - 1 < 8) {
			if (board[startRow + 2][startColumn - 1].getChar() == knight) {
				return true;
			}
		}

		if (startRow + 1 > -1 && startRow + 1 < 8 && startColumn - 2 > -1 && startColumn - 2 < 8) {
			if (board[startRow + 1][startColumn - 2].getChar() == knight) {
				return true;
			}
		}

		if (startRow - 1 > -1 && startRow - 1 < 8 && startColumn - 2 > -1 && startColumn - 2 < 8) {
			if (board[startRow - 1][startColumn - 2].getChar() == knight) {
				return true;
			}
		}

		// checking above king
		for (int i = startRow - 1; i >= 0; i--) {
			char temp = board[i][startColumn].getChar();
			if (temp > asciiCheckLow && temp < asciiCheckHigh) 
				break;
			else if (temp == pawn || temp == knight || temp == bishop || temp == king)
				break;
			else if (temp == '-')
				;// we're good
			else {
				return true;
			}
		}
		
		// checking below king
		for (int i = startRow + 1; i <= 7; i++) {
			char temp = board[i][startColumn].getChar();
			if (temp > asciiCheckLow && temp < asciiCheckHigh) 
				break;
			else if (temp == pawn || temp == knight || temp == bishop || temp == king)
				break;
			else if (temp == '-')
				;// we're good
			else {
				return true;
			}
		}
		
		// checking to the left of the king
		for (int j = startColumn - 1; j >= 0; j--) {
			char temp = board[startRow][j].getChar();
			if (temp > asciiCheckLow && temp < asciiCheckHigh) // use asciiCheckLow and asciiCheckHigh for black king check check
				break;
			else if (temp == pawn || temp == knight || temp == bishop || temp == king)
				break;
			else if (temp == '-')
				;// we're good
			else {
				return true;
			}
		}
		
		// checking to the right of the king
		for (int j = startColumn + 1; j <= 7; j++) {
			char temp = board[startRow][j].getChar();
			if (temp > asciiCheckLow && temp < asciiCheckHigh)
				break;
			else if (temp == pawn || temp == knight || temp == bishop || temp == king)
				break;
			else if (temp == '-')
				;// we're good
			else {
				return true;
			}
		}

		// checking to the lop left of the king
		for (int i = startRow - 1, j = startColumn - 1; i >= 0 && j >= 0; i--, j--) {
			char temp = board[i][j].getChar();
			if (temp > asciiCheckLow && temp < asciiCheckHigh) 
				break;
			else if (temp == knight || temp == rook || temp == pawn || temp == king)
				break;
			else if (temp == '-')
				;// we're good
			else {
				return true;
			}
		}
		
		// checking to the top right of the king
		for (int i = startRow - 1, j = startColumn + 1; i >= 0 && j <= 7; i--, j++) {
			char temp = board[i][j].getChar();
			if (temp > asciiCheckLow && temp < asciiCheckHigh)
				break;
			else if (temp == knight || temp == rook || temp == pawn || temp == king)
				break;
			else if (temp == '-')
				;// we're good
			else {
				return true;
			}
		}
		
		// checking to the bottom left of the king
		for (int i = startRow + 1, j = startColumn - 1; i <= 7 && j >= 0; i++, j--) {
			char temp = board[i][j].getChar();
			if (temp > asciiCheckLow && temp < asciiCheckHigh) 
				break;
			else if (temp == knight || temp == rook || temp == pawn || temp == king)
				break;
			else if (temp == '-')
				;// we're good
			else {
				return true;
			}
		}
		
		// checking to the bottom right of the king
		for (int i = startRow + 1, j = startColumn + 1; i <= 7 && j <= 7; i++, j++) {
			char temp = board[i][j].getChar();
			if (temp > asciiCheckLow && temp < asciiCheckHigh)
				break;
			else if (temp == knight || temp == rook || temp == pawn || temp == king)
				break;
			else if (temp == '-')
				;// we're good
			else {
				return true;
			}
		}
		
		// pawns can only capture a king from one direction, pawnOffset is used to get that direction
		int pawnOffset = 0;
		if (color.equals("white"))
			pawnOffset = -1;
		else if (color.equals("black"))
			pawnOffset = 1;
		
		// two square are checked around a king where a pawn can attack from
		if ((startRow + pawnOffset) > -1 && (startRow + pawnOffset) < 8 && startColumn - 1 > -1) {
			if (board[startRow + pawnOffset][startColumn - 1].getChar() == pawn) {
				return true;
			}
		}
		if ((startRow + pawnOffset) > -1 && (startRow + pawnOffset) < 8 && startColumn + 1 < 8) {
			if (board[startRow + pawnOffset][startColumn + 1].getChar() == pawn) {
				return true;
			}
		}
	
		// checking for an opponent king one square away
		if (startRow - 1 > -1 && startRow - 1 < 8 && startColumn - 1 > -1 && startColumn - 1 < 8) {
			if (board[startRow - 1][startColumn -1].getChar() == king) {
				return true;
			}
		}

		if (startRow - 1 > -1 && startRow - 1 < 8 && startColumn > -1 && startColumn < 8) {
			if (board[startRow - 1][startColumn].getChar() == king) {
				return true;
			}
		}

		if (startRow - 1 > -1 && startRow - 1 < 8 && startColumn + 1 > -1 && startColumn + 1 < 8) {
			if (board[startRow - 1][startColumn + 1].getChar() == king) {
				return true;
			}
		}

		if (startRow > -1 && startRow < 8 && startColumn + 1 > -1 && startColumn + 1 < 8) {
			if (board[startRow][startColumn + 1].getChar() == king) {
				return true;
			}
		}

		if (startRow + 1 > -1 && startRow + 1 < 8 && startColumn + 1 > -1 && startColumn + 1 < 8) {
			if (board[startRow + 1][startColumn + 1].getChar() == king) {
				return true;
			}
		}

		if (startRow + 1 > -1 && startRow + 1 < 8 && startColumn > -1 && startColumn < 8) {
			if (board[startRow + 1][startColumn].getChar() == king) {
				return true;
			}
		}

		if (startRow + 1 > -1 && startRow + 1 < 8 && startColumn - 1 > -1 && startColumn - 1 < 8) {
			if (board[startRow + 1][startColumn - 1].getChar() == king) {
				return true;
			}
		}

		if (startRow > -1 && startRow < 8 && startColumn - 1 > -1 && startColumn - 1 < 8) {
			if (board[startRow][startColumn - 1].getChar() == king) {
				return true;
			}
		}
		
		// if no check states were detected, false is returned
		return false;
	}
	
	/**
	 * This method returns a string representing the current state of the ChessBoard
	 * 
	 * @return String
	 */
	public String toString() {
		String str = "";
		str += "\n  A__B__C__D__E__F__G__H\n";
		for (int i = 0; i < 8; i++)
		{
			str += (8 - i);
			for (int j = 0; j < 8; j++)
			{
				ChessPiece temp = board[i][j];
				if ((i + j) % 2 == 1)
					str += "[" + temp.getChar() + "]";
				else
				{
					if (j == 0){
						str += '|';
						str += temp.getChar();}
					else if (j == 7){
						str += temp.getChar();
						str += '|';}
					else
						str += temp.getChar();
				}
				if (i != 7 && j != 7)
					str += ' ';
				else if (i == 7 && j != 7)
					str += '_';
			}
			str += (8 - i) + "\n";
		}
		str += "  A  B  C  D  E  F  G  H\n";		
		return str;		
	}

	/**
	 * This function returns a list of coordinates where a player has chess pieces
	 * 
	 * @param color
	 * @return ArrayList<Integer>
	 */
	public ArrayList<ArrayList<Integer>> getPlayerPieces(String color) {
		ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j].getColor().equals(color)) {
					ArrayList<Integer> location = new ArrayList<Integer>();
					location.add(i);
					location.add(j);
					temp.add(location);
				}
			}
		}
		return temp;
	}

	/**
	 * returns character array representation of the chess board
	 * @return char[][]
	 */
	public char[][] getCharArray() {
		char[][] temp = new char[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				temp[i][j] = board[i][j].getChar();
			}
		}
		return temp;
	}

	/**
	 * resets variables for en passent state monitoring
	 */
	static void resetThePassent() {
		pawnWasMovedTwoSpaces = false;
		enPassentRow = -1;
		enPassentColumn = -1;
	}
	
	/**
	 * This method sets the location within the ChessPiece array to what was entered.
	 * @param row [of chess board]
	 * @param column [of chess board]
	 * @param temp (ChessPiece to put at row and column location
	 */
	public void setBoard(int row, int column, ChessPiece temp) {
		board[row][column] = temp;
	}
	
	/**
	 * returns if the last move consisted of a pawn moving two spaces
	 * @return
	 */
	public boolean pawnWasMovedTwoSpaces() { return pawnWasMovedTwoSpaces; }

	/**
	 * returns the row which a pawn can move to, to capture an opponent pawn through en passent
	 * @return
	 */
	public int getEnPassentRow() { return enPassentRow; }

	/**
	 * returns the column which a pawn can move to, to capture an opponent pawn though en passent
	 * @return
	 */
	public int getEnPassentColumn() { return enPassentColumn; }

	/**
	 * sets if a pawn was just moved two spaces
	 * @param val
	 */
	public void setPawnWasMovedTwoSpaces(boolean val) { pawnWasMovedTwoSpaces = val; }

	/**
	 * sets enPassentRow variable
	 * @param val
	 */
	public void setEnPassentRow(int val) { enPassentRow = val; }

	/**
	 * sets enPassentColumn variable
	 * @param val
	 */
	public void setEnPassentColumn(int val) { enPassentColumn = val; }

	/**
	 * returns the playerWhite player object
	 * @return
	 */
	public Player getPlayerWhite() { return playerWhite; }

	/**
	 * returns the playerBlack player object
	 * @return Player
	 */
	public Player getPlayerBlack() { return playerBlack; }

	/**
	 * returns the ChessPiece at the specified location on the ChessBoard
	 * @param row
	 * @param column
	 * @return ChessPiece
	 */
	public ChessPiece get(int row, int column) { return board[row][column]; }
	
}