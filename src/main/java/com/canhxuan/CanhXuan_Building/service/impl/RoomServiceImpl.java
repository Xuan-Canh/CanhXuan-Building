package com.canhxuan.CanhXuan_Building.service.impl;

import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.entity.Building;
import com.canhxuan.CanhXuan_Building.entity.Room;
import com.canhxuan.CanhXuan_Building.entity.RoomImage;
import com.canhxuan.CanhXuan_Building.repository.BuildingRepository;
import com.canhxuan.CanhXuan_Building.repository.RoomImageRepository;
import com.canhxuan.CanhXuan_Building.repository.RoomRepository;
import com.canhxuan.CanhXuan_Building.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomImageRepository roomImageRepository;
    private final BuildingRepository buildingRepository;

    public RoomServiceImpl(RoomRepository roomRepository, RoomImageRepository roomImageRepository, BuildingRepository buildingRepository) {
        this.roomRepository = roomRepository;
        this.roomImageRepository = roomImageRepository;
        this.buildingRepository = buildingRepository;
    }

    @Override
    public ApiResponse<Page<Room>> getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        ApiResponse<Page<Room>> response = new ApiResponse<>();
        response.setMessage("Get all rooms successfully");
        response.setData(roomRepository.findAll(pageable));
        return response;
    }

    @Override
    public ApiResponse<Page<Room>> searchByBuildingNameOrBuildingAddress(String keyword, Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        ApiResponse<Page<Room>> response = new ApiResponse<>();
        response.setMessage("Get rooms by name successfully");
        response.setData(roomRepository.findByBuildingNameContainingIgnoreCaseOrBuildingAddressContainingIgnoreCase(keyword, keyword, pageable));
        return response;
    }

    @Override
    public ApiResponse<List<Room>> getByBuildingId(Long buildingId) {
        ApiResponse<List<Room>> response = new ApiResponse<>();
        response.setMessage("Get rooms by building id successfully");
        response.setData(roomRepository.findByBuildingId(buildingId));
        return response;
    }

    @Override
    public ApiResponse<Room> getById(Long id) {
        ApiResponse<Room> response = new ApiResponse<>();
        response.setMessage("Get room by id successfully");
        response.setData(roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id)));
        return response;
    }

    @Override
    public ApiResponse<Room> create(Long buildingId, Room room) {
        Building building = buildingRepository.findById(buildingId)
                .orElseThrow(() -> new RuntimeException("Building not found with id: " + buildingId));

        room.setBuilding(building);

        ApiResponse<Room> response = new ApiResponse<>();
        response.setMessage("Create room successfully");
        response.setData(roomRepository.save(room));
        return response;
    }

    @Override
    public ApiResponse<Room> update(Long id, Room room) {
        Room existingRoom = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));

        existingRoom.setName(room.getName());
        existingRoom.setFloor(room.getFloor());
        existingRoom.setCapacity(room.getCapacity());
        existingRoom.setPrice(room.getPrice());
        existingRoom.setStatus(room.getStatus());
        existingRoom.setDescription(room.getDescription());

        ApiResponse<Room> response = new ApiResponse<>();
        response.setMessage("Update room successfully");
        response.setData(roomRepository.save(existingRoom));
        return response;
    }

    @Override
    public ApiResponse<Void> delete(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));

        roomRepository.delete(room);

        ApiResponse<Void> response = new ApiResponse<>();
        response.setMessage("Delete room successfully");
        return response;
    }

    @Override
    public ApiResponse<RoomImage> saveImage(Long roomId, MultipartFile file) throws IOException {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + roomId));

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get("uploads/images/room/");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        RoomImage roomImage = new RoomImage();
        roomImage.setRoom(room);
        roomImage.setFileName(fileName);
        roomImage.setFilePath(filePath.toString());
        roomImage.setFileType(file.getContentType());

        ApiResponse<RoomImage> response = new ApiResponse<>();
        response.setMessage("Save image successfully");
        response.setData(roomImageRepository.save(roomImage));
        return response;
    }

    @Override
    public ApiResponse<List<RoomImage>> findImagesByRoomId(Long roomId) {
        ApiResponse<List<RoomImage>> response = new ApiResponse<>();
        response.setMessage("Get room images successfully");
        response.setData(roomImageRepository.findByRoomId(roomId));
        return response;
    }

    @Override
    public ApiResponse<Void> deleteImage(Long imageId) {
        RoomImage roomImage = roomImageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + imageId));

        ApiResponse<Void> response = new ApiResponse<>();

        try {
            Files.deleteIfExists(Paths.get(roomImage.getFilePath()));
            roomImageRepository.delete(roomImage);
            response.setMessage("Delete image successfully");
        } catch (IOException e) {
            throw new RuntimeException("Could not delete file: " + roomImage.getFilePath(), e);
        }

        return response;
    }
}
