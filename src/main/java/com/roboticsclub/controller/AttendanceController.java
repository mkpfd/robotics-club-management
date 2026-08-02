package com.roboticsclub.controller;

import com.roboticsclub.model.Attendance;
import com.roboticsclub.service.AttendanceService;
import com.roboticsclub.service.EventService;
import com.roboticsclub.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final EventService eventService;
    private final MemberService memberService;

    public AttendanceController(AttendanceService attendanceService, EventService eventService, MemberService memberService){
        this.attendanceService=attendanceService;
        this.memberService=memberService;
        this.eventService=eventService;
    }

    @GetMapping
    public String list(Model model){
        model.addAttribute("attendanceList", attendanceService.getAllAttendance());
        return "attendance/list";
    }

    @GetMapping("/new")
    public String form(Model model){
        model.addAttribute("attendance", new Attendance());
        model.addAttribute("events", eventService.getAllEvents());
        model.addAttribute("members", memberService.getAllMembers());
        return "attendance/form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("attendance", attendanceService.getAttendanceById(id));
        model.addAttribute("events", eventService.getAllEvents());
        model.addAttribute("members", memberService.getAllMembers());
        return "attendance/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Attendance attendance){
        attendanceService.saveAttendance(attendance);
        return "redirect:/attendance";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        attendanceService.deleteAttendance(id);
        return "redirect:/attendance";
    }
}