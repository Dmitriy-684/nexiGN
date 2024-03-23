package app;

import app.config.SpringConfig;
import app.db.DataBase;
import app.generators.GenerateCDRFile;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
        context.close();
    }
}