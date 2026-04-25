package com.chun.currency.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table (name = "currency")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code", nullable = false, columnDefinition = "CHAR(3)")
    private String code;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void updateTimestamp()  {
        this.updatedAt = LocalDateTime.now();
    }
}
