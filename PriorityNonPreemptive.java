import java.util.Scanner;

public class PriorityNonPreemptive {
    int count = 0;
    int[][] prodata;
    Scanner scan = new Scanner(System.in);

    public void setValues() {
        System.out.print("Enter the number of processes: ");
        count = scan.nextInt();
        prodata = new int[count][5]; // Columns: [Process ID, Burst Time, Priority, Waiting Time, Turnaround Time]

        for (int i = 0; i < count; i++) {
            prodata[i][0] = i;
            System.out.print("Process [" + i + "] -> Burst Time: ");
            prodata[i][1] = scan.nextInt();
            System.out.print("Process [" + i + "] -> Priority: ");
            prodata[i][2] = scan.nextInt();
        }

        sortByPriority(); // Sort by priority for non-preemptive priority scheduling
    }

    public void sortByPriority() {
        for (int i = 0; i < count - 1; i++) {
            for (int j = i + 1; j < count; j++) {
                if (prodata[i][2] > prodata[j][2]) { // Lower priority number indicates higher priority
                    swap(i, j);
                }
            }
        }
    }

    public void swap(int p1, int p2) {
        int[] temp = prodata[p1];
        prodata[p1] = prodata[p2];
        prodata[p2] = temp;
    }

    public void schedule() {
        prodata[0][3] = 0; // Waiting time for the first process is 0
        prodata[0][4] = prodata[0][1]; // Turnaround time for the first process = burst time

        for (int i = 1; i < count; i++) {
            prodata[i][3] = prodata[i - 1][4]; // Waiting time = previous process's turnaround time
            prodata[i][4] = prodata[i][3] + prodata[i][1]; // Turnaround time = waiting time + burst time
        }
    }

    public void display() {
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        System.out.println("\nProcess\tBurst Time\tPriority\tWaiting Time\tTurnaround Time");
        for (int i = 0; i < count; i++) {
            System.out.println("P" + prodata[i][0] + "\t" + prodata[i][1] + "\t\t" + prodata[i][2] + "\t\t" 
                               + prodata[i][3] + "\t\t" + prodata[i][4]);
            totalWaitingTime += prodata[i][3];
            totalTurnaroundTime += prodata[i][4];
        }

        float avgWaitingTime = (float) totalWaitingTime / count;
        float avgTurnaroundTime = (float) totalTurnaroundTime / count;

        System.out.println("\nAverage Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
    }

    public static void main(String[] args) {
        PriorityNonPreemptive priorityScheduler = new PriorityNonPreemptive();
        priorityScheduler.setValues();
        priorityScheduler.schedule();
        priorityScheduler.display();
    }
}
