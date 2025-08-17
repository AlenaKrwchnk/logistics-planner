package com.example.logisticsplanner.web.rest;

import com.example.logisticsplanner.service.calc.QuoteService;
import com.example.logisticsplanner.web.dto.QuoteResponse;
import com.example.logisticsplanner.web.dto.ShipmentDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quotes")
@RequiredArgsConstructor
public class QuoteController {
    private final QuoteService quotes;

    @PostMapping("/price")
    public QuoteResponse price(@Valid @RequestBody ShipmentDto request) {
        return quotes.quote(request);
    }
}