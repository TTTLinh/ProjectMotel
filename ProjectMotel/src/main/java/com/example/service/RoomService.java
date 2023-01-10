package com.example.service;

import com.example.dto.RoomDTO;
import com.example.entities.Room;
import com.example.iservice.IRoom;
import com.example.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService implements IRoom {
    @Autowired private RoomRepository roomRepository;

    @Override
    public List<Room> findByNameContaining(String name) {
        return (List<Room>) roomRepository.findByNameContaining(name);
    }



    @Override
    public Page<Room> findByNameContaining(String name, Pageable pageable) {
        return roomRepository.findByNameContaining(name, pageable);
    }

    @Override
    public void flush() {
        roomRepository.flush();
    }

    @Override
    public <S extends Room> S saveAndFlush(S entity) {
        return roomRepository.saveAndFlush(entity);
    }




    @Override
    public List<RoomDTO> findByName(String name){
        return roomRepository.findByName(name);
    }
//    @Override
//    public List<Room> findByTypeAndPrice(Type type, Double price){
//        return roomRepository.findByTypeAndPrice(type, price);
//    }



    @Override
    public void deleteAllInBatch() {
        roomRepository.deleteAllInBatch();
    }


    @Override
    public <S extends Room> List<S> findAll(Example<S> example) {
        return roomRepository.findAll(example);
    }

    @Override
    public <S extends Room> List<S> findAll(Example<S> example, Sort sort) {
        return roomRepository.findAll(example, sort);
    }

    @Override
    public <S extends Room> List<S> saveAll(Iterable<S> entities) {
        return roomRepository.saveAll(entities);
    }

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public List<Room> findAllById(Iterable<Integer> integers) {
        return roomRepository.findAllById(integers);
    }

    @Override
    public <S extends Room> S save(S entity) {
        return roomRepository.save(entity);
    }

    @Override
    public Optional<Room> findById(Integer integer) {
        return roomRepository.findById(integer);
    }

    @Override
    public boolean existsById(Integer integer) {
        return roomRepository.existsById(integer);
    }

    @Override
    public long count() {
        return roomRepository.count();
    }

    @Override
    public void deleteById(Integer integer) {
        roomRepository.deleteById(integer);
    }

    @Override
    public void delete(Room entity) {
        roomRepository.delete(entity);
    }


    @Override
    public void deleteAll(Iterable<? extends Room> entities) {
        roomRepository.deleteAll(entities);
    }

    @Override
    public List<Room> findAll(Sort sort) {
        return roomRepository.findAll(sort);
    }

    @Override
    public Page<Room> findAll(Pageable pageable) {
        return roomRepository.findAll(pageable);
    }

    @Override
    public <S extends Room> Optional<S> findOne(Example<S> example) {
        return roomRepository.findOne(example);
    }

    @Override
    public <S extends Room> long count(Example<S> example) {
        return roomRepository.count(example);
    }

    @Override
    public <S extends Room> boolean exists(Example<S> example) {
        return roomRepository.exists(example);
    }

    @Override
    public List<Room> findByTypeAndPrice1(Integer typeId, Double price) {
        return roomRepository.findByTypeAndPrice1(typeId, price);
    }
    @Override
    public List<Room> findByTypeAndPrice2(Integer typeId, Double price) {
        return roomRepository.findByTypeAndPrice2(typeId, price);
    }

    @Override
    public List<Room> findByType(Integer typeId) {
        return roomRepository.findByType(typeId);
    }


    @Override
    public List<Room> findByPrice1(Double price) {
        return roomRepository.findByPrice1(price);
    }

    @Override
    public List<Room> findByPrice2(Double price) {
        return roomRepository.findByPrice2(price);
    }


}
