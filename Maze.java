import java.util.*;
import java.io.*;
public class Maze{


  private char[][]maze;
  private boolean animate;//false by default

  /*Constructor loads a maze text file, and sets animate to false by default.

  1. The file contains a rectangular ascii maze, made with the following 4 characters:
  '#' - Walls - locations that cannot be moved onto
  ' ' - Empty Space - locations that can be moved onto
  'E' - the location of the goal (exactly 1 per file)

  'S' - the location of the start(exactly 1 per file)

  2. The maze has a border of '#' around the edges. So you don't have to check for out of bounds!


  3. When the file is not found OR the file is invalid (not exactly 1 E and 1 S) then:

  throw a FileNotFoundException or IllegalStateException

  */

  public Maze(String filename) throws FileNotFoundException{


    int countE =0;
    int countS = 0;
    animate = false;
    File file = new File(filename);
    Scanner scr = new Scanner(file);
    if(! scr.hasNextLine()){
      throw new IllegalStateException("Not exactly one start+one end");
    }
    String line = scr.nextLine();
    int countCols=line.length();
    int countRows =1;
    while(scr.hasNextLine()){
      line = scr.nextLine();
      countRows++;
    }
    System.out.println(countRows+" "+countCols);
    scr.close();
    Scanner scr2 = new Scanner(file);
    maze = new char[countRows][countCols];
    int i=0;
    int j=0;
    while(scr2.hasNextLine()){
      String lin = scr2.nextLine();
      j=0;
      while(j<countCols){
        char chr = lin.charAt(j);
        if(chr=='E')countE++;
        if(chr=='S')countS++;
        maze[i][j]=chr;
        j++;
      }
      i++;
    }
    scr2.close();
    if(countE!=1 || countS!=1) throw new IllegalStateException("Not exactly one start+one end");
  }

  private void wait(int millis){
    try {
      Thread.sleep(millis);
    }
    catch (InterruptedException e) {
    }
  }


  public void setAnimate(boolean b){

    animate = b;

  }


  public void clearTerminal(){

    //erase terminal, go to top left of screen.

    System.out.println("\033[2J\033[1;1H");

  }






  /*Return the string that represents the maze.

  It should look like the text file with some characters replaced.

  */
  public String toString(){
    String returner ="";
    for(char [] seq: maze){
      for(char chr:seq){
        returner+=chr;
      }
      returner+='\n';
    }
    return returner;
  }



  /*Wrapper Solve Function returns the helper function

  Note the helper function has the same name, but different parameters.
  Since the constructor exits when the file is not found or is missing an E or S, we can assume it exists.

  */
  public int solve(){
    for(int i =0;i<maze.length;i++){
      for(int j=0;j<maze[0].length;j++){
        if(maze[i][j]=='S'){
          maze[i][j]='@';
          if(maze[i][j+1]==' ' && solve(i,j+1) !=-1)
            return solve(i,j+1);
          if(maze[i-1][j]==' ' && solve(i-1,j)!=-1)
            return solve(i-1,j);
          if(maze[i+1][j]==' ' && solve(i+1,j)!=-1)
            return solve(i+1,j);;
          if(maze[i][j-1]==' ' && solve(i,j-1)!=-1)
            return solve(i,j-1);
          maze[i][j]='.';
        }
      }
    }
    return -1;
  }

  /*
  Recursive Solve function:

  A solved maze has a path marked with '@' from S to E.

  Returns the number of @ symbols from S to E when the maze is solved,
  Returns -1 when the maze has no solution.


  Postcondition:

  The S is replaced with '@' but the 'E' is not.

  All visited spots that were not part of the solution are changed to '.'

  All visited spots that are part of the solution are changed to '@'
  */
  private int solve(int row, int col){ //you can add more parameters since this is private
    int i = row;
    int j = col;
    if(animate){
      clearTerminal();
      System.out.println(this);
      wait(20);
    }

    if(maze[row][col]=='E'){
      int charCount=0;
      for(char [] seq: maze){
        for(char chr:seq){
          if(chr=='@'){
            charCount++;
          }
        }
      }
      return charCount;
    }
    maze[i][j]='@';
    if("E ".contains(maze[i][j+1]+"") && solve(i,j+1)!=-1){
          return solve(i,j+1);
    }
    if("E ".contains(maze[i-1][j]+"") && solve(i-1,j)!=-1){
          return solve(i-1,j);
    }
    if("E ".contains(maze[i+1][j]+"") && solve(i+1,j)!=-1){
          return solve(i+1,j);
    }
    if("E ".contains(maze[i][j-1]+"") && solve(i,j-1)!=-1){
          return solve(i,j-1);
    }
    maze[i][j]='.';

    return -1; //so it compiles
  }
}
