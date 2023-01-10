package com.example.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Types")
public class Type implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer typeId;

    @Column(name = "type_name", length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = "type_des", length = 200, nullable = false)
    private String description;

    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL)
    private Set<Room> rooms;
}

