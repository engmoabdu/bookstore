/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.auth.controller;

import com.avanzasolutions.bookstore.auth.dto.UserRequest;
import com.avanzasolutions.bookstore.auth.dto.UserResponse;
import com.avanzasolutions.bookstore.auth.service.UserService;
import com.avanzasolutions.bookstore.common.infrastructure.exception.advice.CustomExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
    }

    @Test
    void testAddAdminUser() throws Exception {
        UserRequest userRequest = new UserRequest();
        UserResponse userResponse = new UserResponse();
        userResponse.setId(1);

        when(userService.addAdminUser(any(UserRequest.class))).thenReturn(userResponse);

        mockMvc.perform(post("/v1/auth/register-admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.message").value("AdminUser added successfully"));

        verify(userService).addAdminUser(any(UserRequest.class));
    }

    @Test
    void testAddUser() throws Exception {
        UserRequest userRequest = new UserRequest();
        UserResponse userResponse = new UserResponse();
        userResponse.setId(1);

        when(userService.addUser(any(UserRequest.class))).thenReturn(userResponse);

        mockMvc.perform(post("/v1/auth/register-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.message").value("User added successfully"));

        verify(userService).addUser(any(UserRequest.class));
    }

    @Test
    void testDeleteUser() throws Exception {
        Integer userId = 1;
        String expectedMessage = "User with id 1 deleted successfully";

        when(userService.deleteUser(userId)).thenReturn(expectedMessage);

        mockMvc.perform(delete("/v1/auth?userId=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedMessage));

        verify(userService).deleteUser(userId);
    }

    @Test
    void testIsRunning() throws Exception {
        mockMvc.perform(get("/v1/auth/is-running")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Service is running"));
    }

    @Test
    void testGetAllUsers() throws Exception {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(1);

        List<UserResponse> userResponseList = Collections.singletonList(userResponse);

        when(userService.getAllUsers()).thenReturn(userResponseList);

        mockMvc.perform(get("/v1/auth/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1));

        verify(userService).getAllUsers();
    }
}
