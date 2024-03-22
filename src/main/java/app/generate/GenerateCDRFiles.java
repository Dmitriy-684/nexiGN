package app.generate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.*;

import java.util.*;

@Service
public class GenerateCDRFiles {
    @Value("${generateCDRFiles.telephones.count}")
    public int countOfPhoneNumbers;

    @Value("${generateCDRFiles.currentYear}")
    public int year;
    @Value("${generateCDRFiles.callMinTime}")
    public int differentMin;
    @Value("${generateCDRFiles.callMaxTime}")
    public int differentMax;


    public Set<String> generateTelephoneNumbers() {
        Random random = new Random();
        Set<String> listOfNumbers = new HashSet<>();
        while (listOfNumbers.size() != countOfPhoneNumbers) {
            StringBuilder builder = new StringBuilder("7");
            builder.append(random.nextInt(920, 937));
            builder.append(random.nextInt(1000000, 10000000));
            listOfNumbers.add(builder.toString());
        }
        return listOfNumbers;
    }

public List<Long> generatorTime(Month month){
        List<Long> listTime = new ArrayList<>();
        Random random = new Random();

    int daysInMonth = month.length(Year.isLeap(year));

    Instant dateStart = LocalDate.of(year, month, 1).atStartOfDay().atZone(ZoneId.of("UTC")).toInstant();
    Instant dateEnd = LocalDate.of(year, month, daysInMonth).atStartOfDay().atZone(ZoneId.of("UTC")).toInstant();

        int different = random.nextInt(differentMin, differentMax);

        for(long i = dateStart.getEpochSecond(); i < dateEnd.getEpochSecond(); i += different){
            listTime.add(i);
        }
        if (listTime.size() % 2 != 0){
            listTime.remove(listTime.size() - 1);
        }
        return listTime.stream().sorted().toList();
    }
}
