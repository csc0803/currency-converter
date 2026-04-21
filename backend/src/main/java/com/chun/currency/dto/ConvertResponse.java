package com.chun.currency.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ConvertResponse {
    private String fromCurrency;
    private String toCurrency;
    private BigDecimal amount;
    private BigDecimal convertedAmount;
    private BigDecimal exchangeRate;
}
