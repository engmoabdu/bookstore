/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.common.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
