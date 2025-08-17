package com.example.logisticsplanner.service.calc;

import com.example.logisticsplanner.domain.model.*;
import com.example.logisticsplanner.domain.repo.EcoFeeRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class EcoFeeCalculator {
    private final EcoFeeRuleRepository rules;

    public BigDecimal ecoFeeFor(VehicleType type, double co2Kg) {
        return rules.findByVehicleType(type)
                .map(r -> r.getPricePerKgCO2Usd().multiply(BigDecimal.valueOf(co2Kg)))
                .orElse(BigDecimal.ZERO);
    }
}