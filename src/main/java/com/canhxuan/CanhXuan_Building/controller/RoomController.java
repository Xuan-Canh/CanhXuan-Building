package com.canhxuan.CanhXuan_Building.controller;

import com.canhxuan.CanhXuan_Building.dto.request.CreateRoomRequest;
import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.dto.response.ImageDto;
import com.canhxuan.CanhXuan_Building.dto.response.RoomDTO;
import com.canhxuan.CanhXuan_Building.entity.Room;
import com.canhxuan.CanhXuan_Building.entity.RoomImage;
import com.canhxuan.CanhXuan_Building.service.RoomService;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/canhxuan/rooms")
public class RoomController {

    private String uploadDir = "uploads/images/room/";
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROOM_MANAGE') or hasAuthority('ROOM_READ')")
    public ResponseEntity<ApiResponse<Page<Room>>> getAll(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(roomService.getAll(page, size));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ROOM_MANAGE') or hasAuthority('ROOM_READ')")
    public ResponseEntity<ApiResponse<Page<Room>>> searchByBuildingNameOrBuildingAddress(@RequestParam(defaultValue = "") String keyword,
                                                             @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(roomService.searchByBuildingNameOrBuildingAddress(keyword, page));
    }

    @GetMapping("/building/{buildingId}")
    @PreAuthorize("hasAuthority('ROOM_MANAGE') or hasAuthority('ROOM_READ')")
    public ResponseEntity<ApiResponse<List<Room>>> getByBuildingId(@PathVariable Long buildingId) {
        return ResponseEntity.ok(roomService.getByBuildingId(buildingId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROOM_MANAGE') or hasAuthority('ROOM_READ')")
    public ResponseEntity<ApiResponse<Room>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getById(id));
    }

    @GetMapping("/available")
    @PreAuthorize("hasAuthority('ROOM_MANAGE') or hasAuthority('ROOM_READ')")
    public ResponseEntity<ApiResponse<List<Room>>> getAvailableRooms() {
        return ResponseEntity.ok(roomService.getAvailableRooms());
    }

    @GetMapping("/{roomId}/images")
    public ResponseEntity<ApiResponse<List<ImageDto>>> findImagesByRoomId(@PathVariable Long roomId) {
        return ResponseEntity.ok(roomService.findImagesByRoomId(roomId));
    }

    @GetMapping("/{roomId}/images/{fileName:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable Long roomId, @PathVariable String fileName) throws IOException {
        Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            throw new ResourceNotFoundException("File not found");
        }

        String contentType = Files.probeContentType(filePath);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROOM_MANAGE')")
    public ResponseEntity<ApiResponse<Room>> create(@RequestBody CreateRoomRequest request) {
        return ResponseEntity.ok(roomService.create(request));
    }

    @PostMapping("/{roomId}/images")
    @PreAuthorize("hasAuthority('ROOM_MANAGE')")
    public ResponseEntity<ApiResponse<RoomImage>> addImageToRoom(@PathVariable Long roomId,
                                                                 @RequestParam("file") MultipartFile image) {
        try {
            return ResponseEntity.ok(roomService.saveImage(roomId, image));
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROOM_MANAGE')")
    public ResponseEntity<ApiResponse<RoomDTO>> update(@PathVariable Long id, @RequestBody Room room) {
        return ResponseEntity.ok(roomService.update(id, room));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROOM_MANAGE')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.delete(id));
    }

    @DeleteMapping("/{roomId}/images/{imageId}")
    @PreAuthorize("hasAuthority('ROOM_MANAGE')")
    public ResponseEntity<ApiResponse<Void>> deleteRoomImage(@PathVariable Long imageId) {
        return ResponseEntity.ok(roomService.deleteImage(imageId));
    }
}
