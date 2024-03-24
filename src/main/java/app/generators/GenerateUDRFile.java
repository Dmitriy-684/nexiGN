package app.generators;

import app.udrServices.ParserClass;
import app.udrServices.TimeConverter;
import app.udrServices.UDRFileManager;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A class that implements reading and providing structured information from CDR files to UDR files in Json format stored in the /reports directory
 * The class contains the fields:
 * startYear - month of the beginning of the billing period
 * endYear - month of the end of the billing period
 * pathToUDR - path to the directory /udrFiles
 * parser - a service that structures data from CDR files
 * udrManager - a service that implements editing and working with the directory /reports
 * time Converter - a service that implements the conversion of Unix-time format time to H:M:S format time
 * globalSummary - a structure containing information about the total time of outgoing and incoming calls from all users for the entire billing period
 * phones - list of phones of all users
 * The class contains methods:
 * generateReport() – saves all reports and outputs to the console a table with all subscribers and the total time of calls for the entire billing period of each subscriber
 * generateReport(msisdn) – saves all reports and displays a table in the console for one subscriber and his total call time in each month
 * generateReport(msisdn, month) – saves a report and displays a table in the console for one subscriber and his total call time in the specified month
 * getAllDataFromUDRFiles() - structures information from CDR files and saves the total time of incoming and outgoing calls of all users for the entire billing period in globalSummary
 */

@Service
@Scope(scopeName = "prototype")
public class GenerateUDRFile {
    @Value("${generateCDRFiles.startYear}")
    private byte startYear;
    @Value("${generateCDRFiles.endYear}")
    private byte endYear;
    @Value("${generateUDRFiles.UDRpath}")
    private String pathToUDR;
    @Autowired
    private ParserClass parser;
    @Autowired
    private UDRFileManager udrManager;
    @Autowired
    private TimeConverter timeConverter;
    private Map<Integer, Map<String, List<Long>>> globalSummary;
    private Set<String> phones;

    public void generateReport() {
        for (String phone: phones) {
            generateReport(phone);
        }
    }
    public void generateReport(String phoneNumber) {
        for (int month = startYear; month <= endYear; month++)
            generateReport(phoneNumber, month);
    }
    public void generateReport(String phoneNumber, int month) {
        String pathName = pathToUDR + phoneNumber + "_" + month + ".json";
        List<Long> totalTimeCall = globalSummary.get(month).get(phoneNumber);
        String incomingCall = timeConverter.convertFromUnixTime(totalTimeCall.get(0));
        String outcomingCall = timeConverter.convertFromUnixTime(totalTimeCall.get(1));
        udrManager.writeToFile(pathName,
                "{\n" +
                "   \"msisdn\": \"" + phoneNumber + "\",\n" +
                "   \"incomingCall\": {\n" +
                "        \"totalTime\": \"" + incomingCall + "\"\n" +
                "   },\n" +
                "   \"outcomingCall\": {\n" +
                "        \"totalTime\": \"" + outcomingCall + "\"\n" +
                "   }\n" +
                "}");

        System.out.println("\n\nНомер телефона: " + phoneNumber + "\n" +
                "Месяц: " + Month.of(month) + "\n" +
                "Продолжительность входящих звонков: " + incomingCall + "\n" +
                "Продолжительность исходящих звонков: " + outcomingCall + "\n\n");
    }
//    @Bean
    @PostConstruct
    public void getAllDataFromCDRFiles() {
        globalSummary = new HashMap<>();
        for (int month = startYear; month <= endYear; month++) {
            globalSummary.put(month, parser.summaryByFile(parser.readAllFileByMonth(Month.of(month))));
        }
        phones = parser.getPhoneNumbers(parser.readAllFileByMonth(Month.of(startYear)));
        if (!udrManager.isDirectoryExists()) udrManager.createDirectory();
        if (!udrManager.isDirectoryEmpty()) udrManager.deleteAllFiles();
    }
}
