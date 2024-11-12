import java.util.Scanner;

public class LRU {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        
        // Input the number of frames
        System.out.print("Enter number of frames: ");
        int frames = scan.nextInt();
        
        // Input the number of pages and their values
        System.out.print("Enter number of pages: ");
        int noOfPages = scan.nextInt();
        int[] pages = new int[noOfPages];
        
        System.out.println("Enter the page references: ");
        for (int i = 0; i < noOfPages; i++) {
            pages[i] = scan.nextInt();
        }
        
        // Calculate page faults using LRU replacement
        int faults = lruReplacement(pages, frames);
        System.out.println("Total page faults: " + faults);
    }
    
    public static int lruReplacement(int[] pages, int frames) {
        int[] memory = new int[frames];  // Array to store current pages in memory
        int[] lastUsed = new int[frames];  // Array to store the last used time of each frame
        int pageFaults = 0;
        int time = 0;
        
        // Initialize memory and lastUsed arrays
        for (int i = 0; i < frames; i++) {
            memory[i] = -1;  // -1 indicates an empty slot
            lastUsed[i] = -1;  // -1 indicates that it hasn't been used yet
        }
        
        // Process each page
        for (int page : pages) {
            boolean found = false;
            
            // Check if the page is already in memory
            for (int i = 0; i < frames; i++) {
                if (memory[i] == page) {
                    found = true;
                    lastUsed[i] = time++;  // Update the last used time for the page
                    break;
                }
            }
            
            // If the page is not in memory, replace the least recently used page
            if (!found) {
                pageFaults++;
                int replacementIndex = -1;

                // Find an empty frame if available
                for (int i = 0; i < frames; i++) {
                    if (memory[i] == -1) {
                        replacementIndex = i;
                        break;
                    }
                }

                // If no empty frame is found, find the least recently used page
                if (replacementIndex == -1) {
                    int lruIndex = 0;
                    for (int i = 1; i < frames; i++) {
                        if (lastUsed[i] < lastUsed[lruIndex]) {
                            lruIndex = i;
                        }
                    }
                    replacementIndex = lruIndex;
                }

                // Replace the page in memory
                memory[replacementIndex] = page;
                lastUsed[replacementIndex] = time++;
            }
            // Sort memory for visualization by recent access time
            for (int i = 0; i < frames - 1; i++) {
                for (int j = i + 1; j < frames; j++) {
                    if (lastUsed[i] > lastUsed[j]) {
                        int tempPage = memory[i];
                        memory[i] = memory[j];
                        memory[j] = tempPage;
                        
                        int tempTime = lastUsed[i];
                        lastUsed[i] = lastUsed[j];
                        lastUsed[j] = tempTime;
                    }
                }
            }

            // Display the current state of memory
            System.out.print("Memory: ");
            for (int i = 0; i < frames; i++) {
                if (memory[i] != -1) {
                    System.out.print(memory[i] + " ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }

        return pageFaults;
    }
}
