/**
* Minefield
* This is the Minefield class and the brains of the minesweeper game.
* It creates and manages a 2d array of cell objects drawn on the board.
* It also performs all major computations in the game.
* It has one field, which is a 2d object array.
*/
import java.util.Random;
import java.awt.Graphics;
public class Minefield{
  /**
  * field is a 2d object array that exists as the only instance field for class Minefield
  */
  private Object[][] field;
  /**
  * This is a non-parametric constructor used to initialize the board with 10 rows
  * and 10 columns.
  */
  public Minefield(){
    field = new Object[10][10];
    mineLaying(10);
    addInfoCells();
  }
  /**
  * This is a parametric constructor taking in three integer parameters.
  * Those parameters being number of rows, number of columns, and number of mines.
  * It uses the number of rows and columns to initialize the field array.
  * It uses numMines to lay mines using the minelaying method and then adds info cells.
  */
  public Minefield(int numRows, int numColumns, int numMines){
    field = new Object[numRows][numColumns];
    mineLaying(numMines);
    addInfoCells();
  }
  /**
  * This instantiates numOfMines objects of type MineCell and places them
  * in random locations using the Random class.
  */
  public void mineLaying(int numOfMines){
    /**
    * r represents a Random variable which will generate random integer variables
    */
    Random r = new Random();
    for(int i = 0; i < numOfMines; ++i){
      int randomX = r.nextInt(field.length);
      int randomY = r.nextInt(field[0].length);
      if(field[randomX][randomY] instanceof MineCell == false){
        field[randomX][randomY] = new MineCell(randomX, randomY);
      }
      else{
        int randomXZ = r.nextInt(field.length);
        int randomYZ = r.nextInt(field[0].length);
        if(field[randomXZ][randomYZ] instanceof MineCell == false){
          field[randomXZ][randomYZ] = new MineCell(randomXZ, randomYZ);
        }
        else{
          int randomXZX = r.nextInt(field.length);
          int randomYZY = r.nextInt(field[0].length);
          if(field[randomXZX][randomYZY] instanceof MineCell == false){
            field[randomXZX][randomYZY] = new MineCell(randomXZX, randomYZY);
          }
        }
      }
    }
  }
  /**
  * This method fills in any cells that aren't MineCell with InfoCell objects.
  * It also determines the number of adjacent mines based on the position its checking.
  * The method heavily relies on instanceof to check if a certain index is a MineCell or not.
  * The first four if statements check if they're corners.
  * The next four if statements check if they're on the border but not corners.
  * The last else statement is used when an index is neither a corner or a border.
  */
  public void addInfoCells(){
    for(int i = 0; i < field.length; ++i){
      for(int j = 0; j < field[0].length; ++j){
        int adjMines = 0;
        if(field[i][j] == null){
          if(i == 0 && j == 0){
            if(field[i][j + 1] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i+1][j] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i+1][j+1] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            field[i][j] = new InfoCell(i, j, adjMines);
          }
          else if(i == 0 && j == field[0].length - 1){
            if(field[i][j-1] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i+1][j-1] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i+1][j] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            field[i][j] = new InfoCell(i, j, adjMines);
          }
          else if(i == field.length - 1 && j == 0){
            if(field[i][j+1] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i-1][j+1] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i-1][j] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            field[i][j] = new InfoCell(i, j, adjMines);
          }
          else if(i == field.length - 1 && j == field[0].length - 1){
            if(field[i-1][j-1] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i-1][j] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i][j-1] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            field[i][j] = new InfoCell(i, j, adjMines);
          }
          else if(i == 0){
            if(field[i][j-1] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i][j+1] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i+1][j] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i+1][j+1] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i+1][j-1] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            field[i][j] = new InfoCell(i, j, adjMines);
          }
          else if(j == 0){
            if(field[i-1][j] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i+1][j] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i-1][j+1] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i+1][j+1] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i][j+1] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            field[i][j] = new InfoCell(i, j, adjMines);
          }
          else if(i == field.length - 1){
            if(field[i][j-1] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i][j+1] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i-1][j] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i-1][j+1] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i-1][j-1] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            field[i][j] = new InfoCell(i, j, adjMines);
          }
          else if(j == field[0].length - 1){
            if(field[i-1][j] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i+1][j] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i][j-1] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i-1][j-1] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i+1][j-1] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            field[i][j] = new InfoCell(i, j, adjMines);
          }
          else{
            if(field[i-1][j] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i+1][j] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i][j+1] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i][j-1] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i+1][j-1] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i+1][j+1] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i-1][j-1] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            if(field[i-1][j+1] instanceof MineCell){
              adjMines = adjMines + 1;
            }
            field[i][j] = new InfoCell(i, j, adjMines);
          }
        }
        else{
          continue;
        }
      }
    }
  }
  /**
  * This goes through every object in the minefield and invokes the draw method
  * based on whether it is an infocell or minecell object.
  */
  public void draw(Graphics g){
    for(int i = 0; i < field.length; ++i){
      for(int j = 0; j < field[0].length; ++j){
        if(field[i][j] instanceof MineCell == true){
          ((MineCell)field[i][j]).draw(g);
        }
        else if(field[i][j] instanceof InfoCell == true){
          ((InfoCell)field[i][j]).draw(g);
        }
      }
    }
  }
  /**
  * This checks the cell object or index that corresponds to the mouse clicked
  * location on the board and returns the cell at that index.
  */
  public Object getCellByScreenCoordinates(int x, int y){
    /**
    * detX represents the destination X and is used to determine where the cell is located
    */
    int detX = 0;
    /**
    * detY represents the destination Y and is used to determine where the cell is located
    */
    int detY = 0;
    if(((double)x / (double)Configuration.CELL_SIZE) < 1){
      detX = 0;
    }
    if(((double)y / (double)Configuration.CELL_SIZE) < 1){
      detY = 0;
    }
    if(((double)x % (double)Configuration.CELL_SIZE) == 0){
      detX = x / Configuration.CELL_SIZE;
    }
    if(((double)y % (double)Configuration.CELL_SIZE) == 0){
      detY = y / Configuration.CELL_SIZE;
    }
    if(((double)x % (double)Configuration.CELL_SIZE) != 0){
      detX = (int)((x - ((double)x % (double)Configuration.CELL_SIZE)));
      detX = detX / Configuration.CELL_SIZE;
    }
    if(((double)y % (double)Configuration.CELL_SIZE) != 0){
      detY = (int)((y - ((double)y % (double)Configuration.CELL_SIZE)));
      detY = detY / Configuration.CELL_SIZE;
    }
    return field[detY][detX];
  }
  /**
  * This method returns an object that is at an index based on given row and col.
  */
  public Object getCellByRowCol(int row, int col){
    return field[row][col];
  }
  /**
  * This is a setter method of a MineCell object.
  */
  public void setMineCell(int row, int col, MineCell cell){
    field[row][col] = cell;
  }
  /**
  * This is a setter method of an InfoCell object.
  */
  public void setInfoCell(int row, int col, InfoCell cell){
    field[row][col] = cell;
  }
  /**
  * Counts the number of cells that have a certain status and returns that number.
  */
  public int countCellsWithStatus(String status){
    /**
    * counter represents an integer variable that will be returned by this method representing the number of cells with status
    */
    int counter = 0;
    for(int i = 0; i < field.length; ++i){
      for (int j = 0; j < field[0].length; ++j){
        if(field[i][j] instanceof MineCell == true){
          if(((MineCell)field[i][j]).getStatus().equals(status)){
            counter = counter + 1;
          }
        }
        else if(field[i][j] instanceof InfoCell == true){
          if(((InfoCell)field[i][j]).getStatus().equals(status)){
            counter = counter + 1;
          }
        }
      }
    }
    return counter;
  }
  /**
  * This opens all 8 adjacent cells when a cell opened is not a mine and has
  * 0 mines in its adjacency.
  */
  public void openCells(Object cell){
    int cRow = ((InfoCell)cell).row;
    int cCol = ((InfoCell)cell).column;
    if(((InfoCell)cell).row == 0 && ((InfoCell)cell).column == 0){
      ((InfoCell)field[cRow][cCol+1]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow+1][cCol]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow+1][cCol+1]).setStatus(Configuration.STATUS_OPENED);
    }
    else if(cRow == 0 && cCol == field[0].length - 1){
      ((InfoCell)field[cRow][cCol-1]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow+1][cCol-1]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow+1][cCol]).setStatus(Configuration.STATUS_OPENED);
    }
    else if(cRow == field.length - 1 && cCol == 0){
      ((InfoCell)field[cRow-1][cCol]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow-1][cCol+1]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow][cCol+1]).setStatus(Configuration.STATUS_OPENED);
    }
    else if(cRow == field.length - 1 && cCol == field[0].length - 1){
      ((InfoCell)field[cRow-1][cCol-1]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow-1][cCol]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow][cCol-1]).setStatus(Configuration.STATUS_OPENED);
    }
    else if(cRow == 0){
      ((InfoCell)field[cRow][cCol+1]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow][cCol-1]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow+1][cCol]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow+1][cCol+1]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow+1][cCol-1]).setStatus(Configuration.STATUS_OPENED);
    }
    else if(cRow == field.length - 1){
      ((InfoCell)field[cRow][cCol+1]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow][cCol-1]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow-1][cCol]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow-1][cCol+1]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow-1][cCol-1]).setStatus(Configuration.STATUS_OPENED);
    }
    else if(cCol == 0){
      ((InfoCell)field[cRow-1][cCol]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow+1][cCol]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow][cCol+1]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow-1][cCol+1]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow+1][cCol+1]).setStatus(Configuration.STATUS_OPENED);
    }
    else if(cCol == field[0].length - 1){
      ((InfoCell)field[cRow-1][cCol]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow+1][cCol]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow][cCol-1]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow-1][cCol-1]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow+1][cCol-1]).setStatus(Configuration.STATUS_OPENED);
    }
    else{
      ((InfoCell)field[cRow-1][cCol]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow+1][cCol]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow][cCol-1]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow][cCol+1]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow-1][cCol-1]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow+1][cCol+1]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow-1][cCol+1]).setStatus(Configuration.STATUS_OPENED);
      ((InfoCell)field[cRow+1][cCol-1]).setStatus(Configuration.STATUS_OPENED);
    }
  }
  public void revealIncorrectMarkedCells(){
    for(int i = 0; i < field.length; ++i){
      for(int j = 0; j < field[0].length; ++j){
        if(field[i][j] instanceof InfoCell){
          if(((InfoCell)field[i][j]).getStatus().equals(Configuration.STATUS_MARKED)){
            ((InfoCell)field[i][j]).setStatus(Configuration.STATUS_WRONGLY_MARKED);
          }
        }
      }
    }
  }
}
