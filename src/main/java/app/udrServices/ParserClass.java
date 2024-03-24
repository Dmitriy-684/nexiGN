package app.udrServices;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Month;
import java.util.*;

/**
 * A class that implements the structuring of data from CDR files
 * The class contains fields:
 * path - the path to the /cdrFiles directory
 * The class contains methods:
 * readAllFileByMonth(Month month) - returns all transactions of all users in one month
 * summaryByFile(List<String> transactions) - returns the total time of incoming and outgoing calls of each user in one month
 * getPhoneNumber(List<String> transactions) - returns a list of all users' phone numbers
 */

@Service
public class ParserClass {
    @Value("${generateCDRFiles.CDRpath}")
    private String path;
    public List<String> readAllFileByMonth(Month month) {
        List<String> dataFromFile = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path + "CDR_" + month + ".txt"))) {
            dataFromFile = reader.lines().toList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataFromFile;
    }
    public Map<String, List<Long>> summaryByFile(List<String> transactions) {
        Map<String, List<Long>> summary = new HashMap<>();

        for (String transaction: transactions) {
            String[] splittedTransaction = transaction.split("\\D");
            if (!summary.containsKey(splittedTransaction[1])) {
                summary.put(splittedTransaction[1], new ArrayList<>());
                summary.get(splittedTransaction[1]).add(0L);
                summary.get(splittedTransaction[1]).add(0L);
            }
            if (splittedTransaction[0].equals("01"))
                summary.get(splittedTransaction[1]).set(0,
                        summary.get(splittedTransaction[1]).get(0) + (Long.parseLong(splittedTransaction[5]) - Long.parseLong(splittedTransaction[3])));
            else if (splittedTransaction[0].equals("02"))
                summary.get(splittedTransaction[1]).set(1,
                        summary.get(splittedTransaction[1]).get(1) + (Long.parseLong(splittedTransaction[5]) - Long.parseLong(splittedTransaction[3])));
        }

        return summary;
    }
    public Set<String> getPhoneNumbers(List<String> transactions) {
        Set<String> setOfPhones = new HashSet<>();
        for (String transaction: transactions) {
            String[] splittedTransaction = transaction.split("\\D");
            setOfPhones.add(splittedTransaction[1]);
        }

        return setOfPhones;
    }
}
