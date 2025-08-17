package com.example.logisticsplanner.web.mvc;
import com.example.logisticsplanner.domain.model.Segment;
import com.example.logisticsplanner.domain.repo.ProductCategoryRepository;
import com.example.logisticsplanner.domain.repo.SegmentRepository;
import com.example.logisticsplanner.domain.repo.CustomsCheckpointRepository;
import com.example.logisticsplanner.domain.repo.LocationRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.logisticsplanner.domain.model.VehicleType;
@Controller
@RequestMapping("/ui/segments")
@RequiredArgsConstructor
public class SegmentPageController {

    private final SegmentRepository repo;
    private final LocationRepository locationRepository;
    private VehicleType vehicleType;
    private final CustomsCheckpointRepository customsCheckpointRepository;
    @GetMapping
    public String list(Model model) {
        model.addAttribute("segments", repo.findAll());

        return "segments/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("segment", new Segment());
        model.addAttribute("checkpoints", customsCheckpointRepository.findAll());
        model.addAttribute("locations", locationRepository.findAll());
        model.addAttribute("vehicleTypes", VehicleType.values());
        return "segments/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("segment", repo.findById(id).orElseThrow());
        model.addAttribute("vehicleTypes", VehicleType.values());
        model.addAttribute("locations", locationRepository.findAll());
        model.addAttribute("checkpoints", customsCheckpointRepository.findAll());
        return "segments/form";
    }

    @PostMapping
    public String save(@ModelAttribute Segment segment) {

        segment.setGeoJson(  "{ \"type\": \"LineString\", \"coordinates\": [[" +
                segment.getOrigin().getLon() + ", " + segment.getOrigin().getLat() + "], [" +
                segment.getDestination().getLon() + ", " + segment.getDestination().getLat() + "]] }");


        repo.save(segment);
        return "redirect:/ui/segments";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/ui/segments";
    }}