package com.example.service;

import com.example.entities.User;
import com.example.iservice.IUser;
import com.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUser {
    @Autowired private UserRepository userRepository;

    @Override
    public User getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example) {
        return userRepository.findAll(example);
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
        return userRepository.findAll(example, sort);
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        return userRepository.saveAll(entities);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findAllById(Iterable<Integer> integers) {
        return userRepository.findAllById(integers);
    }

    @Override
    public <S extends User> S save(S entity) {
        return userRepository.save(entity);
    }

    @Override
    public Optional<User> findById(Integer integer) {
        return userRepository.findById(integer);
    }

    @Override
    public boolean existsById(Integer integer) {
        return userRepository.existsById(integer);
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public void deleteById(Integer integer) {
        userRepository.deleteById(integer);
    }

    @Override
    public void delete(User entity) {
        userRepository.delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }


    @Override
    public void deleteAll(Iterable<? extends User> entities) {
        userRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public <S extends User> Optional<S> findOne(Example<S> example) {
        return userRepository.findOne(example);
    }

    @Override
    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
        return userRepository.findAll(example, pageable);
    }

    @Override
    public <S extends User> long count(Example<S> example) {
        return userRepository.count(example);
    }

    @Override
    public <S extends User> boolean exists(Example<S> example) {
        return userRepository.exists(example);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }


    @Override
    public List<User> findByEmailContaining(String email) {
        return (List<User>) userRepository.findByEmailContaining(email);
    }


//    @Override
//    public UserDTO findByUsernameAndPassword(String email, String password) {
//        return userRepository.findByUsernameAndPassword(email, password);
//    }
}
