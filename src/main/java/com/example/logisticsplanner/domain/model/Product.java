package com.example.logisticsplanner.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sku;
    private String name;
    @ManyToOne(optional = false) private ProductCategory category;
    private BigDecimal unitWeightKg;
    private BigDecimal unitValueUsd;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<VehicleType> allowedVehicleTypes;
}