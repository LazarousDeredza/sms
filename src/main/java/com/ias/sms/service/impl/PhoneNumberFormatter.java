package com.ias.sms.service.impl;

public class PhoneNumberFormatter {

    public static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return phoneNumber;
        }

        phoneNumber = phoneNumber.trim(); // Remove leading and trailing spaces

        if (phoneNumber.startsWith("0")) {
            phoneNumber = "263" + phoneNumber.substring(1);
        } else if (phoneNumber.startsWith("+")) {
            phoneNumber = phoneNumber.substring(1);
        } else if (phoneNumber.startsWith("7")) {
            phoneNumber = "263" + phoneNumber;
        } else if (phoneNumber.startsWith("2")) {
            // No change needed
        }

        return phoneNumber.replace(" ", ""); // Remove any remaining spaces
    }

//    public static void main(String[] args) {
//        String[] testNumbers = {"0712345678", "+263712345678", "263 712 345 678", "712345678", "071 234 5678"};
//        for (String number : testNumbers) {
//            System.out.println("Formatted number: " + formatPhoneNumber(number));
//        }
//    }
}

