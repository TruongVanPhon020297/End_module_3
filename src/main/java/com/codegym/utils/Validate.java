package com.codegym.utils;

import java.util.regex.Pattern;

public class Validate {
    public static final String NUMBER_REGEX = "\\d+";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}$";
    public static final String PHONE_REGEX = "^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$";
    public static final String DATE_REGEX = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";

    public static boolean isNumberValid(String number) {
        return Pattern.compile(NUMBER_REGEX).matcher(number).matches();
    }
    public static boolean isEmail(String email){
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }
    public static boolean isPhone(String phone){
        return Pattern.compile(PHONE_REGEX).matcher(phone).matches();
    }
    public static boolean isDateValid(String strDate) {
        return Pattern.compile(DATE_REGEX).matcher(strDate).matches();
    }
}
