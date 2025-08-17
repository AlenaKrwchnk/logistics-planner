package com.example.logisticsplanner.web.rest;

import com.example.logisticsplanner.domain.model.EcoFeeRule;
import com.example.logisticsplanner.domain.repo.EcoFeeRuleRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/eco-fee-rules")
@RequiredArgsConstructor
public class EcoFeeRuleController {
    private final EcoFeeRuleRepository repo;

    @GetMapping
    public List<EcoFeeRule> all() { return repo.findAll(); }

    @PostMapping
    public ResponseEntity<EcoFeeRule> create(@Valid @RequestBody EcoFeeRule e) {
        EcoFeeRule saved = repo.save(e);
        return ResponseEntity.created(URI.create("/api/eco-fee-rules/" + saved.getId())).body(saved);
    }

    @GetMapping("/{id}")
    public EcoFeeRule one(@PathVariable Long id) { return repo.findById(id).orElseThrow(); }

    @PutMapping("/{id}")
    public EcoFeeRule update(@PathVariable Long id, @Valid @RequestBody EcoFeeRule e) {
        e.setId(id);
        return repo.save(e);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { repo.deleteById(id); }
}
