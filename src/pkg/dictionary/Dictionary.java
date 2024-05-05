package pkg.dictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Dictionary {

    static final List<String> dictionary = loadDictionary();
    public static void main(String[] args) {
        makeMappedDictionary();
    }

    public static void makeMappedDictionary() {
        String fileNameRead = "src/data/raw/dictionary.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(fileNameRead))) {
            List<List<String>> wordListsByLength = new ArrayList<>();

            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim(); 
                if (!line.isEmpty()) {
                    int length = line.length();

                    while (wordListsByLength.size() <= length) {
                        wordListsByLength.add(new ArrayList<>());
                    }

                    wordListsByLength.get(length).add(line);
                }
            }

            for (int i = 0; i < wordListsByLength.size(); i++) { 

                List<String> wordsOfLength = wordListsByLength.get(i);
                System.out.println("Processing words of length: " + i);

                if (!wordsOfLength.isEmpty()) {
                    Map<String, List<String>> wordMap = new HashMap<>();

                    for (int j = 0; j < wordsOfLength.size(); j++) {

                        String word1 = wordsOfLength.get(j);
                        wordMap.put(word1, new ArrayList<>());

                        for (int k = 0; k < wordsOfLength.size(); k++) {
                            String word2 = wordsOfLength.get(k);

                            if (isChildOf(word1, word2)) {
                                wordMap.get(word1).add(word2);
                            } else if (isChildOf(word2, word1)) {
                                wordMap.get(word2).add(word1);
                            }
                        }
                    }

                    String fileNameWrite = "src/data/map/" + i + ".txt";
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileNameWrite))) {
                        for (int j = 0; j < wordsOfLength.size(); j++) {
                            writer.write(wordsOfLength.get(j));
                            writer.newLine(); 
                            writer.write(Integer.toString(wordMap.get(wordsOfLength.get(j)).size()));
                            writer.newLine();
                            for (int k = 0; k < wordMap.get(wordsOfLength.get(j)).size(); k++) {
                                writer.write(wordMap.get(wordsOfLength.get(j)).get(k));
                                writer.newLine();
                            }
                        }
                    } catch (IOException e) {
                        System.err.println("Error writing to file: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public static Map<String, List<String>> loadMappedDictonary(int length) {
        String fileName = "src/data/map/" + length + ".txt";
        Map<String, List<String>> wordMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String line;
            String word = "";
            int childCount = 0;
            boolean mapDone = false;

            List<String> children = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                if (word.isEmpty()) {
                    word = line;
                } else if (childCount == 0) {
                    childCount = Integer.parseInt(line);

                    if (childCount == 0) {
                        mapDone = true;
                    }
                } else {
                    children.add(line);
                    childCount--;

                    if (childCount == 0) {
                        mapDone = true;
                    }
                }

                if (mapDone) {
                    // System.out.println(word + " -> " + children);
                    wordMap.put(word, children);
                    word = "";
                    children = new ArrayList<>();
                    mapDone = false;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return wordMap;
    }

    public static List<String> loadDictionary() {
        String fileName = "src/data/raw/dictionary.txt";
        List<String> words = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                words.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return words;
    }
    
    public static boolean isChildOf(String word1, String word2) {
        if (word1.length() != word2.length()) {
            return false;
        }

        int diffCount = 0;

        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                diffCount++;
            }

            if (diffCount > 1) {
                return false;
            }
        }

        return diffCount == 1;
    }

    public static int countDifferentLetters(String word1, String word2) {
        int count = 0;

        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                count++;
            }
        }

        return count;
    }   

    public static boolean isValidWord(String word) {
        return dictionary.contains(word);
    }
} 