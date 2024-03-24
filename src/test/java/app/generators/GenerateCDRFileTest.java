package app.generators;

import app.cdrServices.CDRFileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Month;
import static org.mockito.Mockito.*;

public class GenerateCDRFileTest {
    @InjectMocks
    private GenerateCDRFile generateCDRFile;

    @Mock
    private CDRFileManager cdrFileManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGenerateTransactionsTime() throws Exception {
        GenerateCDRFile generateCDRFile = new GenerateCDRFile();
        Method method = GenerateCDRFile.class.getDeclaredMethod("generateTransactionsTime", Month.class);
        method.setAccessible(true);

        Month month = Month.JANUARY;
        Object result = method.invoke(generateCDRFile, month);

        assertNotNull(result);
    }

    @Test
    public void testGenerateCDR() {

        int month = 1;
        when(cdrFileManager.isDirectoryExists()).thenReturn(true);
        when(cdrFileManager.isDirectoryEmpty()).thenReturn(true);

        generateCDRFile.generateCDR(month);

        verify(cdrFileManager, times(1)).isDirectoryExists();
        verify(cdrFileManager, times(1)).isDirectoryEmpty();
    }
}