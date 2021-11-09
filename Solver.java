import java.util.Set;

public class Solver {
  private Matrix matrix;

  public int[][] solve(int[] clues) {
    matrix = new Matrix(clues);
    try {
      Position startPosition = matrix.getInitPosition();
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

  private boolean calculatePosition(Position position) {
    Set<Integer> possibleValues = matrix.getPossibleValuesForPosition(position);
    for (Integer possibleValue : possibleValues) {
      matrix.setValueForPosition(position, possibleValue);
      try {
        Position nextPosition = matrix.getNextPosition(position);
        if(calculatePosition(nextPosition))
          return true;
        else
          matrix.setValueForPosition(position, 0);
      } catch (Exception e){ 
        return true;
      }
    }
    return false;
  }
}