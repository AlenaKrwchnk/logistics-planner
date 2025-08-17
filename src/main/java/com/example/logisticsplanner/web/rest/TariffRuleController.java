package com.example.logisticsplanner.web.rest;

import com.example.logisticsplanner.domain.model.TariffRule;
import com.example.logisticsplanner.domain.repo.TariffRuleRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tariff-rules")
@RequiredArgsConstructor
public class TariffRuleController {
    private final TariffRuleRepository repo;

    @GetMapping
    public List<TariffRule> all() { return repo.findAll(); }

    @PostMapping
    public ResponseEntity<TariffRule> create(@Valid @RequestBody TariffRule t) {
        TariffRule saved = repo.save(t);
        return ResponseEntity.created(URI.create("/api/tariff-rules/" + saved.getId())).body(saved);
    }

    @GetMapping("/{id}")
    public TariffRule one(@PathVariable Long id) { return repo.findById(id).orElseThrow(); }

    @PutMapping("/{id}")
    public TariffRule update(@PathVariable Long id, @Valid @RequestBody TariffRule t) {
        t.setId(id);
        return repo.save(t);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { repo.deleteById(id); }
}
