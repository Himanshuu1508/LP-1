import java.util.Scanner;

public class PriorityPreemptive {
    int count, currentTime = 0;
    int[][] processData;
    Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        PriorityPreemptive scheduler = new PriorityPreemptive();
        scheduler.setValues();
        System.out.println("\nBefore Scheduling");
        scheduler.displayValues();
        scheduler.schedulePriorityPreemptive();
        System.out.println("\nAfter Scheduling");
        scheduler.displayValues();
        scheduler.calculateAndDisplayAvgTimes();
    }

    public void setValues() {
        System.out.print("Enter Number of processes: ");
        count = scan.nextInt();
        processData = new int[count][7]; // Process ID, Arrival Time, Burst Time, Priority, Start Time, End Time, Remaining Time

        for (int i = 0; i < count; i++) {
            processData[i][0] = i;
            System.out.print("Process [" + i + "] -> Arrival Time: ");
            processData[i][1] = scan.nextInt();
            System.out.print("Process [" + i + "] -> Burst Time: ");
            processData[i][2] = scan.nextInt();
            System.out.print("Process [" + i + "] -> Priority: ");
            processData[i][3] = scan.nextInt();
            processData[i][6] = processData[i][2]; // Initial remaining time is burst time
        }
    }

    public void schedulePriorityPreemptive() {
        int completedProcesses = 0;

        while (completedProcesses < count) {
            int highestPriorityIndex = -1, highestPriority = Integer.MAX_VALUE;

            for (int i = 0; i < count; i++) {
                if (processData[i][1] <= currentTime && processData[i][6] > 0 && processData[i][3] < highestPriority) {
                    highestPriority = processData[i][3];
                    highestPriorityIndex = i;
                }
            }

            if (highestPriorityIndex == -1) {
                currentTime++;
            } else {
                if (processData[highestPriorityIndex][4] == 0) 
                    processData[highestPriorityIndex][4] = currentTime; // Set start time if not already set
                processData[highestPriorityIndex][6]--; // Decrease remaining time
                currentTime++;

                if (processData[highestPriorityIndex][6] == 0) {
                    processData[highestPriorityIndex][5] = currentTime; // Set end time
                    completedProcesses++;
                }
            }
        }
    }

    public void calculateAndDisplayAvgTimes() {
        int totalWaitingTime = 0, totalTurnaroundTime = 0;

        System.out.println("\nProcess ID | Waiting Time | Turnaround Time");
        System.out.println("-------------------------------------------");

        for (int i = 0; i < count; i++) {
            int turnaroundTime = processData[i][5] - processData[i][1];
            int waitingTime = turnaroundTime - processData[i][2];

            System.out.printf("   P[%d]       |      %d       |     %d%n", processData[i][0], waitingTime, turnaroundTime);

            totalWaitingTime += waitingTime;
            totalTurnaroundTime += turnaroundTime;
        }

        System.out.println("-------------------------------------------");

        float avgWaitingTime = (float) totalWaitingTime / count;
        float avgTurnaroundTime = (float) totalTurnaroundTime / count;

        System.out.printf("\nAverage Waiting Time: %.2f", avgWaitingTime);
        System.out.printf("\nAverage Turnaround Time: %.2f%n", avgTurnaroundTime);
    }

    public void displayValues() {
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("Process ID | Arrival Time | Burst Time | Priority | Start Time | End Time");
        System.out.println("-----------------------------------------------------------------------");
        for (int i = 0; i < count; i++) {
            System.out.printf("   P[%d]       |      %d       |     %d      |    %d     |      %d      |     %d%n",
                    processData[i][0], processData[i][1], processData[i][2], processData[i][3], processData[i][4], processData[i][5]);
        }
        System.out.println("-----------------------------------------------------------------------");
    }
}
