package com.example.dto;


import com.example.utils.EnumRole;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {

    private Integer userId;
    private String firstName;
    private String lastName;

    @NotEmpty
    @Length(min=3)
    private String email;
    @NotEmpty
    private String password;

    private EnumRole role;


    private boolean isEdit=false;
}
