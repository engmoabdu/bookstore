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

import java.util.List;

public interface UserService {
    UserResponse addAdminUser(UserRequest userRequest);

    UserResponse addUser(UserRequest userRequest);

    List<UserResponse> getAllUsers();

    UserResponse getUser(int id);

    String deleteUser(Integer userId);

    User retrieveCurrentLoginUser();
}
