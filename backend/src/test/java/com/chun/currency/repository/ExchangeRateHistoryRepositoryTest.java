package com.chun.currency.repository;

import com.chun.currency.entity.ExchangeRateHistory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ExchangeRateHistoryRepositoryTest {

    @Autowired
    private ExchangeRateHistoryRepository repository;

    @Test
    void givenMultipleHistories_whenFindByPair_thenShouldReturnOrderByFetchedAtDesc(){
        ExchangeRateHistory history1 = new ExchangeRateHistory();
        history1.setBaseCurrency("USD");
        history1.setTargetCurrency("TWD");
        history1.setRate(new BigDecimal("31.500000"));
        history1.setFetchedAt(LocalDateTime.now().minusDays(2));

        ExchangeRateHistory history2 = new ExchangeRateHistory();
        history2.setBaseCurrency("USD");
        history2.setTargetCurrency("TWD");
        history2.setRate(new BigDecimal("31.700000"));
        history2.setFetchedAt(LocalDateTime.now().minusDays(1));

        ExchangeRateHistory history3 = new ExchangeRateHistory();
        history3.setBaseCurrency("USD");
        history3.setTargetCurrency("TWD");
        history3.setRate(new BigDecimal("31.900000"));
        history3.setFetchedAt(LocalDateTime.now());

        repository.save(history1);
        repository.save(history2);
        repository.save(history3);

        List<ExchangeRateHistory> result = repository.findByBaseCurrencyAndTargetCurrencyOrderByFetchedAtDesc("USD", "TWD");

        assertThat(result).hasSize(3);
        assertThat(result.get(0).getRate()).isEqualByComparingTo("31.900000");
        assertThat(result.get(1).getRate()).isEqualByComparingTo("31.700000");
        assertThat(result.get(2).getRate()).isEqualByComparingTo("31.500000");
    }

    @Test
    void givenNonExistentCurrencyPair_whenFind_thenShouldReturnEmptyList(){

        List<ExchangeRateHistory> result = repository.findByBaseCurrencyAndTargetCurrencyOrderByFetchedAtDesc("USD", "XYZ");

        assertThat(result).isEmpty();
    }
}
