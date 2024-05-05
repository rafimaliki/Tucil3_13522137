package pkg.result;

import java.util.List;
import java.util.ArrayList;
import pkg.util.Util;

public class Result {
    private List<String> path;
    private int nodesVisited;
    private long timeElapsed;
    private long memoryUsed;
    private int errorCode;

    /*
     * Error code:
     * 0: No error
     * 1: No path found
     * 2: Invalid start word
     * 3: Invalid end word
     * 4: Start word dan end word sama
     * 5: Start word dan end word tidak sama panjang
     */

    public Result(List<String> path, int nodesVisited, long timeElapsed, long memoryUsed) {
        this.path = path;
        this.nodesVisited = nodesVisited;
        this.timeElapsed = timeElapsed;
        this.memoryUsed = memoryUsed;
        this.errorCode = 0;
    }

    public Result(int errorCode) {
        this.path = new ArrayList<String>();
        this.nodesVisited = 0;
        this.timeElapsed = 0;
        this.memoryUsed = 0;
        this.errorCode = errorCode;
    }

    public Result(int errorCode, int nodesVisited, long timeElapsed, long memoryUsed) {
        this.path = new ArrayList<String>();
        this.nodesVisited = nodesVisited;
        this.timeElapsed = timeElapsed;
        this.memoryUsed = memoryUsed;
        this.errorCode = errorCode;
    }

    public void print() {
        switch (this.errorCode) {
            case 0:
                Util.printlnColor(Util.GREEN,"Path ditemukan!\n");
                printPath();
                System.out.println("Jumlah langkah  : " + (path.size() - 1));
                System.out.println("Node dikunjungi : " + nodesVisited);
                System.out.println("Waktu eksekusi  : " + timeElapsed + " ms");
                System.out.println("Memori          : " + memoryUsed + " bytes");
                break;
            case 1:
                Util.printlnColor(Util.RED,"Path tidak ditemukan!\n");
                System.out.println("Node dikunjungi : " + nodesVisited);
                System.out.println("Waktu eksekusi  : " + timeElapsed + " ms");
                System.out.println("Memori          : " + memoryUsed + " bytes");
                break;
            case 2:
                Util.printlnColor(Util.RED, "Kata awal tidak valid");
                break;
            case 3:
                Util.printlnColor(Util.RED, "Kata akhir tidak valid");
                break;
            case 4:
                Util.printlnColor(Util.RED, "Kata awal dan akhir sama");
                break;
            case 5:
                Util.printlnColor(Util.RED, "Kata awal dan akhir tidak sama panjang");
                break;
            default:
                break;
        }
    }

    public void printPath() {

        String startWord = path.get(0);
        String endWord = path.get(path.size()-1);

        System.out.println(startWord);

        for (int i = 1; i < path.size(); i++) {

            String current = path.get(i);
   
            for (int j = 0; j < current.length(); j++) {
                if (current.charAt(j) == endWord.charAt(j)) {
                    Util.printCharColor(Util.GREEN, current.charAt(j));
                } else {
                    System.out.print(current.charAt(j));
                }
            }

            System.out.println();
        }

        System.out.println();
    }
}
