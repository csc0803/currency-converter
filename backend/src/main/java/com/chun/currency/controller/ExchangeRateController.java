package com.chun.currency.controller;

import com.chun.currency.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exchange-rates")
@RequiredArgsConstructor
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @PostMapping("/fetch")
    public ResponseEntity<String> fetchRates(
            @RequestParam(defaultValue = "USD") String baseCurrency){

        exchangeRateService.fetchAndSaveRates(baseCurrency.toUpperCase());

        return ResponseEntity.ok("匯率更新成功");
    }

}
