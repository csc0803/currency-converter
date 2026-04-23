package com.chun.currency.controller;

import com.chun.currency.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exchange-rates")
//@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @PostMapping("/fetch")
    public ResponseEntity<String> fetchRates(
            @RequestParam(defaultValue = "USD") String baseCurrency){

        exchangeRateService.fetchAndSaveRates(baseCurrency.toUpperCase());

        return ResponseEntity.ok("匯率更新成功");
    }

    @GetMapping("/currencies")
    public ResponseEntity<List<String>> getAvailableCurrencies(){
        return ResponseEntity.ok(exchangeRateService.getAvailableCurrencies());
    }

}
