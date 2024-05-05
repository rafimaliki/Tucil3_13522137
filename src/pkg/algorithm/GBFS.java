package pkg.algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import pkg.dictionary.Dictionary;
import pkg.result.Result;
import pkg.util.Util;

public class GBFS {
    public static Result search(final String startWord, final String endWord){

        System.out.print("\nMencari path menggunakan ");
        Util.printlnColor(Util.GREEN, "Greedy Best First Search\n");

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
        // prioQueue = list of string yang akan diurutkan berdasarkan weightToEndWord
        // weightToEndWord = jumlah huruf yang berbeda dengan endWord
        List<String> prioQueue = new ArrayList<String>();
        prioQueue.add(startWord);
        
        // Inisialisasi parent dengan map kosong
        // parent[string] = parent dari string
        Map<String, String> parent = new HashMap<String, String>();

        // Inisialisasi visited dengan startWord
        // visited = list of string yang sudah dikunjungi
        List<String> visited = new ArrayList<String>();
        visited.add(startWord);

        // Loop sampai prioQueue kosong atau ditemukan endWord
        while (!prioQueue.isEmpty()){

            // dequeue
            String current = prioQueue.get(0);
            prioQueue.remove(0);

            // Untuk setiap neighbor dari current
            for (String neighbor : wordMap.get(current)){

                // Jika neighbor sudah dikunjungi, lanjutkan
                if (visited.contains(neighbor)){
                    continue;

                // Jika neighbor adalah endWord, return
                } else if (neighbor.equals(endWord)){
                    parent.put(neighbor, current);

                    List<String> path = getPath(parent, endWord);
                    int nodesVisited = parent.size() + 1;
                    long duration = System.currentTimeMillis() - startTime;
                    long memory = Util.getMemoryUsage() - startMemory;
                    
                    return new Result(path, nodesVisited, duration, memory);
                
                // Jika neighbor belum dikunjungi, tambahkan ke prioQueue
                } else {
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                    prioQueue.add(neighbor);
                    prioQueue.sort(Comparator.comparingInt(word -> Dictionary.countDifferentLetters(word, endWord)));
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
