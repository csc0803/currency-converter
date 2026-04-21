package com.chun.currency.scheduler;

import com.chun.currency.entity.ExchangeRate;
import com.chun.currency.repository.ExchangeRateRepository;
import com.chun.currency.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExchangeRateScheduler {

    private final ExchangeRateService exchangeRateService;
    private final ExchangeRateRepository exchangeRateRepository;

    @Scheduled(cron = "0 * * * * *")
    public void autoFetchRates(){
        Optional<ExchangeRate> latestUpdatedAt =
                exchangeRateRepository.findTopByOrderByUpdatedAtDesc();

        boolean isOutdated = exchangeRateRepository.findTopByOrderByUpdatedAtDesc()
                .map(rate -> rate.getUpdatedAt().toLocalDate().isBefore(LocalDate.now()))
                .orElse(true);

        if(isOutdated){
            log.info("匯率資料已經過期，開始更新...");
            exchangeRateService.fetchAndSaveRates("USD");
            log.info("匯率更新完成");
        } else {
            log.info("匯率資料已經是最新，略過更新");
        }
    }
}
