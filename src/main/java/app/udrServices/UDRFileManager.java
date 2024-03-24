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
 * directoryPath - path to the /reports directory
 * The class contains the following methods:
 * writeToFile(String pathName, String data) - writing data to a file by pathName
 * deleteAllJsonFiles() - clears the /reports directory
 */

@Service
public class UDRFileManager {
    @Value("${udrFileManager.pathToUDRDirectory}")
    private String directoryPath;
    public void writeToFile(String pathName, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathName))) {
            writer.write(data);
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
    public boolean isDirectoryExists() {
        File cdrFilesDirectory = new File(directoryPath);
        return cdrFilesDirectory.exists();
    }
    public boolean isDirectoryEmpty() {
        File cdrDirectory = new File(directoryPath);
        if (cdrDirectory.isDirectory()) return Objects.requireNonNull(cdrDirectory.listFiles()).length == 0;
        return false;
    }
    public boolean createDirectory() {
        File cdrDirectory = new File(directoryPath);
        if (!isDirectoryExists()) {
            return cdrDirectory.mkdir();
        }
        return false;
    }
    public void deleteAllFiles() {
        try {
            if (!isDirectoryEmpty()) {
                File reports = new File(directoryPath);
                if (reports.isDirectory()) {
                    for (File file : Objects.requireNonNull(reports.listFiles()))
                        FileUtils.delete(file);
                }
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}
