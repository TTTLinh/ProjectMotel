package com.example.repositories;


import com.example.dto.RoomDTO;
import com.example.entities.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findByNameContaining(String name);

    List<RoomDTO> findByName(String name);

    Page<Room> findByNameContaining(String name, Pageable pageable);

    @Query(
            value = "SELECT * FROM db.rooms where price>= :price and type_id= :typeId",
            nativeQuery = true)
    List<Room> findByTypeAndPrice1(Integer typeId, Double price);
    @Query(
            value = "SELECT * FROM db.rooms where price < :price and type_id= :typeId",
            nativeQuery = true)
    List<Room> findByTypeAndPrice2(Integer typeId, Double price);


    @Query(
            value = "SELECT * FROM db.rooms where type_id= :typeId",
            nativeQuery = true)
    List<Room> findByType(Integer typeId);


    @Query(
            value = "SELECT * FROM db.rooms where  price >= :price",
            nativeQuery = true)
    List<Room> findByPrice1(Double price);


    @Query(
            value = "SELECT * FROM db.rooms where  price < :price",
            nativeQuery = true)
    List<Room> findByPrice2(Double price);


//
//    List<Room> findByTypeAndPrice(
//            @Param(value = "type") Type type,
//            @Param(value = "price") Double price);;
}
