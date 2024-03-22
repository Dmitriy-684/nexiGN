package app.generate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.*;

import java.util.*;

@Service
public class GenerateCDRFiles {
    @Value("${generateCDRFiles.telephones.count}")
    private int countOfPhoneNumbers;
    @Value("${generateCDRFiles.currentYear}")
    private int year;
    @Value("${generateCDRFiles.callMinTime}")
    private int callMinTime;
    @Value("${generateCDRFiles.callMaxTime}")
    private int callMaxTime;
    @Value("${generateCDRFiles.startYear}")
    private byte startYear;
    @Value("${generateCDRFiles.endYear}")
    private byte endYear;
    @Value("${generateCDRFiles.CDRpath}")
    private String path;
    private Map<Long, List<String>> cdrFile;


    private Set<String> generateTelephoneNumbers() {
        Random random = new Random();
        Set<String> listOfNumbers = new HashSet<>();
        while (listOfNumbers.size() != countOfPhoneNumbers) {
            String builder = "7" + random.nextInt(920, 937) +
                    random.nextInt(1000000, 10000000);
            listOfNumbers.add(builder);
        }
        return listOfNumbers;
    }
    private List<Long> generatorTime(Month month){
        List<Long> listTime = new ArrayList<>();
        Random random = new Random();

        int daysInMonth = month.length(Year.isLeap(year));

        Instant dateStart = LocalDate.of(year, month, 1).atStartOfDay().atZone(ZoneId.of("UTC")).toInstant();
        Instant dateEnd = LocalDate.of(year, month, daysInMonth).atStartOfDay().atZone(ZoneId.of("UTC")).toInstant();

        int callLength;
        for(long i = dateStart.getEpochSecond(); i <= dateEnd.getEpochSecond(); i += callLength){
            callLength = random.nextInt(callMinTime, callMaxTime);
            listTime.add(i);
        }
        if (listTime.size() % 2 != 0){
            listTime.remove(listTime.size() - 1);
        }
        return listTime;
    }
    private String generateCallType() {return "0" + new Random().nextInt(1, 3);}
    private void generateTransactionsMap(String phone, Month month) {
        List<Long> times = generatorTime(month);

        for (int i = 0; i < times.size(); i+=2) {
            if (!cdrFile.containsKey(times.get(i))) cdrFile.put(times.get(i), new ArrayList<>());
            cdrFile.get(times.get(i)).add(generateCallType() + "," + phone + ", " + times.get(i) + ", " + times.get(i+1));
        }
    }
    private void generateCDRFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path + fileName, true))) {
            for (Map.Entry transactions: cdrFile.entrySet()) {
                for (String transaction: (List<String>) transactions.getValue()) writer.write(transaction + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void generate() {
        Set<String> phoneNumbers = generateTelephoneNumbers();
        cdrFile = new TreeMap<>();
        for (int month = startYear; month <= endYear; month++) {
            for (String phone : phoneNumbers) {
                generateTransactionsMap(phone, Month.of(month));
            }
            generateCDRFile("CDR_" + Month.of(month) + ".txt");
            cdrFile.clear();
        }
    }
}
