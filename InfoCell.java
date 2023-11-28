/**
* InfoCell
* This class represents an InfoCell which is a cell that doesn't have a mine in it but provides info about the 8 adjacent cells.
* An InfoCell can have one of four statuses which are covered, uncovered, marked, and wrongly_marked.
* The values for the statuses can be found in the Configuration class.
* InfoCell does not have any public fields.
* It has four non-public fields which are row, column, status defaulted to covered, and numOfAdjacentMines
*/
import java.awt.Image;
import java.awt.Graphics;
import javax.swing.ImageIcon;
public class InfoCell{
  /**
  * an integer representing row
  */
  protected int row;
  /**
  * an integer representing column
  */
  protected int column;
  /**
  * a string representing the status initialized with covered
  */
  private String status = Configuration.STATUS_COVERED;
  /**
  * an integer representing the number of mines adjacent to current cell
  */
  private int numOfAdjacentMines;
  /**
  * This is the default constructor to initialize row, column, and numofadjacentmines.
  */
  public InfoCell(int row, int column, int numOfAdjacentMines){
    this.row = row;
    this.column = column;
    this.numOfAdjacentMines = numOfAdjacentMines;
  }
  /**
  * This is the same as MineCell's similarly named method.
  */
  public void draw(Graphics g){
    g.drawImage(getImage(), getHorizontalPosition(), getVerticalPosition(), null);
  }
  /**
  * This is the same as MineCell's similarly named method.
  */
  public int getHorizontalPosition(){
    return Configuration.CELL_SIZE * (column);
  }
  /**
  * This is the same as MineCell's similarly named method.
  */
  public int getVerticalPosition(){
    return Configuration.CELL_SIZE * (row);
  }
  /**
  * This is the same as MineCell's similarly named method.
  */
  public String getStatus(){
    return status;
  }
  /**
  * This method is similar to the similarly named method in MineCell but the difference is that it has four values to check instead of three.
  * It takes the status parameter and checks if it is equal to any of the four statuses from the Configuration parameters and changes the status.
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
    else if(status.equals(Configuration.STATUS_WRONGLY_MARKED)){
      this.status = Configuration.STATUS_WRONGLY_MARKED;
    }
  }
  /**
  * This determines the image to return to represent the InfoCell from a set of 12 images, similar to MineCell's getImage method.
  * This uses the getStatus method and checks if it is equal to a certain status and returns an image based on that status.
  * If the status is opened, then it also checks the value of the number of adjacent mines and returns based on both conditions.
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
    else if(getStatus().equals(Configuration.STATUS_WRONGLY_MARKED)){
      ImageIcon a = new ImageIcon("img/wrong_mark.png");
      return a.getImage();
    }
    else if(getStatus().equals(Configuration.STATUS_OPENED) && numOfAdjacentMines == 0){
      ImageIcon a = new ImageIcon("img/info_0.png");
      return a.getImage();
    }
    else if(getStatus().equals(Configuration.STATUS_OPENED) && numOfAdjacentMines == 1){
      ImageIcon a = new ImageIcon("img/info_1.png");
      return a.getImage();
    }
    else if(getStatus().equals(Configuration.STATUS_OPENED) && numOfAdjacentMines == 2){
      ImageIcon a = new ImageIcon("img/info_2.png");
      return a.getImage();
    }
    else if(getStatus().equals(Configuration.STATUS_OPENED) && numOfAdjacentMines == 3){
      ImageIcon a = new ImageIcon("img/info_3.png");
      return a.getImage();
    }
    else if(getStatus().equals(Configuration.STATUS_OPENED) && numOfAdjacentMines == 4){
      ImageIcon a = new ImageIcon("img/info_4.png");
      return a.getImage();
    }
    else if(getStatus().equals(Configuration.STATUS_OPENED) && numOfAdjacentMines == 5){
      ImageIcon a = new ImageIcon("img/info_5.png");
      return a.getImage();
    }
    else if(getStatus().equals(Configuration.STATUS_OPENED) && numOfAdjacentMines == 6){
      ImageIcon a = new ImageIcon("img/info_6.png");
      return a.getImage();
    }
    else if(getStatus().equals(Configuration.STATUS_OPENED) && numOfAdjacentMines == 7){
      ImageIcon a = new ImageIcon("img/info_7.png");
      return a.getImage();
    }
    else if(getStatus().equals(Configuration.STATUS_OPENED) && numOfAdjacentMines == 8){
      ImageIcon a = new ImageIcon("img/info_8.png");
      return a.getImage();
    }
    else{
      ImageIcon a = new ImageIcon("img/covered_cell.png");
      return a.getImage();
    }
  }
  /**
  * A getter method for the variable numOfAdjacentMines.
  */
  public int getNumOfAdjacentMines(){
    return numOfAdjacentMines;
  }
}
