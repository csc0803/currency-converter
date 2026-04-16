package com.chun.currency.repository;

import com.chun.currency.entity.ExchangeRateHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeRateHistoryRepository extends JpaRepository<ExchangeRateHistory, Long> {

    List<ExchangeRateHistory> findByBaseCurrencyAndTargetCurrencyOrderByFetchedAtDesc(
            String baseCurrency, String targetCurrency
    );
}
