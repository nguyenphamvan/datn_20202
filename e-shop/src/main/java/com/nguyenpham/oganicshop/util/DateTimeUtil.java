package com.nguyenpham.oganicshop.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtil {

    public static String dateTimeFormat(Date date) {
        return new SimpleDateFormat("HH:mm dd/MM/yyyy").format(date);
    }

    public static String dateFormat(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }
}
