package com.example.logisticsplanner.service.calc;

import com.example.logisticsplanner.domain.model.*;
import com.example.logisticsplanner.domain.repo.TariffRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TariffCalculator {
    private final TariffRuleRepository rules;

    public BigDecimal customsFor(Product product, String originCountry, String destCountry, int qty) {
        List<TariffRule> list = rules.findByCategoryIdAndOriginCountryAndDestCountry(
                product.getCategory().getId(), originCountry, destCountry);
        if (list.isEmpty()) return BigDecimal.ZERO;

        BigDecimal base = product.getUnitValueUsd().multiply(BigDecimal.valueOf(qty));
        BigDecimal sum = BigDecimal.ZERO;

        for (TariffRule r : list) {
            BigDecimal fee = base.multiply(r.getRatePercent()).movePointLeft(2); // % → доли
            if (r.getMinUsd() != null && fee.compareTo(r.getMinUsd()) < 0) fee = r.getMinUsd();
            if (r.getMaxUsd() != null && fee.compareTo(r.getMaxUsd()) > 0) fee = r.getMaxUsd();
            sum = sum.add(fee);
        }
        return sum;
    }
}