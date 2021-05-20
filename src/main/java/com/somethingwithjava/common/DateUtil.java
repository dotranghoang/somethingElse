package com.somethingwithjava.common;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtil {
    private final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    public String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        return DATE_TIME_FORMAT.format(now);
    }
}
