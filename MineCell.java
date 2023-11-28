/**
* MineCell
* This class represents an InfoCell which is a cell that has a mine in it.
* A MineCell can have one of three statuses which are covered, uncovered, and marked.
* The values for the statuses can be found in the Configuration class.
* MineCell does not have any public fields.
* It has three non-public fields which are row, column, and status defaulted to covered.
*/
import java.awt.Image;
import java.awt.Graphics;
import javax.swing.ImageIcon;
public class MineCell {
  /**
  * an integer representing row
  */
  protected int row;
  /**
  * an integer representing column
  */
  protected int column;
  /**
  * a string representing status initialized with covered parameter
  */
  private String status = Configuration.STATUS_COVERED;
  /**
  * This is the default constructor to initialize row and column.
  */
  public MineCell(int row, int column){
    this.row = row;
    this.column = column;
  }
  /**
  * This method uses the Graphics class to invoke a method drawImage().
  * This method will be invoked by the Minefield's class draw method.
  * It's purpose is to draw an image that represents the status of the cell.
  */
  public void draw(Graphics g){
    g.drawImage(getImage(), getHorizontalPosition(), getVerticalPosition(), null);
  }
  /**
  * This calculates the pixel-level horizontal position of top-left corner of the cell and returns it.
  */
  public int getHorizontalPosition(){
    return Configuration.CELL_SIZE * (column);
  }
  /**
  * This calculates the pixel-level vertical position of top-left corner of the cell and returns it.
  */
  public int getVerticalPosition(){
    return Configuration.CELL_SIZE * (row);
  }
  /**
  * This returns the status field of the MineCell class.
  */
  public String getStatus(){
    return status;
  }
  /**
  * This sets the status field based on the parameter passed into the method.
  * It checks if the parameter is equal to a certain status in Configuration.
  * After checking, the status is set using the Configuration parameter.
  */
  public void setStatus(String status){
    if(status.equals(Configuration.STATUS_COVERED)){
      this.status = Configuration.STATUS_COVERED;
    }
    else if(status.equals(Configuration.STATUS_OPENED)){
      this.status = Configuration.STATUS_OPENED;
    }
    else if(status.equals(Configuration.STATUS_MARKED)){
      this.status = Configuration.STATUS_MARKED;
    }
  }
  /**
  * This returns an image representing the status of the MineCell.
  * Image objects are created using ImageIcon objects, using getImage.
  */
  public Image getImage(){
    if(getStatus().equals(Configuration.STATUS_COVERED)){
      ImageIcon a = new ImageIcon("img/covered_cell.png");
      return a.getImage();
    }
    else if(getStatus().equals(Configuration.STATUS_MARKED)){
      ImageIcon a = new ImageIcon("img/marked_cell.png");
      return a.getImage();
    }
    else if(getStatus().equals(Configuration.STATUS_OPENED)){
      ImageIcon a = new ImageIcon("img/mine_cell.png");
      return a.getImage();
    }
    else{
      ImageIcon a = new ImageIcon("img/covered_cell.png");
      return a.getImage();
    }
  }
}
