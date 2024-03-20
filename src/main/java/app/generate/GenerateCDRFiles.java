package app.generate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class GenerateCDRFiles {
    @Value("${telephones.count}")
    public int countOfPhoneNumbers;
    public List<String> generateTelephoneNumbers() {
        Random random = new Random();
        List<String> listOfNumbers = new ArrayList<>(countOfPhoneNumbers);
        for (int i = 0; i < countOfPhoneNumbers; i++) {
            StringBuilder builder = new StringBuilder("7");
            builder.append(random.nextInt(920, 937));
            builder.append(random.nextInt(1000000, 10000000));
            listOfNumbers.add(builder.toString());
        }
        return listOfNumbers;
    }
}
