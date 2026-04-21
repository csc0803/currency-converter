package com.chun.currency.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(
        name="conversion_records"
)
public class ConversionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_currency", nullable = false, columnDefinition = "CHAR(3)")
    private String fromCurrency;

    @Column(name = "to_currency", nullable = false, columnDefinition = "CHAR(3)")
    private String toCurrency;

    @Column(name = "amount", nullable = false, precision = 15, scale = 4)
    private BigDecimal amount;

    @Column(name = "converted_amount", nullable = false, precision = 15, scale = 4)
    private BigDecimal convertedAmount;

    @Column(name = "exchange_rate", nullable = false, precision = 18, scale = 6)
    private BigDecimal exchangeRate;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
