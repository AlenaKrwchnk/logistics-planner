package com.example.logisticsplanner.web.rest;

import com.example.logisticsplanner.domain.model.ProductCategory;
import com.example.logisticsplanner.domain.repo.ProductCategoryRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/product-categories")
@RequiredArgsConstructor
public class ProductCategoryController {
    private final ProductCategoryRepository repo;

    @GetMapping
    public List<ProductCategory> all() { return repo.findAll(); }

    @PostMapping
    public ResponseEntity<ProductCategory> create(@Valid @RequestBody ProductCategory p) {
        ProductCategory saved = repo.save(p);
        return ResponseEntity.created(URI.create("/api/product-categories/" + saved.getId())).body(saved);
    }

    @GetMapping("/{id}")
    public ProductCategory one(@PathVariable Long id) { return repo.findById(id).orElseThrow(); }

    @PutMapping("/{id}")
    public ProductCategory update(@PathVariable Long id, @Valid @RequestBody ProductCategory p) {
        p.setId(id);
        return repo.save(p);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { repo.deleteById(id); }
}
