/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.auth.dto;

import com.avanzasolutions.bookstore.common.annotations.ValidEmail;
import com.avanzasolutions.bookstore.common.constants.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request object for User API")
public class UserRequest {

    @NotNull(message = "firstName " + Constants.Messages.NOT_NULL_ERROR_MESSAGE)
    @NotBlank(message = "firstName " + Constants.Messages.NOT_BLANK_ERROR_MESSAGE)
    @Schema(description = "User's first name", example = "John")
    private String firstName;

    @NotNull(message = "lastName " + Constants.Messages.NOT_NULL_ERROR_MESSAGE)
    @NotBlank(message = "lastName " + Constants.Messages.NOT_BLANK_ERROR_MESSAGE)
    @Schema(description = "User's last name", example = "Doe")
    private String lastName;

    @Email
    @ValidEmail
    @Schema(description = "User's email address", example = "johndoe@example.com")
    private String email;

    @Size(min = 4, max = 16)
    @NotNull(message = "username " + Constants.Messages.NOT_NULL_ERROR_MESSAGE)
    @NotBlank(message = "username " + Constants.Messages.NOT_BLANK_ERROR_MESSAGE)
    @Schema(description = "User's username should be between 4 to 16 character", example = "moabdu")
    private String username;

    @Pattern(regexp = Constants.Regex.PASSWORD_REGEX, message = "Password must contain at least one capital letter, one small letter, and one number.")
    @Schema(description = "User's password", example = "Password1234", pattern = Constants.Regex.PASSWORD_REGEX)
    private String password;
}
