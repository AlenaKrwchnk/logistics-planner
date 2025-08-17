package com.example.logisticsplanner.web.rest;

import com.example.logisticsplanner.domain.model.Location;
import com.example.logisticsplanner.domain.repo.LocationRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationRepository repo;

    @GetMapping
    public List<Location> all() { return repo.findAll(); }

    @PostMapping
    public ResponseEntity<Location> create(@Valid @RequestBody Location l) {
        Location saved = repo.save(l);
        return ResponseEntity.created(URI.create("/api/locations/" + saved.getId())).body(saved);
    }

    @GetMapping("/{id}")
    public Location one(@PathVariable Long id) { return repo.findById(id).orElseThrow(); }

    @PutMapping("/{id}")
    public Location update(@PathVariable Long id, @Valid @RequestBody Location l) {
        l.setId(id);
        return repo.save(l);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { repo.deleteById(id); }
}