import app.config.SpringConfig;
import app.generate.GenerateCDRFiles;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class JavaTest {
    @Test
    public void test() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

        GenerateCDRFiles generator = context.getBean("generateCDRFiles", GenerateCDRFiles.class);
        System.out.println(generator.generateTelephoneNumbers());

        context.close();
    }
}
