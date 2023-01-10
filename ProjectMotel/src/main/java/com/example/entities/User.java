package com.example.entities;

import com.example.utils.EnumRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer userId;

    @Column(name="first_name",length = 20, nullable = false)
    private String firstName;

    @Column(name="last_name",length = 20, nullable = false)
    private String lastName;

    @Column(name="email", nullable = false, unique = true, length = 30)
    private String email;

    @Column(name = "password", nullable = false, length = 10)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private EnumRole role;


}