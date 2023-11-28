/**
* Board
* This is the Board class which is the foundation for the whole game.
* It controls the board to be displayed, the statusbar at the bottom of the window, the mouseclicks, etc.
* This class inherits from JPanel
* This class has no public fields
*/
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.Dimension;

public class Board extends JPanel
{
	/**
	* integer variable representing height
	*/
	private int height;
	/**
	* integer variable representing width
	*/
	private int width;
	/**
	* integer variable representing mines
	*/
	private int mines;
	/**
	* jlabel variable representing statusbar
	*/
	private JLabel statusbar;
	/**
	* minefield variable representing the minefield of the board
	*/
	private Minefield boardfield;
	/**
	* This is the default constructor taking in four parameters and initializing all fields and making a new minefield.
	*/
	public Board(int height, int width, int mines, JLabel statusbar)
	{
		this.height = height;
		this.width = width;
		this.mines = mines;
		this.statusbar = statusbar;
		boardfield = new Minefield(height, width, Configuration.MINES);
		setPreferredSize(new Dimension(Configuration.BOARD_WIDTH, Configuration.BOARD_HEIGHT));
		addMouseListener(new MouseReader(this));
	}
	/**
	* This method is called automatically when Board paints or repaints all components on screen.
	*/
	@Override
	public void paintComponent(Graphics g)
	{
		boardfield.draw(g);
	}
	/**
	* A getter method for minefield.
	*/
	public Minefield getMinefield(){
		return boardfield;
	}
	/**
	* determines when a game is over or not and returns a boolean if it is true or false
	*/
	public boolean isGameOver(){
		for(int i = 0; i < height; ++i){
			for(int j = 0; j < width; ++j){
				Object check = boardfield.getCellByRowCol(i, j);
				if(check instanceof MineCell){
					if(((MineCell)check).getStatus().equals(Configuration.STATUS_OPENED)){
						boardfield.revealIncorrectMarkedCells();
						setStatusbar("Game over - You lost!");
						getStatusbar();
						return true;
					}
				}
			}
		}
		/**
		* minecount is used to check if it adds up to the configuration parameter mines
		*/
		int mineCount = 0;
		for(int i = 0; i < height; ++i){
			for(int j = 0; j < width; ++j){
				Object check = boardfield.getCellByRowCol(i, j);
				if(check instanceof MineCell){
					if(((MineCell)check).getStatus().equals(Configuration.STATUS_MARKED)){
						mineCount = mineCount + 1;
				}
			}
		}
	}
		for(int i = 0; i < height; ++i){
			for(int j = 0; j < width; ++j){
				Object check = boardfield.getCellByRowCol(i, j);
				if(check instanceof InfoCell){
					if(((InfoCell)check).getStatus().equals(Configuration.STATUS_COVERED)){
						return false;
				}
			}
		}
	}
	if(mineCount == Configuration.MINES){
		setStatusbar("Game over - You won!");
		getStatusbar();
		return true;
	}
	return false;
	}
	/**
	* sets the statusbar using settext of the jlabel class
	*/
	public void setStatusbar(String text){
		statusbar.setText(text);
	}
	/**
	* gets the statusbar using gettext of the jlabel class
	*/
	public String getStatusbar(){
		return statusbar.getText();
	}
	/**
	* this determines the logic of removing a mine and sets the status bar accordingly, performs action on mines if true
	*/
	public boolean removeMine(){
		if(mines > 0){
			mines = mines - 1;
			setStatusbar("" + mines + " mines remaining");
			return true;

		}
		else{
			setStatusbar("Invalid action");
			return false;
		}
	}
	/**
	* this determines the logic of adding a mine and sets the status bar accordingly, performs action on mines if true
	*/
	public boolean addMine(){
		if(mines < Configuration.MINES){
			mines = mines + 1;
			setStatusbar("" + mines + " mines remaining");
			return true;
		}
		else{
			setStatusbar("Invalid action");
			return false;
		}
	}
	/**
	* this method is called when a mouse button is clicked. it uses methods from other classes to determines cell
	* location using the x and y coordinates, depending on whether a cell is left clicked or right clicked, an action is conducted
	* based on the status of the cell
	*/
	public void mouseClickOnLocation(int x, int y, String button)
	{
		Object chad = boardfield.getCellByScreenCoordinates(x, y);
		if(button.equals("left")){
			if(chad instanceof MineCell){
				((MineCell)chad).setStatus(Configuration.STATUS_OPENED);
				isGameOver();
			}
			else if(chad instanceof InfoCell){
				if(((InfoCell)chad).getStatus().equals(Configuration.STATUS_MARKED)){
					((InfoCell)chad).setStatus(Configuration.STATUS_OPENED);
					addMine();
					isGameOver();
				}
				else if(((InfoCell)chad).getStatus().equals(Configuration.STATUS_OPENED) && ((InfoCell)chad).getNumOfAdjacentMines() == 0){
					boardfield.openCells(chad);
					isGameOver();
				}
				else{
					((InfoCell)chad).setStatus(Configuration.STATUS_OPENED);
					isGameOver();
				}
			}
		}
		else if(button.equals("right")){
			if(chad instanceof MineCell){
				if(((MineCell)chad).getStatus().equals(Configuration.STATUS_COVERED)){
					((MineCell)chad).setStatus(Configuration.STATUS_MARKED);
					removeMine();
					isGameOver();
				}
				else if(((MineCell)chad).getStatus().equals(Configuration.STATUS_MARKED)){
					((MineCell)chad).setStatus(Configuration.STATUS_COVERED);
					addMine();
				}
			}
			else if(chad instanceof InfoCell){
				if(((InfoCell)chad).getStatus().equals(Configuration.STATUS_COVERED)){
					((InfoCell)chad).setStatus(Configuration.STATUS_MARKED);
					removeMine();
					isGameOver();
				}
				else if(((InfoCell)chad).getStatus().equals(Configuration.STATUS_MARKED)){
					((InfoCell)chad).setStatus(Configuration.STATUS_COVERED);
					addMine();
				}
			}
		}
		repaint();
	}
}
