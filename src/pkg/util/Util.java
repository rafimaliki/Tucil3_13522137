package pkg.util;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

public class Util {

    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String RESET = "\033[0m";

    public static String[] inputWords() {

        String startWord, endWord = "";

        System.out.print("\nStart word : ");
        startWord = System.console().readLine();

        System.out.print("End word   : ");
        endWord = System.console().readLine();

        return new String[] { startWord, endWord };
    }

    public static String inputMethod() {
        System.out.print("\nMetode Pencarian:\n1. Uniform Cost Search\n2. Greedy Best First Search\n3. A* Search\n4. Semua\n\nPilih metode pencarian (1-4): ");
        return System.console().readLine();
    }

    public static long getMemoryUsage() {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        return memoryBean.getHeapMemoryUsage().getUsed();
    }

    public static void printColor(String color, String message) {
        System.out.print(color + message + RESET);
    }

    public static void printlnColor(String color, String message) {
        System.out.println(color + message + RESET);
    }

    public static void printCharColor(String color, char message) {
        System.out.print(color + message + RESET);
    }
}

