package com.ias.sms.service.impl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class NetOneSmsNotificationJob {

    @Value("${sms_gateway_netone_url}")
    private String smsGatewayNetoneUrl;

    @Value("${sms_gateway_netone_access_id}")
    private String smsGatewayNetoneAccessId;

    @Value("${sms_gateway_netone_access_key}")
    private String smsGatewayNetoneAccessKey;

    private final RestTemplate restTemplate;

    public NetOneSmsNotificationJob(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendNetOneSms(String message, String phoneNumber) {
        System.out.println("netone gateway trying to send sms");

        String formattedPhoneNumber = PhoneNumberFormatter.formatPhoneNumber(phoneNumber);

        Map<String, String> payload = new HashMap<>();
        payload.put("message", message);
        payload.put("receiver", formattedPhoneNumber);

        HttpHeaders headers = new HttpHeaders();
        headers.set("access-id", smsGatewayNetoneAccessId);
        headers.set("access-key", smsGatewayNetoneAccessKey);
        headers.set("Content-Type", "application/json"); // Set to application/json

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    smsGatewayNetoneUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("RESPONSE: " + response.getBody());
            } else {
                System.out.println("Failed to send SMS. Status code: " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
