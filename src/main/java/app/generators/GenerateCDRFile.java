package app.generators;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.*;

import java.util.*;

/**
 * A class that implements the generation of CDR files and pseudo-random data for them
 * The class contains the fields:
 * countOfPhone Numbers - the number of subscribers
 * year - billing year
 * CallMinTime - minimum delay time between calls
 * callMaxTime - maximum delay time between calls
 * startYear - month of the beginning of the billing period
 * endYear - month of the end of the billing period
 * path - path to the directory /cdrFiles
 * cdrFilePrefix - CDR file prefix (CDR_)
 * cdrFileType - type of CDR file
 * subscribersTelephones - list of phones of all subscribers
 * lastGeneratedCDRData - a field that contains all transactions of the last generated CDR file
 * The class contains methods:
 * generateSubscribersTelephones() - generates pseudorandom subscriber phones
 * getStartYear() - returns the month of the beginning of the billing period
 * getEndYear() - returns the month of the end of the billing period
 * getSubscribersTelephones() - returns a list of phone numbers of all subscribers
 * getLastGeneratedCDRData() - returns all transactions of the last generated CDR file
 * generateTransactionsTime(Month month) - returns a list of the start and end points of one user's transactions for one month in Unix-time format
 * callStrategy(int strategy) - returns the sleep time between calls for the user
 * generateCallType() - returns a pseudo-random transaction type (incoming/outgoing)
 * generateTransactionsMaps(String phone, Month month, Map<Long, List<String>> transactions) - generates all transactions in one month for one subscriber
 * and fills in the lastGeneratedCDRData field
 * writeToCDR(String fileName, Map<Long, List<String>> cdrData) - creates a CDR file and writes all transactions to it all users per month
 * generateCDR(int month) - the main method of the class, creates and writes all transactions for a month to a CDR file
 */

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
    private List<Long> generateTransactionsTime(Month month) {
        List<Long> listTime = new ArrayList<>();
        Random random = new Random();
        int callLength;
        int daysInMonth = month.length(Year.isLeap(year));
        int flagSleep = 0;
        Instant dateStart = LocalDate.of(year, month, 1).atStartOfDay().atZone(ZoneId.of("UTC")).toInstant();
        Instant dateEnd = LocalDate.of(year, month, daysInMonth).atStartOfDay().atZone(ZoneId.of("UTC")).toInstant();

        for(long i = dateStart.getEpochSecond(); i <= dateEnd.getEpochSecond(); i += callLength){
            flagSleep += 1;
            if (flagSleep % 2 == 0){
                callLength = callStrategy(2);
            }else {
                callLength = callStrategy(1);
            }
            listTime.add(i);
        }

        if (listTime.size() % 2 != 0){
            listTime.remove(listTime.size() - 1);
        }
        return listTime;
    }

    public int callStrategy(int strategy){
        Random random = new Random();
        int callSleep = 0;
        int callLength = 0;
        if (strategy == 2){
            callSleep = random.nextInt(100000,1000000);
        } else {
            callLength = random.nextInt(1,4);
        }

        if (callLength == 1){
            callSleep = random.nextInt(300,3200);
        }else if (strategy == 2){
            callSleep = random.nextInt(100,400);
        }else if (strategy == 3){
            callSleep = random.nextInt(400,1000);
        }else if (strategy == 4){
            callSleep = random.nextInt(1000,2100);
        }
        return callSleep;
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
