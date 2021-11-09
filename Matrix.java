/**
 * Datum: 11.9.2021
 * Author: Flurin Brühwiler, Henrik Fäh
 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

class Matrix {
  int[][] matrix;
  Line[] horizontalLines = new Line[4];
  Line[] verticalLines = new Line[4];

  Matrix(int[] clues) {
    initmatrix();
    initLinesWithClues(clues);
  }

   /**
   * Descripition: Sets the given value at the given position
   * @param position
   * @param value
   * @return
   */
  void setValueForPosition(Position position, int value) {
    int x = position.x;
    int y = position.y;
    setValueForPosition(x, y, value);
    verticalLines[x].line[y] = value; 
    horizontalLines[y].line[x] = value; 
  }

  private void setValueForPosition(int x, int y, int value) {
    matrix[y][x] = value; 
  }

  /**
   * Descripition: gets the next Position
   * @param position
   * @return
   */
  Position getNextPosition(Position position){
    int x = position.x;
    int y = position.y;
    do {
      if (x == 3){
        x = 0;
        y++;
      }  
      else{
        x++;
      }
    } while (matrix[y][x] != 0);
    return new Position(x,y);
  }

  /**
  * Descripition: gets all the possible balues for a given position
  * @param position
  * @return
  */
  Set<Integer> getPossibleValuesForPosition(Position position) {
    int x = position.x;
    int y = position.y;
    Set<Integer> possibleValues = new HashSet<>();
    Set<Integer> possibleValuesForHorizontalLine = horizontalLines[y].getPossibleValuesForPosition(x); 
    Set<Integer> possibleValuesForVerticalLine = verticalLines[x].getPossibleValuesForPosition(y);
    possibleValues.addAll(possibleValuesForHorizontalLine);
    possibleValues.retainAll(possibleValuesForVerticalLine);
    return possibleValues;
  }

  /**
  * Descripition: creates all the lines
  * @param clues
  * @return
  */
  private void initLinesWithClues(int[] clues) {
    IntStream.range(0, 4).forEach(x -> verticalLines[x] = new Line(getVerticalLine(x), clues[x], clues[3*4-1-x]));
    synchmatrixWithVerticalLines();
    IntStream.range(0, 4).forEach(y -> horizontalLines[y] = new Line(getHorizontalLine(y), clues[4*4-1-y], clues[4+y]));
    synchmatrixWithHorizontalLines();
  }

  /**
  * Descripition: upates the matrix from the horizontal lines
  * @return
  */
  private void synchmatrixWithHorizontalLines() {
    for (int x = 0 ; x < 4 ; x++)
      for (int y = 0 ; y < 4 ; y++)
        setValueForPosition(x, y, horizontalLines[y].line[x]);
  }

  /**
  * Descripition: upates the matrix from the vertical lines
  * @return
  */
  private void synchmatrixWithVerticalLines() {
    for (int x = 0 ; x < 4 ; x++)
      for (int y = 0 ; y < 4 ; y++)
        setValueForPosition(x, y, verticalLines[x].line[y]);
  }

  /**
  * Descripition: gets the vertical line from matrix
  * @param x
  * @return
  */
  private int[] getVerticalLine(int x) {
    int[] line = new int[4];
    IntStream.range(0, 4).forEach(y -> line[y] = matrix[y][x]);
    return line;
  }

  /**
  * Descripition: gets the horizontal line from matrix
  * @param y
  * @return
  */
  private int[] getHorizontalLine(int y) {
    int[] line = new int[4];
    IntStream.range(0, 4).forEach(x -> line[x] = matrix[y][x]);
    return line;
  }


  /**
   * Description: creates Matrix with 0s
   */
  private void initmatrix() {
    matrix = new int[4][4];
    IntStream.range(0, 4).forEach(i -> Arrays.fill(matrix[i], 0));
  }
}