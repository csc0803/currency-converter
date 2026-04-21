package com.chun.currency.repository;

import com.chun.currency.entity.ConversionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversionRecordRepository extends JpaRepository<ConversionRecord, Long > {

    List<ConversionRecord> findTop20ByOrderByCreatedAtDesc();
}
