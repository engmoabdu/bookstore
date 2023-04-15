/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.auth.service.impl;

import com.avanzasolutions.bookstore.auth.dto.UserRequest;
import com.avanzasolutions.bookstore.auth.dto.UserResponse;
import com.avanzasolutions.bookstore.auth.entity.User;
import com.avanzasolutions.bookstore.auth.enums.Rules;
import com.avanzasolutions.bookstore.auth.service.UserService;
import com.avanzasolutions.bookstore.common.enums.ErrorCodes;
import com.avanzasolutions.bookstore.common.enums.SuccessCodes;
import com.avanzasolutions.bookstore.common.infrastructure.exception.custom.CustomNotFoundException;
import com.avanzasolutions.bookstore.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse addAdminUser(UserRequest userRequest) {
        return modelMapper.map(this.createUser(userRequest, Collections.singletonList(Rules.ROLE_ADMIN.toString())), UserResponse.class);
    }

    @Override
    public UserResponse addUser(UserRequest userRequest) {
        return modelMapper.map(this.createUser(userRequest, Collections.singletonList(Rules.ROLE_USER.toString())), UserResponse.class);
    }

    @Override
    public UserResponse getUser(int id) {
        return this.modelMapper.map(repository.findById(id).orElseThrow(() -> {
            log.info(String.format(ErrorCodes.NOT_FOUND_ERR.getMessage(), id));
            return new CustomNotFoundException(ErrorCodes.NOT_FOUND_ERR.getErrorCode(), String.format(ErrorCodes.NOT_FOUND_ERR.getMessage(), id));
        }), UserResponse.class);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return modelMapper.map(repository.findAll(), new TypeToken<List<UserResponse>>() {
        }.getType());
    }

    @Override
    public String deleteUser(Integer userId) {
        return this.repository.findById(userId).map(user -> {
            this.repository.delete(user);
            return String.format(SuccessCodes.DELETED_SUCCESS.getMessage(), user.getId());
        }).orElseThrow(() -> {
            log.info(String.format(ErrorCodes.NOT_FOUND_ERR.getMessage(), userId));
            return new CustomNotFoundException(ErrorCodes.NOT_FOUND_ERR.getErrorCode(), String.format(ErrorCodes.NOT_FOUND_ERR.getMessage(), userId));
        });
    }

    @Override
    public User retrieveCurrentLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        log.info("UserName is : {}", username);
        return this.findUserByUsername(username);
    }

    private User createUser(UserRequest userRequest, List<String> authorities) {
        userRequest.setPassword(encodePassword(userRequest.getPassword()));
        userRequest.setUsername(userRequest.getUsername().toLowerCase());
        User user = modelMapper.map(userRequest, User.class);
        user.setAuthorities(authorities);
        return repository.save(user);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private User findUserByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
    }
}
