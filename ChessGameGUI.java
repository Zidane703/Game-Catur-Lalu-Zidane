import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * @author L.Zidane collab by Devan
 */
public class ChessGameGUI
{
	/* 
	 * Creates a JFrame and adds the main JPanel to the JFrame.
	 * @param args (unused)
	 */
	public static void main(String args[])
	{
		try { // used to make appearance the same across multiple platforms
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame frame = new JFrame("chess by devan");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (args.length == 0) // default chess board setup
			frame.getContentPane().add(new ChessGameWindow());
		else // customized chess board setup based on correctly formatted text file
			frame.getContentPane().add(new ChessGameWindow(args[0]));
		frame.setPreferredSize(new Dimension(760, 540));
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false); // size of window is locked to maintain JPanel proportions
	}
}