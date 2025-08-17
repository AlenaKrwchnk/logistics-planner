package com.example.logisticsplanner.web.mvc;
import com.example.logisticsplanner.domain.model.CustomsCheckpoint;
import com.example.logisticsplanner.domain.model.Location;
import com.example.logisticsplanner.domain.repo.CustomsCheckpointRepository;
import com.example.logisticsplanner.domain.repo.LocationRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ui/customs-checkpoints")
@RequiredArgsConstructor
public class CustomsCheckpointPageController {

    private final CustomsCheckpointRepository repo;
    private final LocationRepository repo1;
    private Location location;
    @GetMapping
    public String list(Model model) {
        model.addAttribute("checkpoints", repo.findAll());
        return "customs-checkpoints/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("checkpoint", new CustomsCheckpoint());
        model.addAttribute("locations", repo1.findAll());
        return "customs-checkpoints/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {

        model.addAttribute("checkpoint", repo.findById(id).orElseThrow());
        List<Location> locations = repo1.findAll();
        model.addAttribute("locations", locations);

        return "customs-checkpoints/form";
    }

    @PostMapping
    public String save(@ModelAttribute CustomsCheckpoint checkpoint) {
        repo.save(checkpoint);
        return "redirect:/ui/customs-checkpoints";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/ui/customs-checkpoints";
    }
}