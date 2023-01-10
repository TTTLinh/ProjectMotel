package com.example.repositories;

import com.example.entities.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {
    Page<Type> findByNameContaining(String name, Pageable pageable);

    List<Type> findByNameContaining(String name);
}
