package app.udrServices;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TimeConverterTest {

    @Test
    void convertFromUnixTimeTest() {
        TimeConverter timeConverter = new TimeConverter();
        Assertions.assertEquals(timeConverter.convertFromUnixTime(2100L), "00:35:00");
    }
}