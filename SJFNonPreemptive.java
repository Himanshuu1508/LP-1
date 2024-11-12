import java.util.Scanner;

public class SJFNonPreemptive {
    int count = 0;
    int[][] prodata;
    Scanner scan = new Scanner(System.in);

    public void setval() {
        System.out.println("Enter the number of processes:");
        count = scan.nextInt();
        prodata = new int[count][4]; // Columns: [Process ID, Burst Time, Waiting Time, Turnaround Time]

        for (int i = 0; i < count; i++) {
            System.out.println("Enter burst time for process " + i + ":");
            prodata[i][0] = i; // Process ID
            prodata[i][1] = scan.nextInt(); // Burst Time
        }
        
        sortByBurstTime(); // Sort processes by burst time for SJF
    }

    public void sortByBurstTime() {
        for (int i = 0; i < count - 1; i++) {
            for (int j = i + 1; j < count; j++) {
                if (prodata[i][1] > prodata[j][1]) {
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
        prodata[0][2] = 0; // Waiting time for the first process is 0
        prodata[0][3] = prodata[0][1]; // Turnaround time for the first process = burst time

        for (int i = 1; i < count; i++) {
            prodata[i][2] = prodata[i - 1][3]; // Waiting time = previous process's turnaround time
            prodata[i][3] = prodata[i][2] + prodata[i][1]; // Turnaround time = waiting time + burst time
        }
    }

    public void display() {
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        System.out.println("\nProcess\tBurst Time\tWaiting Time\tTurnaround Time");
        for (int i = 0; i < count; i++) {
            System.out.println("P" + prodata[i][0] + "\t" + prodata[i][1] + "\t\t" 
                               + prodata[i][2] + "\t\t" + prodata[i][3]);
            totalWaitingTime += prodata[i][2];
            totalTurnaroundTime += prodata[i][3];
        }

        float avgWaitingTime = (float) totalWaitingTime / count;
        float avgTurnaroundTime = (float) totalTurnaroundTime / count;

        System.out.println("\nAverage Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
    }

    public static void main(String[] args) {
        SJFNonPreemptive sjf = new SJFNonPreemptive();
        sjf.setval();
        sjf.schedule();
        sjf.display();
    }
}
