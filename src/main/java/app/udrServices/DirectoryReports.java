package app.udrServices;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

/**
 * A class that implements editing of the /reports directory
 * The class contains the fields:
 * pathToRecordsDirectory - path to the /reports directory
 * The class contains the following methods:
 * writeToFile(String pathName, String data) - writing data data to a file by pathName
 * deleteAllJsonFiles() - clears the /reports directory
 */

@Service
public class DirectoryReports {
    @Value("${generateUDRFiles.UDRpath}")
    private String pathToRecordsDirectory;
    public void writeToFile(String pathName, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathName))) {
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void deleteAllJsonFiles() {
        try {
            File reports = new File(pathToRecordsDirectory);
            if (reports.isDirectory()) {
                for (File file: Objects.requireNonNull(reports.listFiles()))
                    FileUtils.delete(file);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
