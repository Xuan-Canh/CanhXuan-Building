package com.canhxuan.CanhXuan_Building.service;

import com.canhxuan.CanhXuan_Building.dto.request.CreateRoomRequest;
import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.dto.response.ImageDto;
import com.canhxuan.CanhXuan_Building.dto.response.RoomDTO;
import com.canhxuan.CanhXuan_Building.entity.Room;
import com.canhxuan.CanhXuan_Building.entity.RoomImage;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RoomService {
    ApiResponse<Page<Room>> getAll(Integer page, Integer size);
    ApiResponse<Page<Room>> searchByBuildingNameOrBuildingAddress(String keyword, Integer page);
    ApiResponse<List<Room>> getByBuildingId(Long buildingId);
    ApiResponse<Room> getById(Long id);
    ApiResponse<List<Room>> getAvailableRooms();
    ApiResponse<Room> create(CreateRoomRequest roomRequest);
    ApiResponse<RoomDTO> update(Long id, Room room);
    ApiResponse<Void> delete(Long id);
    ApiResponse<RoomImage> saveImage(Long roomId, MultipartFile file) throws IOException;
    ApiResponse<List<ImageDto>> findImagesByRoomId(Long roomId);
    ApiResponse<Void> deleteImage(Long imageId);
}
