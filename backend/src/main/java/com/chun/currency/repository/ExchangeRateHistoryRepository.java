package com.chun.currency.repository;

import com.chun.currency.entity.ExchangeRate;
import com.chun.currency.entity.ExchangeRateHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExchangeRateHistoryRepository extends JpaRepository<ExchangeRateHistory, Long> {

    Optional<ExchangeRate> findByBaseCurrencyAndTargetCurrency(
            String baseCurrency, String targetCurrency
    );
}
