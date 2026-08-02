package com.roboticsclub.controller;

import com.roboticsclub.model.EquipmentRequest;
import com.roboticsclub.service.RequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/requests")
public class RequestController {

    private final RequestService service;

    public RequestController(RequestService service) {
        this.service = service;
    }

    @GetMapping
    public String listRequests(Model model) {
        List<EquipmentRequest> requests = service.getAllRequests();
        model.addAttribute("requests", requests);
        return "requests/list";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("request", new EquipmentRequest());
        return "requests/form";
    }

    @PostMapping
    public String submitRequest(@ModelAttribute EquipmentRequest request) {
        service.createRequest(request);
        return "redirect:/requests";
    }

    @PostMapping("/{id}/approve")
    public String approve(@PathVariable Long id) {
        service.approveRequest(id);
        return "redirect:/requests";
    }

    @PostMapping("/{id}/reject")
    public String reject(@PathVariable Long id) {
        service.rejectRequest(id);
        return "redirect:/requests";
    }

    @PostMapping("/{id}/handover")
    public String handover(@PathVariable Long id) {
        service.handOverEquipment(id);
        return "redirect:/requests";
    }

    @PostMapping("/{id}/return")
    public String returnItem(@PathVariable Long id) {
        service.returnEquipment(id);
        return "redirect:/requests";
    }
}