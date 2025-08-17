package com.example.logisticsplanner.web.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public record ShipmentDto(
        @NotNull Long originId,
        @NotNull Long destinationId,
        @NotNull Objective objective, // COST | TIME | BALANCED
        @NotEmpty List<Item> items
) {
    public enum Objective { COST, TIME, BALANCED; }
    public record Item(@NotNull Long productId,
                       @Positive int quantity) {}
}
