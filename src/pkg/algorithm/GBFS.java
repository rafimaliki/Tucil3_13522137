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
    public static Result search(String startWord, String endWord){

        System.gc();
        
        startWord = startWord.trim().toLowerCase();
        endWord = endWord.trim().toLowerCase();

        if (!Dictionary.isValidWord(startWord)){
            return new Result(2);
        } else if (!Dictionary.isValidWord(endWord)){
            return new Result(3);
        } else if (startWord.equals(endWord)){
            return new Result(4);
        } else if (startWord.length() != endWord.length()){
            return new Result(5);
        }

        Map<String, List<String>> wordMap = Dictionary.loadMappedDictonary(startWord.length());

        long startTime = System.currentTimeMillis();
        long memoryBefore = Util.getMemoryUsage();

        List<String> prioQueue = new ArrayList<String>();
        prioQueue.add(startWord);

        Map<String, String> cameFrom = new HashMap<String, String>();

        List<String> visited = new ArrayList<String>();
        visited.add(startWord);

        while (!prioQueue.isEmpty()){
            String current = prioQueue.get(0);
            prioQueue.remove(0);

            for (String neighbor : wordMap.get(current)){

                if (!visited.contains(neighbor)){
                    if (neighbor.equals(endWord)){
                        cameFrom.put(neighbor, current);

                        List<String> path = constructPath(cameFrom, endWord);
                        int nodesVisited = cameFrom.size();
                        long duration = System.currentTimeMillis() - startTime;
                        long memory = Util.getMemoryUsage() - memoryBefore;

                        return new Result(path, nodesVisited, duration, memory);
                    } else {
                        final String finalEndWord = endWord;

                        visited.add(neighbor);
                        cameFrom.put(neighbor, current);
                        prioQueue.add(neighbor);
                        prioQueue.sort(Comparator.comparingInt(word -> h(word, finalEndWord)));
                    }
                }
            }
        }
        
        int nodesVisited = cameFrom.size();
        long duration = System.currentTimeMillis() - startTime;
        long memory = Util.getMemoryUsage() - memoryBefore;

        return new Result(1, nodesVisited, duration, memory);
    }

    public static int h(String word, String endWord){
        return Dictionary.countSameLetters(word, endWord);
    }

    public static List<String> constructPath(Map<String, String> cameFrom, String endWord){
        List<String> path = new ArrayList<String>();
        String current = endWord;
        while (current != null){
            path.add(0, current);
            current = cameFrom.get(current);
        }
        return path;
    }
}
