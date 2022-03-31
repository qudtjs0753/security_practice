package com.example.security_practice.admin;

import com.example.security_practice.user.User;
import com.example.security_practice.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@Transactional
class AdminControllerTest {

    @Autowired
    private UserRepository userRepository;
    private MockMvc mockMvc;
    private User user;
    private User admin;

    @BeforeEach
    public void setUp(@Autowired WebApplicationContext applicationContext){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .alwaysDo(print())
                .build();
        user = userRepository.save(new User("user", "user", "ROLE_USER"));
        admin = userRepository.save(new User("admin", "admin", "ROLE_ADMIN"));
    }

    @DisplayName("getNote_인증없을시")
    @Test
    void getNoteForAdmin_인증없음() throws Exception{
        mockMvc.perform(get("/admin").with(csrf()))
                .andExpect(redirectedUrlPattern("**/login"))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("getNote_admin_인증시")
    @Test
    void getNoteForAdmin_인증있음() throws Exception{
        mockMvc.perform(get("/admin").with(csrf()).with(user(admin))) // 어드민 추가
                .andExpect(status().is2xxSuccessful());
    }

    @DisplayName("getNote_user_인증시")
    @Test
    void getNoteForAdmin_유저인증있음() throws Exception{
        mockMvc.perform(get("/admin").with(csrf()).with(user(user))) // 유저 추가
                .andExpect(status().isForbidden());
    }
}