// Need to import explicitly
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReaderMain extends ReadFile {

    public static void main(String[] args) {
        List<Process> processes = readProcessesFromFile("processes.txt");
        if (processes.isEmpty()) {
            System.out.println("No processes found in the file.");
            return;
        }

        System.out.println("Executing Shortest Job First (SJF) Scheduling:");
        SJFScheduler.schedule(new ArrayList<>(processes));

        ReadFile();
    }

    public static List<Process> readProcessesFromFile(String filename) {
        List<Process> processes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                // Split at whitespace
                String[] parts = line.trim().split("\\s+");
                if (parts.length == 4) {
                    processes.add(
                        new Process(
                            Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]),
                            Integer.parseInt(parts[2]),
                            Integer.parseInt(parts[3])
                        )
                    );
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return processes;
    }
}
