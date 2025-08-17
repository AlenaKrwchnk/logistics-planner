package com.example.logisticsplanner.service.calc;

import com.example.logisticsplanner.domain.model.Product;
import com.example.logisticsplanner.domain.repo.ProductRepository;
import com.example.logisticsplanner.service.route.RouteOptimizationService;
import com.example.logisticsplanner.web.dto.QuoteResponse;
import com.example.logisticsplanner.web.dto.ShipmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuoteService {
    private final ProductRepository products;
    private final TariffCalculator tariff;
    private final RouteOptimizationService optimizer;
    private final SegmentCostService segmentCost;

    public QuoteResponse quote(ShipmentDto dto) {
        // масса и стоимость груза
        BigDecimal totalWeightKg = BigDecimal.ZERO;
        BigDecimal declaredValueUsd = BigDecimal.ZERO;
        for (ShipmentDto.Item i : dto.items()) {
            Product p = products.findById(i.productId()).orElseThrow();
            totalWeightKg = totalWeightKg.add(p.getUnitWeightKg().multiply(BigDecimal.valueOf(i.quantity())));
            declaredValueUsd = declaredValueUsd.add(p.getUnitValueUsd().multiply(BigDecimal.valueOf(i.quantity())));
        }
        double payloadTons = totalWeightKg.doubleValue() / 1000.0;

        // лучший маршрут
        var path = optimizer.findBest(dto.originId(), dto.destinationId(), dto.objective(), payloadTons);

        // таможня (примерно: по основному направлению маршрута, берем страны узлов)
        String originCountry = path.path().getFirst().getOrigin().getCountry();
        String destCountry = path.path().getLast().getDestination().getCountry();

        BigDecimal customs = BigDecimal.ZERO;
        for (ShipmentDto.Item i : dto.items()) {
            Product p = products.findById(i.productId()).orElseThrow();
            customs = customs.add(tariff.customsFor(p, originCountry, destCountry, i.quantity()));
        }

        BigDecimal transport = path.transportCost();
        BigDecimal ecoFees = path.ecoFees();
        BigDecimal total = transport.add(ecoFees).add(customs);

        List<QuoteResponse.SegmentView> views = new ArrayList<>();
        path.path().forEach(s -> views.add(new QuoteResponse.SegmentView(
                s.getId(),
                s.getVehicleType().name(),
                s.getOrigin().getCode(),
                s.getDestination().getCode(),
                s.getDistanceKm().doubleValue(),
                segmentCost.hours(s),
                s.getGeoJson()
        )));

        return new QuoteResponse(total, transport, customs, ecoFees, path.totalHours(), views);
    }
}