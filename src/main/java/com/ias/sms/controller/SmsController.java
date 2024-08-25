package com.ias.sms.controller;

import com.ias.sms.dto.MultipleSmsPayloadDto;
import com.ias.sms.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SmsController {

    private final SmsService smsService;

    @Autowired
    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/send-sms")
    public String sendSms(@RequestParam String message, @RequestParam String phone) {

        smsService.triggerSms(message, phone);
        return "SMS sent successfully!";
    }


    @PostMapping("/send-sms/mutiple")
    public String sendSms(@RequestBody MultipleSmsPayloadDto payloadDto) {

return  smsService.triggerManySms(payloadDto);
    }
}

