package app.udrServices;

import org.springframework.stereotype.Service;

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
