package app.generators;

import app.parser.ParserClass;
import app.udrServices.TimeConverter;
import app.udrServices.DirectoryReports;
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

@Service
@Scope(scopeName = "prototype")
public class GenerateUDRFiles {
    @Value("${generateCDRFiles.startYear}")
    private byte startYear;
    @Value("${generateCDRFiles.endYear}")
    private byte endYear;
    @Value("${generateUDRFiles.UDRpath}")
    private String pathToUDR;
    @Autowired
    private ParserClass parser;
    @Autowired
    private DirectoryReports directoryReports;
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
        directoryReports.writeToFile(pathName,
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
        directoryReports.deleteAllJsonFiles();
    }
}
