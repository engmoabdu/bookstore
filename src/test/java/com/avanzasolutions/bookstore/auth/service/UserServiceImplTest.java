/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.auth.service;

import com.avanzasolutions.bookstore.auth.dto.UserRequest;
import com.avanzasolutions.bookstore.auth.dto.UserResponse;
import com.avanzasolutions.bookstore.auth.entity.User;
import com.avanzasolutions.bookstore.auth.enums.Rules;
import com.avanzasolutions.bookstore.auth.repository.UserRepository;
import com.avanzasolutions.bookstore.auth.service.impl.UserServiceImpl;
import com.avanzasolutions.bookstore.common.infrastructure.exception.custom.CustomNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private UserRequest userRequest;

    @BeforeEach
    void setup() {
        userRequest = UserRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .username("moabdu")
                .password("Password1234")
                .build();

    }

    @Test
    void testAddAdminUser() {
        User user = new User();
        UserResponse expectedResponse = new UserResponse();
        when(modelMapper.map(any(UserRequest.class), eq(User.class))).thenReturn(user);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        when(modelMapper.map(any(User.class), eq(UserResponse.class))).thenReturn(expectedResponse);

        UserResponse actualResponse = userService.addAdminUser(userRequest);

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void testAddUser() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("moabdu");
        User user = new User();
        UserResponse expectedResponse = new UserResponse();
        when(modelMapper.map(any(UserRequest.class), eq(User.class))).thenReturn(user);
        when(passwordEncoder.encode(userRequest.getPassword())).thenReturn(userRequest.getPassword());
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(any(User.class), eq(UserResponse.class))).thenReturn(expectedResponse);

        UserResponse actualResponse = userService.addUser(userRequest);

        assertThat(actualResponse).isEqualTo(expectedResponse);
        verify(modelMapper).map(any(UserRequest.class), eq(User.class));
        verify(userRepository).save(any(User.class));
        verify(modelMapper).map(user, UserResponse.class);
    }

    @Test
    void testGetUser() {
        int id = 1;
        User user = new User();
        UserResponse expectedResponse = new UserResponse();
        Mockito.when(modelMapper.map(Mockito.any(User.class), Mockito.any())).thenReturn(expectedResponse);
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));

        UserResponse actualResponse = userService.getUser(id);

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("moabdu", "John", Collections.singletonList(Rules.ROLE_ADMIN.toString())));
        users.add(new User("Khaled", "Jane", Collections.singletonList(Rules.ROLE_USER.toString())));
        Mockito.when(userRepository.findAll()).thenReturn(users);

        List<UserResponse> expectedResponse = new ArrayList<>();
        expectedResponse.add(new UserResponse("moabdu", "John", "moabdu", "moabdu@gmail.com"));
        expectedResponse.add(new UserResponse("khaled", "John", "khaled", "khaled@gmail.com"));

        Mockito.when(modelMapper.map(users, new TypeToken<List<UserResponse>>() {
        }.getType())).thenReturn(expectedResponse);

        List<UserResponse> actualResponse = userService.getAllUsers();

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void testDeleteUser() {
        Integer userId = 1;
        User user = new User();
        user.setId(userId);
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
        Mockito.doNothing().when(userRepository).delete(Mockito.any(User.class));

        String actualResponse = userService.deleteUser(userId);

        assertThat(actualResponse).contains("Deleted Successfully.");
    }

    @Test
    void testRetrieveCurrentLoginUser() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext newContext = SecurityContextHolder.createEmptyContext();
        newContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(newContext);
        Mockito.when(authentication.getName()).thenReturn("testuser");
        User user = new User();
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(user));

        User actualResponse = userService.retrieveCurrentLoginUser();

        assertThat(actualResponse).isEqualTo(user);
    }

    @Test
    void testDeleteUserNotExist() {
        when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.deleteUser(1))
                .isInstanceOf(CustomNotFoundException.class);
    }

    @Test
    void testGetUserNotExist() {
        when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.getUser(1))
                .isInstanceOf(CustomNotFoundException.class);
    }
}