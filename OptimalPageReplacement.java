import java.util.Scanner;
import java.util.Arrays;

public class OptimalPageReplacement {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter number of frames: ");
        int frames = scan.nextInt();
        System.out.print("Enter number of pages: ");
        int noOfPages = scan.nextInt();
        int[] pages = new int[noOfPages];
        System.out.println("Enter the page references: ");
        for (int i = 0; i < noOfPages; i++) pages[i] = scan.nextInt();
        
        System.out.println("Total page faults: " + optimalPageReplacement(pages, frames));
    }

    public static int optimalPageReplacement(int[] pages, int frames) {
        int[] memory = new int[frames];
        Arrays.fill(memory, -1);
        int pageFaults = 0;

        for (int i = 0; i < pages.length; i++) {
            int page = pages[i];
            boolean found = false;

            for (int j = 0; j < frames; j++) {
                if (memory[j] == page) { found = true; break; }
            }

            if (!found) {
                pageFaults++;
                int replacementIndex = -1;

                for (int j = 0; j < frames; j++) {
                    if (memory[j] == -1) { replacementIndex = j; break; }
                }

                if (replacementIndex == -1) {
                    int farthest = -1;
                    for (int j = 0; j < frames; j++) {
                        int nextUse = findNextUse(pages, i, memory[j]);
                        if (nextUse == -1) { replacementIndex = j; break; }
                        if (nextUse > farthest) { farthest = nextUse; replacementIndex = j; }
                    }
                }

                memory[replacementIndex] = page;
            }

            System.out.print("Memory: ");
            for (int j = 0; j < frames; j++) System.out.print(memory[j] == -1 ? "- " : memory[j] + " ");
            System.out.println();
        }

        return pageFaults;
    }

    public static int findNextUse(int[] pages, int currentIndex, int page) {
        for (int i = currentIndex + 1; i < pages.length; i++) {
            if (pages[i] == page) return i;
        }
        return -1;
    }
}
