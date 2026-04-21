package com.chun.currency.repository;

import com.chun.currency.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    Optional<ExchangeRate> findByBaseCurrencyAndTargetCurrency(
            String baseCurrency, String targetCurrency
    );

    Optional<ExchangeRate> findTopByOrderByUpdatedAtDesc();


}
