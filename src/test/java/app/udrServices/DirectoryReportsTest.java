package app.udrServices;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DirectoryReportsTest {

    @Autowired
    private DirectoryReports directoryReports;

    @Test
    void writeToFileTest() {
        String filePath = "./src/main/resources/reports/TestFile";

        String data = "Test text";

        directoryReports.writeToFile(filePath, data);

        assertTrue(new File(filePath).exists());

        try {
            assertEquals(data, Files.readString(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteAllJsonFiles() {
        File directory = new File("./src/main/resources/reports/");
        File testFile1 = new File(directory, "TestFile1.json");
        File testFile2 = new File(directory, "TestFile2.json");

        try {
            assertTrue(testFile1.createNewFile());
            assertTrue(testFile2.createNewFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        directoryReports.deleteAllJsonFiles();

        assertFalse(testFile1.exists());
        assertFalse(testFile2.exists());
    }
}