package com.example.logisticsplanner.domain.repo;
import com.example.logisticsplanner.domain.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface TariffRuleRepository extends JpaRepository<TariffRule, Long> {
    List<TariffRule> findByCategoryIdAndOriginCountryAndDestCountry(
            Long categoryId, String originCountry, String destCountry);
}
