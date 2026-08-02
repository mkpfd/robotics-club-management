package com.roboticsclub.controller;

import com.roboticsclub.repository.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;


@Controller
public class DashboardController {

    private final MemberRepository memberRepository;
    private final EquipmentRepository equipmentRepository;
    private final ProjectRepository projectRepository;
    private final EventRepository eventRepository;
    private final EquipmentRequestRepository requestRepository;


    public DashboardController(
            MemberRepository memberRepository,
            EquipmentRepository equipmentRepository,
            ProjectRepository projectRepository,
            EventRepository eventRepository,
            EquipmentRequestRepository requestRepository) {

        this.memberRepository = memberRepository;
        this.equipmentRepository = equipmentRepository;
        this.projectRepository = projectRepository;
        this.eventRepository = eventRepository;
        this.requestRepository = requestRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("memberCount", memberRepository.count());
        model.addAttribute("equipmentCount", equipmentRepository.count());
        model.addAttribute("projectCount", projectRepository.count());
        model.addAttribute("eventCount", eventRepository.countByEventDateGreaterThanEqual(LocalDate.now()));
        model.addAttribute("requestCount", requestRepository.count());
        return "dashboard";
    }
}