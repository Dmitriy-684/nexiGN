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
        boolean isTheStartOfCall = true;
        int daysInMonth = month.length(Year.isLeap(year)), timeS;
        float subscriberCallLengthCoeff = new Random().nextFloat(0.1F, 1F),
                subscriberCallBreakCoeff = new Random().nextFloat(1F, 1.5F), timeConvert;
        Instant dateStart = LocalDate.of(year, month, 1).atStartOfDay().atZone(ZoneId.of("UTC")).toInstant();
        Instant dateEnd = LocalDate.of(year, month, daysInMonth).atStartOfDay().atZone(ZoneId.of("UTC")).toInstant();
        for(long i = dateStart.getEpochSecond(); i <= dateEnd.getEpochSecond(); i += timeS){
            timeConvert = isTheStartOfCall ? 60*subscriberCallLengthCoeff : 3600*subscriberCallBreakCoeff;
            timeS = generateTimeS((int) (timeConvert));
            listTime.add(i);
            isTheStartOfCall = !isTheStartOfCall;
        }
        if (listTime.size() % 2 != 0){listTime.remove(listTime.size() - 1);}
        return listTime;
    }
    private int generateTimeS(int timeCoeff){
        Random random = new Random();
        int timeS;
        switch (random.nextInt(1, 27)){
            case 1 -> timeS = (int) random.nextFloat(0.1F, 1F) * timeCoeff;
            case 2 -> timeS = (int) random.nextFloat(1F, 2F) * timeCoeff;
            case 3 -> timeS = (int) random.nextFloat(2F, 3F) * timeCoeff;
            case 4 -> timeS = (int) random.nextFloat(3F, 4F) * timeCoeff;
            case 5 -> timeS = (int) random.nextFloat(4F, 5F) * timeCoeff;
            case 6 -> timeS = (int) random.nextFloat(5F, 6F) * timeCoeff;
            case 7 -> timeS = (int) random.nextFloat(6F, 7F) * timeCoeff;
            case 8 -> timeS = (int) random.nextFloat(7F, 8F) * timeCoeff;
            case 9 -> timeS = (int) random.nextFloat(8F, 9F) * timeCoeff;
            case 10 -> timeS = (int) random.nextFloat(9F, 10F) * timeCoeff;
            case 12 -> timeS = (int) random.nextFloat(10F, 11F) * timeCoeff;
            case 13 -> timeS = (int) random.nextFloat(11F, 12F) * timeCoeff;
            case 14 -> timeS = (int) random.nextFloat(12F, 13F) * timeCoeff;
            case 15 -> timeS = (int) random.nextFloat(13F, 14F) * timeCoeff;
            case 16 -> timeS = (int) random.nextFloat(14F, 15F) * timeCoeff;
            case 17 -> timeS = random.nextInt(10, 15) * timeCoeff;
            case 18 -> timeS = random.nextInt(15, 20) * timeCoeff;
            case 19 -> timeS = random.nextInt(20, 25) * timeCoeff;
            case 20 -> timeS = random.nextInt(25, 30) * timeCoeff;
            case 21 -> timeS = random.nextInt(35, 40) * timeCoeff;
            case 22 -> timeS = random.nextInt(40, 45) * timeCoeff;
            case 23 -> timeS = random.nextInt(45, 50) * timeCoeff;
            case 24 -> timeS = random.nextInt(50 , 55) * timeCoeff;
            case 25 -> timeS = random.nextInt(55, 60) * timeCoeff;
            default -> timeS = random.nextInt(60, 65) * timeCoeff;
        }
        return timeS;
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
