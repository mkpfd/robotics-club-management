package com.roboticsclub.controller;

import com.roboticsclub.model.Project;
import com.roboticsclub.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("projects", projectService.getAllProjects());
        return "projects/list";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("project", new Project());
        return "projects/form";
    }

    @GetMapping("/edit/{id}")
    public String editProject(@PathVariable Long id, Model model) {
        Project project = projectService.getProjectById(id);
        model.addAttribute("project", project);
        return "projects/form";
    }


    @PostMapping("/save")
    public String save(@ModelAttribute Project project) {
        projectService.saveProject(project);
        return "redirect:/projects";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        projectService.deleteProject(id);
        return "redirect:/projects";
    }
}