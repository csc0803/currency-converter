package com.chun.currency.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ConvertRequest {
    private String fromCurrency;
    private String toCurrency;
    private BigDecimal amount;
}
