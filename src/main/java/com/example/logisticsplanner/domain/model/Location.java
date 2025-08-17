package com.example.logisticsplanner.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Location {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;   // порт/станция/город
    private String name;
    private double lat;
    private double lon;
    private String country; // ISO code
}