package app.udrServices;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;


class UDRFileManagerTest {
    @Test
    void writeToFileTest() {
        UDRFileManager udrFileManager = new UDRFileManager();
        String filePath = "./reports/TestFile";

        String data = "Test text";

        udrFileManager.writeToFile(filePath, data);

        assertTrue(new File(filePath).exists());

        try {
            assertEquals(data, Files.readString(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}