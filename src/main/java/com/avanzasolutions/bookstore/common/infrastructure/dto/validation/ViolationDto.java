/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.common.infrastructure.dto.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author moabdu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViolationDto {
    private String filedName;
    private String message;
}
