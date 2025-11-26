package com.canhxuan.CanhXuan_Building.repository;

import com.canhxuan.CanhXuan_Building.entity.Room;
import com.canhxuan.CanhXuan_Building.entity.RoomStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @EntityGraph(value = "Room.full")
    Page<Room> findByNameContainingIgnoreCaseOrBuildingNameContainingIgnoreCaseOrBuildingAddressContainingIgnoreCase(String buildingName, String buildingAddress, String name, Pageable pageable);

    List<Room> findByBuildingId(Long buildingId);

    @EntityGraph(value = "Room.full")
    List<Room> findByStatus(RoomStatus status);

    @EntityGraph(value = "Room.full")
    Page<Room> findAll(Pageable pageable);
}
