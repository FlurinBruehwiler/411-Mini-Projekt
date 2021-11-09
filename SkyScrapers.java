import java.util.Set;

public class SkyScrapers {
  static int[][] solvePuzzle (int[] clues) {
    return new SkyScrapers().solve(clues);
  }

  private Table table;

  private int[][] solve(int[] clues) {
    table = new Table(clues);
    try {
      Position initPosition = table.getInitPosition();
      if (iter(initPosition)){
        return table.table;
      } 
      else {
        throw new RuntimeException("no solution");
      }
    }catch (Exception e) { 
      return table.table; 
    }
  }

  private boolean iter(Position position) {
    Set<Integer> possibleValues = table.getPossibleValuesForPosition(position);
    for (Integer possibleValue : possibleValues) {
      table.setValueForPosition(position, possibleValue);
      try {
        Position nextPosition = table.getNextPosition(position);
        if(iter(nextPosition))
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