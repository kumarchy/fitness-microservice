package com.fitness.userservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class userConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
