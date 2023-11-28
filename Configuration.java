/**
* Configuration
*
* This is the configuration class, which has many public static fields to represent the setting of the board used for minesweeper.
* It only has one method, loadParameters, which reads from a file using a scanner and initializes each of the fields in Configuration.
*/
import java.util.Scanner;
import java.io.File;
import java.util.Random;
public class Configuration {
  /**
  * Number of rows of cells on the board of the game
  */
  public static int ROWS;
  /**
  * Number of columns of cells on the board of the game
  */
  public static int COLS;
  /**
  * width/height of the square-size cell (in pixels)
  */
  public static int CELL_SIZE;
  /**
  * Number of mines hidden in the minefield
  */
  public static int MINES;
  /**
  * The width of the board in pixels. It’s calculated by the formula:
  * number of columns * width of the cell + 1
  */
  public static int BOARD_WIDTH;
  /**
  * The height of the board in pixels. It’s calculated by the formula:
  * number of rows * height of the cell + 1
  */
  public static int BOARD_HEIGHT;
  /**
  * A string that represents the tag we use in the code when a cell is
  * hidden.
  */
  public static String STATUS_COVERED;
  /**
  * A string that represents the tag we use in the code when a cell is
  * uncovered.
  */
  public static String STATUS_OPENED;
  /**
  * A string that represents the tag we use in the code when a cell is
  * marked as a mine.
  */
  public static String STATUS_MARKED;
  /**
  * A string that represents the tag we use in the code when a cell is
  * wrongly marked as a mine.
  */
  public static String STATUS_WRONGLY_MARKED;
  /**
  * This is the method opening the file based on the parameter then reads and stores the values into their respective variables.
  * It checks each line, separates the line by a space character, and checks index 0 if its equal to a certain string then uses
  * index 1 to initialize the respective field.
  */
  public static void loadParameters(String filename){
    try{
      File input = new File(filename);
      Scanner inScan = new Scanner(input);
      while(inScan.hasNextLine()){
        String y = inScan.nextLine();
        String[] x = y.split(" ");
        if(x[0].equals("ROWS")){
          ROWS = Integer.parseInt(x[1]);
        }
        else if(x[0].equals("COLS")){
          COLS = Integer.parseInt(x[1]);
        }
        else if(x[0].equals("MINES")){
          MINES = Integer.parseInt(x[1]);
        }
        else if(x[0].equals("CELL_SIZE")){
          CELL_SIZE = Integer.parseInt(x[1]);
        }
        else if(x[0].equals("STATUS_OPENED")){
          STATUS_OPENED = x[1];
        }
        else if(x[0].equals("STATUS_COVERED")){
          STATUS_COVERED = x[1];
        }
        else if(x[0].equals("STATUS_MARKED")){
          STATUS_MARKED = x[1];
        }
        else if(x[0].equals("STATUS_WRONGLY_MARKED")){
          STATUS_WRONGLY_MARKED = x[1];
        }
      }
      inScan.close();
    }
    catch (Exception e){

    }
    /**
    * After all of the other variables are initialized, the board width and height are calculated based on the values of cell_size, rows, and cols.
    */
    BOARD_WIDTH = (COLS * CELL_SIZE) + 1;
    BOARD_HEIGHT = (ROWS * CELL_SIZE) + 1;
  }

}
