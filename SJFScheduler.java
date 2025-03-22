import java.util.*;

public class SJFScheduler {

    // Main method to execute the Shortest Job First (SJF) scheduling
    public static void schedule(List<Process> processes) {
        // Sort processes by arrival time
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        // PriorityQueue to pick the process with the shortest burst time
        PriorityQueue<Process> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.burstTime));

        int currentTime = 0;
        int completed = 0;
        List<String> ganttChart = new ArrayList<>();
        List<Integer> timeTrack = new ArrayList<>();
        Set<Integer> addedPIDs = new HashSet<>(); 

        timeTrack.add(currentTime);

        while (completed < processes.size()) {
            // Add processes that have arrived and haven't been added yet
            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && !addedPIDs.contains(p.pid)) {
                    pq.add(p);
                    addedPIDs.add(p.pid);
                }
            }

            if (!pq.isEmpty()) {
                // Pick the process with the shortest burst time
                Process current = pq.poll();
                ganttChart.add("P" + current.pid);

                // Calculate times
                current.waitingTime = currentTime - current.arrivalTime;
                current.turnaroundTime = current.waitingTime + current.burstTime;

                currentTime += current.burstTime;
                timeTrack.add(currentTime);

                completed++;
            } else {
                // CPU is idle
                ganttChart.add("Idle");
                currentTime++;
                timeTrack.add(currentTime);
            }
        }

        displayResults(processes, ganttChart, timeTrack);
    }

    // Display Gantt chart and process stats
    private static void displayResults(List<Process> processes, List<String> ganttChart, List<Integer> timeTrack) {
        System.out.println("\nGantt Chart:");
        for (String p : ganttChart) {
            System.out.print("| " + p + " ");
        }
        System.out.println("|");
        for (Integer time : timeTrack) {
            System.out.print(time + " ");
        }
        System.out.println("\n");

        double totalWT = 0, totalTAT = 0;
        System.out.println("PID | Waiting Time | Turnaround Time");

        for (Process p : processes) {
            System.out.printf("%3d | %12d | %15d%n", p.pid, p.waitingTime, p.turnaroundTime);
            totalWT += p.waitingTime;
            totalTAT += p.turnaroundTime;
        }

        System.out.printf("\nAverage Waiting Time: %.2f%n", totalWT / processes.size());
        System.out.printf("Average Turnaround Time: %.2f%n", totalTAT / processes.size());
    }
}
