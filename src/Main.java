import pkg.util.Util;
import pkg.algorithm.*;
import pkg.result.Result;

public class Main {
    public static void main(String[] args) {

        System.out.println("\033[2J\033[1;1H\nSelamat datang di Word Ladder Solver!");

        boolean loop = true;
    
        while(loop){

            String[] words = Util.inputWords(); 
            String startWord = words[0], endWord = words[1];
            String method = Util.inputMethod();
            Result result;
    
            switch (method) {
                case "1":
                    // Uniform Cost Search
                    result = UCS.search(startWord, endWord);
                    result.print();
                    break;

                case "2":
                    // Greedy Best First Search
                    result = GBFS.search(startWord, endWord);
                    result.print();
                    break;

                case "3":
                    // A* Search
                    result = AStar.search(startWord, endWord);
                    result.print();
                    break;

                case "4":
                    // Uniform Cost Search
                    result = UCS.search(startWord, endWord);
                    result.print();
                    
                    // Greedy Best First Search
                    result = GBFS.search(startWord, endWord);
                    result.print();
                    
                    // A* Search
                    result = AStar.search(startWord, endWord);
                    result.print();
                    break;

                case "-1":
                    // Exit program
                    System.out.println("\nTerima kasih telah menggunakan Word Ladder Solver!");
                    loop = false;
                    break;
                    
                default:
                    System.out.println("\nMetode pencarian tidak valid");
                    break;
            }
        }
    }
}