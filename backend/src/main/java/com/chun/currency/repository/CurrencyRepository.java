package com.chun.currency.repository;

import com.chun.currency.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency,Long> {

    Optional<Currency> findByCode(String code);

    List<Currency> findByIsActiveTrueOrderByCodeAsc();
}
