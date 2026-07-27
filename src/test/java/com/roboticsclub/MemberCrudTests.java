package com.roboticsclub;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * End-to-end smoke test for the Membership Management CRUD flow.
 * Runs against an in-memory H2 database (see src/test/resources/application.properties)
 * so it works without a real MySQL server. This only verifies application wiring;
 * the real MySQL setup should still be tested locally before submission.
 */
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = "ADMIN")
class MemberCrudTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void memberListPageLoads() throws Exception {
        mockMvc.perform(get("/members"))
                .andExpect(status().isOk())
                .andExpect(view().name("members/list"));
    }

    @Test
    void newMemberFormLoads() throws Exception {
        mockMvc.perform(get("/members/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("members/form"));
    }

    @Test
    void fullCrudFlow() throws Exception {
        // CREATE
        mockMvc.perform(post("/members/save")
                        .param("studentId", "IT21999")
                        .param("name", "Test Student")
                        .param("email", "test.student@example.com")
                        .param("phone", "0770000000")
                        .param("department", "IT")
                        .param("year", "2")
                        .param("joinDate", "2026-01-01")
                        .param("status", "ACTIVE")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/members"));

        // READ (verify it shows up in the list)
        mockMvc.perform(get("/members"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Test Student")));

        // UPDATE (must include the existing id, otherwise the service treats it
        // as a new member and correctly rejects it as a duplicate studentId)
        mockMvc.perform(post("/members/save")
                        .param("id", "1")
                        .param("studentId", "IT21999")
                        .param("name", "Test Student Updated")
                        .param("email", "test.student@example.com")
                        .param("phone", "0770000000")
                        .param("department", "IT")
                        .param("year", "2")
                        .param("joinDate", "2026-01-01")
                        .param("status", "INACTIVE")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/members"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Test Student Updated")));
    }

    @Test
    void validationRejectsBlankRequiredFields() throws Exception {
        mockMvc.perform(post("/members/save")
                        .param("studentId", "")
                        .param("name", "")
                        .param("email", "")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("members/form"));
    }
}
