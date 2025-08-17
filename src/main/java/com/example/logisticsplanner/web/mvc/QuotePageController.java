package com.example.logisticsplanner.web.mvc;

import com.example.logisticsplanner.service.calc.QuoteService;
import com.example.logisticsplanner.web.dto.ShipmentDto;
import com.example.logisticsplanner.web.dto.ShipmentForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class QuotePageController {
    private final QuoteService quotes;

    @GetMapping("/")
    public String index(Model model) {
        ShipmentForm form = new ShipmentForm();

        form.getItems().add(new ShipmentForm.ItemForm());
        model.addAttribute("form", form);
        return "index";
    }

    @PostMapping("/quote")
    public String quote(@ModelAttribute("form") ShipmentForm form, Model model) {

        ShipmentDto shipmentDto = new ShipmentDto(
                form.getOriginId(),
                form.getDestinationId(),
                form.getObjective(),
                form.getItems().stream()
                        .map(i -> new ShipmentDto.Item(i.getProductId(), i.getQuantity()))
                        .toList()
        );

        var res = quotes.quote(shipmentDto);
        model.addAttribute("quote", res);
        return "quote";
    }

}
