package com.chun.currency.repository;

import com.chun.currency.entity.ExchangeRate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.AutoConfigureDataJpa;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ExchangeRateRepositoryTest {

    @Autowired
    private ExchangeRateRepository repository;

    @Test
    void givenExchangeRate_whenSaveAndFind_thenShouldReturnPresent(){
        ExchangeRate rate = new ExchangeRate();
        rate.setBaseCurrency("USD");
        rate.setTargetCurrency("TWD");
        rate.setRate(new BigDecimal("31.755100"));
        rate.setUpdatedAt(LocalDateTime.now());

        repository.save(rate);

        Optional<ExchangeRate> result = repository.findByBaseCurrencyAndTargetCurrency("USD", "TWD");

        assertThat(result).isPresent();
        assertThat(result.get().getBaseCurrency()).isEqualTo("USD");
        assertThat(result.get().getTargetCurrency()).isEqualTo("TWD");
        assertThat(result.get().getRate()).isEqualTo("31.755100");
    }

    @Test
    void givenNonExistentCurrencyPair_whenFind_thenShouldReturnEmpty(){
        Optional<ExchangeRate> result = repository.findByBaseCurrencyAndTargetCurrency("USD","XYZ");

        assertThat(result).isEmpty();
    }

}
