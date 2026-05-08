package com.fitness.activityservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
public class activityConfig {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
