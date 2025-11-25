package com.canhxuan.CanhXuan_Building.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse <T>{
    boolean success = false;
    String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<String> errors;
    T data;
}
