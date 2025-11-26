package com.canhxuan.CanhXuan_Building.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@NamedEntityGraph(
        name = "Room.full",
        attributeNodes = {
                @NamedAttributeNode("images"),
                @NamedAttributeNode("building")
        })
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "Room name is required")
    @Size(min = 1, max = 50, message = "Room name must be between 1 and 50 characters")
    @Column(unique = true)
    String name;

    @Min(value = 1, message = "Floor number must be at least 1")
    @Max(value = 100, message = "Floor number must not exceed 100")
    Integer floor;

    @Min(value = 1, message = "Capacity must be at least 1")
    @Max(value = 9, message = "Capacity must not exceed 9")
    Integer capacity;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    Double price;

    @Enumerated(EnumType.STRING)
    RoomStatus status;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    List<RoomImage> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", nullable = false)
    @JsonIgnoreProperties("roomList")
    Building building;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<Contract> contracts = new ArrayList<>();
}
