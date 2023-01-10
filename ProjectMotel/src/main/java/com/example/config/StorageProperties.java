package com.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;



@ConfigurationProperties("storage")
@Data
public class StorageProperties {

    private String location; //location xác định vị trí các found đc up lên server

}
