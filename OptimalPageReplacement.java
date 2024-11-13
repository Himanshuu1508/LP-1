import java.util.Scanner;
import java.util.Arrays;

public class OptimalPageReplacement {
    public static void main(String[] args) {
        // Create a Scanner object to take input from the user
        Scanner scan = new Scanner(System.in);
        
        // Ask the user to enter the number of frames in memory
        System.out.print("Enter number of frames: ");
        int frames = scan.nextInt();
        
        // Ask the user to enter the number of pages
        System.out.print("Enter number of pages: ");
        int noOfPages = scan.nextInt();
        
        // Create an array to store the page reference string
        int[] pages = new int[noOfPages];
        
        // Ask the user to enter the page reference string
        System.out.println("Enter the page references: ");
        for (int i = 0; i < noOfPages; i++) {
            pages[i] = scan.nextInt();  // Store each page reference in the array
        }
        
        // Call the optimal page replacement method and print the total number of page faults
        System.out.println("Total page faults: " + optimalPageReplacement(pages, frames));
    }

    // Method that implements the Optimal Page Replacement algorithm
    public static int optimalPageReplacement(int[] pages, int frames) {
        // Array to represent the memory frames
        int[] memory = new int[frames];
        // Initialize all memory frames to -1 (empty)
        Arrays.fill(memory, -1);
        
        // Variable to count the number of page faults
        int pageFaults = 0;

        // Loop through each page reference in the page reference string
        for (int i = 0; i < pages.length; i++) {
            int page = pages[i];  // Current page reference
            boolean found = false;  // Flag to check if the page is found in memory

            // Check if the page is already in memory
            for (int j = 0; j < frames; j++) {
                if (memory[j] == page) {
                    found = true;  // Page found in memory
                    break;  // Exit the loop if the page is found
                }
            }

            // If the page is not found in memory (page fault)
            if (!found) {
                pageFaults++;  // Increment the page fault count
                int replacementIndex = -1;  // Index to replace in memory

                // Look for an empty frame in memory (indicated by -1)
                for (int j = 0; j < frames; j++) {
                    if (memory[j] == -1) {
                        replacementIndex = j;  // Set the index to replace
                        break;  // Exit the loop since an empty frame is found
                    }
                }

                // If no empty frame is found, we need to replace the least optimal page
                if (replacementIndex == -1) {
                    int farthest = -1;  // Variable to track the farthest next use of the pages
                    // Check the next use of each page in memory to find the farthest one
                    for (int j = 0; j < frames; j++) {
                        int nextUse = findNextUse(pages, i, memory[j]);  // Find next use of the page
                        
                        if (nextUse == -1) {
                            replacementIndex = j;  // If the page is not used again, replace it
                            break;  // Exit the loop since we have chosen the page to replace
                        }
                        
                        // Track the page with the farthest next use time
                        if (nextUse > farthest) {
                            farthest = nextUse;  // Update the farthest next use
                            replacementIndex = j;  // Set the index to replace this page
                        }
                    }
                }

                // Replace the selected page in memory
                memory[replacementIndex] = page;
            }

            // Print the current state of memory after processing the page reference
            System.out.print("Memory: ");
            for (int j = 0; j < frames; j++) {
                // Print the pages in memory or a '-' if the frame is empty
                System.out.print(memory[j] == -1 ? "- " : memory[j] + " ");
            }
            System.out.println();  // Move to the next line after printing memory state
        }

        // Return the total number of page faults
        return pageFaults;
    }

    // Helper method to find the next use of a page starting from the current index
    public static int findNextUse(int[] pages, int currentIndex, int page) {
        // Loop through the remaining pages after the current index
        for (int i = currentIndex + 1; i < pages.length; i++) {
            if (pages[i] == page) {
                return i;  // Return the index where the page will be used again
            }
        }
        return -1;  // If the page is not used again, return -1
    }
}
