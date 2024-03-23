import app.config.SpringConfig;
import app.generators.GenerateCDRFile;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class JavaTest {
    @Test
    public void test() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

        GenerateCDRFile generator = context.getBean("generateCDRFiles", GenerateCDRFile.class);
//        System.out.println(generator.generateTelephoneNumbers());
        context.close();

    }
}
