package com.example.logisticsplanner.web.dto;

import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

public class ShipmentForm {
    @NotNull
    private Long originId;
    @NotNull
    private Long destinationId;
    @NotNull
    private ShipmentDto.Objective objective = ShipmentDto.Objective.BALANCED;

    @NotEmpty
    private List<ItemForm> items = new ArrayList<>();

    public static class ItemForm {
        @NotNull
        private Long productId;
        @Positive
        private int quantity;

        // геттеры и сеттеры
        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }

    // геттеры и сеттеры
    public Long getOriginId() { return originId; }
    public void setOriginId(Long originId) { this.originId = originId; }

    public Long getDestinationId() { return destinationId; }
    public void setDestinationId(Long destinationId) { this.destinationId = destinationId; }

    public ShipmentDto.Objective getObjective() { return objective; }
    public void setObjective(ShipmentDto.Objective objective) { this.objective = objective; }

    public List<ItemForm> getItems() { return items; }
    public void setItems(List<ItemForm> items) { this.items = items; }
}