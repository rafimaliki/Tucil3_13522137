package pkg.algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import pkg.dictionary.Dictionary;
import pkg.result.Result;
import pkg.util.Util;

public class AStar {
    public static Result search(final String startWord, final String endWord){

        System.out.print("\nMencari path menggunakan ");
        Util.printlnColor(Util.GREEN, "A* Search\n");

        // Bersihkan memory
        System.gc();

        // Validasi kata
        if (!Dictionary.isValidWord(startWord)){
            return new Result(2);
        } else if (!Dictionary.isValidWord(endWord)){
            return new Result(3);
        } else if (startWord.equals(endWord)){
            return new Result(4);
        } else if (startWord.length() != endWord.length()){
            return new Result(5);
        }

        // Load dictionary
        Map<String, List<String>> wordMap = Dictionary.loadMappedDictonary(startWord.length());
        
        // Inisialisasi waktu mulai dan memory mula-mula
        long startTime = System.currentTimeMillis();
        long startMemory = Util.getMemoryUsage();

        // Inisialisasi priority queue dengan startWord
        // prioQueue = list of string yang akan diurutkan berdasarkan weightTotal
        List<String> prioQueue = new ArrayList<String>();
        prioQueue.add(startWord);

        // Inisialisasi parent dengan map kosong
        // parent[string] = parent dari string
        Map<String, String> parent = new HashMap<String, String>();

        // Inisialisasi weightFromRoot map dengan startWord
        // weightFromRoot[string] = jumlah langkah dari root ke string
        Map<String, Integer> weightFromRoot = new HashMap<String, Integer>();
        weightFromRoot.put(startWord, 0);

        // Init weightTotal map dengan startWord
        // weightTotal[string] = weightFromRoot[string] + jumlah huruf yang sama dengan endWord
        Map<String, Integer> weightTotal = new HashMap<String, Integer>();
        weightTotal.put(startWord, Dictionary.countDifferentLetters(startWord, endWord));

        // Loop sampai prioQueue kosong atau ditemukan endWord
        while (!prioQueue.isEmpty()) {

            // dequeue
            String current = prioQueue.get(0);
            prioQueue.remove(0);

            // Jika current adalah endWord, return
            if (current.equals(endWord)){

                List<String> path = getPath(parent, endWord);
                int nodesVisited = parent.size() + 1;
                long duration = System.currentTimeMillis() - startTime;
                long memory = Util.getMemoryUsage() - startMemory;
                
                return new Result(path, nodesVisited, duration, memory);
            } 

            // Untuk setiap neighbor dari current
            for (String neighbor : wordMap.get(current)) {

                // weightFromRoot neighbor berdasarkan current path
                int newWeightFromRoot = weightFromRoot.get(current) + 1;

                // Jika weightFromRoot neighbor lebih kecil dari weightFromRoot[neighbor] atau neighbor belum dijelajahi
                if (newWeightFromRoot < weightFromRoot.getOrDefault(neighbor, Integer.MAX_VALUE)){
                    
                    // Update nilai weightFromRoot, weightTotal, parent, dan prioQueue
                    parent.put(neighbor, current);
                    weightFromRoot.put(neighbor, newWeightFromRoot);
                    weightTotal.put(neighbor, weightFromRoot.get(neighbor) + Dictionary.countDifferentLetters(neighbor, endWord));

                    if (!prioQueue.contains(neighbor)){
                        prioQueue.add(neighbor);
                    }
                    prioQueue.sort(Comparator.comparingInt(weightTotal::get));
                }
            }
        }

        int nodesVisited = parent.size() + 1;
        long duration = System.currentTimeMillis() - startTime;
        long memory = Util.getMemoryUsage() - startMemory;

        return new Result(1, nodesVisited, duration, memory);
    }

    // Helper method untuk mendapatkan path dari parent map
    public static List<String> getPath(Map<String, String> parent, String endWord){
        List<String> path = new ArrayList<String>();
        String current = endWord;
        while (current != null){
            path.add(0, current);
            current = parent.get(current);
        }
        return path;
    }
}
