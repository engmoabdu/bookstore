/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.common.infrastructure.exception.custom;

import java.io.Serial;

/**
 * @author moabdu
 */
public class CustomNotFoundException extends CustomException {

    @Serial
    private static final long serialVersionUID = 7168555867098356326L;

    public CustomNotFoundException(String message, String errorCode) {
        super(message, errorCode);
    }
}
