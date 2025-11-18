package com.canhxuan.CanhXuan_Building.repository;

import com.canhxuan.CanhXuan_Building.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByNameContaining(String name);
    List<Room> findByBuildingId(Long buildingId);
}
