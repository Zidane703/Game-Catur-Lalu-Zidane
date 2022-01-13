import java.util.ArrayList;

/**
 * This class implements methods from the ChessPiece interface.
 * Instantiations of this class are stored in ChessBoard objects.
 * 
 * @author Devan
 */
public class King implements ChessPiece{
	
	private String color;
	private char name;
	
	private int startRow;
	private int startColumn;
	
	private ArrayList<MoveCommand> goodMovesList = new ArrayList<MoveCommand>();
	private ArrayList<MoveCommand> noLossMovesList = new ArrayList<MoveCommand>();
	private ArrayList<MoveCommand> takeOpponentPieceMovesList = new ArrayList<MoveCommand>();
	private ArrayList<MoveCommand> opponentKingInCheckMovesList = new ArrayList<MoveCommand>();
	private ArrayList<MoveCommand> opponentInCheckMateMovesList = new ArrayList<MoveCommand>();
	private ArrayList<MoveCommand> canTakePieceAfterwardsList = new ArrayList<MoveCommand>();
	//private ArrayList<MoveCommand> minimumLossMovesList = new ArrayList<MoveCommand>();

	private int pieceValue = 100;
	
	/**
	 * This is the constructor for King objects.
	 * @param color [of the king]
	 * @param row
	 * @param col
	 */
	King (String color, int row, int col) {
		this.color = color; 
		startRow = row;
		startColumn = col;
		name = color.equals("white") ? 'K' : 'k';
	}
	
	@Override
	public ChessPiece copyOf() {
		King copy = new King(color, startRow, startColumn);
		return copy;
	}
	
	@Override
	public void generateGoodMoves(ChessBoard board) {
		
		ArrayList<MoveCommand> maybeGoodMoves = new ArrayList<MoveCommand>();
		ArrayList<MoveCommand> goodMoves = new ArrayList<MoveCommand>();
        
		if (color.equals("white")) {
			
           if (startRow - 1 > -1)
		      if (!(board.get(startRow-1, startColumn).getColor().equals("white")))
			      maybeGoodMoves.add(new MoveCommand(startRow, startColumn, startRow-1, startColumn));
           
           if (startRow - 1 > -1 && startColumn + 1 < 8)
        	   if (!(board.get(startRow-1, startColumn+1).getColor().equals("white")))
 			        maybeGoodMoves.add(new MoveCommand(startRow, startColumn, startRow-1, startColumn+1));
           
           if (startRow == 7 && startColumn == 4) {
        	  if (board.getPlayerWhite().canDoKSC()) {
        		 if (board.get(7, 5).getChar() == '-' && board.get(7, 6).getChar() == '-') {
  			        if (!board.isInCheckState(7, 5, "white")) {
  			        	maybeGoodMoves.add(new MoveCommand(startRow, startColumn, startRow, startColumn+2));
  			        }
        		 }
        	  }
           }

           if (startColumn + 1 < 8)
			  if (!(board.get(startRow, startColumn+1).getColor().equals("white")))
			     maybeGoodMoves.add(new MoveCommand(startRow, startColumn, startRow, startColumn+1));

           if (startRow + 1 < 8 && startColumn + 1 < 8)
        	   if (!(board.get(startRow+1, startColumn+1).getColor().equals("white")))
        		  maybeGoodMoves.add(new MoveCommand(startRow, startColumn, startRow+1, startColumn+1));
		
           if (startRow + 1 < 8)
        	  if (!(board.get(startRow+1, startColumn).getColor().equals("white")))
 		         maybeGoodMoves.add(new MoveCommand(startRow, startColumn, startRow+1, startColumn));
 		  
 		   if (startRow + 1 < 8 && startColumn - 1 > -1)      
 			  if (!(board.get(startRow+1, startColumn-1).getColor().equals("white")))
 		   		 maybeGoodMoves.add(new MoveCommand(startRow, startColumn, startRow+1, startColumn-1));

 		   if (startRow == 7 && startColumn == 4) {
 			  if (board.getPlayerWhite().canDoQSC()) {
 				 if (board.get(7, 1).getChar() == '-' && board.get(7, 2).getChar() == '-' && board.get(7, 3).getChar() == '-') {
 					if (!board.isInCheckState(7, 3, "white")) {
 						maybeGoodMoves.add(new MoveCommand(startRow, startColumn, startRow, startColumn-2));
 					}
 				 }
 			  }
 		   }

 		   if (startColumn - 1 > -1)
  			  if (!(board.get(startRow, startColumn-1).getColor().equals("white")))
			     maybeGoodMoves.add(new MoveCommand(startRow, startColumn, startRow, startColumn-1));
		   
		   if (startRow - 1 > -1 && startColumn - 1 > -1)
			  if (!(board.get(startRow-1, startColumn-1).getColor().equals("white")))
		         maybeGoodMoves.add(new MoveCommand(startRow, startColumn, startRow-1, startColumn-1));
		     
		}

		if (color.equals("black")) {

	        if (startRow - 1 > -1)
			   if (!(board.get(startRow-1, startColumn).getColor().equals("black")))
				  maybeGoodMoves.add(new MoveCommand(startRow, startColumn, startRow-1, startColumn));
	           
	         if (startRow - 1 > -1 && startColumn + 1 < 8)
	        	if (!(board.get(startRow-1, startColumn+1).getColor().equals("black")))
	 			   maybeGoodMoves.add(new MoveCommand(startRow, startColumn, startRow-1, startColumn+1));
	           
	         if (startRow == 0 && startColumn == 4) {
	            if (board.getPlayerBlack().canDoKSC()) {
	        	    if (board.get(0, 5).getChar() == '-' && board.get(0, 6).getChar() == '-') {
	  			        if (!board.isInCheckState(0, 5, "black")) {
	  			        	maybeGoodMoves.add(new MoveCommand(startRow, startColumn, startRow, startColumn+1));
	  			        }
	                }
	        	 }
	          }

	         if (startColumn + 1 < 8)
				 if (!(board.get(startRow, startColumn+1).getColor().equals("black")))
				    maybeGoodMoves.add(new MoveCommand(startRow, startColumn, startRow, startColumn+1));

	          if (startRow + 1 < 8 && startColumn + 1 < 8)
	        	  if (!(board.get(startRow+1, startColumn+1).getColor().equals("black")))
	        		 maybeGoodMoves.add(new MoveCommand(startRow, startColumn, startRow+1, startColumn+1));
			
	          if (startRow + 1 < 8)
	        	 if (!(board.get(startRow+1, startColumn).getColor().equals("black")))
	 		        maybeGoodMoves.add(new MoveCommand(startRow, startColumn, startRow+1, startColumn));
	 		  
	 		  if (startRow + 1 < 8 && startColumn - 1 > -1)      
	 			 if (!(board.get(startRow+1, startColumn-1).getColor().equals("black")))
	 		   		maybeGoodMoves.add(new MoveCommand(startRow, startColumn, startRow+1, startColumn-1));

	 		  if (startRow == 0 && startColumn == 4) {
	 			 if (board.getPlayerBlack().canDoQSC()) {
	 				if (board.get(0, 1).getChar() == '-' && board.get(0, 2).getChar() == '-' && board.get(0, 3).getChar() == '-') {
	 				   if (!board.isInCheckState(0, 3, "black")) {
	 					   maybeGoodMoves.add(new MoveCommand(startRow, startColumn, startRow, startColumn-2));
	 				   }
	 				}
	 			 }
	 		  } 
	 		  if (startColumn - 1 > -1)
	  			 if (!(board.get(startRow, startColumn-1).getColor().equals("black")))
				    maybeGoodMoves.add(new MoveCommand(startRow, startColumn, startRow, startColumn-1));
			   
			  if (startRow - 1 > -1 && startColumn - 1 > -1)
				 if (!(board.get(startRow-1, startColumn-1).getColor().equals("black")))
			        maybeGoodMoves.add(new MoveCommand(startRow, startColumn, startRow-1, startColumn-1));		
	    }
		
		goodMovesList = new ArrayList<MoveCommand>();
		for (int i = 0; i < maybeGoodMoves.size(); i++) {
			MoveCommand tempMove = maybeGoodMoves.get(i);
			int startRow = tempMove.getStartRow();
			int startColumn = tempMove.getStartColumn();
			int endRow = tempMove.getEndRow();
			int endColumn = tempMove.getEndColumn();
			ChessBoard temp = board.copyOf();
			generateNoLossMoves(temp);
			int before = noLossMovesList.size();
			if (temp.executeMove(tempMove) != false) {
				generateNoLossMoves(temp);
				int after = noLossMovesList.size();
				if (!(temp.isInCheckState(endRow, endColumn, color))) {
				    maybeGoodMoves.get(i).setDiffNumSafeMoves(after - before);
					int val = (int)(Math.abs(3.5 - endRow) + Math.abs(3.5 - endColumn) / 2.0);
					maybeGoodMoves.get(i).setAvgDistToCenter(val);
					goodMoves.add(maybeGoodMoves.get(i));
				}
			}
		}
		goodMovesList = goodMoves;
	}

	@Override
	public void generateNoLossMoves(ChessBoard board) {
		ArrayList<MoveCommand> maybeNoLossMoves = new ArrayList<MoveCommand>();
		ArrayList<ArrayList<Integer>> playerPieceLocations = new ArrayList<ArrayList<Integer>>();
		playerPieceLocations = board.getPlayerPieces(color);

		if (color.equals("white")) {
			
           if (startRow - 1 > -1)
		      if (!(board.get(startRow-1, startColumn).getColor().equals("white")))
			      maybeNoLossMoves.add(new MoveCommand(startRow, startColumn, startRow-1, startColumn));
           
           if (startRow - 1 > -1 && startColumn + 1 < 8)
        	   if (!(board.get(startRow-1, startColumn+1).getColor().equals("white")))
 			        maybeNoLossMoves.add(new MoveCommand(startRow, startColumn, startRow-1, startColumn+1));
           
           if (startRow == 7 && startColumn == 4) {
        	  if (board.getPlayerWhite().canDoKSC()) {
        		 if (board.get(7, 5).getChar() == '-' && board.get(7, 6).getChar() == '-') {
  			        maybeNoLossMoves.add(new MoveCommand(startRow, startColumn, startRow, startColumn+1));
        		 }
        	  }
           }

           if (startColumn + 1 < 8)
			  if (!(board.get(startRow, startColumn+1).getColor().equals("white")))
			     maybeNoLossMoves.add(new MoveCommand(startRow, startColumn, startRow, startColumn+1));

           if (startRow + 1 < 8 && startColumn + 1 < 8)
        	   if (!(board.get(startRow+1, startColumn+1).getColor().equals("white")))
        		  maybeNoLossMoves.add(new MoveCommand(startRow, startColumn, startRow+1, startColumn+1));
		
           if (startRow + 1 < 8)
        	  if (!(board.get(startRow+1, startColumn).getColor().equals("white")))
 		         maybeNoLossMoves.add(new MoveCommand(startRow, startColumn, startRow+1, startColumn));
 		  
 		   if (startRow + 1 < 8 && startColumn - 1 > -1)      
 			  if (!(board.get(startRow+1, startColumn-1).getColor().equals("white")))
 		   		 maybeNoLossMoves.add(new MoveCommand(startRow, startColumn, startRow+1, startColumn-1));

 		   if (startRow == 7 && startColumn == 4) {
 			  if (board.getPlayerWhite().canDoQSC()) {
 				 if (board.get(7, 1).getChar() == '-' && board.get(7, 2).getChar() == '-' && board.get(7, 3).getChar() == '-') {
 					maybeNoLossMoves.add(new MoveCommand(startRow, startColumn, startRow, startColumn-2));
 				 }
 			  }
 		   }

 		   if (startColumn - 1 > -1)
  			  if (!(board.get(startRow, startColumn-1).getColor().equals("white")))
			     maybeNoLossMoves.add(new MoveCommand(startRow, startColumn, startRow, startColumn-1));
		   
		   if (startRow - 1 > -1 && startColumn - 1 > -1)
			  if (!(board.get(startRow-1, startColumn-1).getColor().equals("white")))
		         maybeNoLossMoves.add(new MoveCommand(startRow, startColumn, startRow-1, startColumn-1));
		     
		}

		if (color.equals("black")) {

	        if (startRow - 1 > -1)
			   if (!(board.get(startRow-1, startColumn).getColor().equals("black")))
				  maybeNoLossMoves.add(new MoveCommand(startRow, startColumn, startRow-1, startColumn));
	           
	         if (startRow - 1 > -1 && startColumn + 1 < 8)
	        	if (!(board.get(startRow-1, startColumn+1).getColor().equals("black")))
	 			   maybeNoLossMoves.add(new MoveCommand(startRow, startColumn, startRow-1, startColumn+1));
	           
	         if (startRow == 0 && startColumn == 4) {
	            if (board.getPlayerBlack().canDoKSC()) {
	        	    if (board.get(0, 5).getChar() == '-' && board.get(0, 6).getChar() == '-') {
	  			       maybeNoLossMoves.add(new MoveCommand(startRow, startColumn, startRow, startColumn+1));
	                }
	        	 }
	          }

	         if (startColumn + 1 < 8)
				 if (!(board.get(startRow, startColumn+1).getColor().equals("black")))
				    maybeNoLossMoves.add(new MoveCommand(startRow, startColumn, startRow, startColumn+1));

	          if (startRow + 1 < 8 && startColumn + 1 < 8)
	        	  if (!(board.get(startRow+1, startColumn+1).getColor().equals("black")))
	        		 maybeNoLossMoves.add(new MoveCommand(startRow, startColumn, startRow+1, startColumn+1));
			
	          if (startRow + 1 < 8)
	        	 if (!(board.get(startRow+1, startColumn).getColor().equals("black")))
	 		        maybeNoLossMoves.add(new MoveCommand(startRow, startColumn, startRow+1, startColumn));
	 		  
	 		  if (startRow + 1 < 8 && startColumn - 1 > -1)      
	 			 if (!(board.get(startRow+1, startColumn-1).getColor().equals("black")))
	 		   		maybeNoLossMoves.add(new MoveCommand(startRow, startColumn, startRow+1, startColumn-1));

	 		  if (startRow == 0 && startColumn == 4) {
	 			 if (board.getPlayerBlack().canDoQSC()) {
	 				if (board.get(0, 1).getChar() == '-' && board.get(0, 2).getChar() == '-' && board.get(0, 3).getChar() == '-') {
	 				   maybeNoLossMoves.add(new MoveCommand(startRow, startColumn, startRow, startColumn-2));
	 				}
	 			 }
	 		  } 
	 		  if (startColumn - 1 > -1)
	  			 if (!(board.get(startRow, startColumn-1).getColor().equals("black")))
				    maybeNoLossMoves.add(new MoveCommand(startRow, startColumn, startRow, startColumn-1));
			   
			  if (startRow - 1 > -1 && startColumn - 1 > -1)
				 if (!(board.get(startRow-1, startColumn-1).getColor().equals("black")))
			        maybeNoLossMoves.add(new MoveCommand(startRow, startColumn, startRow-1, startColumn-1));		
	    }
		
		noLossMovesList = new ArrayList<MoveCommand>();
		noLossMovesList = filteredMaybeNoLossMoves(board, playerPieceLocations, maybeNoLossMoves);
		
	}

	@Override
	public ArrayList<MoveCommand> filteredMaybeNoLossMoves(ChessBoard board, ArrayList<ArrayList<Integer>> playerPieceLocations, ArrayList<MoveCommand> maybeNoLossMoves) {
		ArrayList<MoveCommand> noLossMoves= new ArrayList<MoveCommand>();	
		//minimumLossMovesList = new ArrayList<MoveCommand>();

		int tempPieceRow = 0;
		int tempPieceColumn = 0;
		
		for (int i = 0; i < maybeNoLossMoves.size(); i++) {
			MoveCommand tempMove = maybeNoLossMoves.get(i);
			int startRow = tempMove.getStartRow();
			int startColumn = tempMove.getStartColumn();
			int endRow = tempMove.getEndRow();
			int endColumn = tempMove.getEndColumn();
			boolean addToNoLossMovesFlag = true;
			int numSafePoints = 0;
			for (int j = 0; j < playerPieceLocations.size(); j++) {
				ChessBoard temp = board.copyOf();
				if (temp.get(playerPieceLocations.get(j).get(0), playerPieceLocations.get(j).get(1)).getChar() != 'P' && temp.get(playerPieceLocations.get(j).get(0), playerPieceLocations.get(j).get(1)).getChar() != 'p')
					if (temp.executeMove(tempMove) != false) {
						if (startRow == playerPieceLocations.get(j).get(0) && startColumn == playerPieceLocations.get(j).get(1)) {
							tempPieceRow = endRow;
							tempPieceColumn = endColumn;
						}
						else {
							tempPieceRow = playerPieceLocations.get(j).get(0);
							tempPieceColumn = playerPieceLocations.get(j).get(1);
						}
						if (temp.isInCheckState(tempPieceRow, tempPieceColumn, color)){
							addToNoLossMovesFlag = false;
						}
						else {
							numSafePoints += board.get(tempPieceRow, tempPieceColumn).getPieceValue();
						}
					}
			}
			maybeNoLossMoves.get(i).setNumSafePoints(numSafePoints);
			if (addToNoLossMovesFlag)
				maybeNoLossMoves.get(i).setActuallySafe();
			noLossMoves.add(maybeNoLossMoves.get(i));
			//minimumLossMovesList.add(maybeNoLossMoves.get(i));
		}
		return noLossMoves;
	}

	@Override
	public void generateTakeOpponentPieceMoves(ChessBoard board) {
		takeOpponentPieceMovesList = new ArrayList<MoveCommand>();
		ArrayList<MoveCommand> takeOpponentPieceMoves = new ArrayList<MoveCommand>();

		if (color.equals("white")) {
			
           if (startRow - 1 > -1)
		      if (board.get(startRow-1, startColumn).getColor().equals("black")) {
		    	  MoveCommand temp = new MoveCommand(startRow, startColumn, startRow-1, startColumn);
		    	  temp.setTakePieceDiff(board.get(startRow-1, startColumn).getPieceValue() - pieceValue);
		          takeOpponentPieceMoves.add(temp);
		      }
           
           if (startRow - 1 > -1 && startColumn + 1 < 8)
        	   if (board.get(startRow-1, startColumn+1).getColor().equals("black")) {
        		   MoveCommand temp = new MoveCommand(startRow, startColumn, startRow-1, startColumn+1);
        		   temp.setTakePieceDiff(board.get(startRow-1, startColumn+1).getPieceValue() - pieceValue);
 			       takeOpponentPieceMoves.add(temp);
        	   }
           
           if (startRow == 7 && startColumn == 4) {
        	  if (board.getPlayerWhite().canDoKSC()) {
        		 if (board.get(7, 5).getChar() == '-' && board.get(7, 6).getChar() == '-') {
  			        ;
        		 }
        	  }
           }

           if (startColumn + 1 < 8)
			  if (board.get(startRow, startColumn+1).getColor().equals("black")) {
				  MoveCommand temp = new MoveCommand(startRow, startColumn, startRow, startColumn+1);
				  temp.setTakePieceDiff(board.get(startRow,  startColumn+1).getPieceValue() - pieceValue);
			      takeOpponentPieceMoves.add(temp);
			  }

           if (startRow + 1 < 8 && startColumn + 1 < 8)
        	   if (board.get(startRow+1, startColumn+1).getColor().equals("black")){
        		  MoveCommand temp = new MoveCommand(startRow, startColumn, startRow+1, startColumn+1);
        		  temp.setTakePieceDiff(board.get(startRow+1, startColumn+1).getPieceValue() - pieceValue);
        		  takeOpponentPieceMoves.add(temp);
        	   }
		
           if (startRow + 1 < 8)
        	  if (board.get(startRow+1, startColumn).getColor().equals("black")){
        		  MoveCommand temp = new MoveCommand(startRow, startColumn, startRow+1, startColumn);
        		  temp.setTakePieceDiff(board.get(startRow+1, startColumn).getPieceValue() - pieceValue);
 		          takeOpponentPieceMoves.add(temp);
        	  }
 		  
 		   if (startRow + 1 < 8 && startColumn - 1 > -1)      
 			  if (board.get(startRow+1, startColumn-1).getColor().equals("black")) {
 				  MoveCommand temp = new MoveCommand(startRow, startColumn, startRow+1, startColumn-1);
 				  temp.setTakePieceDiff(board.get(startRow+1, startColumn-1).getPieceValue() - pieceValue);
 		   		  takeOpponentPieceMoves.add(temp);
 			  }

 		   if (startRow == 7 && startColumn == 4) {
 			  if (board.getPlayerWhite().canDoQSC()) {
 				 if (board.get(7, 1).getChar() == '-' && board.get(7, 2).getChar() == '-' && board.get(7, 3).getChar() == '-') {
 					;
 				 }
 			  }
 		   }

 		   if (startColumn - 1 > -1)
  			  if (board.get(startRow, startColumn-1).getColor().equals("black")){
  				  MoveCommand temp = new MoveCommand(startRow, startColumn, startRow, startColumn-1);
  				  temp.setTakePieceDiff(board.get(startRow, startColumn-1).getPieceValue() - pieceValue);
  				  takeOpponentPieceMoves.add(temp);
  			  }
		   
		   if (startRow - 1 > -1 && startColumn - 1 > -1)
			  if (board.get(startRow-1, startColumn-1).getColor().equals("black")){
				  MoveCommand temp = new MoveCommand(startRow, startColumn, startRow-1, startColumn-1);
				  temp.setTakePieceDiff(board.get(startRow-1, startColumn-1).getPieceValue() - pieceValue);
		          takeOpponentPieceMoves.add(temp);
			  }
		}

		if (color.equals("black")) {

	        if (startRow - 1 > -1)
			   if (board.get(startRow-1, startColumn).getColor().equals("white")){
				   MoveCommand temp = new MoveCommand(startRow, startColumn, startRow-1, startColumn);
				   temp.setTakePieceDiff(board.get(startRow-1, startColumn).getPieceValue() - pieceValue);
				   takeOpponentPieceMoves.add(temp);
			   }
	           
	         if (startRow - 1 > -1 && startColumn + 1 < 8)
	        	if (board.get(startRow-1, startColumn+1).getColor().equals("white")){
	        		MoveCommand temp = new MoveCommand(startRow, startColumn, startRow-1, startColumn+1);
	        		temp.setTakePieceDiff(board.get(startRow-1, startColumn+1).getPieceValue() - pieceValue);
	 			    takeOpponentPieceMoves.add(temp);
	        	}
	           
	         if (startRow == 0 && startColumn == 4) {
	            if (board.getPlayerBlack().canDoKSC()) {
	        	    if (board.get(0, 5).getChar() == '-' && board.get(0, 6).getChar() == '-') {
	  			       ;
	                }
	        	 }
	          }

	         if (startColumn + 1 < 8)
				 if (board.get(startRow, startColumn+1).getColor().equals("white")) {
					 MoveCommand temp = new MoveCommand(startRow, startColumn, startRow, startColumn+1);
					 temp.setTakePieceDiff(board.get(startRow, startColumn+1).getPieceValue() - pieceValue);
				     takeOpponentPieceMoves.add(temp);
				 }

	          if (startRow + 1 < 8 && startColumn + 1 < 8)
	        	  if (board.get(startRow+1, startColumn+1).getColor().equals("white")){
	        		  MoveCommand temp = new MoveCommand(startRow, startColumn, startRow+1, startColumn+1);
	        		  temp.setTakePieceDiff(board.get(startRow+1, startColumn+1).getPieceValue() - pieceValue);
	        		  takeOpponentPieceMoves.add(temp);
	        	  }
			
	          if (startRow + 1 < 8)
	        	 if (board.get(startRow+1, startColumn).getColor().equals("white")){
	        		 MoveCommand temp = new MoveCommand(startRow, startColumn, startRow+1, startColumn);
	        		 temp.setTakePieceDiff(board.get(startRow+1, startColumn).getPieceValue() - pieceValue);
	 		         takeOpponentPieceMoves.add(temp);
	        	 }
	 		  
	 		  if (startRow + 1 < 8 && startColumn - 1 > -1)      
	 			 if (board.get(startRow+1, startColumn-1).getColor().equals("white")){
	 				 MoveCommand temp = new MoveCommand(startRow, startColumn, startRow+1, startColumn-1);
	 				 temp.setTakePieceDiff(board.get(startRow+1, startColumn-1).getPieceValue() - pieceValue);
	 		   		 takeOpponentPieceMoves.add(temp);
	 			 }

	 		  if (startRow == 0 && startColumn == 4) {
	 			 if (board.getPlayerBlack().canDoQSC()) {
	 				if (board.get(0, 1).getChar() == '-' && board.get(0, 2).getChar() == '-' && board.get(0, 3).getChar() == '-') {
	 				   ;
	 				}
	 			 }
	 		  } 
	 		  if (startColumn - 1 > -1)
	  			 if (board.get(startRow, startColumn-1).getColor().equals("white")){
	  				 MoveCommand temp = new MoveCommand(startRow, startColumn, startRow, startColumn-1);
	  				 temp.setTakePieceDiff(board.get(startRow, startColumn-1).getPieceValue() - pieceValue);
				     takeOpponentPieceMoves.add(temp);
	  			 }
			   
			  if (startRow - 1 > -1 && startColumn - 1 > -1)
				 if (board.get(startRow-1, startColumn-1).getColor().equals("white")){
					 MoveCommand temp = new MoveCommand(startRow, startColumn, startRow-1, startColumn-1);
					 temp.setAvgDistToCenter(board.get(startRow-1, startColumn-1).getPieceValue() - pieceValue);
			         takeOpponentPieceMoves.add(temp);	
				 }
	    }
		
		takeOpponentPieceMovesList = takeOpponentPieceMoves;
	}

	@Override
	public void generateOpponentKingInCheckMoves(ChessBoard board) {
		
		generateGoodMoves(board);	
		ArrayList<MoveCommand> maybeOpponentKingInCheckMovesList = goodMovesList;
		opponentKingInCheckMovesList = new ArrayList<MoveCommand>();
		
		int tempPieceRow = -1;
		int tempPieceColumn = -1;
		String tempColor = "";
		
		if (color.equals("white")) {
			tempPieceRow = board.getPlayerBlack().getKingRow();
			tempPieceColumn = board.getPlayerBlack().getKingColumn();
			tempColor = "black";
		}
		else if (color.equals("black")) {
			tempPieceRow = board.getPlayerWhite().getKingRow();
			tempPieceColumn = board.getPlayerWhite().getKingColumn();
			tempColor = "white";
		}
		
		for (int i = 0; i < maybeOpponentKingInCheckMovesList.size(); i++) {
			MoveCommand tempMove = maybeOpponentKingInCheckMovesList.get(i);
			int startRow = tempMove.getStartRow();
			int startColumn = tempMove.getStartColumn();
			int endRow = tempMove.getEndRow();
			int endColumn = tempMove.getEndColumn();
			ChessBoard temp = board.copyOf();
			if (temp.executeMove(tempMove) != false) {
				if (temp.isInCheckState(tempPieceRow, tempPieceColumn, tempColor)) {
					opponentKingInCheckMovesList.add(tempMove);
				}
			}
		}
	}
	
	@Override
	public void generateOpponentInCheckMateMoves(ChessBoard board) {

		generateGoodMoves(board);	
		if (goodMovesList.size() != 0) {
			ArrayList<MoveCommand> maybeOpponentInCheckMateMovesList = goodMovesList;
			opponentInCheckMateMovesList = new ArrayList<MoveCommand>();

			int tempPieceRow = -1;
			int tempPieceColumn = -1;
			String tempColor = "";
			
			if (color.equals("white")) {
				tempPieceRow = board.getPlayerBlack().getKingRow();
				tempPieceColumn = board.getPlayerBlack().getKingColumn();
				tempColor = "black";
			}
			else if (color.equals("black")) {
				tempPieceRow = board.getPlayerWhite().getKingRow();
				tempPieceColumn = board.getPlayerWhite().getKingColumn();
				tempColor = "white";
			}

			for (int i = 0; i < maybeOpponentInCheckMateMovesList.size(); i++) {
				MoveCommand tempMove = maybeOpponentInCheckMateMovesList.get(i);
				int startRow = tempMove.getStartRow();
				int startColumn = tempMove.getStartColumn();
				int endRow = tempMove.getEndRow();
				int endColumn = tempMove.getEndColumn();
				ChessBoard temp = board.copyOf();
				if (temp.executeMove(tempMove) != false) {
					ArrayList<MoveCommand> opponentGoodMoves = new ArrayList<MoveCommand>();
					if (color.equals("white"))
						opponentGoodMoves = board.getPlayerBlack().getGoodMoves(temp, tempColor);
					else if (color.equals("black"))
						opponentGoodMoves = board.getPlayerWhite().getGoodMoves(temp, tempColor);
					if (opponentGoodMoves.size() == 0)
						opponentInCheckMateMovesList.add(maybeOpponentInCheckMateMovesList.get(i));
				}
			}
		}
	}
	
	@Override
	public void generateCanTakePieceAfterwardsMoves(ChessBoard board) {
		ArrayList<MoveCommand> maybeCanTakePieceAfterwardsList = new ArrayList<MoveCommand>();
		generateGoodMoves(board);
		maybeCanTakePieceAfterwardsList = getGoodMoves();
		for (int i = 0; i < maybeCanTakePieceAfterwardsList.size(); i++){
			ChessBoard boardCopy = board.copyOf();
			boardCopy.executeMove(maybeCanTakePieceAfterwardsList.get(i));
			generateTakeOpponentPieceMoves(boardCopy);
			ArrayList<MoveCommand> metaTakeOpponentPieceMoves = getTakeOpponentPieceMoves();
			for (int j = 0; j < metaTakeOpponentPieceMoves.size(); j++){
				canTakePieceAfterwardsList.add(metaTakeOpponentPieceMoves.get(j));
			}
		}
	}

	@Override
	public ArrayList<MoveCommand> getTakeOpponentPieceMoves() { return takeOpponentPieceMovesList; }

	@Override
	public ArrayList<MoveCommand> getNoLossMoves() { return noLossMovesList;	}
	
	@Override
	public ArrayList getGoodMoves() { return goodMovesList;	}

	@Override
	public ArrayList<MoveCommand> getOpponentKingInCheckMoves() { return opponentKingInCheckMovesList; }

	@Override
	public ArrayList<MoveCommand> getOpponentInCheckMateMoves() { return opponentInCheckMateMovesList; }

	@Override 
	public int getPieceValue() { return pieceValue; }
	
	@Override
	public char getChar() {	return name; }
	
	@Override
	public String getColor() { return color; }
		
	@Override
	public int getStartRow() { return startRow; }
	
	@Override
	public void setStartRow(int startRow) { this.startRow = startRow; }
	
	@Override
	public int getStartColumn() { return startColumn; }
	
	@Override
	public void setStartColumn(int startColumn) { this.startColumn = startColumn; }

	@Override
	public ArrayList<MoveCommand> getCanTakePieceAfterwardsMoves() { return canTakePieceAfterwardsList; }

}
