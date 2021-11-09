import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

class Table {
    private final static Integer SIZE = 4;

    int[][] table;
    Line[] horizontalLines = new Line[SIZE];
    Line[] verticalLines = new Line[SIZE];

    Table(int[] clues) {
      initTable();
      initLinesWithClues(clues);
    }

    private Integer getValueForPosition(int x, int y) {
      return table[y][x]; // inverted!
    }

    void setValueForPosition(Position position, int value) {
      int x = position.getX();
      int y = position.getY();
      setValueForPosition(x, y, value);
      verticalLines[x].line[y] = value; 
      horizontalLines[y].line[x] = value; 
    }

    private void setValueForPosition(int x, int y, int value) {
      table[y][x] = value; // inverted!
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
        if (x == SIZE-1)
          if (y == SIZE-1)
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
      IntStream.range(0, SIZE).forEach(x -> verticalLines[x] = new Line(getVerticalLine(x), clues[x], clues[3*SIZE-1-x]));
      synchTableWithVerticalLines();
      IntStream.range(0, SIZE).forEach(y -> horizontalLines[y] = new Line(getHorizontalLine(y), clues[4*SIZE-1-y], clues[SIZE+y]));
      synchTableWithHorizontalLines();
    }

    private void synchTableWithHorizontalLines() {
      for (int x = 0 ; x < SIZE ; x++)
        for (int y = 0 ; y < SIZE ; y++)
          setValueForPosition(x, y, horizontalLines[y].line[x]);
    }

    private void synchTableWithVerticalLines() {
      for (int x = 0 ; x < SIZE ; x++)
        for (int y = 0 ; y < SIZE ; y++)
          setValueForPosition(x, y, verticalLines[x].line[y]);
    }

    private int[] getVerticalLine(int x) {
      int[] line = new int[SIZE];
      IntStream.range(0, SIZE).forEach(y -> line[y] = getValueForPosition(x,y));
      return line;
    }

    private int[] getHorizontalLine(int y) {
      int[] line = new int[SIZE];
      IntStream.range(0, SIZE).forEach(x -> line[x] = getValueForPosition(x,y));
      return line;
    }

    private void initTable() {
      table = new int[SIZE][SIZE];
      IntStream.range(0, SIZE).forEach(i -> Arrays.fill(table[i], 0));
    }

  }