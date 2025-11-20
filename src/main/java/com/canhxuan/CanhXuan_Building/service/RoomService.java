package com.canhxuan.CanhXuan_Building.service;

import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.entity.Room;
import com.canhxuan.CanhXuan_Building.entity.RoomImage;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RoomService {
    ApiResponse<Page<Room>> getAll(Integer page);
    ApiResponse<List<Room>> getByName(String name);
    ApiResponse<List<Room>> getByBuildingId(Long buildingId);
    ApiResponse<Room> getById(Long id);
    ApiResponse<Room> create(Long buildingId, Room room);
    ApiResponse<Room> update(Long id, Room room);
    ApiResponse<Void> delete(Long id);
    ApiResponse<RoomImage> saveImage(Long roomId, MultipartFile file) throws IOException;
    ApiResponse<List<RoomImage>> findImagesByRoomId(Long roomId);
    ApiResponse<Void> deleteImage(Long imageId);
}
