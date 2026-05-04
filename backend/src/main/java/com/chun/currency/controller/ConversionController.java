package com.chun.currency.controller;

import com.chun.currency.dto.ConvertRequest;
import com.chun.currency.dto.ConvertResponse;
import com.chun.currency.entity.ConversionRecord;
import com.chun.currency.service.ConversionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/conversions")
@RequiredArgsConstructor
public class ConversionController {

    private final ConversionService conversionService;

    @PostMapping("/convert")
    public ResponseEntity<ConvertResponse> convert(@RequestBody ConvertRequest request){
        return ResponseEntity.ok(conversionService.convert(request));
    }

    @GetMapping("/convertAll")
    public ResponseEntity<List<ConvertResponse>> convert(
            @RequestParam String fromCurrency,
            @RequestParam BigDecimal amount
    ){
        return ResponseEntity.ok(conversionService.convertAll(fromCurrency, amount));
    }

    @GetMapping("/history")
    public ResponseEntity<List<ConversionRecord>> getHistory(){
        return ResponseEntity.ok(conversionService.getHistory());
    }
}
