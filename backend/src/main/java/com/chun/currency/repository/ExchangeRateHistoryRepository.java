package com.chun.currency.repository;

import com.chun.currency.entity.ExchangeRateHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ExchangeRateHistoryRepository extends JpaRepository<ExchangeRateHistory, Long> {

    List<ExchangeRateHistory> findByBaseCurrencyAndTargetCurrencyOrderByFetchedAtDesc(
            String baseCurrency, String targetCurrency
    );

    Optional<ExchangeRateHistory> findByBaseCurrencyAndTargetCurrencyAndFetchedAtBetween(
            String baseCurrency,
            String targetCurrency,
            LocalDateTime start,
            LocalDateTime end
    );
}
