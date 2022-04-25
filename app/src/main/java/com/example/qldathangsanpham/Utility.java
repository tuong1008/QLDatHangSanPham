package com.example.qldathangsanpham;

import android.annotation.SuppressLint;

import org.jasypt.util.password.StrongPasswordEncryptor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
//    }
//
//    public static List<Map<String, String>> testProductInOrdersData() {
//        List<Map<String, String>> productInOrders = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            Map<String, String> map = new HashMap<>();
//            map.put(DatabaseHelper.CL_TEN_SAN_PHAM, "TV Samsung");
//            map.put(DatabaseHelper.CL_DON_GIA, "100");
//            map.put(DatabaseHelper.CL_SO_LUONG, "1000000");
//            productInOrders.add(map);
//        }
//        return productInOrders;
//    }


}
