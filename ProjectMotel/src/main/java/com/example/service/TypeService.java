package com.example.service;

import com.example.entities.Type;
import com.example.iservice.IType;
import com.example.repositories.RoomRepository;
import com.example.repositories.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeService implements IType {
    @Autowired
    TypeRepository typeRepository;
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public List<Type> findByNameContaining(String name) {
        return (List<Type>) typeRepository.findByNameContaining(name);
    }


    @Override
    public Page<Type> findByNameContaining(String name, Pageable pageable) {
        return typeRepository.findByNameContaining(name, pageable);
    }

    @Override
    public <S extends Type> S saveAndFlush(S entity) {
        return typeRepository.saveAndFlush(entity);
    }

    @Override
    public <S extends Type> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Type> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }


    @Override
    public void deleteAllInBatch() {
        typeRepository.deleteAllInBatch();
    }

    @Override
    public Type getReferenceById(Integer integer) {
        return null;
    }


    @Override
    public <S extends Type> List<S> findAll(Example<S> example) {
        return typeRepository.findAll(example);
    }

    @Override
    public <S extends Type> List<S> findAll(Example<S> example, Sort sort) {
        return typeRepository.findAll(example, sort);
    }

    @Override
    public <S extends Type> List<S> saveAll(Iterable<S> entities) {
        return typeRepository.saveAll(entities);
    }

    @Override
    public List<Type> findAll() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> findAllById(Iterable<Integer> integers) {
        return typeRepository.findAllById(integers);
    }

    @Override
    public <S extends Type> S save(S entity) {
        return typeRepository.save(entity);
    }

    @Override
    public Optional<Type> findById(Integer integer) {
        return typeRepository.findById(integer);
    }

    @Override
    public boolean existsById(Integer integer) {
        return typeRepository.existsById(integer);
    }

    @Override
    public long count() {
        return typeRepository.count();
    }

    @Override
    public void deleteById(Integer integer) {
        typeRepository.deleteById(integer);
    }


    @Override
    public void delete(Type entity) {
        typeRepository.delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }


    @Override
    public void deleteAll(Iterable<? extends Type> entities) {
        typeRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        typeRepository.deleteAll();
    }

    @Override
    public List<Type> findAll(Sort sort) {
        return typeRepository.findAll(sort);
    }

    @Override
    public Page<Type> findAll(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public <S extends Type> Optional<S> findOne(Example<S> example) {
        return typeRepository.findOne(example);
    }

    @Override
    public <S extends Type> Page<S> findAll(Example<S> example, Pageable pageable) {
        return typeRepository.findAll(example, pageable);
    }

    @Override
    public <S extends Type> long count(Example<S> example) {
        return typeRepository.count(example);
    }

    @Override
    public <S extends Type> boolean exists(Example<S> example) {
        return typeRepository.exists(example);
    }
}
