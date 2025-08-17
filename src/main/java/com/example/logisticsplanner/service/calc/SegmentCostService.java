package com.example.logisticsplanner.service.calc;

import com.example.logisticsplanner.domain.model.Segment;
import com.example.logisticsplanner.domain.model.VehicleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class SegmentCostService {
    private final EcoFeeCalculator eco;

    public BigDecimal transportCost(Segment s, double payloadTons) {
        // простая модель: baseCostPerKm * distance * k (от массы)
        double massFactor = Math.max(0.5, Math.min(2.0, payloadTons / 10.0)); // [0.5..2.0]
        BigDecimal base = s.getBaseCostPerKmUsd()
                .multiply(s.getDistanceKm())
                .multiply(BigDecimal.valueOf(massFactor));
        return base.setScale(2, RoundingMode.HALF_UP);
    }

    public double hours(Segment s) {
        return s.getDistanceKm().doubleValue() / s.getSpeedKmh().doubleValue();
    }

    public BigDecimal ecoFee(Segment s, double payloadTons) {
        // экология: co2(кг/км) * distance * (1 + payloadFactor) * price
        double payloadFactor = Math.max(0, payloadTons / 20.0);
        double co2 = s.getCo2KgPerKm().doubleValue() * s.getDistanceKm().doubleValue() * (1.0 + payloadFactor);
        return eco.ecoFeeFor(s.getVehicleType(), co2);
    }
}
