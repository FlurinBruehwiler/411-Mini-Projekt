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

  private Integer getValueForPosition(int x, int y) {
    return matrix[y][x]; 
  }

  void setValueForPosition(Position position, int value) {
    int x = position.getX();
    int y = position.getY();
    setValueForPosition(x, y, value);
    verticalLines[x].line[y] = value; 
    horizontalLines[y].line[x] = value; 
  }

  private void setValueForPosition(int x, int y, int value) {
    matrix[y][x] = value; 
  }

  Position getInitPosition() throws Exception {
    Position position = new Position(0,0);
    if (getValueForPosition(0,0) != 0){
      position = getNextPosition(position);
    } 
    return position;  
  }

  Position getNextPosition(Position position) throws Exception {
    int x = position.getX();
    int y = position.getY();
    do {
      if (x == 3)
        if (y == 3)
          throw new Exception();
        else {
          x = 0;
          y++;
        }
      else
        x++;
    } while (getValueForPosition(x,y) != 0);
    return new Position(x,y);
  }

  Set<Integer> getPossibleValuesForPosition(Position position) {
    int x = position.getX();
    int y = position.getY();
    Set<Integer> possibleValues = new HashSet<>();
    Set<Integer> possibleValuesForHorizontalLine = horizontalLines[y].getPossibleValuesForPosition(x); 
    Set<Integer> possibleValuesForVerticalLine = verticalLines[x].getPossibleValuesForPosition(y);
    possibleValues.addAll(possibleValuesForHorizontalLine);
    possibleValues.retainAll(possibleValuesForVerticalLine);
    return possibleValues;
  }

  private void initLinesWithClues(int[] clues) {
    IntStream.range(0, 4).forEach(x -> verticalLines[x] = new Line(getVerticalLine(x), clues[x], clues[3*4-1-x]));
    synchmatrixWithVerticalLines();
    IntStream.range(0, 4).forEach(y -> horizontalLines[y] = new Line(getHorizontalLine(y), clues[4*4-1-y], clues[4+y]));
    synchmatrixWithHorizontalLines();
  }

  private void synchmatrixWithHorizontalLines() {
    for (int x = 0 ; x < 4 ; x++)
      for (int y = 0 ; y < 4 ; y++)
        setValueForPosition(x, y, horizontalLines[y].line[x]);
  }

  private void synchmatrixWithVerticalLines() {
    for (int x = 0 ; x < 4 ; x++)
      for (int y = 0 ; y < 4 ; y++)
        setValueForPosition(x, y, verticalLines[x].line[y]);
  }

  private int[] getVerticalLine(int x) {
    int[] line = new int[4];
    IntStream.range(0, 4).forEach(y -> line[y] = getValueForPosition(x,y));
    return line;
  }

  private int[] getHorizontalLine(int y) {
    int[] line = new int[4];
    IntStream.range(0, 4).forEach(x -> line[x] = getValueForPosition(x,y));
    return line;
  }

  private void initmatrix() {
    matrix = new int[4][4];
    IntStream.range(0, 4).forEach(i -> Arrays.fill(matrix[i], 0));
  }
}