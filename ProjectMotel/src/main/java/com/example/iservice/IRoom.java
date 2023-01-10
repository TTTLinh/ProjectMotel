package com.example.iservice;

import com.example.dto.RoomDTO;
import com.example.entities.Room;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface IRoom {

    List<Room> findByNameContaining(String name);






    Page<Room> findByNameContaining(String name, Pageable pageable);

    void flush();

    <S extends Room> S saveAndFlush(S entity);

//    List<Room> findByTypeAndPrice(Type type, Double price);

    List<RoomDTO> findByName(String name);

    //    @Override
    //    public List<Room> findByTypeAndPrice(Type type, Double price){
    //        return roomRepository.findByTypeAndPrice(type, price);
    //    }

    void deleteAllInBatch();

    <S extends Room> List<S> findAll(Example<S> example);

    <S extends Room> List<S> findAll(Example<S> example, Sort sort);

    <S extends Room> List<S> saveAll(Iterable<S> entities);

    List<Room> findAll();

    List<Room> findAllById(Iterable<Integer> integers);

    <S extends Room> S save(S entity);

    Optional<Room> findById(Integer integer);

    boolean existsById(Integer integer);

    long count();

    void deleteById(Integer integer);

    void delete(Room entity);

    void deleteAll(Iterable<? extends Room> entities);

    List<Room> findAll(Sort sort);

    Page<Room> findAll(Pageable pageable);

    <S extends Room> Optional<S> findOne(Example<S> example);

    <S extends Room> long count(Example<S> example);

    <S extends Room> boolean exists(Example<S> example);


    List<Room> findByTypeAndPrice1(Integer typeId, Double price);


    List<Room> findByTypeAndPrice2(Integer typeId, Double price);

    List<Room> findByType(Integer typeId);

    List<Room> findByPrice1(Double price);

    List<Room> findByPrice2(Double price);
}
