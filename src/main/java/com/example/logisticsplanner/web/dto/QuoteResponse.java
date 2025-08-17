package com.example.logisticsplanner.web.dto;

import java.math.BigDecimal;
import java.util.List;

public record QuoteResponse(
        BigDecimal totalCostUsd,
        BigDecimal transportCostUsd,
        BigDecimal customsCostUsd,
        BigDecimal ecoFeesUsd,
        double totalHours,
        List<SegmentView> segments
){
    public record SegmentView(
            long segmentId,
            String mode,
            String originCode,
            String destinationCode,
            double distanceKm,
            double hours,
            String geoJson
    ) {}
}