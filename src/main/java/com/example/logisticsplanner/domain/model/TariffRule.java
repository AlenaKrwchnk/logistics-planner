package com.example.logisticsplanner.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TariffRule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false) private ProductCategory category;
    private String originCountry;
    private String destCountry;
    private BigDecimal ratePercent; // напр. 5% от стоимости
    private BigDecimal minUsd;      // минимальная сумма
    private BigDecimal maxUsd;      // максимальная (опц.)
}
