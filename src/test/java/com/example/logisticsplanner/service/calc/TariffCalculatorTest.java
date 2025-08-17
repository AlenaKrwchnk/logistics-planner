package com.example.logisticsplanner.service.calc;

import com.example.logisticsplanner.domain.model.*;
import com.example.logisticsplanner.domain.repo.TariffRuleRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TariffCalculatorTest {
    @Test
    void computesCustoms() {
        var repo = mock(TariffRuleRepository.class);
        var cat = ProductCategory.builder().id(1L).build();
        var rule = TariffRule.builder()
                .category(cat).ratePercent(new BigDecimal("5"))
                .originCountry("PL").destCountry("NL").build();

        when(repo.findByCategoryIdAndOriginCountryAndDestCountry(1L,"PL","NL"))
                .thenReturn(List.of(rule));

        var product = Product.builder()
                .category(cat)
                .unitValueUsd(new BigDecimal("500"))
                .build();

        var calc = new TariffCalculator(repo);
        var fee = calc.customsFor(product, "PL","NL", 10); // 10 шт × $500 = $5000
        assertEquals(new BigDecimal("250.00"), fee.setScale(2));
    }
}