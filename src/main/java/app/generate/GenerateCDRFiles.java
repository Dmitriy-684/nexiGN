package app.generate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GenerateCDRFiles {
    @Value("${telephones.count}")
    public int countOfPhoneNumbers;
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
}
