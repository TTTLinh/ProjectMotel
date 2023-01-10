package com.example;

import com.example.config.StorageProperties;
import com.example.iservice.IStorage;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class) // sử dụng cacsc thông số cấu hình trong storage
public class ProjectMotelApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectMotelApplication.class, args);
    }

    @Bean
   CommandLineRunner init(IStorage iStorage){
        return (args -> {
            iStorage.init();
        });
   }
}
