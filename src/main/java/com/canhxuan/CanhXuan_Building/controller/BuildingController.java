package com.canhxuan.CanhXuan_Building.controller;

import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.entity.Building;
import com.canhxuan.CanhXuan_Building.entity.BuildingImage;
import com.canhxuan.CanhXuan_Building.service.BuildingImageService;
import com.canhxuan.CanhXuan_Building.service.BuildingService;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/canhxuan/buildings")
public class BuildingController {

    private String uploadDir = "uploads/images/building/";

    private final BuildingService  buildingService;

    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Building>>> getAll(@RequestParam (defaultValue = "0") int page) {
        return ResponseEntity.ok(buildingService.getAll(page));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Building>>> getByName(@RequestParam String name) {
        return ResponseEntity.ok(buildingService.getByName(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Building>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(buildingService.getById(id));
    }

    @GetMapping("/{buildingId}/images")
    public ResponseEntity<ApiResponse<List<BuildingImage>>> findByBuildingId(@PathVariable Long buildingId) {
        return ResponseEntity.ok(buildingService.findByBuildingId(buildingId));
    }

    @GetMapping("{buildingId}/images/{fileName:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable String fileName) throws IOException {
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
    public ResponseEntity<ApiResponse<Building>> create(@RequestBody Building building) {
        return ResponseEntity.ok(buildingService.create(building));
    }

    @PostMapping("/{buildingId}/images")
    public ResponseEntity<ApiResponse<BuildingImage>> addImageToBuilding(@PathVariable Long buildingId,
                                                                         @RequestParam("file") MultipartFile image) throws IOException {
        try {
            return ResponseEntity.ok(buildingService.saveImage(buildingId, image));
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Building>> update(@PathVariable Long id, @RequestBody Building building) {
        return ResponseEntity.ok(buildingService.update(id, building));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(buildingService.delete(id));
    }

    @DeleteMapping("/{buildingId}/images/{imageId}")
    public ResponseEntity<ApiResponse<Void>> deleteBuildingImage(@PathVariable Long imageId) {
        return ResponseEntity.ok(buildingService.deleteImage(imageId));
    }
}
