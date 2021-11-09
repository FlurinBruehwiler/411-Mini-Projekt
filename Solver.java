import java.util.Set;

public class Solver {
  private Table table;

  public int[][] solve(int[] clues) {
    table = new Table(clues);
    try {
      Position startPosition = table.getInitPosition();
      if (calculatePosition(startPosition)){
        return table.table;
      } 
      else {
        return null;
      }
    }catch (Exception e) { 
      return table.table; 
    }
  }

  private boolean calculatePosition(Position position) {
    Set<Integer> possibleValues = table.getPossibleValuesForPosition(position);
    for (Integer possibleValue : possibleValues) {
      table.setValueForPosition(position, possibleValue);
      try {
        Position nextPosition = table.getNextPosition(position);
        if(calculatePosition(nextPosition))
          return true;
        else
          table.setValueForPosition(position, 0);
      } catch (Exception e){ 
        return true;
      }
    }
    return false;
  }
}