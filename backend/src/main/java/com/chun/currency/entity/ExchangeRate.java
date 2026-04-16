package com.chun.currency.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(
        name="exchange_rates",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UQ_exchange_rates_pair",
                        columnNames = {"base_currency", "target_currency"}
                )
        }
)
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "base_currency", nullable = false, columnDefinition = "CHAR(3)")
    private String baseCurrency;

    @Column(name = "target_currency", nullable = false, columnDefinition = "CHAR(3)")
    private String targetCurrency;

    @Column(nullable = false, precision = 18, scale = 6)
    private BigDecimal rate;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
