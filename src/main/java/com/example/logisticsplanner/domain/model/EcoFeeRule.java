package com.example.logisticsplanner.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class EcoFeeRule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
    private BigDecimal pricePerKgCO2Usd; // цена за кг CO2
}
