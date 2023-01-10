package com.example.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {

        private Integer roomId;

        @NotEmpty
        @Length(min=3)
        private String name;
        private Double price;
        private String imageSrc;
        private MultipartFile image;
        private String address;
        private String phone;
        private String description;
        private Boolean status;
        private Integer typeId;
        private String typeName;
        private boolean isEdit=false;

}
