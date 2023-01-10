package com.example.iservice;

import com.example.entities.Type;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface IType {

    List<Type> findByNameContaining(String name);

    Page<Type> findByNameContaining(String name, Pageable pageable);

    <S extends Type> S saveAndFlush(S entity);

    <S extends Type> List<S> saveAllAndFlush(Iterable<S> entities);

    void deleteAllInBatch(Iterable<Type> entities);

    void deleteAllByIdInBatch(Iterable<Integer> integers);

    void deleteAllInBatch();

    Type getReferenceById(Integer integer);

    <S extends Type> List<S> findAll(Example<S> example);

    <S extends Type> List<S> findAll(Example<S> example, Sort sort);

    <S extends Type> List<S> saveAll(Iterable<S> entities);

    List<Type> findAll();

    List<Type> findAllById(Iterable<Integer> integers);

    <S extends Type> S save(S entity);

    Optional<Type> findById(Integer integer);

    boolean existsById(Integer integer);

    long count();

    void deleteById(Integer integer);

    void delete(Type entity);

    void deleteAllById(Iterable<? extends Integer> integers);

    void deleteAll(Iterable<? extends Type> entities);

    void deleteAll();

    List<Type> findAll(Sort sort);

    Page<Type> findAll(Pageable pageable);

    <S extends Type> Optional<S> findOne(Example<S> example);

    <S extends Type> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends Type> long count(Example<S> example);

    <S extends Type> boolean exists(Example<S> example);

}
