/**
 * Datum: 11.9.2021
 * Author: Flurin Brühwiler, Henrik Fäh
 */

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Line {
  int[] line;
  int topClue;
  int bottomClue;

  private Set<Integer> allNonInitClues = IntStream.rangeClosed(2, 3).boxed().collect(Collectors.toSet());

  Line(int[] line, int topClue, int bottomClue) {
    this.line = line;
    this.topClue = topClue;
    this.bottomClue = bottomClue;
    resolveInitClues();
  }


  /**
   * Description: Solves the easy fields
   */
  private void resolveInitClues() {
    if (topClue == 1) line[0] = 4;
    if (bottomClue == 1) line[3] = 4;
    if (topClue == 4) IntStream.range(0, 4).forEach(i -> line[i] = i + 1);
    if (bottomClue == 4) IntStream.range(0, 4).forEach(i -> line[3-i] = i + 1);
  }

  /**
   * Description: get all Possible values for a certain position in the line
   * @param positionInLine
   * @return
   */
  Set<Integer> getPossibleValuesForPosition(Integer positionInLine) {
    Set<Integer> alreadyPlacedValues = getAlreadyPlacedValues(positionInLine);
    Set<Integer> possibleValues = IntStream.rangeClosed(1, 4).boxed().collect(Collectors.toSet());
    possibleValues.removeAll(alreadyPlacedValues); 
    possibleValues.removeIf(valueToTest -> !isCompatibleWithClues(valueToTest, positionInLine));
    return possibleValues;
  }

  /**
   * Description: checks if the value can be insertet at a given position
   * @param valueToTest
   * @param position
   * @return
   */
  private boolean isCompatibleWithClues(Integer valueToTest, int position) {
    line[position] = valueToTest;
    boolean result = true;
    if(this.isComplete()) result = this.isCompatibleWithClues();
    line[position] = 0;
    return result;
  }

  /**
   * Description: checks if a line is complete
   * @return
   */
  private boolean isComplete() {
    return IntStream.range(0, 4).allMatch(i -> line[i] != 0);
  }

  /**
   * Description: checks if the line is compatible with the clues
   * @return
   */
  private boolean isCompatibleWithClues() {
    boolean isNotCompatibleWithClues = 
      (allNonInitClues.contains(topClue) && getNumberOfSkyCrapersOnLine(true) != topClue) ||
      (allNonInitClues.contains(bottomClue) && getNumberOfSkyCrapersOnLine(false) != bottomClue);
    return !isNotCompatibleWithClues;
  }

  /**
   * Descriptioon: counts the visible skyscrapers
   * @param isAscending
   * @return
   */
  private int getNumberOfSkyCrapersOnLine(boolean isAscending) {
    int result = 0;
    int maxValue = 0;
    for (int i = 0 ; i < 4 ; i++) {
      int positionInLine = isAscending ? i : 4-1-i; 
      if (line[positionInLine] > maxValue) {
        maxValue = line[positionInLine];
        result++;
      }    
    }
    return result;
  }

  /**
   * Description: gets all the values in a line
   * @param positionInLine
   * @return
   */
  private Set<Integer> getAlreadyPlacedValues(int positionInLine) {
    Set<Integer> alreadyPlacedValues = new HashSet<Integer>();
    for (int i = 0 ; i < 4 ; i ++) {
      if (i == positionInLine) continue;
      if (line[i] != 0) alreadyPlacedValues.add(line[i]);
    }
    return alreadyPlacedValues;
  }
}