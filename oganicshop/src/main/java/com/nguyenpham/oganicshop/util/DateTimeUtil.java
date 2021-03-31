package com.nguyenpham.oganicshop.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    public static String dateTimeFormat(Timestamp time) {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(time);
    }
}
