package com.chun.currency.service;

import com.chun.currency.dto.ConvertRequest;
import com.chun.currency.dto.ConvertResponse;
import com.chun.currency.entity.ConversionRecord;
import com.chun.currency.entity.ExchangeRate;
import com.chun.currency.repository.ConversionRecordRepository;
import com.chun.currency.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversionService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final ConversionRecordRepository conversionRecordRepository;

    @Transactional
    public ConvertResponse convert(ConvertRequest request){
        // 1.查詢匯率
        ExchangeRate exchangeRate = exchangeRateRepository
                .findByBaseCurrencyAndTargetCurrency(
                        request.getFromCurrency().toUpperCase(),
                        request.getToCurrency().toUpperCase()
                )
                .orElseThrow(() -> new RuntimeException(
                        "查無匯率: " + request.getFromCurrency() + " -> " + request.getToCurrency()
                ));

        // 2.換算金額
        BigDecimal convertedAmount = request.getAmount()
                .multiply(exchangeRate.getRate())
                .setScale(4, RoundingMode.HALF_UP);

        // 3.儲存紀錄
        ConversionRecord record = new ConversionRecord();
        record.setFromCurrency(request.getFromCurrency());
        record.setToCurrency(request.getToCurrency());
        record.setAmount(request.getAmount());
        record.setConvertedAmount(convertedAmount);
        record.setExchangeRate(exchangeRate.getRate());
        conversionRecordRepository.save(record);

        // 4.組裝DTO回傳
        ConvertResponse response = new ConvertResponse();
        response.setFromCurrency(record.getFromCurrency());
        response.setToCurrency(record.getToCurrency());
        response.setAmount(record.getAmount());
        response.setConvertedAmount(record.getConvertedAmount());
        response.setExchangeRate(record.getExchangeRate());
        return response;
    }

    public List<ConversionRecord> getHistory(){
        return conversionRecordRepository.findTop20ByOrderByCreatedAtDesc();
    }
}
