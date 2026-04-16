package com.chun.currency.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(
        name = "exchange_rate_histories"
)
public class ExchangeRateHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "base_currency", nullable = false, columnDefinition = "CHAR(3)")
    private String baseCurrency;

    @Column(name = "target_currency", nullable = false, columnDefinition = "CHAR(3)")
    private String targetCurrency;

    @Column(nullable = false, precision = 18, scale = 6)
    private BigDecimal rate;

    @Column(name = "fetched_at", nullable = false)
    private LocalDateTime fetchedAt;

    @PrePersist
    public void prePersist() {
        this.fetchedAt = LocalDateTime.now();
    }
}
