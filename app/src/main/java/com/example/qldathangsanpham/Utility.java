package com.example.qldathangsanpham;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.jasypt.util.password.StrongPasswordEncryptor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Utility {
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT = "HH:mm";

    public static String encryptPassword(String inputPassword) {
        StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
        return encryptor.encryptPassword(inputPassword);
    }

    public static boolean checkPassword(String inputPassword, String encryptedStoredPassword) {
        StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
        return encryptor.checkPassword(inputPassword, encryptedStoredPassword);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDateTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").format(new Date());
    }

    @SuppressLint("SimpleDateFormat")
    public static String getTimeFromDatetime(String datetime) throws ParseException {
        @SuppressLint("SimpleDateFormat") Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").parse(datetime);
        return new SimpleDateFormat("HH:mm aa").format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDateFromDatetime(String datetime) throws ParseException {
        @SuppressLint("SimpleDateFormat") Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").parse(datetime);
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

//    @SuppressLint("SimpleDateFormat")
//    public static Date getDate(String datetime) throws ParseException {
//        @SuppressLint("SimpleDateFormat") Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").parse(datetime);
//        return date;
//    }
//
//    public static boolean isDateFallInCurrentMonth(String input) throws ParseException {
//        ZoneId timeZone = ZoneOffset.UTC; // Use whichever time zone makes sense for your use case
//        @SuppressLint("SimpleDateFormat") Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").parse(input);
//        LocalDateTime givenLocalDateTime = LocalDateTime.ofInstant(date.toInstant(), timeZone);
//        YearMonth currentMonth = YearMonth.now(timeZone);
//        return currentMonth.equals(YearMonth.from(givenLocalDateTime));
//    }


}
