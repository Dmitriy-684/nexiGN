import app.config.SpringConfig;
import app.generate.GenerateCDRFiles;
import app.generate.GenerateUDRFiles;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class JavaTest {
    @Test
    public void test() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

        GenerateCDRFiles generatorCDR = context.getBean("generateCDRFiles", GenerateCDRFiles.class);
        GenerateUDRFiles generatorUDR = context.getBean("generateUDRFiles", GenerateUDRFiles.class);
//        DirectoryReports directory = context.getBean("directoryReports", DirectoryReports.class);

//        directory.deleteAllJsonFiles();
//        generatorCDR.generate();
        generatorUDR.generateReport("79342098718", 2);
        context.close();

    }
}
