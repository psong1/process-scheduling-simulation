import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Scanner;
import javax.sound.sampled.Line;

public class ReadFile extends Fifo {

    static int[][] proc = new int[4][4];

    public static void ReadFile() {
        int i = 0;
        String[] stringArr;

        int lines = 0;
        File getNumber = new File("processes.txt");
        try {
            Scanner lineCounter = new Scanner(getNumber);
            while (lineCounter.hasNextLine()) {
                lines++;
                lineCounter.nextLine();
            }
            lineCounter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int[][] proc = new int[lines][4];

        try {
            File processes = new File("processes.txt");
            Scanner fileReader = new Scanner(processes);
            while (fileReader.hasNextLine()) {
                String data = fileReader.nextLine();
                System.out.println(data);
                if (i > 0) {
                    stringArr = (data.replaceAll("\\s", "")).split("");
                    for (int ii = 0; ii < 4; ii++) {
                        proc[i][ii] = Integer.parseInt(stringArr[ii]);
                    }
                }
                i++;
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error has occured");
            e.printStackTrace();
        }
        proc = Arrays.copyOfRange(proc, 1, proc.length);

        Fifo(proc);
    }
}
