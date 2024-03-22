import app.config.SpringConfig;
import app.generate.GenerateCDRFiles;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.Month;
import java.time.Year;

public class JavaTest {
    @Test
    public void test() {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
//
//        GenerateCDRFiles generator = context.getBean("generateCDRFiles", GenerateCDRFiles.class);
//        System.out.println(generator.generateTelephoneNumbers());
        int daysInMonth = Month.FEBRUARY.length(Year.isLeap(2023));
        System.out.println(daysInMonth);
//        context.close();

    }
    @Test
    public void test1(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

        GenerateCDRFiles generator = context.getBean("generateCDRFiles", GenerateCDRFiles.class);
        System.out.println(generator.generatorTime(Month.DECEMBER));
        context.close();
    }
}
