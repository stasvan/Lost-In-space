package IO;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileSystemService {

    public FileSystemService() {
    }




    public void writeToFile(String filename, String text) {

        try(FileWriter writer = new FileWriter(filename, true)) {

            writer.write(text + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public List<String> readLines(String filename) {

        try {
            return Files.readAllLines(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
