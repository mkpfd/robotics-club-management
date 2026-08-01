package com.roboticsclub.controller;

import com.roboticsclub.model.Project;
import com.roboticsclub.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // Create Project
    @PostMapping
    public Project createProject(@RequestBody Project project) {
        return projectService.saveProject(project);
    }

    // Get All Projects
    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }
}