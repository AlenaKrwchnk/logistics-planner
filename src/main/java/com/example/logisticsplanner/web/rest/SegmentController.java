package com.example.logisticsplanner.web.rest;

import com.example.logisticsplanner.domain.model.Segment;
import com.example.logisticsplanner.domain.repo.SegmentRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/segments")
@RequiredArgsConstructor
public class SegmentController {
    private final SegmentRepository repo;

    @GetMapping
    public List<Segment> all() { return repo.findAll(); }

    @PostMapping
    public ResponseEntity<Segment> create(@Valid @RequestBody Segment s) {
        Segment saved = repo.save(s);
        return ResponseEntity.created(URI.create("/api/segments/" + saved.getId())).body(saved);
    }

    @GetMapping("/{id}")
    public Segment one(@PathVariable Long id) { return repo.findById(id).orElseThrow(); }

    @PutMapping("/{id}")
    public Segment update(@PathVariable Long id, @Valid @RequestBody Segment s) {
        s.setId(id);
        return repo.save(s);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { repo.deleteById(id); }
}
