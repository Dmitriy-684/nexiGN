package app.udrServices;
import app.udrServices.ParserClass;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserClassTest {

    @Test
    void getPhoneNumbers_ReturnsUniquePhoneNumbers() {
        // Arrange
        ParserClass parserClass = new ParserClass();
        List<String> transactions = new ArrayList<>();
        transactions.add("01,123456789,123,456,123456,789");
        transactions.add("02,987654321,456,789,987654,321");
        transactions.add("01,123456789,123,456,123456,789");

        // Act
        Set<String> phoneNumbers = parserClass.getPhoneNumbers(transactions);

        // Assert
        Set<String> expectedPhoneNumbers = new HashSet<>();
        expectedPhoneNumbers.add("123456789");
        expectedPhoneNumbers.add("987654321");
        assertEquals(expectedPhoneNumbers, phoneNumbers);
    }
}