package com.canhxuan.CanhXuan_Building.entity;

import com.canhxuan.CanhXuan_Building.dto.response.DashboardDto;
import com.canhxuan.CanhXuan_Building.dto.response.InvoiceDashboardDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "buildings")
@Getter
@Setter
@SqlResultSetMapping(
        name = "DashboardMapping",
        classes = @ConstructorResult(
                targetClass = DashboardDto.class,
                columns = {
                        @ColumnResult(name = "totalBuildings", type = Long.class),
                        @ColumnResult(name = "totalRooms", type = Long.class),
                        @ColumnResult(name = "emptyRooms", type = Long.class),
                        @ColumnResult(name = "rentedRooms", type = Long.class),
                        @ColumnResult(name = "totalCustomers", type = Long.class),
                        @ColumnResult(name = "activeContracts", type = Long.class)
                }
        )
)

@NamedEntityGraph(
        name = "Building.images",
        attributeNodes = @NamedAttributeNode("images")
)
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "Building name is required")
    @Size(min = 3, max = 100, message = "Building name must be between 3 and 100 characters")
    String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    String description;

    @NotBlank(message = "Address is required")
    @Size(max = 200, message = "Address must not exceed 200 characters")
    String address;

    @NotNull(message = "Number of floors is required")
    @Min(value = 1, message = "Number of floors must be at least 1")
    @Max(value = 100, message = "Number of floors must not exceed 100")
    Integer floors;

    @NotNull(message = "Number of rooms is required")
    @Min(value = 1, message = "Number of rooms must be at least 1")
    Integer rooms;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 25)
    List<BuildingImage> images = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "building", orphanRemoval = true)
    @JsonIgnoreProperties("building")
    List<Room> roomList = new ArrayList<>();

}
