import javax.sound.sampled.SourceDataLine;

public class Test {
    public static void main(String[] args) {
        Solver solver = new Solver();

        int clues[][] = {
            { 2, 2, 1, 3,  
              2, 2, 3, 1,  
              1, 2, 2, 3,  
              3, 2, 1, 3 },
            { 0, 0, 1, 2,   
              0, 2, 0, 0,   
              0, 3, 0, 0, 
              0, 1, 0, 0 }
            };

        for(int i = 0; i < clues.length; i++){
            var result = solver.solve(clues[i]);
            printResult(result);
        }

    }

    static void printResult(int[][] result){
        System.out.println("--------------------");
        for(int i = 0; i < result.length; i++){
            for(int j = 0; j < result.length; j++){
                System.out.print(result[i][j]);
            }
            System.out.println("");
        }
    }
}
