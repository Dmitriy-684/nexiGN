
import app.cdrServices.CDRFileManager;
import app.config.SpringConfig;
import app.generators.GenerateUDRFile;
import app.udrServices.UDRFileManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class JavaTest {
    @Test
    public void test() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

        context.close();
    }
}
