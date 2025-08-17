package com.example.logisticsplanner.service.route;

import com.example.logisticsplanner.domain.model.Location;
import com.example.logisticsplanner.domain.model.Segment;
import com.example.logisticsplanner.domain.repo.LocationRepository;
import com.example.logisticsplanner.domain.repo.SegmentRepository;
import com.example.logisticsplanner.service.calc.SegmentCostService;
import com.example.logisticsplanner.web.dto.ShipmentDto.Objective;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RouteOptimizationService {
    private final SegmentRepository segments;
    private final LocationRepository locations;
    private final SegmentCostService segmentCost;

    public record PathResult(List<Segment> path, double totalHours, BigDecimal transportCost, BigDecimal ecoFees) {}

    public PathResult findBest(Long originId, Long destId, Objective objective, double payloadTons) {
        // Dijkstra: вершины — locationId, хранить лучший вес + предшественник (segment)
        Map<Long, Double> dist = new HashMap<>();
        Map<Long, Segment> prevEdge = new HashMap<>();
        Set<Long> visited = new HashSet<>();
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingDouble(a -> a[1]));

        for (Location l : locations.findAll()) dist.put(l.getId(), Double.POSITIVE_INFINITY);
        dist.put(originId, 0.0);
        pq.add(new long[]{originId, 0});

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            long u = cur[0];
            if (!visited.add(u)) continue;
            if (u == destId) break;

            for (Segment e : segments.findByOriginId(u)) {
                long v = e.getDestination().getId();
                if (visited.contains(v)) continue;

                // вес ребра
                double hours = segmentCost.hours(e);
                double cost = segmentCost.transportCost(e, payloadTons).doubleValue()
                        + segmentCost.ecoFee(e, payloadTons).doubleValue();

                double w = switch (objective) {
                    case TIME -> hours;
                    case COST -> cost;
                    case BALANCED -> cost + hours * 20.0; // цена часа = 20$ (пример)
                };

                double alt = dist.get(u) + w;
                if (alt < dist.get(v)) {
                    dist.put(v, alt);
                    prevEdge.put(v, e);
                    pq.add(new long[]{v, Double.doubleToLongBits(alt)});
                }
            }
        }

        if (!prevEdge.containsKey(destId)) throw new IllegalStateException("Path not found");

        List<Segment> path = new ArrayList<>();
        for (Long cur = destId; !cur.equals(originId); ) {
            Segment e = prevEdge.get(cur);
            path.add(e);
            cur = e.getOrigin().getId();
        }
        Collections.reverse(path);

        BigDecimal transport = BigDecimal.ZERO;
        BigDecimal eco = BigDecimal.ZERO;
        double hoursSum = 0;
        for (Segment s : path) {
            transport = transport.add(segmentCost.transportCost(s, payloadTons));
            eco = eco.add(segmentCost.ecoFee(s, payloadTons));
            hoursSum += segmentCost.hours(s);
        }
        return new PathResult(path, hoursSum, transport, eco);
    }



}
