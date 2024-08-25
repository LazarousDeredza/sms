package com.ias.sms.dto;

import lombok.Data;

import java.util.List;

@Data
public class MultipleSmsPayloadDto {
    private String message;
    private List<String> numbers;
}
