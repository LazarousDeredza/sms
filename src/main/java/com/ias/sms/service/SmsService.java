package com.ias.sms.service;

import com.ias.sms.dto.MultipleSmsPayloadDto;
import com.ias.sms.service.impl.EconetSmsNotificationJob;
import com.ias.sms.service.impl.NetOneSmsNotificationJob;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ias.sms.service.impl.PhoneNumberFormatter.formatPhoneNumber;

@Service
@RequiredArgsConstructor
public class SmsService {

    private final EconetSmsNotificationJob econetSmsNotificationJob;
    private final NetOneSmsNotificationJob netOneSmsNotificationJob;

    public void triggerSms(String message, String phoneNumber) {
        phoneNumber=formatPhoneNumber(phoneNumber);
        if (isNetOneNumber(phoneNumber)) {
            // Send SMS via NetOne
            netOneSmsNotificationJob.sendNetOneSms(message, phoneNumber);
        } else if (isEconetNumber(phoneNumber)) {
            // Send SMS via Econet
            econetSmsNotificationJob.sendEconetSms(message, phoneNumber);
        } else {
            // Handle cases where the number is neither NetOne nor Econet
            System.out.println("Unsupported number provider: " + phoneNumber);
        }
    }

    private boolean isNetOneNumber(String phoneNumber) {
        // Implement logic to determine if the number is a NetOne number
        // Example: return phoneNumber.startsWith("071") || phoneNumber.startsWith("073");
        return phoneNumber.startsWith("071") || phoneNumber.startsWith("26371") || phoneNumber.startsWith("+26371"); // Example for NetOne
    }

    private boolean isEconetNumber(String phoneNumber) {
        // Implement logic to determine if the number is an Econet number
        // Example: return phoneNumber.startsWith("077") || phoneNumber.startsWith("078");
        return phoneNumber.startsWith("077") || phoneNumber.startsWith("26377")|| phoneNumber.startsWith("+26377")
                || phoneNumber.startsWith("078")|| phoneNumber.startsWith("26378")|| phoneNumber.startsWith("+26378"); // Example for Econet
    }

    public String triggerManySms(MultipleSmsPayloadDto payloadDto) {
        String message = payloadDto.getMessage();
        List<String> phoneNumbers = payloadDto.getNumbers();

        for (String phoneNumber : phoneNumbers) {
            String formattedPhoneNumber = formatPhoneNumber(phoneNumber);
            System.out.println("Sending to :"+formattedPhoneNumber);
            if (isNetOneNumber(formattedPhoneNumber)) {
                // Send SMS via NetOne
                netOneSmsNotificationJob.sendNetOneSms(message, formattedPhoneNumber);
            } else if (isEconetNumber(formattedPhoneNumber)) {
                // Send SMS via Econet
                econetSmsNotificationJob.sendEconetSms(message, formattedPhoneNumber);
            } else {
                // Handle cases where the number is neither NetOne nor Econet
                System.out.println("Unsupported number provider: " + formattedPhoneNumber);
            }
        }
        return  "Finished sending multiple sms";
    }
}

