package com.chun.currency.service;

import com.chun.currency.dto.ConvertRequest;
import com.chun.currency.dto.ConvertResponse;
import com.chun.currency.entity.ConversionRecord;
import com.chun.currency.entity.Currency;
import com.chun.currency.entity.ExchangeRate;
import com.chun.currency.repository.ConversionRecordRepository;
import com.chun.currency.repository.CurrencyRepository;
import com.chun.currency.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConversionService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final ConversionRecordRepository conversionRecordRepository;
    private final ExchangeRateService exchangeRateService;
    private final CurrencyRepository currencyRepository;

    @Transactional
    public ConvertResponse convert(ConvertRequest request){

        ConvertResponse response = calculate(request);

        // 儲存紀錄
        ConversionRecord record = new ConversionRecord();
        record.setFromCurrency(request.getFromCurrency());
        record.setToCurrency(request.getToCurrency());
        record.setAmount(request.getAmount());
        record.setConvertedAmount(response.getConvertedAmount());
        record.setExchangeRate(response.getExchangeRate());
        conversionRecordRepository.save(record);

        return response;
    }

    public List<ConvertResponse> convertAll(String fromCurrency, BigDecimal amount){
        String from = fromCurrency.toUpperCase();

        List<String> currencies = currencyRepository.findByIsActiveTrueOrderByCodeAsc()
                .stream()
                .map(Currency::getCode)
                .toList();

        List<ConvertResponse> result = new ArrayList<>();

        for(String to : currencies){
            if(to.equals(from)) continue;
            try{
                ConvertRequest request = new ConvertRequest();
                request.setFromCurrency(from);
                request.setToCurrency(to);
                request.setAmount(amount);

                result.add(calculate(request));
            } catch (RuntimeException e){

            }
        }
        return result;
    }

    private ConvertResponse calculate(ConvertRequest request){
        String from = request.getFromCurrency().toUpperCase();
        String to = request.getToCurrency().toUpperCase();

        // 1.查詢匯率
        BigDecimal rate = calculateRate(from, to);

        // 2.換算金額
        BigDecimal convertedAmount = request.getAmount()
                .multiply(rate)
                .setScale(4, RoundingMode.HALF_UP);

        // 3.組裝DTO回傳
        ConvertResponse response = new ConvertResponse();
        response.setFromCurrency(from);
        response.setToCurrency(to);
        response.setAmount(request.getAmount());
        response.setConvertedAmount(convertedAmount);
        response.setExchangeRate(rate);

        return response;
    }

    private BigDecimal calculateRate(String from, String to){

        // 如果是USD，直接查詢
        if(from.equals("USD")){
            return exchangeRateRepository
                    .findByBaseCurrencyAndTargetCurrency("USD", to)
                    .orElseThrow(() -> new RuntimeException("查無匯率: " + "USD" + " -> " + to))
                    .getRate();
        }

        // 用 USD 當中間人：rate = (USD→to) / (USD→from)
        BigDecimal fromRate = exchangeRateRepository
                .findByBaseCurrencyAndTargetCurrency("USD", from)
                .orElseThrow(() -> new RuntimeException("查無匯率: " + from))
                .getRate();

        BigDecimal toRate = exchangeRateRepository
                .findByBaseCurrencyAndTargetCurrency("USD", to)
                .orElseThrow(() -> new RuntimeException("查無匯率: " + to))
                .getRate();

        return toRate.divide(fromRate, 6, RoundingMode.HALF_UP);
    }

    public List<ConversionRecord> getHistory(){
        return conversionRecordRepository.findTop20ByOrderByCreatedAtDesc();
    }


}
