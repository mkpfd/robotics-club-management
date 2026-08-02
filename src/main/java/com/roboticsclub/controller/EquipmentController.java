package com.roboticsclub.controller;

import com.roboticsclub.model.Equipment;
import com.roboticsclub.service.EquipmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    // View all equipment
    @GetMapping
    public String listEquipment(Model model) {
        model.addAttribute("equipmentList", equipmentService.getAllEquipment());
        return "equipment/list";
    }

    // Show Add Form
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("equipment", new Equipment());
        return "equipment/form";
    }

    // Save Equipment
    @PostMapping("/save")
    public String saveEquipment(@ModelAttribute Equipment equipment) {
        equipmentService.saveEquipment(equipment);
        return "redirect:/equipment";
    }

    // Show Edit Form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Equipment equipment = equipmentService.getEquipmentById(id).orElseThrow();
        model.addAttribute("equipment", equipment);
        return "equipment/form";
    }

    // Delete Equipment
    @GetMapping("/delete/{id}")
    public String deleteEquipment(@PathVariable Long id) {
        equipmentService.deleteEquipment(id);
        return "redirect:/equipment";
    }

}
