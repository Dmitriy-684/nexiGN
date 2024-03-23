package app.udrServices;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;

class TimeConverterTest {

    @Test
    void convertFromUnixTimeTest() {
        TimeConverter timeConverter = new TimeConverter();
        Assertions.assertEquals(timeConverter.convertFromUnixTime(2100L), "00:35:00");
    }
}