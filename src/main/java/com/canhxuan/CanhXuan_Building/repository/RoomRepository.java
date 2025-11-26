package com.canhxuan.CanhXuan_Building.repository;

import com.canhxuan.CanhXuan_Building.entity.Room;
import com.canhxuan.CanhXuan_Building.entity.RoomStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Page<Room> findByNameContainingIgnoreCaseOrBuildingNameContainingIgnoreCaseOrBuildingAddressContainingIgnoreCase(String buildingName, String buildingAddress, String name, Pageable pageable);

    List<Room> findByBuildingId(Long buildingId);

    List<Room> findByStatus(RoomStatus status);

    Optional<Room> findByName(String name);
}
