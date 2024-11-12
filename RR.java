import java.util.Scanner;

public class RR {
    public static void main(String args[]) {
        int n, i, qt, count = 0, temp, sq = 0;
        int[] bt = new int[10];    // Burst Time
        int[] wt = new int[10];    // Waiting Time
        int[] tat = new int[10];   // Turnaround Time
        int[] rem_bt = new int[10];  // Remaining Burst Time
        float awt = 0, atat = 0;    // Average Waiting Time and Average Turnaround Time
        Scanner s = new Scanner(System.in);
        
        System.out.print("Enter the number of processes (maximum 10): ");
        n = s.nextInt();
        
        System.out.print("Enter the burst time of the processes:\n");
        for (i = 0; i < n; i++) {
            System.out.print("P" + i + " = ");
            bt[i] = s.nextInt();
            rem_bt[i] = bt[i]; // Initialize remaining burst time
        }
        
        System.out.print("Enter the quantum time: ");
        qt = s.nextInt();

        // Round Robin Scheduling Logic
        while (true) {
            for (i = 0, count = 0; i < n; i++) {
                temp = qt;

                // Skip process if it is already completed
                if (rem_bt[i] == 0) {
                    count++;
                    continue;
                }

                // If remaining burst time is greater than quantum, reduce by quantum
                if (rem_bt[i] > qt) {
                    rem_bt[i] -= qt;
                }
                // If remaining burst time is less than or equal to quantum, execute fully
                else if (rem_bt[i] >= 0) {
                    temp = rem_bt[i];
                    rem_bt[i] = 0;
                }

                // Update the total execution time
                sq += temp;
                tat[i] = sq;
            }

            if (n == count) break; // Exit when all processes are finished
        }

        // Print the Process Information Table
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Process\t Burst Time\t Turnaround Time\t Waiting Time");
        System.out.println("--------------------------------------------------------------------------------");

        // Calculate waiting time and print results
        for (i = 0; i < n; i++) {
            wt[i] = tat[i] - bt[i];
            awt += wt[i];
            atat += tat[i];
            System.out.printf("P%d\t %d\t\t %d\t\t %d\n", i + 1, bt[i], tat[i], wt[i]);
        }

        // Calculate averages
        awt /= n;
        atat /= n;

        // Print average times
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("\nAverage Waiting Time = %.2f\n", awt);
        System.out.printf("Average Turnaround Time = %.2f\n", atat);
    }
}
