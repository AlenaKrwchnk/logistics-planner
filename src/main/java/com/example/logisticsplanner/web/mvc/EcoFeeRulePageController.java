package com.example.logisticsplanner.web.mvc;
import com.example.logisticsplanner.domain.model.EcoFeeRule;
import com.example.logisticsplanner.domain.model.VehicleType;
import com.example.logisticsplanner.domain.repo.EcoFeeRuleRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/ui/eco-fee-rules")
@RequiredArgsConstructor
public class EcoFeeRulePageController {

    private final EcoFeeRuleRepository repo;
    private VehicleType vehicleType;
    @GetMapping
    public String list(Model model) {
        model.addAttribute("rules", repo.findAll());

        return "eco-fee-rules/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("rule", new EcoFeeRule());

        model.addAttribute("vehicleTypes", VehicleType.values());
        return "eco-fee-rules/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("rule", repo.findById(id).orElseThrow());
        model.addAttribute("vehicleTypes", VehicleType.values());
        return "eco-fee-rules/form";
    }

    @PostMapping
    public String save(@ModelAttribute EcoFeeRule rule) {
        repo.save(rule);
        return "redirect:/ui/eco-fee-rules";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/ui/eco-fee-rules";
    }
}
