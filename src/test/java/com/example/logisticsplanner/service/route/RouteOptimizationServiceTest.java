package com.example.logisticsplanner.service.route;

import com.example.logisticsplanner.domain.model.*;
import com.example.logisticsplanner.domain.repo.LocationRepository;
import com.example.logisticsplanner.domain.repo.SegmentRepository;
import com.example.logisticsplanner.service.calc.SegmentCostService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RouteOptimizationServiceTest {
    @Test
    void findsPath() {
        var locRepo = mock(LocationRepository.class);
        var segRepo = mock(SegmentRepository.class);
        var segCost = mock(SegmentCostService.class);

        var A = Location.builder().id(1L).country("PL").build();
        var B = Location.builder().id(2L).country("DE").build();
        var C = Location.builder().id(3L).country("NL").build();

        when(locRepo.findAll()).thenReturn(List.of(A,B,C));

        var s1 = Segment.builder().id(1L).origin(A).destination(B)
                .vehicleType(VehicleType.TRUCK)
                .distanceKm(BigDecimal.valueOf(100)).speedKmh(BigDecimal.valueOf(50))
                .baseCostPerKmUsd(BigDecimal.ONE).co2KgPerKm(BigDecimal.valueOf(1)).build();

        var s2 = Segment.builder().id(2L).origin(B).destination(C)
                .vehicleType(VehicleType.RAIL)
                .distanceKm(BigDecimal.valueOf(200)).speedKmh(BigDecimal.valueOf(100))
                .baseCostPerKmUsd(BigDecimal.ONE).co2KgPerKm(BigDecimal.valueOf(0.5)).build();

        when(segRepo.findByOriginId(1L)).thenReturn(List.of(s1));
        when(segRepo.findByOriginId(2L)).thenReturn(List.of(s2));
        when(segRepo.findByOriginId(3L)).thenReturn(List.of());

        when(segCost.hours(any())).thenCallRealMethod(); // можно реальную имплементацию
        when(segCost.transportCost(any(), anyDouble())).thenReturn(BigDecimal.TEN);
        when(segCost.ecoFee(any(), anyDouble())).thenReturn(BigDecimal.ONE);

        var service = new RouteOptimizationService(segRepo, locRepo, segCost);
        var res = service.findBest(1L,3L, com.example.logisticsplanner.web.dto.ShipmentDto.Objective.TIME, 5.0);

        assertEquals(2, res.path().size());
    }
}