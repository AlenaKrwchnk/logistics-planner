package com.example.logisticsplanner.web.rest;

import com.example.logisticsplanner.domain.model.CustomsCheckpoint;
import com.example.logisticsplanner.domain.repo.CustomsCheckpointRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/customs-checkpoints")
@RequiredArgsConstructor
public class CustomsCheckpointController {
    private final CustomsCheckpointRepository repo;

    @GetMapping
    public List<CustomsCheckpoint> all() { return repo.findAll(); }

    @PostMapping
    public ResponseEntity<CustomsCheckpoint> create(@Valid @RequestBody CustomsCheckpoint c) {
        CustomsCheckpoint saved = repo.save(c);
        return ResponseEntity.created(URI.create("/api/customs-checkpoints/" + saved.getId())).body(saved);
    }

    @GetMapping("/{id}")
    public CustomsCheckpoint one(@PathVariable Long id) { return repo.findById(id).orElseThrow(); }

    @PutMapping("/{id}")
    public CustomsCheckpoint update(@PathVariable Long id, @Valid @RequestBody CustomsCheckpoint c) {
        c.setId(id);
        return repo.save(c);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { repo.deleteById(id); }
}