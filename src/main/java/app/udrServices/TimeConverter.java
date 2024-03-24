package app.udrServices;

import org.springframework.stereotype.Service;

/**
 * A class that converts Unix-time format time to H format time:M:S
 * The class contains the convertFromUnixTime(Long time) method, which converts time to time in the H:M:S format
 */

@Service
public class TimeConverter {
    public String convertFromUnixTime(Long time) {
        StringBuilder builder = new StringBuilder();
        long hours = time / 3600, minutes = (time - (hours*3600)) / 60, seconds = time - (hours * 3600) - (minutes * 60);
        if (hours < 10) builder.append(0);
        builder.append(hours).append(':');
        if (minutes < 10) builder.append(0);
        builder.append(minutes).append(':');
        if (seconds < 10) builder.append(0);
        builder.append(seconds);
        return builder.toString();
    }
}
