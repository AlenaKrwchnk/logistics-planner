package com.example.logisticsplanner.web.mvc;
import com.example.logisticsplanner.domain.model.Location;
import com.example.logisticsplanner.domain.repo.LocationRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/ui/locations")
@RequiredArgsConstructor
public class LocationPageController {

    private final LocationRepository repo;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("locations", repo.findAll());
        return "locations/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("location", new Location());
        return "locations/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("location", repo.findById(id).orElseThrow());
        return "locations/form";
    }

    @PostMapping
    public String save(@ModelAttribute Location location) {
        repo.save(location);
        return "redirect:/ui/locations";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/ui/locations";
    }
}