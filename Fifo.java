import java.sql.Array;
import java.util.Arrays;
import java.util.Comparator;

class Fifo {

    public static void Fifo(int[][] processes) {
        Arrays.sort(processes, Comparator.comparingInt(a -> a[1]));

        System.out.println("\n========FIRST IN FIRST OUT========\n");

        for (int i = 0; i <= processes.length - 1; i++) { // This prints the header of gantt chart
            System.out.print("| P" + processes[i][0] + " ");
        }

        int[] waitingTime = new int[processes.length]; // This will be used to store the waiting times
        int[] turnAroundTime = new int[processes.length]; // This will be used to store the turn around times
        System.out.print("|\n0"); // Prints the start of the gantt diagram

        int ii = 0; // initializes a counter
        int time = 0; // simulates the time of the CPU
        int processTime = 0; // simulates the time spent on each process
        while (true) { // beginning of the CPU loop
            if (ii == processes.length) { // If the number of processes completed equals the amount of proesses, theres nothing left to do, so the loop is broken.
                break; // break loop
            } else if (processes[ii][1] < time) { //check if arrival time has passed
                processTime++; // 1 time unit for the current process is elapsed
                if (processTime == processes[ii][2]) { // if the amount of time spent on the process equals the burst time of the current process, waiting time of process is calculated.
                    waitingTime[ii] = (time -
                        processes[ii][1] -
                        processes[ii][2]);
                    turnAroundTime[ii] = (time - processes[ii][1]); // Turn around time is calculated
                    ii++; // counter is incremented
                    processTime = 0; // new process, so process time is reset
                    System.out.print("   " + time + " "); // print out the time for the gantt diagram
                    time++; // Overall CPU time is incremented
                } else { // if process still isnt done
                    time++; // CPU time is incremented
                }
            } else {
                time++;
            }
        }

        int waitingTimeTotal = 0;

        System.out.println("\nPID | Waiting Time | Turnaround Time");
        for (int i = 0; i < waitingTime.length; i++) {
            waitingTimeTotal = (waitingTimeTotal + waitingTime[i]);
            System.out.print(
                "   " +
                i +
                "|\t\t  " +
                waitingTime[i] +
                "|\t\t  " +
                turnAroundTime[i] +
                "\n"
            );
        }

        double waitingTimeAverage =
            (double) waitingTimeTotal / (double) waitingTime.length;
        System.out.println("\nWaiting Time: " + waitingTimeTotal);
        System.out.println("Average Waiting Time: " + waitingTimeAverage);
        double averageTurnAroundTime =
            ((waitingTimeTotal + (time - 1)) / (double) processes.length);
        System.out.println(
            "Turn Around Time: " +
            (waitingTimeTotal + (time - 1)) +
            "\nAverage Turn Around Time: " +
            averageTurnAroundTime
        );
    }
}
