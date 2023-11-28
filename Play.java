/**
 * Lines 17 and 24 are the only ones you must edit
*/
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Play
{
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
			{
				public void run()
				{
					Configuration.loadParameters(args[0]);
					JFrame frame = new JFrame();
					JLabel statusbar = new JLabel("select a cell");
					frame.add(statusbar, BorderLayout.SOUTH);
					frame.add(new Board(Configuration.ROWS, Configuration.COLS, Configuration.MINES, statusbar));
					frame.setResizable(false);
					frame.pack();
					frame.setTitle("Minefield for CS211 Spring!");
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
				}
			});
	}
}
