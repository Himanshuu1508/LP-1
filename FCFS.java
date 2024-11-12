import java.util.Scanner;

public class FCFS {
    int count = 0;
    int[][] prodata;
    Scanner scan = new Scanner(System.in);

    public void setval() {
        System.out.println("Enter the number of processes:");
        count = scan.nextInt();
        prodata = new int[count][4];  // Columns: [Process ID, Burst Time, Waiting Time, Turnaround Time]

        for (int i = 0; i < count; i++) {
            System.out.println("Enter burst time for process " + i + ":");
            prodata[i][0] = i;  // Process ID
            prodata[i][1] = scan.nextInt();  // Burst Time
        }
    }

    public void schedule() {
        // Initializing waiting time and turnaround time for the first process
        prodata[0][2] = 0;  // Waiting time for first process is 0
        prodata[0][3] = prodata[0][1];  // Turnaround time for first process = burst time

        for (int i = 1; i < count; i++) {
            // Waiting time = previous process's turnaround time
            prodata[i][2] = prodata[i - 1][3];
            // Turnaround time = waiting time + burst time
            prodata[i][3] = prodata[i][2] + prodata[i][1];
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
        FCFS fcfs = new FCFS();
        fcfs.setval();
        fcfs.schedule();
        fcfs.display();
    }
}
