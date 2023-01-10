package com.example.iservice;

import com.example.entities.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface IUser {


    User getReferenceById(Integer integer);

    <S extends User> List<S> findAll(Example<S> example);

    <S extends User> List<S> findAll(Example<S> example, Sort sort);

    <S extends User> List<S> saveAll(Iterable<S> entities);

    List<User> findAll();

    List<User> findAllById(Iterable<Integer> integers);

    <S extends User> S save(S entity);

    Optional<User> findById(Integer integer);

    boolean existsById(Integer integer);

    long count();

    void deleteById(Integer integer);

    void delete(User entity);

    void deleteAllById(Iterable<? extends Integer> integers);

    void deleteAll(Iterable<? extends User> entities);

    void deleteAll();

    <S extends User> Optional<S> findOne(Example<S> example);

    <S extends User> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends User> long count(Example<S> example);

    <S extends User> boolean exists(Example<S> example);

    Optional<User> findByEmailAndPassword(String email, String password);


    List<User> findByEmailContaining(String email);

}
