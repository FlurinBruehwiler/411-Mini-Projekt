/**
 * Datum: 11.9.2021
 * Author: Flurin Brühwiler, Henrik Fäh
 */
import java.util.Set;

public class Solver {
  private Matrix matrix;

  /**
   * Descripition: gets the clues and returns the solved matrix or if there is no solution, null;
   * @param clues
   * @return
   */
  public int[][] solve(int[] clues) {
    matrix = new Matrix(clues);
    try {
      Position startPosition = new Position(0,0);
      if (calculatePosition(startPosition)){
        return matrix.matrix;
      } 
      else {
        return null;
      }
    }catch (Exception e) { 
      return matrix.matrix; 
    }
  }

  /**
   * Descripition: calculates the value of a certain position, returns true if the maze was solved;
   * @param clues
   * @return
   */
  private boolean calculatePosition(Position position) {
    Set<Integer> possibleValues = matrix.getPossibleValuesForPosition(position);
    for (Integer possibleValue : possibleValues) {
      matrix.setValueForPosition(position, possibleValue);
      
      Position nextPosition = matrix.getNextPosition(position);
      if(calculatePosition(nextPosition))
        return true;
      else
        matrix.setValueForPosition(position, 0);
      
    }
    return false;
  }
}