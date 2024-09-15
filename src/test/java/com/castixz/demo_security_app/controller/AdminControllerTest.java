package com.castixz.demo_security_app.controller;

import com.castixz.demo_security_app.config.SecurityConfig;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
@Import(SecurityConfig.class)
public class AdminControllerTest {

    private static final String ADMIN_ENDPOINT = "/api/v1/admin";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = "NO_ADMIN")
    public void testAdminAccessWithUsernameAdmin() throws Exception {
        mockMvc.perform(get(ADMIN_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello from admin controller"));
    }

    @Test
    @WithMockUser(username = "user1", roles = "ADMIN")
    public void testAdminAccessWithAuthorityAdmin() throws Exception {
        mockMvc.perform(get(ADMIN_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello from admin controller"));
    }

    @Test
    @WithMockUser(username = "user2", roles = "NO_ADMIN")
    public void testAdminAccessDeniedForNonAdminUser() throws Exception {
        mockMvc.perform(get(ADMIN_ENDPOINT))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testAdminAccessWithoutAuthentication() throws Exception {
        mockMvc.perform(get(ADMIN_ENDPOINT))
                .andExpect(status().isUnauthorized());
    }
}
