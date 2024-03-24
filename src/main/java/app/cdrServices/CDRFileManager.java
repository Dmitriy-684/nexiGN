package app.cdrServices;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * A class that implements editing of the /cdrFiles directory
 * The class contains the fields:
 * directoryPath - path to the /cdrFiles directory
 * The class contains the following methods:
 * isDirectoryExists() - returns true if directory /cdrFiles exist, else return false
 * isDirectoryEmpty() - returns true if directory /cdrFiles empty, else return false
 * createDirectory() - create the directory /cdrFiles
 * deleteAllFiles() - clears the /cdrFiles directory
 */

@Service
public class CDRFileManager {
    @Value("${cdrFileManager.pathToCDRDirectory}")
    private String directoryPath;
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
