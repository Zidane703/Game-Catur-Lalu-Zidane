import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * This class includes the graphics and logic for running the chess game program.
 * 
 * @author Devan Karsann
 */
public class ChessGameWindow extends JPanel {
	
	private JPanel gamePanel;
	private JPanel sidePanel;
	private JButton resetGame;
	private JButton[][] chessPieces;
	private JLabel infoMessage = new JLabel();
	private int selectedRow = 0;
	private int selectedColumn = 0;
	private int dimension = 8;
	private ChessBoard board;
	private Player user;
	private Player aiPlayer;
	private Color lightGrey = new Color(224, 224, 224);
	private Color darkGrey = new Color(192, 192, 192);
	private boolean tileWasSelected;
	private int[] moveIndices = new int[5];
	private boolean checkmateState;
	private JButton resetButton;
	private String rowColumnSelected;
	private int turn = 0;
	
	// default chess board
	public ChessGameWindow(){
		this.setLayout(new BorderLayout());
		gamePanel = new JPanel();
		add(gamePanel, BorderLayout.CENTER);
		sidePanel = new JPanel();
		add(sidePanel, BorderLayout.EAST); 
		user = new Player("white");
		aiPlayer = new Player("black");
		board = new ChessBoard(user, aiPlayer);
		System.out.println("board state for aiPlayer");
		System.out.println(board.toString());
		infoMessage.setText("select a move to start the game");
		RefreshGUI();
	}
	
	// only one of these two constructors will be used when running the program
	
	// chess board read from a file
	public ChessGameWindow(String filename){
		this.setLayout(new BorderLayout());
		gamePanel = new JPanel();
		add(gamePanel, BorderLayout.CENTER);
		sidePanel = new JPanel();
		add(sidePanel, BorderLayout.EAST); 
		user = new Player("white");
		aiPlayer = new Player("black");
		board = new ChessBoard(user, aiPlayer, filename);
		System.out.println("board state for aiPlayer");
		System.out.println(board.toString());
		infoMessage.setText("select a move to start the game");
		RefreshGUI();
	}
	
	/**
	 * This method reconstructs the two JPanels used in the JFrame.
	 */
	private void RefreshGUI(){
		constructGamePanel();
		constructSidePanel();
	}
	
	/**
	 * The gamePanel contains the chess board and consists of a two-dimensional array of JButtons.
	 * Each JButton has either a light grey or dark grey background. If a chess piece is located
	 * at the same indices as a JButton on the corresponding ChessBoard object, the picture representing 
	 * that piece will be added to the JButton as an ImageIcon. 
	 */
	private void constructGamePanel(){
		gamePanel.removeAll();
		gamePanel.setPreferredSize(new Dimension(540, 540));
		gamePanel.setLayout(new GridLayout(dimension, dimension));
		chessPieces = new JButton[dimension][dimension];
		ChessPieceListener chessPieceListener = new ChessPieceListener();              
		int count = 0;
		for (int i = 0; i < dimension; i++){
			for (int j = 0; j < dimension; j++, count++){
				JButton newButton = new JButton();
				if (board.get(i, j).getChar() == 'r') {
					ImageIcon icon = new ImageIcon("blackCastle.png");
					newButton.setIcon(icon);
				}
				else if (board.get(i, j).getChar() == 'n') {
					ImageIcon icon = new ImageIcon("blackKnight.png");
					newButton.setIcon(icon);
				}
				else if (board.get(i, j).getChar() == 'b') {
					ImageIcon icon = new ImageIcon("blackBishop.png");
					newButton.setIcon(icon);
				}
				else if (board.get(i, j).getChar() == 'q') {
					ImageIcon icon = new ImageIcon("blackQueen.png");
					newButton.setIcon(icon);
				}
				else if (board.get(i, j).getChar() == 'k') {
					ImageIcon icon = new ImageIcon("blackKing.png");
					newButton.setIcon(icon);
				}
				else if (board.get(i, j).getChar() == 'p') {
					ImageIcon icon = new ImageIcon("blackPawn.png");
					newButton.setIcon(icon);
				}
				else if (board.get(i, j).getChar() == 'R') {
					ImageIcon icon = new ImageIcon("whiteCastle.png");
					newButton.setIcon(icon);
				}
				else if (board.get(i, j).getChar() == 'N') {
					ImageIcon icon = new ImageIcon("whiteKnight.png");
					newButton.setIcon(icon);
				}
				else if (board.get(i, j).getChar() == 'B') {
					ImageIcon icon = new ImageIcon("whiteBishop.png");
					newButton.setIcon(icon);
				}
				else if (board.get(i, j).getChar() == 'Q') {
					ImageIcon icon = new ImageIcon("whiteQueen.png");
					newButton.setIcon(icon);
				}
				else if (board.get(i, j).getChar() == 'K') {
					ImageIcon icon = new ImageIcon("whiteKing.png");
					newButton.setIcon(icon);
				}
				else if (board.get(i, j).getChar() == 'P') {
					ImageIcon icon = new ImageIcon("whitePawn.png");
					newButton.setIcon(icon);
				}
				else {
					// board at i j is a hyphen
					newButton = new JButton();
				}
				chessPieces[i][j] = newButton;
				if (i % 2 == 0) {
					if (count % 2 == 0)
						newButton.setBackground(lightGrey);
					else
						newButton.setBackground(darkGrey);
				}
				else {
					if (count % 2 == 0)
						newButton.setBackground(darkGrey);
					else
						newButton.setBackground(lightGrey);
				}
				
				newButton.addActionListener(chessPieceListener);
				gamePanel.add(newButton);
			}
		}
		gamePanel.updateUI();
		revalidate();
	}
	
	/**
	 * Currently the sidePanel only has a JLabel for info regarding the game and a reset button for returning
	 * the chess board to its original state. In the future, information about the logic behind each of the AI's
	 * moves will be added to explain to the user how it plays the game.
	 */
	private void constructSidePanel(){
		sidePanel.removeAll();
		sidePanel.setPreferredSize(new Dimension(220, 540));
		sidePanel.setLayout(new BorderLayout());
		sidePanel.add(infoMessage, BorderLayout.NORTH);
		SidePanelButtonListener sidePanelButtonListener = new SidePanelButtonListener();              
		resetButton = new JButton("reset the board");
		resetButton.addActionListener(sidePanelButtonListener);
		sidePanel.add(resetButton, BorderLayout.SOUTH);
		sidePanel.updateUI();
		revalidate();
	}
	
	/**
	 * For simplicity reasons, each JPanel has its own ActionListener for JButtons it contains.
	 * 
	 * @author Devan Karsann
	 */
	private class SidePanelButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton) e.getSource();
			if (source == resetButton) {
				board = new ChessBoard(user, aiPlayer);
				checkmateState = false;
				infoMessage.setText("select a move to start the game");
				RefreshGUI();
			}
		}
	}
	
	/**
	 * An instantiation of this class is used as the ActionListener object for JButtons in two-dimensional 
	 * array of JButtons representing the chess board in play.
	 * 
	 * @author Devan Karsann
	 */
	private class ChessPieceListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!checkmateState) {
				//System.out.println("user castling variables:\n" + "can do KSC: " + board.getPlayerWhite().canDoKSC() + "\ncan do QSC: " + board.getPlayerWhite().canDoQSC());
				JButton source = (JButton) e.getSource();
				for (int i = 0; i < dimension; i++){
					for (int j = 0; j < dimension; j++){
						if(chessPieces[i][j] == source) {
							//infoMessage.setText("row: " + i + " col: " + j + " was selected");
							//System.out.println("row: " + i + " col: " + j + " was selected");
							if (tileWasSelected) {
								if (moveIndices[0] == i && moveIndices[1] == j){
									break;
								}
								else if (board.get(i, j).getColor().equals("white")) {
									moveIndices[0] = i;
									moveIndices[1] = j;
								}
								else {
									tileWasSelected = false;
									moveIndices[3] = i;
									moveIndices[4] = j;
									MoveCommand userMove = new MoveCommand(moveIndices[0], moveIndices[1], moveIndices[3], moveIndices[4]);
									System.out.println("player selected the following move: " + userMove.toString());
									infoMessage.setText("");
									boolean moveWasFound = false;
									ArrayList<MoveCommand> acceptableMoves = new ArrayList<MoveCommand>();
									acceptableMoves = board.getPlayerWhite().getGoodMoves(board, board.getPlayerWhite().getColor());
									for (int k = 0; k < acceptableMoves.size() && !moveWasFound; k++) {
										if (acceptableMoves.get(k).toString().equals(userMove.toString()))
											moveWasFound = true;
									}
									if (moveWasFound) {
										// execute and repaint chess board
										board.executeMove(userMove);
										gamePanel.removeAll();
										constructGamePanel();
										gamePanel.updateUI();
										RefreshGUI();
										revalidate();
										// check for check mate for user
										acceptableMoves = new ArrayList<MoveCommand>();
										acceptableMoves = board.getPlayerBlack().getGoodMoves(board, "black");
										// check for check mate against aiPlayer								
										// looking for check mate state for player black
										if (acceptableMoves.size() == 0) {
											System.out.println(board.toString());
											System.out.println("Check mate! user wins!\nNo valid moves were found for aiPlayer.");
											infoMessage.setText("<html>Check mate! user wins!<br>reset the board to play again</html>");
											sidePanel.removeAll();
											constructSidePanel();
											sidePanel.updateUI();
											revalidate();
											checkmateState = true;
											pause(2500);
											continue;
										}
										System.out.println("board state for aiPlayer");
										System.out.println(board.toString());

										// new code for pausing in between turns
										pause(1000);

										// new code for ai
										aiPlayer01 ai = new aiPlayer01(board, turn);
										MoveCommand aiMove = ai.getaiMove();
										System.out.println(ai.getMoveInfo());
										// end of new code for ai
										// execute and repaint chess board
										moveWasFound = false;
										for (int k = 0; k < acceptableMoves.size() && !moveWasFound; k++) {
											if (acceptableMoves.get(k).toString().equals(aiMove.toString()))
												moveWasFound = true;
										}
										if (!moveWasFound){
											System.out.println("the AI selected an invalid move!!!\nwhat!\nthe!\nheck!");
											aiMove = acceptableMoves.get(0);
										}
										if (moveWasFound) {
											board.executeMove(aiMove);
											gamePanel.removeAll();
											constructGamePanel();	
											gamePanel.updateUI();
											revalidate();
											// check for check mate state for aiPlayer
											acceptableMoves = new ArrayList<MoveCommand>();
											acceptableMoves = board.getPlayerWhite().getGoodMoves(board, "white");
											if (acceptableMoves.size() == 0) {
												System.out.println(board.toString());
												System.out.println("Check mate! aiPlayer wins!\n No valid moves were found for user.\n\n");
												infoMessage.setText("<html>Check mate! aiPlayer wins!<br>reset the board to play again</html>");
												sidePanel.removeAll();
												constructSidePanel();
												sidePanel.updateUI();
												revalidate();
												checkmateState = true;
												pause(2500);
												continue;
											}
											if (board.getTileTaken() != ' ') {
												char tileTaken = board.getTileTaken();
												String pieceName = "";
												if (tileTaken == 'P')
													pieceName = "pawn";
												if (tileTaken == 'R')
													pieceName = "rook";
												if (tileTaken == 'B')
													pieceName = "bishop";
												if (tileTaken == 'N')
													pieceName = "knight";
												if (tileTaken == 'Q')
													pieceName = "queen";
												infoMessage.setText("aiPlayer captured " + pieceName + " from user");
												if (board.isInCheckState(user.getColor())) {
													infoMessage.setText(infoMessage.getText() + "<html><br>the user is in check</html>");
												}
											}
											else if (board.isInCheckState(user.getColor())) {
												infoMessage.setText("the user is in check");
											}
											System.out.println("board state for user");
											System.out.println(board.toString());
										}
//										else {
//											// this block of code should never execute
//											System.out.println("invalid ai move selected");
//											System.out.println(aiMove.toString());
//											pause(5000);
//											System.exit(0);
//										}
									turn++;
									}
									// this code might need to be uncommented later...
									else {
										infoMessage.setText("invalid user move selected");
									}
								}
							}
							else {
								moveIndices[0] = i;
								moveIndices[1] = j;
								// the toString of MoveCommands contains a space at index 2
								tileWasSelected = true;
							}
						}
					}
				}
				gamePanel.removeAll();
				constructGamePanel();
				sidePanel.removeAll();
				constructSidePanel();
				RefreshGUI();
		
			}
			else {
				infoMessage.setText("reset the board to play again");
			}
		}
	}

	/**
	 * This method pauses a program for the specified amount of milliseconds.
	 * 
	 * @param input
	 */
	public void pause(int input) {
		try {
			TimeUnit.MILLISECONDS.sleep(input);
		} catch (InterruptedException timeError) {
			timeError.printStackTrace();
		}
	}
	
}