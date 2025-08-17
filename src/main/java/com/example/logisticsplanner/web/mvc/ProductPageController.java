package com.example.logisticsplanner.web.mvc;

import com.example.logisticsplanner.domain.model.Product;
import com.example.logisticsplanner.domain.repo.ProductRepository;
import com.example.logisticsplanner.domain.repo.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ui/products")
@RequiredArgsConstructor
public class ProductPageController {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository categoryRepository;


    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "products/list";
    }


    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll());
        return "products/form";
    }


    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productRepository.findById(id).orElseThrow());
        model.addAttribute("categories", categoryRepository.findAll());
        return "products/form";
    }


    @PostMapping
    public String save(@ModelAttribute Product product, @RequestParam Long categoryId) {
        product.setCategory(categoryRepository.findById(categoryId).orElse(null));
        productRepository.save(product);
        return "redirect:/ui/products";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/ui/products";
    }
}
