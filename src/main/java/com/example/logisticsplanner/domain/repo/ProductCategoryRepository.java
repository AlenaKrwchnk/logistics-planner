package com.example.logisticsplanner.domain.repo;
import com.example.logisticsplanner.domain.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {}