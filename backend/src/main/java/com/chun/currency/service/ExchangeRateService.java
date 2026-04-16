package com.chun.currency.service;

import com.chun.currency.entity.ExchangeRate;
import com.chun.currency.entity.ExchangeRateHistory;
import com.chun.currency.repository.ExchangeRateHistoryRepository;
import com.chun.currency.repository.ExchangeRateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final ExchangeRateClient exchangeRateClient;
    private final ExchangeRateRepository exchangeRateRepository;
    private final ExchangeRateHistoryRepository exchangeRateHistoryRepository;

    @Transactional
    public void fetchAndSaveRates(String baseCurrency){

        // 1. 呼叫外部API
        Map<String, Number> rates = exchangeRateClient.fetchLatestRates(baseCurrency);
        LocalDateTime now = LocalDateTime.now();

        rates.forEach((targetCurrency, rate) -> {
            BigDecimal rateValue = new BigDecimal(rate.toString());

            // 2. 更新exchange_rates(有就更新，沒有就新增)
            ExchangeRate exchangeRate = exchangeRateRepository
                    .findByBaseCurrencyAndTargetCurrency(baseCurrency, targetCurrency)
                    .orElse(new ExchangeRate());

            exchangeRate.setBaseCurrency(baseCurrency);
            exchangeRate.setTargetCurrency(targetCurrency);
            exchangeRate.setRate(rateValue);
            exchangeRate.setUpdatedAt(now);
            exchangeRateRepository.save(exchangeRate);

            // 3. 新增或更新當天歷史紀錄
            LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
            LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

            ExchangeRateHistory history = exchangeRateHistoryRepository
                    .findByBaseCurrencyAndTargetCurrencyAndFetchedAtBetween(
                            baseCurrency, targetCurrency, startOfDay, endOfDay)
                    .orElse(new ExchangeRateHistory());

            history.setBaseCurrency(baseCurrency);
            history.setTargetCurrency(targetCurrency);
            history.setRate(rateValue);
            history.setFetchedAt(now);
            exchangeRateHistoryRepository.save(history);
        });
    }
}
