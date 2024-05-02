import pkg.util.Util;
import pkg.algorithm.*;
import pkg.result.Result;

public class Main {
    public static void main(String[] args) {

        // TO DO: tambahin checker apakah file dictionary lengkap, kalau belum ada init dlu

        System.out.println("\033[2J\033[1;1H\nSelamat datang di Word Ladder Solver!");

        boolean loop = true;
    
        while(loop){

            String[] words = Util.inputWords(); 
            String startWord = words[0], endWord = words[1];
    
            String method = Util.inputMethod();
    
            switch (method) {
                case "1":
                    // Uniform Cost Search
                    System.out.println("\nMencari path menggunakan Uniform Cost Search...\n");
                    Result result1 = UCS.search(startWord, endWord);
                    result1.print();
                    break;
                    
                case "2":
                    // Greedy Best First Search
                    System.out.println("\nMencari path menggunakan Greedy Best First Search...\n");
                    Result result2 = GBFS.search(startWord, endWord);
                    result2.print();
                    break;

                case "3":
                    // A* Search
                    System.out.println("\nMencari path menggunakan A* Search...\n");
                    Result result3 = AStar.search(startWord, endWord);
                    result3.print();
                    break;

                case "4":
                    // Uniform Cost Search
                    System.out.println("\nMencari path menggunakan Uniform Cost Search...\n");
                    Result resultUCS = UCS.search(startWord, endWord);
                    resultUCS.print();
                    
                    // Greedy Best First Search
                    System.out.println("\nMencari path menggunakan Greedy Best First Search...\n");
                    Result resultGBFS = GBFS.search(startWord, endWord);
                    resultGBFS.print();
                    
                    // A* Search
                    System.out.println("\nMencari path menggunakan A* Search...\n");
                    Result resultAStar = AStar.search(startWord, endWord);
                    resultAStar.print();
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