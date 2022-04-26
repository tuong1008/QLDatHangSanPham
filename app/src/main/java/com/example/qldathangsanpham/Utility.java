package com.example.qldathangsanpham;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.jasypt.util.password.StrongPasswordEncryptor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static String showGia(Double gia) {
        return String.format("Giá: %,.0fđ", gia);
    }

//    public static List<Map<String, String>> testOrdersData() {
//        List<Map<String, String>> orders = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            Map<String, String> oder = new HashMap<>();
//            oder.put(DatabaseHelper.CL_ID, "890921231");
//            oder.put(DatabaseHelper.Cl_NGAY_DAT_HANG, "20/04/2022");
//            oder.put(DatabaseHelper.CL_HO_TEN, "Bùi Minh Tơ");
//            orders.add(oder);
//        }
//        return orders;

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