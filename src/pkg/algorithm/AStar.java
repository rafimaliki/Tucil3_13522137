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

        Map<String, Integer> gScore = new HashMap<String, Integer>();
        gScore.put(startWord, 0);

        Map<String, Integer> fScore = new HashMap<String, Integer>();
        fScore.put(startWord, h(startWord, endWord));

        while (!prioQueue.isEmpty()) {
            String current = prioQueue.get(0);
            prioQueue.remove(0);

            if (current.equals(endWord)){

                List<String> path = constructPath(cameFrom, endWord);
                int nodesVisited = cameFrom.size();
                long duration = System.currentTimeMillis() - startTime;
                long memory = Util.getMemoryUsage() - memoryBefore;
                
                return new Result(path, nodesVisited, duration, memory);
            } 

            for (String neighbour : wordMap.get(current)) {
                int tentativeGScore = gScore.get(current) + 1;

                if (tentativeGScore < gScore.getOrDefault(neighbour, Integer.MAX_VALUE)){
                    cameFrom.put(neighbour, current);
                    gScore.put(neighbour, tentativeGScore);
                    fScore.put(neighbour, gScore.get(neighbour) + h(neighbour, endWord));

                    if (!prioQueue.contains(neighbour)){
                        prioQueue.add(neighbour);
                        prioQueue.sort(Comparator.comparingInt(fScore::get));
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
