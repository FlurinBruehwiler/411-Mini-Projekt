import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Line {
    private final static Integer SIZE = 4;

    int[] line;
    int topClue;
    int bottomClue;

    private Set<Integer> allNonInitClues = IntStream.rangeClosed(2, SIZE-1).boxed().collect(Collectors.toSet());

    Line(int[] line, int topClue, int bottomClue) {
      this.line = line;
      this.topClue = topClue;
      this.bottomClue = bottomClue;
      resolveInitClues();
    }

    private void resolveInitClues() {
      if (topClue == 1) line[0] = SIZE;
      if (bottomClue == 1) line[SIZE-1] = SIZE;
      if (topClue == SIZE) IntStream.range(0, SIZE).forEach(i -> line[i] = i + 1);
      if (bottomClue == SIZE) IntStream.range(0, SIZE).forEach(i -> line[SIZE-1-i] = i + 1);
    }

    Set<Integer> getPossibleValuesForPosition(Integer positionInLine) {
      if(line[positionInLine] != 0)
        throw new IllegalArgumentException("Fatal Error : Trying to discover already discovered value !!"
            + "\n position = " + positionInLine + "\n line : " + this);
      Set<Integer> alreadyPlacedValues = getAlreadyPlacedValues(positionInLine);
      Set<Integer> possibleValues = IntStream.rangeClosed(1, SIZE).boxed().collect(Collectors.toSet());
      possibleValues.removeAll(alreadyPlacedValues); 
      possibleValues.removeIf(valueToTest -> !isCompatibleWithClues(valueToTest, positionInLine));
      return possibleValues;
    }

    private boolean isCompatibleWithClues(Integer valueToTest, int position) {
      line[position] = valueToTest;
      boolean result = true;
      if(this.isComplete()) result = this.isCompatibleWithClues(); // double negation!
      line[position] = 0;
      return result;
    }

    private boolean isComplete() {
      return IntStream.range(0, SIZE).allMatch(i -> line[i] != 0);
    }

    private boolean isCompatibleWithClues() {
      boolean isNotCompatibleWithClues = 
        (allNonInitClues.contains(topClue) && getNumberOfSkyCrapersOnLine(true) != topClue) ||
        (allNonInitClues.contains(bottomClue) && getNumberOfSkyCrapersOnLine(false) != bottomClue);
      return !isNotCompatibleWithClues;
    }

    private int getNumberOfSkyCrapersOnLine(boolean isAscending) {
      int result = 0;
      int maxValue = 0;
      for (int i = 0 ; i < SIZE ; i++) {
        int positionInLine = isAscending ? i : SIZE-1-i; 
        if (line[positionInLine] > maxValue) {
          maxValue = line[positionInLine];
          result++;
        }    
      }
      return result;
    }

    private Set<Integer> getAlreadyPlacedValues(int positionInLine) {
      Set<Integer> alreadyPlacedValues = new HashSet<Integer>();
      for (int i = 0 ; i < SIZE ; i ++) {
        if (i == positionInLine) continue;
        if (line[i] != 0) alreadyPlacedValues.add(line[i]);
      }
      return alreadyPlacedValues;
    }

  }