package com.chun.currency.service;

import com.chun.currency.entity.Currency;
import com.chun.currency.entity.ExchangeRate;
import com.chun.currency.entity.ExchangeRateHistory;
import com.chun.currency.repository.CurrencyRepository;
import com.chun.currency.repository.ExchangeRateHistoryRepository;
import com.chun.currency.repository.ExchangeRateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final ExchangeRateClient exchangeRateClient;
    private final ExchangeRateRepository exchangeRateRepository;
    private final ExchangeRateHistoryRepository exchangeRateHistoryRepository;
    private final CurrencyRepository currencyRepository;



    @Transactional
    public void fetchAndSaveRates(String baseCurrency){

        // 1. 呼叫外部API
        Map<String, Number> rates = exchangeRateClient.fetchLatestRates(baseCurrency);
        LocalDateTime now = LocalDateTime.now();

        // 1.1 更新幣別
        updateCurrencies(rates);

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

    public List<String> getAvailableCurrencies() {
        List<Currency> currencies = currencyRepository.findByIsActiveTrueOrderByCodeAsc();
        return currencies.stream()
                .map(Currency::getCode)
                .toList();
    }

    public void updateCurrencies(Map<String, Number> rates) {

        // 1. 取得最新幣別
        List<String> newCurrencies = new ArrayList<>(rates.keySet());

        // 2. 從DB取得現有幣別
        List<Currency> oldCurrencies = currencyRepository.findAll();
        List<String> oldCode = getAvailableCurrencies();

        // 3. 新幣別 → INSERT，is_active = true
        for(String currency : newCurrencies){
            if(!oldCode.contains(currency)){
                Currency newCurrency = new Currency();
                newCurrency.setCode(currency);
                newCurrency.setIsActive(true);
                currencyRepository.save(newCurrency);
            }
        }

        // 4. 舊幣別但 API 已不存在 → is_active = false
        for(Currency old : oldCurrencies){
            if(!newCurrencies.contains(old.getCode())){
                old.setIsActive(false);
                currencyRepository.save(old);
            }
        }

        // 5. 確保現有幣別 is_active = true
        for(Currency old : oldCurrencies){
            if(newCurrencies.contains(old.getCode()) &&  !old.getIsActive()){
                old.setIsActive(true);
                currencyRepository.save(old);
            }
        }
    }
}
