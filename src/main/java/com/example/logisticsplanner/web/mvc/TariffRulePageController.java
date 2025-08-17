package com.example.logisticsplanner.web.mvc;
import com.example.logisticsplanner.domain.model.TariffRule;
import com.example.logisticsplanner.domain.repo.ProductCategoryRepository;
import com.example.logisticsplanner.domain.repo.TariffRuleRepository;
import com.example.logisticsplanner.domain.repo.ProductCategoryRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/ui/tariff-rules")
@RequiredArgsConstructor
public class TariffRulePageController {

    private final TariffRuleRepository repo;
    private final ProductCategoryRepository categoryRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("rules", repo.findAll());
        return "tariff-rules/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("rule", new TariffRule());
        model.addAttribute("categories", categoryRepository.findAll());
        return "tariff-rules/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("rule", repo.findById(id).orElseThrow());
        model.addAttribute("categories", categoryRepository.findAll());
        return "tariff-rules/form";
    }

    @PostMapping
    public String save(@ModelAttribute TariffRule rule) {
        repo.save(rule);
        return "redirect:/ui/tariff-rules";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/ui/tariff-rules";
    }
}
