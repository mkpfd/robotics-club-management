package com.roboticsclub.controller;

import com.roboticsclub.model.Event;
import com.roboticsclub.repository.UserRepository;
import com.roboticsclub.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final UserRepository userRepository;

    @Autowired
    public EventController(EventService eventService, UserRepository userRepository) {
        this.eventService = eventService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String listEvents(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        return "events/list";
    }

    @GetMapping("/new")
    public String newEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "events/form";
    }

    @PostMapping("/save")
    public String saveEvent(@Valid @ModelAttribute("event") Event event,
                             BindingResult result,
                             Authentication authentication,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "events/form";
        }

        if (event.getId() != null) {
            // Editing an existing event: the form doesn't resubmit the
            // createdBy relationship, so carry over the original creator
            // instead of losing it.
            event.setCreatedBy(eventService.getEventById(event.getId()).getCreatedBy());
        } else if (authentication != null) {
            // New event: attribute it to whoever is logged in, when that
            // account has a matching row in the users table. Falls back to
            // null (schema allows it) so this works even before user
            // accounts from the users table are wired up to login.
            userRepository.findByUsername(authentication.getName())
                    .ifPresent(event::setCreatedBy);
        }

        eventService.saveEvent(event);
        redirectAttributes.addFlashAttribute("message", "Event saved successfully.");
        return "redirect:/events";
    }

    @GetMapping("/edit/{id}")
    public String editEventForm(@PathVariable Long id, Model model) {
        Event event = eventService.getEventById(id);
        model.addAttribute("event", event);
        return "events/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        eventService.deleteEvent(id);
        redirectAttributes.addFlashAttribute("message", "Event deleted successfully.");
        return "redirect:/events";
    }
}
