package app.generators;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.*;

import java.util.*;

@Service
public class GenerateCDRFile {
    @Value("${generateCDRFiles.telephones.count}")
    private int countOfPhoneNumbers;
    @Value("${generateCDRFiles.currentYear}")
    private int year;
    @Value("${generateCDRFiles.callMinTime}")
    private int callMinTime;
    @Value("${generateCDRFiles.callMaxTime}")
    private int callMaxTime;
    @Value("${generateCDRFiles.startYear}")
    private int startYear;
    @Value("${generateCDRFiles.endYear}")
    private int endYear;
    @Value("${generateCDRFiles.CDRpath}")
    private String path;
    @Value("${generateCDRFiles.cdrFilePrefix}")
    private String cdrFilePrefix;
    @Value("${generateCDRFiles.cdrFileType}")
    private String cdrFileType;
    private final Set<String> subscribersTelephones = new HashSet<>();
    private HashMap<String, ArrayList<String>> lastGeneratedCDRData;
    @PostConstruct
    private void generateSubscribersTelephones() {
        Random random = new Random();
        while (subscribersTelephones.size() != countOfPhoneNumbers) {
            String builder = "7" + random.nextInt(920, 937) +
                    random.nextInt(1000000, 10000000);
            subscribersTelephones.add(builder);
        }
    }
    public int getStartYear(){return startYear;}
    public int getEndYear(){return endYear;}
    public Set<String> getSubscribersTelephones(){
        return subscribersTelephones;
    }
    public HashMap<String, ArrayList<String>> getLastGeneratedCDRData(){return lastGeneratedCDRData;}
    private List<Long> generateTransactionsTime(Month month){
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
    private void generateTransactionsMaps(String phone, Month month, Map<Long, List<String>> transactions) {
        List<Long> times = generateTransactionsTime(month);
        lastGeneratedCDRData.put(phone, new ArrayList<>());
        String transaction;
        for (int i = 0; i < times.size(); i+=2) {
            transaction = generateCallType() + "," + phone + ", " + times.get(i) + ", " + times.get(i+1);
            lastGeneratedCDRData.get(phone).add(transaction);
            if (!transactions.containsKey(times.get(i))) transactions.put(times.get(i), new ArrayList<>());
            transactions.get(times.get(i)).add(transaction);
        }
    }
    private void writeToCDR(String fileName, Map<Long, List<String>> cdrData) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path + fileName))) {
            for (Map.Entry transactions: cdrData.entrySet()) {
                for (String transaction: (List<String>) transactions.getValue()) writer.write(transaction + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void generateCDR(int month) {
        Map<Long, List<String>> transactions = new TreeMap<>();
        lastGeneratedCDRData = new HashMap<>();
        for (String phone : subscribersTelephones) {
            generateTransactionsMaps(phone, Month.of(month), transactions);
        }
        writeToCDR(cdrFilePrefix + Month.of(month) + cdrFileType, transactions);
    }
}
