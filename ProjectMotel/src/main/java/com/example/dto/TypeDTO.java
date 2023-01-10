package com.example.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TypeDTO implements Serializable {

    private Integer typeId;

    // đưa vào để kiểm tra dữ liệu
    @NotEmpty
    @Length(min=3)
    private String name;
    private String description;
    private boolean isEdit=false;


}
