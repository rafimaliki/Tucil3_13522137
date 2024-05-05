package pkg.util;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.Scanner;

public class Util {

    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String RESET = "\033[0m";

    public static String[] inputWords() {

        String startWord, endWord = "";
        
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nStart word : ");
        startWord = scanner.nextLine().trim().toLowerCase();;

        System.out.print("End word   : ");
        endWord = scanner.nextLine().trim().toLowerCase();;

        return new String[] { startWord, endWord };
    }

    public static String inputMethod() {

        String method;

        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("\nMetode Pencarian:\n1. Uniform Cost Search\n2. Greedy Best First Search\n3. A* Search\n4. Semua\n\nPilih metode pencarian (1-4): ");
        
        method = scanner.nextLine().trim();

        return method;
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

