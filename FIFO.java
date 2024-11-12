import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;

public class FIFO {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        // Taking input for frames and pages
        System.out.print("Enter number of frames: ");
        int frames = scan.nextInt();

        System.out.print("Enter number of pages: ");
        int no = scan.nextInt();

        int[] pages = new int[no];

        // Taking page requests as input
        System.out.println("Enter the pages: ");
        for (int i = 0; i < no; i++) {
            pages[i] = scan.nextInt();
        }

        // Call FIFO replacement method and get page fault count
        int faults = fiforeplacement(pages, frames);

        // Display the result
        System.out.println("\nTotal Page Faults: " + faults);
    }

    // Method to implement FIFO page replacement
    public static int fiforeplacement(int[] pages, int frames) {
        Queue<Integer> memory = new LinkedList<>();  // Memory to store pages
        int pageFaults = 0;  // Counter for page faults

        // Header for the output display
        System.out.println("\nFIFO Page Replacement Simulation:");
        System.out.println("---------------------------------------------------------");
        System.out.println("Page Request | Memory State");
        System.out.println("---------------------------------------------------------");

        // Iterate through each page request
        for (int pg : pages) {
            // Check if the page is not already in memory
            if (!memory.contains(pg)) {
                pageFaults++;  // Increment page fault count

                // If memory is full, remove the oldest page (FIFO)
                if (memory.size() == frames) {
                    memory.poll();
                }

                // Add the new page to memory
                memory.add(pg);
            }

            // Display the current state of memory after each page request
            System.out.printf("     %2d       | ", pg);
            System.out.print(memory);
            System.out.println();
        }

        // Return total page faults
        return pageFaults;
    }
}
