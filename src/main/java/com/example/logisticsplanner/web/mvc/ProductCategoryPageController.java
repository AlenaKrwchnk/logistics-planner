package com.example.logisticsplanner.web.mvc;


import com.example.logisticsplanner.domain.model.ProductCategory;
import com.example.logisticsplanner.domain.repo.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ui/product-categories")
@RequiredArgsConstructor
public class ProductCategoryPageController {

    private final ProductCategoryRepository repo;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("categories", repo.findAll());
        return "product-categories/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("category", new ProductCategory());
        return "product-categories/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("category", repo.findById(id).orElseThrow());
        return "product-categories/form";
    }

    @PostMapping
    public String save(@ModelAttribute ProductCategory category) {
        repo.save(category);
        return "redirect:/ui/product-categories";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/ui/product-categories";
    }
}