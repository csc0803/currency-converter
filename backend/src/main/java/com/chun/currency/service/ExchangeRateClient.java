package com.chun.currency.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class ExchangeRateClient {

    @Value("${exchangerate.api.key}")
    private String apiKey;

    @Value("${exchangerate.api.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @SuppressWarnings("unchecked")
    public Map<String, Double> fetchLatestRates(String baseCurrency){
        String url = String.format("%s/%s/latest/%s", baseUrl, apiKey, baseCurrency);

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if(response == null || !"success".equals(response.get("result"))){
            throw new RuntimeException("無法取得匯率資料，請確認 API Key 是否正確");
        }

        return (Map<String, Double>) response.get("conversion_rates");
    }

}
