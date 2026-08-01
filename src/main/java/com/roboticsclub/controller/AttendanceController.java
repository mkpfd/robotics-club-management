package com.roboticsclub.controller;

import com.roboticsclub.model.Attendance;
import com.roboticsclub.service.AttendanceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService){
        this.attendanceService=attendanceService;
    }

    @GetMapping
    public String list(Model model){
        model.addAttribute("attendanceList", attendanceService.getAllAttendance());
        return "attendance/list";
    }

    @GetMapping("/new")
    public String form(Model model){
        model.addAttribute("attendance", new Attendance());
        return "attendance/form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("attendance", attendanceService.getAttendanceById(id));
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