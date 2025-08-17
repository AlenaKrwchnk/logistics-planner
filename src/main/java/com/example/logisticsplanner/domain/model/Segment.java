package com.example.logisticsplanner.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Segment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) private Location origin;
    @ManyToOne(optional = false) private Location destination;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    private BigDecimal distanceKm;
    private BigDecimal baseCostPerKmUsd; // базовый тариф
    private BigDecimal speedKmh;         // средняя скорость

    private BigDecimal co2KgPerKm;       // эмиссия

    @ManyToOne private CustomsCheckpoint customs; // опционально

    @Lob
    private String geoJson; // линия для Leaflet (упрощенно)
}
