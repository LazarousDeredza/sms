package com.ias.sms.service.impl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class EconetSmsNotificationJob {

    // The full URL including the API key
    private final String econetSmsGatewayUrl = "https://mobilemessaging.econet.co.zw/client/api/sendmessage?apikey=a93bbe99c497fe15&mobiles=";

    private final RestTemplate restTemplate;

    public EconetSmsNotificationJob(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Scheduled(cron = "0 0 * * * ?")  // Adjust the cron expression as needed
    public void sendEconetSms(String message, String phone) {
        System.out.println("econet gateway trying to send sms");

        String formattedPhoneNumber = phoneNumberFormatter(phone);
        String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);

        String url = UriComponentsBuilder.fromHttpUrl(econetSmsGatewayUrl + formattedPhoneNumber)
                .queryParam("sms", encodedMessage)
                .queryParam("senderid", "Tumai")
                .toUriString();

        String response = restTemplate.getForObject(url, String.class);

        System.out.println("RESPONSE: " + response);
    }

    private String phoneNumberFormatter(String phone) {
        // Implement phone number formatting logic here
        return phone; // Example placeholder
    }



}
