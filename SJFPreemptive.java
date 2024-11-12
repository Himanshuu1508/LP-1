import java.util.Scanner;

public class SJFPreemptive {
    int count, currentTime = 0;
    int[][] processData;
    Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        SJFPreemptive scheduler = new SJFPreemptive();
        scheduler.setValues();
        System.out.println("\nBefore Scheduling:");
        scheduler.displayValues();
        scheduler.scheduleSJFPreemptive();
        System.out.println("\nAfter Scheduling:");
        scheduler.displayValues();
        scheduler.calculateAndDisplayAverageTimes();
    }

    public void setValues() {
        System.out.print("Enter Number of processes: ");
        count = scan.nextInt();
        processData = new int[count][7]; // Columns: [Process ID, Arrival Time, Burst Time, Start Time, End Time, Remaining Time, Waiting Time]

        for (int i = 0; i < count; i++) {
            processData[i][0] = i;
            System.out.print("Process [" + i + "] -> Arrival Time: ");
            processData[i][1] = scan.nextInt();
            System.out.print("Process [" + i + "] -> Burst Time: ");
            processData[i][2] = scan.nextInt();
            processData[i][5] = processData[i][2]; // Remaining time initially equal to burst time
        }
    }

    public void scheduleSJFPreemptive() {
        int completedProcesses = 0;

        while (completedProcesses < count) {
            int shortestJobIndex = -1;
            int minRemainingTime = Integer.MAX_VALUE;

            for (int i = 0; i < count; i++) {
                if (processData[i][1] <= currentTime && processData[i][5] > 0 && processData[i][5] < minRemainingTime) {
                    minRemainingTime = processData[i][5];
                    shortestJobIndex = i;
                }
            }

            if (shortestJobIndex == -1) {
                currentTime++;
            } else {
                if (processData[shortestJobIndex][2] == processData[shortestJobIndex][5]) {
                    processData[shortestJobIndex][3] = currentTime; // Start Time
                }

                processData[shortestJobIndex][5]--; // Decrease remaining time
                currentTime++;

                if (processData[shortestJobIndex][5] == 0) {
                    processData[shortestJobIndex][4] = currentTime; // End Time
                    completedProcesses++;
                    processData[shortestJobIndex][6] = currentTime - processData[shortestJobIndex][1] - processData[shortestJobIndex][2]; // Waiting Time
                }
            }
        }
    }

    public void calculateAndDisplayAverageTimes() {
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        System.out.println("-------------------------------------------------------------");
        System.out.println("Process ID | Arrival Time | Burst Time | Waiting Time | Turnaround Time");
        System.out.println("-------------------------------------------------------------");

        for (int i = 0; i < count; i++) {
            int turnaroundTime = processData[i][4] - processData[i][1]; // Turnaround Time = End Time - Arrival Time
            totalWaitingTime += processData[i][6];
            totalTurnaroundTime += turnaroundTime;

            System.out.printf("   P[%d]       |      %d       |     %d      |      %d      |      %d%n",
                    processData[i][0], processData[i][1], processData[i][2], processData[i][6], turnaroundTime);
        }

        float avgWaitingTime = (float) totalWaitingTime / count;
        float avgTurnaroundTime = (float) totalTurnaroundTime / count;

        System.out.println("-------------------------------------------------------------");
        System.out.println("Average Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
    }

    public void displayValues() {
        System.out.println("------------------------------------------------------------");
        System.out.println("Process ID | Arrival Time | Burst Time | Start Time | End Time");
        System.out.println("------------------------------------------------------------");

        for (int i = 0; i < count; i++) {
            System.out.printf("   P[%d]       |      %d       |     %d      |     %d      |     %d%n",
                    processData[i][0], processData[i][1], processData[i][2], processData[i][3], processData[i][4]);
        }
        System.out.println("------------------------------------------------------------");
    }
}
