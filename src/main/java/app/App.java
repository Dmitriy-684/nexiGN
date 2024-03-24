package app;

import app.config.SpringConfig;
import app.db.DataBase;
import app.generators.GenerateCDRFile;
import app.generators.GenerateUDRFile;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * A class that implements the launch of the application
 * The class contains the main() method, which launches the application
 */

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        DataBase dataBase = context.getBean("baseH2", DataBase.class);
        GenerateCDRFile generateCDRFile = context.getBean("generateCDRFile", GenerateCDRFile.class);;
        dataBase.saveAllSubscribersTelephones(generateCDRFile.getSubscribersTelephones());
        for (int month = generateCDRFile.getStartYear(); month <= generateCDRFile.getEndYear(); month++){
            generateCDRFile.generateCDR(month);
            dataBase.saveSubscribersTransactions(generateCDRFile.getLastGeneratedCDRData(),
                    generateCDRFile.getSubscribersTelephones(), month);
        }
        GenerateUDRFile generateUDRFile = context.getBean("generateUDRFile", GenerateUDRFile.class);

        generateUDRFile.generateReport();
        context.close();
    }
}